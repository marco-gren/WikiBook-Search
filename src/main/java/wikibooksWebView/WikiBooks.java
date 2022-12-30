/**
 * Author: Marco Gren Matrikelnummer: 19745 Umgebung: IntelliJ, JDK 17, Windows 11 Letzte Änderung:
 * 30.12.2022 Aufgabe : LPSW, WS2022/2023, Aufgabenblatt5
 **/
package wikibooksWebView;


import SoftwareLabor.Medien.WikiBuch;
import SoftwareLabor.XML.WikibooksMediumCreater;
import java.util.ArrayList;

public class WikiBooks {

  /**
   * Die Methode getURL bildet aus dem _searchTerm ein wikibooks.org Link
   * @param _searchTerm
   * @return String mit der Wikibooks URL + Searchterm
   **/
  public static String getUrl(String _searchTerm) {
    _searchTerm = _searchTerm.trim();
    _searchTerm = _searchTerm.replace(' ', '_');
    return "https://de.wikibooks.org/wiki/" + _searchTerm;
  }

  /**
   * Die Methode getLabels erstellt ein temporäres Wikibuch und liest die letzte Änderung, Regal und
   * Username aus dem Wikibuch ab und speichert diese in einer
   * @param _url ist ein String
   * @return Arraylist mit Metadaten vom Buch
   **/
  public static ArrayList<String> getLabels(String _url) {
    ArrayList<String> ausgabe = new ArrayList<>();
    ArrayList<WikiBuch> temp;
    if (checkURL(_url)) {
      String urlSearchterm = _url.substring(_url.indexOf("/wiki/") + 6);
      temp = WikibooksMediumCreater.parseXML2WikiBuch(new String[]{urlSearchterm});
      if (temp.size() > 0) {
        WikiBuch wikiBuch = temp.get(0);
        ArrayList<String> regale = wikiBuch.getRegal();
        StringBuilder builder = new StringBuilder();
        for (String s : regale) {
          builder.append(s).append(" ");
        }
        ausgabe.add(builder.toString());
        ausgabe.add(wikiBuch.getUsername());
        ausgabe.add(wikiBuch.getTimestamp());
        return ausgabe;
      }
    }
    return ausgabe;
  }

  /**
   * Die Methode checkURL prüft bei dem übergebenen String ob, es sich bei einem Link mit einem Buch
   * von Wikibook handelt, indem der Link auf folgende Kriterien überprüft wird: Zeichen "/w" in
   * Kombination mit ":", die Hauptseite, Links die "search=" enthalten sind alles keine Bücher. Um
   * auch Bücher mit":" zu erkennen wird auch :_ getestet.
   * @param _url ist ein String
   * @return boolean
   **/
  public static boolean checkURL(String _url) {
    return !_url.substring(_url.indexOf("/w")).contains(":") && !_url.equals(
        "https://de.wikibooks.org/wiki/Hauptseite") && !_url.contains("search=") || _url.contains(
        ":_");
  }

  /**
   * Die Methode getBook bildet aus dem übergeben String einen Wikibooks Link mit einem Buch. Mit
   * diesem Link wird mit der Methode parseXML2WikiBuch zu ein Wikibuch Arraylist erstellt. Es
   * handelt sich jedoch nur um eine einziges Wikibuch, deswegen wird nur das erste Element aus dem
   * Array zurückgeben.
   * @param _url ist ein String
   * @return Wikibuch
   **/
  public static WikiBuch getBook(String _url) {
    ArrayList<WikiBuch> ausgabeArraylist;
    if (checkURL(_url)) {
      String urlSearchterm = _url.substring(_url.indexOf("/wiki/") + 6);
      ausgabeArraylist = WikibooksMediumCreater.parseXML2WikiBuch(new String[]{urlSearchterm});
      if (ausgabeArraylist.get(0) != null) {
        return ausgabeArraylist.get(0);
      }
    }
    System.out.println("Auf dieser Seite befindet sich kein WikiBuch");
    return null;
  }
}

