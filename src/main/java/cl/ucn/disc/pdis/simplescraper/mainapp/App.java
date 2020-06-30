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

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

public class App {

    /**
     * The Logger
     */
    private static final Logger log = LoggerFactory.getLogger(App.class);

    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {

        /*
        No se captan las tildes o ñ's.
        El "cod" o el numero id al final de la url, en primer semestre de 2020, no sobrepasa los 29800.
        Esto ultimo verificado buscando ultimos profesores agregados este semestre con id 29600 aprox.
        */

        /**
         * Auxiliaries.
         */
        int id = 0;
        int maxCod = 100;
        String url = "http://online.ucn.cl/directoriotelefonicoemail/fichaGenerica/?cod=";

        // Method from http://decodigo.com/java-crear-archivos-de-texto
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

                // The id to database.
                id = i;

                // Get variables from URL.
                cargo = document.getElementById("lblCargo").text();
                unidad = document.getElementById("lblUnidad").text();
                email = document.getElementById("lblEmail").text();
                telefono = document.getElementById("lblTelefono").text();
                oficina = document.getElementById("lblOficina").text();
                direccion = document.getElementById("lblDireccion").text();

                // Concatenation of Functionary data.
                StringBuilder newFunctionary = new StringBuilder();
                newFunctionary.append(id)
                        .append(",")
                        .append(nombre)
                        .append(",")
                        .append(cargo)
                        .append(",")
                        .append(unidad)
                        .append(",")
                        .append(email)
                        .append(",")
                        .append(telefono)
                        .append(",")
                        .append(oficina)
                        .append(",")
                        .append(direccion);

                // Add new valid functionary to csv file.
                printWriter.println(newFunctionary.toString());

                log.debug("New identified: {}", newFunctionary.toString());

                // Time to wait not to do DDoS.
                try {
                    Thread.sleep(1000 + random.nextInt(1000));

                } catch (InterruptedException e) {
                    log.error("Thread is interrupted either before or during the activity. Details: {}", e);
                }
            }
        }

        // End of record insertion.
        printWriter.close();
        log.info("End of insertions.");

    }
}
