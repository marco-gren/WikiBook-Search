/**
 * Author: Marco Gren
 * Matrikelnummer: 19745
 * Umgebung: IntelliJ,JDK 17, Windows 11
 * Letzte Änderung: 12.11.2022
 * Aufgabe : LPSW, WS2022/2023, Aufgabenblatt1, Aufgabe 11
 **/
package SoftwareLabor.Medien;

import java.io.Serializable;

public class CD extends Medium implements Serializable {

  private String label, kuenstler;

  public CD(String _label, String _kuenstler, String _title) {
    super(_title);
    setKuenstler(_kuenstler);
    setLabel(_label);


  }

  public void setKuenstler(String _kuenstler) {

    this.kuenstler = _kuenstler;
  }

  public void setLabel(String _label) {

    this.label = _label;
  }

  public String getLabel() {
    return label;
  }

  public String getKuenstler() {
    return kuenstler;
  }
  /**
   * Abstrakte Methode aus Medium die für die Ausgabe der Daten von dem Medium CD
   *
   **/
  @Override
  public String calculateRepresentation() {
    StringBuilder builder = new StringBuilder();
    builder.append("Titel: ").append(getTitel());
    builder.append("\nLabel: ").append(getLabel());
    builder.append("\nKuenstler: ").append(getKuenstler());
    return builder.toString();
  }
  /**
   * Abstrakte Methode aus Medium die für die Ausgabe der Daten in BibTex von dem Medium CD
   *
   **/
  @Override
  public String calculateBibRepresentation(){
    StringBuilder builder =new StringBuilder();
    builder.append("@cd{");
    builder.append("title = {"+getTitel()+"}, ");
    builder.append("artist = {"+getKuenstler()+"}, ");
    builder.append("label = {"+getLabel()+"}").append("}");
    return builder.toString();
  }
}
