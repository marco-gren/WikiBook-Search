/**
 * Author: Marco Gren
 * Matrikelnummer: 19745
 * Umgebung: IntelliJ, JDK 17, Windows 11
 * Letzte Änderung: 12.11.2022
 * Aufgabe : LPSW, WS2022/2023, Aufgabenblatt1, Aufgabe 11
 **/
package SoftwareLabor.Medien;

import java.io.Serializable;

public class Zeitschrift extends Medium implements Serializable {

  private String issn;
  private int volume, nummer;

  public Zeitschrift(String _issn, int _volume, int _nummer, String _title) {
    super(_title);
    setIssn(_issn);
    setNummer(_nummer);
    setVolume(_volume);

  }

  public int getNummer() {
    return nummer;
  }

  public String getIssn() {
    return issn;
  }

  public int getVolume() {
    return volume;
  }

  public void setIssn(String _issn) {

    this.issn = _issn;}
  public void setNummer(int _nummer) {
    if(_nummer < 0){
      throw new IllegalArgumentException("Nummer von der Zeitschrift darf nicht negativ sein. Bitte Eingabe überprüfen!");
    }
    this.nummer = _nummer;
  }

  public void setVolume(int _volume) {
    if(_volume < 0){
      throw new IllegalArgumentException("Volume von der Zeitschrift darf nicht negativ sein. Bitte Eingabe überprüfen!");
    }

    this.volume = _volume;
  }
  /**
   * Abstrakte Methode aus Medium die für die Ausgabe der Daten von dem Medium Zeitschrift
   *
   **/
  @Override
  public String calculateRepresentation() {
    StringBuilder builder = new StringBuilder();
    builder.append("Titel: ").append(getTitel());
    builder.append("\nISSN: ").append(getIssn());
    builder.append("\nVolume: ").append(getVolume());
    builder.append("\nNummer: ").append(getNummer());
    return builder.toString();
  }
  /**
   * Abstrakte Methode aus Medium die für die Ausgabe der Daten in BibTex von dem Medium Zeitschrift
   *
   **/
  @Override
  public String calculateBibRepresentation(){
    StringBuilder builder =new StringBuilder();
    builder.append("@journal{");
    builder.append("title = {"+getTitel()+"}, ");
    builder.append("issn = {"+getIssn()+"}, ");
    builder.append("volume = "+getVolume()+", ");
    builder.append("number = "+getNummer()).append("}");
    return builder.toString();
  }
}
