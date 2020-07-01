/*
 * Copyright (c) 2020. Charlie Condorcet Engineering Student
 *
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License 2.0 which is available at
 *  http://www.eclipse.org/legal/epl-2.0, or the Apache License, Version 2.0
 *  which is available at https://www.apache.org/licenses/LICENSE-2.0.
 *
 *  SPDX-License-Identifier: EPL-2.0 OR Apache-2.0
 */

package cl.ucn.disc.pdis.simplescraper.mainapp;

import cl.ucn.disc.pdis.simplescraper.model.Functionary;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Random;

/**
 * Principal App to do scraping.
 *
 * @author Charlie Condorect.
 */
public class App {

    /**
     * The Logger
     */
    private static final Logger log = LoggerFactory.getLogger(App.class);

    /**
     * Principal Main class.
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException, SQLException {

        /*
        El "cod" o el numero id al final de la url, en primer semestre de 2020, no sobrepasa los 29800.
        Esto ultimo verificado buscando ultimos profesores agregados este semestre con id 29600 aprox.
        */


        /**
         * Database configuration.
         */
        /*
        // Use slite database to replace h2.
        String databaseURL = "jdbc:sqlite:functionarydb.db";
        // Create a connection source to our database.
        ConnectionSource connectionSource = new JdbcConnectionSource(databaseURL);
        // Instance the DAO.
        Dao<Functionary, String> functionaryDao = DaoManager.createDao(connectionSource, Functionary.class);
        // if you need to create the ’accounts’ table make this call.
        TableUtils.createTableIfNotExists(connectionSource, Functionary.class);
        /*

        /**
         * Auxiliaries.
         */
        int id = 0;
        int maxCod = 100;
        String url = "http://online.ucn.cl/directoriotelefonicoemail/fichaGenerica/?cod=";
        PrintWriter printWriter = new PrintWriter("records.txt", "UTF-8");

        /**
         * Random variable to interleave time.
         */
        Random random = new Random();

        /**
         * Data extracted from telephone directory.
         */
        String nombre = "";
        String cargo = "";
        String unidad = "";
        String email = "";
        String telefono = "";
        String oficina = "";
        String direccion = "";

        log.info("Initialization of scraping..");

        for (int i = 0; i < maxCod; i++) {

            // Build the URL to check the data.
            StringBuilder actualUrl = new StringBuilder();
            actualUrl.append(url).append(i);
            Document document = Jsoup.connect(actualUrl.toString()).get();

            // Verify the index value.
            nombre = document.getElementById("lblNombre").text();

            if (!nombre.isEmpty()) {

                // Get variables from URL.
                cargo = document.getElementById("lblCargo").text();
                unidad = document.getElementById("lblUnidad").text();
                email = document.getElementById("lblEmail").text();

                telefono = document.getElementById("lblTelefono").text();
                // Formate to fone number.
                telefono = telefono.substring(5, telefono.length());

                oficina = document.getElementById("lblOficina").text();
                direccion = document.getElementById("lblDireccion").text();

                if (direccion.length() == 0) {
                    log.debug("VACIOS");
                }

                // Concatenation of Functionary data.
                StringBuilder sbFunctionary = new StringBuilder();
                sbFunctionary.append(id).append(",")
                        .append(nombre).append(",")
                        .append(cargo).append(",")
                        .append(unidad).append(",")
                        .append(email).append(",")
                        .append(telefono).append(",")
                        .append(oficina).append(",")
                        .append(direccion);

                log.debug("New identified: {}", sbFunctionary.toString());

                // Add new valid functionary to csv file.
                printWriter.println(sbFunctionary.toString());

                formatToDatabase(nombre,
                        cargo,
                        unidad,
                        email,
                        telefono,
                        oficina,
                        direccion);

                /*
                // Add new valid functionary to database.
                Functionary functionary = new Functionary(nombre,
                        cargo,
                        unidad,
                        email,
                        telefono,
                        oficina,
                        direccion);

                // Duplicaded Functionaries.
                try {
                    functionaryDao.createIfNotExists(functionary);

                } catch (SQLException e) {
                    log.error("New Functionary {} no added. Details: {}", nombre, e.getMessage());
                }
                 */

                // Time to wait not to do DDoS.
                try {
                    Thread.sleep(1000 + random.nextInt(1000));

                } catch (InterruptedException e) {
                    log.error("Thread is interrupted either before or during the activity. Details: {}", e.getMessage());
                }

                // ID real to csv file.
                id++;
            }
        }

        // End of record insertion.
        printWriter.close();
        //connectionSource.close();
        log.info("End of insertions.");

        /*
        Thanks to
        ORM Lite documentation https://ormlite.com/javadoc/ormlite-core/doc-files/ormlite_2.html#Using
        Save scv in txt http://decodigo.com/java-crear-archivos-de-texto
         */
    }

    /**
     * Formater to save data in database.
     *
     * @param nombre
     * @param cargo
     * @param unidad
     * @param email
     * @param telefono
     * @param oficina
     * @param direccion
     * @return
     * @throws SQLException
     * @throws IOException
     */
    public static boolean formatToDatabase(String nombre, String cargo, String unidad, String email, String telefono,
                                         String oficina, String direccion) throws SQLException, IOException {

        /**
         * Database configuration.
         */
        // Use slite database to replace h2.
        String databaseURL = "jdbc:sqlite:functionarydb.db";
        // Create a connection source to our database.
        ConnectionSource connectionSource = new JdbcConnectionSource(databaseURL);
        // Instance the DAO.
        Dao<Functionary, String> functionaryDao = DaoManager.createDao(connectionSource, Functionary.class);
        // if you need to create the ’accounts’ table make this call.
        TableUtils.createTableIfNotExists(connectionSource, Functionary.class);

        // Save variables like null if is empty.
        cargo = EmptyToNUll(cargo);
        unidad = EmptyToNUll(unidad);
        email = EmptyToNUll(email);
        telefono = EmptyToNUll(telefono);
        oficina = EmptyToNUll(oficina);
        direccion = EmptyToNUll(direccion);

        // Add new valid functionary to database.
        Functionary functionary = new Functionary(nombre,
                cargo,
                unidad,
                email,
                telefono,
                oficina,
                direccion);

        // Duplicaded Functionaries.
        try {
            functionaryDao.createIfNotExists(functionary);

        } catch (SQLException e) {
            log.error("New Functionary {} no added. Details: {}", nombre, e.getMessage());
            return false;
        }

        connectionSource.close();
        return true;

    }

    public static String EmptyToNUll(String var) {
        var = var.isEmpty() ? null : var;
        return var;
    }


}
