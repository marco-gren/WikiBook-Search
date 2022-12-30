/**
 * Author: Marco Gren
 * Matrikelnummer: 19745
 * Umgebung: IntelliJ, JDK 17, Windows 11
 * Letzte Änderung: 30.12.2022
 * Aufgabe : LPSW, WS2022/2023, Aufgabenblatt5
 * Diese Klasse wurde aus dem Aufgabenblatt übernommen
 **/
package wikibooksWebView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
public class Syn2 {
  public static ArrayList<String> findSynonyme(String _synonymeString) throws ParseException, IOException {
    String BasisUrl = "http://api.corpora.uni-leipzig.de/ws/similarity/";
    String Corpus = "deu_news_2012_1M";
    String Anfragetyp = "/coocsim/";
    String Suchbegriff = _synonymeString;
    String Parameter = "?minSim=0.1&limit=32";
    ArrayList<String> synonyme=new ArrayList<>();
    URL myURL;
    myURL = new URL(BasisUrl + Corpus + Anfragetyp + Suchbegriff + Parameter);
    JSONParser jsonParser = new JSONParser();
    String jsonResponse = streamToString(myURL.openStream());
// den gelieferten String in ein Array parsen
    JSONArray jsonResponseArr = (JSONArray) jsonParser.parse(jsonResponse);
// das erste Element in diesem Array
    if(jsonResponseArr.size()>0){
    JSONObject firstEntry = (JSONObject) jsonResponseArr.get(0);
// aus dem Element den Container für das Synonym beschaffen
    JSONObject wordContainer = (JSONObject) firstEntry.get("word");
// aus dem Container das Synonym beschaffen
    String synonym = (String) wordContainer.get("word");
// alle abgefragten Synonyme
    for (Object el : jsonResponseArr) {
      wordContainer = (JSONObject) ((JSONObject) el).get("word");
      synonym = (String) wordContainer.get("word");
      synonyme.add(synonym);
    }return synonyme;
  }return null;}
/**
 * Reads InputStream's contents into String
 *
 * @param is - the InputStream
 * @return String representing is' contents
 * @throws IOException
 */

  public static String streamToString(InputStream is) throws IOException {
    String result = "";
// other options: https://stackoverflow.com/questions/309424/read-convert-an-inputstream-to-a-string
    try (Scanner s = new Scanner(is)) {
      s.useDelimiter("\\A");
      result = s.hasNext() ? s.next() : "";
      is.close();
    }
    return result;
  }
}