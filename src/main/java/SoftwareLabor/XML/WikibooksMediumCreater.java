/**
 * Author: Marco Gren Matrikelnummer: 19745 Umgebung: IntelliJ, JDK 17, Windows 11 Letzte Änderung:
 * 02.12.2022 Aufgabe : LPSW, WS2022/2023, Aufgabenblatt4, Aufgabe 5,6,7
 **/
package SoftwareLabor.XML;

import SoftwareLabor.Medien.WikiBuch;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class WikibooksMediumCreater {

  private static String redirect = "";// Wird benötigt, falls es zu einem Redirect kommt. Der String wird in die for-Schleife in parseXML2Wikibuch benötigt, um die korrekte Seite des Titels zu erhalten.
  private static String link;
  private static ArrayList<String[]> kapitel;
  private static final ArrayList<WikiBuch> wikiBuchArrayList = new ArrayList<>();

  /**
   * Die Methode parseXML2WikiBuch erstellt eine Arraylist mit Wikibuch Objekten, nachdem sie diese
   * erstellt hat, mit den übergebenen Suchbegriffen. Die Objekte können danach in einen
   * Zettelkasten hinzugefügt werden können
   **/
  public static ArrayList<WikiBuch> parseXML2WikiBuch(String[] _suchbegriffe) {
    wikiBuchArrayList.clear();
    for (int i = 0; i < _suchbegriffe.length; i++) {
      redirect = "";
      link = "https://de.wikibooks.org/wiki/Spezial:Exportieren/"
          + _suchbegriffe[i]; // Link der URL übergeben wird
      DefaultHandler handler = new CountHandler();
      SAXParserFactory factory = SAXParserFactory.newInstance();
      factory.setNamespaceAware(true);// auf Namespace achten
      kapitel = getKapitel.extractKapitel(_suchbegriffe[i]);
      try {
        URL url = new URL(link);
        URLConnection connection = url.openConnection();
        connection.setDoInput(true);
        InputStream inStream = connection.getInputStream();
        SAXParser parser = factory.newSAXParser();
        parser.parse(inStream, handler);
        //Falls es zu einem Redirect kommt wird der aktuelle Suchbegriff mit dem Redirect Titel ersetzt und die Schleife wird bei diesem Element durchlaufen
        if (!redirect.isBlank()) {
          for (int j = 0; j < _suchbegriffe.length; j++) {
            if (i == j) {
              System.out.println("Es wurde nach \"" + _suchbegriffe[i]
                  + "\" gesucht. Ihre Suche wurde weitergeleitet zu \"" + redirect
                  + "\"");// Meldung für den User, dass es zu einem Redirect gekommen ist.
              _suchbegriffe[j] = redirect;
              i -= 1;
            }
          }
        }
      } catch (IOException | ParserConfigurationException | SAXException e) {
        throw new RuntimeException("Fehler bei dem laden von \"" + link
            + "\". Bitte überprüfen Sie Ihre Eingabe und Internetverbindung. ");
      }
    }
    return wikiBuchArrayList;
  }

  static class CountHandler extends DefaultHandler {

    private WikiBuch content;
    private final StringBuilder currentValue = new StringBuilder();

    public void characters(char[] ch, int start, int length) {
      currentValue.append(ch, start, length);
    }

    // Methode wird aufgerufen, wenn der Parser zu einem Start-Tag kommt
    public void startElement(String uri, String localName, String qName, Attributes atts) {
      currentValue.setLength(0);
      // Falls die Seite ein XML Tag redirect besitzt, wird von diesem das Element title ausgelesen und als ReTitle gesetzt. Durch das Setzen wird kein Wikibuch Objekt in die Arraylist hinzugefügt.
      if (localName.equals("redirect")) {
        redirect = atts.getValue("title");
        content.setReTitle(currentValue.toString());
      }
    }

    public void endElement(String uri, String localName, String qName) {
      //Setzen des Titels und erstellen eines neuen Objektes vom Typ Wikibuch
      if (localName.equals("title")) {
        content = new WikiBuch(
            (link.substring(0, link.indexOf("/Spezial:Exportieren/")) + link.substring(
                link.indexOf("/Spezial:Exportieren/") + 20)), currentValue.toString());
      }
      //Setzen der Zeitangabe, wird als String in dem Objekt Wikibuch gespeichert

      if (localName.equals("timestamp")) {
        DateTimeFormatter dtf = DateTimeFormatter.ISO_DATE_TIME;
        ZonedDateTime zdt = ZonedDateTime.parse(currentValue.toString(), dtf);
        zdt = zdt.withZoneSameInstant(ZoneId.of("Europe/Berlin"));// Setzen der "richtigen" Zeitzone
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(
            "dd.MM.yyyy 'um' HH:mm 'Uhr'");
        String formattedString = zdt.format(formatter);
        content.setTimestamp(formattedString);
      }
      // Setzen des Usernamen
      if (localName.equals("username")) {
        content.setUsername(currentValue.toString());
      }
      //Setzen der IP
      if (localName.equals("ip")) {
        content.setIp(currentValue.toString());
      }
      // Setzen des Regales/ der Regale
      if (localName.equals("text")) {
        if (content.getReTitle() == null) {
          ArrayList<String> regale = new ArrayList<>();// Falls mehrere Regale vorhanden sind muss einen Arraylist erstellt werden um diesen Fall abzusichern.
          //Geklauter Code von Jan
          String stringContent = currentValue.toString().trim();
          // extrahiere Regal[e]
          if(stringContent.contains("{{Regal") || stringContent.contains("{{Infoleiste")); {
            String regal;
            if (stringContent.contains("{{Regalhinweis")) regal = stringContent.substring(
                stringContent.indexOf("{{Regalhinweis") + 14);
            else if(stringContent.contains("{{Regal")) regal = stringContent.substring(
                stringContent.indexOf("{{Regal") + 7);
            else regal = stringContent.substring(stringContent.indexOf("{{Infoleiste") + 12);

            char c = regal.charAt(0);
            int i;
            boolean loop = true;
            while(loop) {
              i = -1;
              while(++i < regal.length() && !Title.isValidChar(regal.charAt(i)) && regal.charAt(i) != '}');
              if(regal.charAt(i) == '}') loop = false;
              else {
                regal = regal.substring(i);
                i = -1;
                while(++i < regal.length() && (Title.isValidChar(regal.charAt(i)) || regal.charAt(i) == '='));
                if(regal.substring(0, i).length() > 2) {
                  String string = regal.substring(0, i);
                  if(string.contains("=")) string = string.substring(string.indexOf("=") + 1);
                  regale.add(string);
                }
                regal = regal.substring(i);
              }
            }
          }
          //Bis Hier alles geklaut
          content.setRegal(regale);


        }
      }
    }


    @Override
    public void endDocument() {
      // Wenn kein Titel erkannt wurde, wird kein Wikibuch erstellt. Somit lässt sich prüfen, ob die Seite gefunden wurde.
      if (content == null) {
//        throw new IllegalArgumentException(
//            "Seite nicht gefunden! Bitte überprüfen Sie Ihre Eingabe");
        return;
      }
      // Mit der Abfrage wird geprüft, ob es sich um ein Redirect handelt. Wenn dies der Fall ist, wird kein Wikibuch erstellt
      if (content.getReTitle() == null) {
        content.setKapitel(kapitel);
        wikiBuchArrayList.add(content);
      }
    }

  }
}

