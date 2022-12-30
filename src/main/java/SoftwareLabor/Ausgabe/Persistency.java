/**
 * Author: Marco Gren Matrikelnummer: 19745 Umgebung: IntelliJ, JDK 17, Windows 11 #
 * Letzte Änderung:
 * 12.11.2022
 * Aufgabe : LPSW, WS2022/2023, Aufgabenblatt3, Aufgabe 6
 **/
package SoftwareLabor.Ausgabe;


import SoftwareLabor.Zettelkasten;


public interface Persistency {

  void save(String _dateiname, Zettelkasten _zk);
  Zettelkasten load(String _dateiname);
}
