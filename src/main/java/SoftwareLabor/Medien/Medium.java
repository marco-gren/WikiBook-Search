/**
 * Author: Marco Gren Matrikelnummer: 19745 Umgebung: IntelliJ, JDK 17, Windows 11 #
 * Letzte Änderung:
 * 04.11.2022
 * Aufgabe : LPSW, WS2022/2023, Aufgabenblatt1, Aufgabe 2
 **/
package SoftwareLabor.Medien;

import java.io.Serializable;

public abstract class Medium implements Comparable, Serializable {

  private String titel;

  public Medium(String _titel) {
    setTitel(_titel);
  }

  public void setTitel(String _titel) {
    this.titel = _titel;
  }


  public String getTitel() {
    return titel;
  }

/** Die Methode compareTo vergleicht Elemente von Objekten miteinander. Das Vergleichselement hier ist der Titel. **/
  @Override
  public int compareTo(Object _object) {
    if (_object != null) {
      if (_object instanceof Medium) {
        if (this.getTitel().equals(((Medium) _object).getTitel())) {
          //Bei gleichen Titeln werden die Medien gegeneinander geprüft
          if(_object instanceof Buch && this instanceof CD){
            return 1;
          }
          if(_object instanceof Buch && this instanceof ElektronischesMedium){
            return 1;
          }
          if(_object instanceof Buch && this instanceof Zeitschrift){
            return 1;
          }
          if(_object instanceof CD && this instanceof ElektronischesMedium){
            return 1;
          }
          if(_object instanceof CD && this instanceof Zeitschrift){
            return 1;
          }
          if(_object instanceof CD && this instanceof Buch){
            return -1;
          }
          if(_object instanceof ElektronischesMedium && this instanceof Buch){
            return -1;
          }
          if(_object instanceof ElektronischesMedium && this instanceof Zeitschrift){
            return 1;
          }
          if(_object instanceof ElektronischesMedium && this instanceof CD){
            return -1;
          }
          if(_object instanceof Zeitschrift && this instanceof CD){
            return -1;
          }
          if(_object instanceof Zeitschrift && this instanceof Buch){
            return -1;
          }
          if(_object instanceof Zeitschrift && this instanceof ElektronischesMedium){
            return -1;
          }

          return 0;
        }

        if((this.getTitel()).compareToIgnoreCase((((Medium) _object).getTitel()))>0){

          return 1;
        }
        else{

          return -1;
        }


      } else {
        throw new IllegalArgumentException(
            "Beim Vergleichen wurde Objekt: \"" + _object + "\" nicht als Medium erkannt.");
      }
    }
    throw new IllegalArgumentException("Beim Vergleichen wurde ein null entdeckt.");
  }

  public abstract String calculateRepresentation();
  public abstract String calculateBibRepresentation();

}
