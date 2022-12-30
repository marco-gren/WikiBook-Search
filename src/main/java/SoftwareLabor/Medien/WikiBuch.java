/**
 * Author: Marco Gren
 * Matrikelnummer: 19745
 * Umgebung: IntelliJ,JDK 17, Windows 11
 * Letzte Änderung: 03.12.2022
 * Aufgabe : LPSW, WS2022/2023, Aufgabenblatt4, Aufgabe 5,6,7
 **/
package SoftwareLabor.Medien;

import java.net.URL;
import java.util.ArrayList;

public class WikiBuch extends ElektronischesMedium {

  private String reTitle;
  private String timestamp;

  private ArrayList<String[]> kapitel = new ArrayList<>();
  private String username, ip, url,  title;
private ArrayList<String> regale=new ArrayList<>();
  public WikiBuch(String _url, String _title) {
    super(_url, _title);
    setUrl(_url);
  }

  public void setUrl(String _url) {
    if (checkURL(_url)) {
      this.url = _url;
    } else {
      throw new IllegalArgumentException("Keine gültige URL");
    }

  }

  public String getUrl() {
    return url;
  }

  public static boolean checkURL(String _urlString) {
    try {
      URL url = new URL(_urlString);
      url.toURI();
      return true;
    } catch (Exception exception) {
      return false;
    }
  }

  public String getTitle() {
    return title;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String _username) {
    this.username = _username;
  }

  public String getIp() {
    return ip;
  }

  public void setIp(String _ip) {
    this.ip = _ip;
  }

  public String getTimestamp() {

    return timestamp;
  }

  public void setTimestamp(String _timestamp) {
    this.timestamp = _timestamp;
  }

  public String getReTitle() {
    return reTitle;
  }

  public void setReTitle(String _reTitle) {
    this.reTitle = _reTitle;
  }

  public ArrayList<String[]> getKapitel() {
    return kapitel;
  }

  public void setKapitel(ArrayList<String[]> kapitel) {
    this.kapitel = kapitel;
  }

  public ArrayList<String> getRegal() {
    return regale;
  }

  public void setRegal(ArrayList<String> _regal) {
    this.regale = _regal;
  }


  /**
   * Abstrakte Methode aus Medium die für die Ausgabe der Daten von dem Medium Wikibuch
   *
   *
   **/
  @Override
  public String calculateRepresentation() {
    StringBuilder builder = new StringBuilder();
    builder.append("Titel: ").append(getTitel());
    builder.append("\nURL: ").append(getUrl());
    builder.append("\nRegal: ");
    for (int a = 0 ; a< getRegal().size();a++){
      builder.append(getRegal().get(a)).append(" ");
    }
    builder.append("\nKapitel:\n");
    for (int i = 0; i < getKapitel().size(); i++) {
        builder.append(getKapitel().get(i)[0]).append(" ").append(getKapitel().get(i)[1])
            .append("\n");
      }
    builder.append("Letzte Änderung: ").append(getTimestamp()).append("\n");
    if(getUsername()!=null){
    builder.append("Urheber: ").append(getUsername()).append("\n");}
    if(getIp()!=null){
      builder.append("Urheber: ").append(getIp()).append("\n");}
    return builder.toString();
  }
}
