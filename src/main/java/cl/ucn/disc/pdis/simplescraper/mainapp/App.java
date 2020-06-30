package cl.ucn.disc.pdis.simplescraper.mainapp;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
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

            // Build the URL to check.
            StringBuilder actualUrl = new StringBuilder();
            actualUrl.append(url).append(i);
            Document document = Jsoup.connect(actualUrl.toString()).get();

            // The id to database.
            id = i;

            // Verify the index value.
            nombre = document.getElementById("lblNombre").text();

            if(!nombre.isEmpty()) {

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
                        .append(nombre)
                        .append(cargo)
                        .append(unidad)
                        .append(email)
                        .append(telefono)
                        .append(oficina)
                        .append(direccion);

                log.debug("New identified: {}", newFunctionary.toString());

                // Time to wait not to do DDoS.
                try {
                    Thread.sleep(1000 + random.nextInt(1000));

                } catch (InterruptedException e) {
                    log.error("Thread is interrupted either before or during the activity. Details: {}", e);
                }
            }

        }

    }
}
