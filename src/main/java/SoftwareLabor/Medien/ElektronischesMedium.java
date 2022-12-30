/**
 * Author: Marco Gren
 * Matrikelnummer: 19745
 * Umgebung: IntelliJ,JDK 17, Windows 11
 * Letzte Änderung: 07.11.2022
 * Aufgabe : LPSW, WS2022/2023, Aufgabenblatt1, Aufgabe 11
 **/
package SoftwareLabor.Medien;

import java.io.Serializable;
import java.net.URL;

public class ElektronischesMedium extends Medium implements Serializable {

  private String url;

  public ElektronischesMedium(String _url, String _title) {
    super(_title);
    this.url = _url;

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
  /**
   * Abstrakte Methode aus Medium die für die Ausgabe der Daten von dem Medium ElektronischesMedium
   *
   *
   **/
  @Override
  public String calculateRepresentation() {
    StringBuilder builder = new StringBuilder();
    builder.append("Titel: ").append(getTitel());
    builder.append("\nURL: ").append(getUrl());
    return builder.toString();
  }
  /**
   * Abstrakte Methode aus Medium die für die Ausgabe der Daten in BibTex von dem Medium ElektronischesMedium
   *
   **/
  @Override
  public String calculateBibRepresentation(){
    StringBuilder builder =new StringBuilder();
    builder.append("@elMed{");
    builder.append("title = {"+getTitel()+"}, ");
    builder.append("URL = {"+getUrl()+"}").append("}");
    return builder.toString();
  }
}
