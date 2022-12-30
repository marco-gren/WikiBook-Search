/**
 * Author: Marco Gren Matrikelnummer: 19745 Umgebung: IntelliJ, JDK 17, Windows 11 Letzte Änderung:
 * 03.12.2022 Aufgabe : LPSW, WS2022/2023, Aufgabenblatt4, Aufgabe 5,6,7 Funktion: Die Klasse
 * speichert die Kapitel und ihre Nummer in einer Arraylist.
 **/
package SoftwareLabor.XML;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class getKapitel extends DefaultHandler {

  private static ArrayList<String[]> kapitel;
  private static String link;

  /**
   * Die Methode extractKapitel erstellt eine Arraylist mit Stringarrays, die Stringarrays
   * beinhalten das ein Element vom Inhaltsverzeichnis und die dazugehörige Nummer des Elements.
   * Die Methode wird nur von parseXML2Wikibuch aufgerufen, um für die Wikibuch die Kapitel zu erhalten.
   **/
  public static ArrayList<String[]> extractKapitel(String _suchbegriff) {
    kapitel = new ArrayList<>(); //Erstellt für jeden Suchbegriff eine neue Arraylist
    // Link für die Kapitel mit ihren Nummern
    link = "https://de.wikibooks.org/w/api.php?action=parse&format=xml&prop=sections&page="
        + _suchbegriff + "&redirect";
    DefaultHandler handler = new CountHandler();
    SAXParserFactory factory = SAXParserFactory.newInstance();
    factory.setNamespaceAware(true);// auf Namespace achten
    try {
      URL url = new URL(link);
      URLConnection connection = url.openConnection();
      connection.setDoInput(true);//Setzen der Streamrichtung
      InputStream inStream = connection.getInputStream();
      SAXParser parser = factory.newSAXParser();
      parser.parse(inStream, handler);
    } catch (IOException | ParserConfigurationException | SAXException e) {
      throw new RuntimeException("Fehler bei dem laden von \"" + link
          + "\". Bitte überprüfen Sie Ihre Eingabe und Internetverbindung. ");
    }
    return kapitel;
  }

  static class CountHandler extends DefaultHandler {

    private final StringBuilder currentValue = new StringBuilder();

    public void characters(char[] ch, int start, int length) {
      currentValue.append(ch, start, length);
    }

    // Methode wird aufgerufen, wenn der Parser zu einem Start-Tag kommt
    public void startElement(String uri, String localName, String qName, Attributes atts) {
      currentValue.setLength(0);
      String[] temp = new String[2];// Array für die Strings, so bleibt die Position und der Inhalt zusammen übergeben werden
      if (localName.equals("s")) {
        temp[0] = atts.getValue("number");//Nummer vom Element der Inhaltsangabe
        temp[1] = atts.getValue("line");//Element der Inhaltsangabe
        kapitel.add(temp);
      }

    }

  }
}



