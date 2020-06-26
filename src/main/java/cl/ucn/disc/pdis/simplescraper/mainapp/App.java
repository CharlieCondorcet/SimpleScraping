package cl.ucn.disc.pdis.simplescraper.mainapp;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import java.io.IOException;

public class App {

    public static void main(String[] args) throws IOException {

        Document document = Jsoup.connect("http://online.ucn.cl/directoriotelefonicoemail/fichaGenerica/?cod=29569").get();

        // No se captan las tildes o ñ's.
        Element Nombre = document.getElementById("lblNombre");
        System.out.println("El nombre: " + Nombre.text());
    }
}
