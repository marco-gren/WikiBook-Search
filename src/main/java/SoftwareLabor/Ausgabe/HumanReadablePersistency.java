/**
 * Author: Marco Gren Matrikelnummer: 19745 Umgebung: IntelliJ, JDK 17, Windows 11 #
 * Letzte Änderung:
 * 04.11.2022
 * Aufgabe : LPSW, WS2022/2023, Aufgabenblatt 3, Aufgabe 6
 **/
package SoftwareLabor.Ausgabe;

import SoftwareLabor.Medien.Medium;
import SoftwareLabor.Zettelkasten;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;

public class HumanReadablePersistency implements Persistency, Serializable {

  @Override
  public void save(String _dateiname,
      Zettelkasten _zk) {
    try {
      PrintWriter out = new PrintWriter(_dateiname, StandardCharsets.UTF_8);
      for (Medium medium : _zk) {
        out.write(medium.calculateRepresentation() + "\n" + "" + "\n");
      }
      out.flush();
      out.close();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

  }

  @Override
  public Zettelkasten load(String _dateiname) {
    throw new UnsupportedOperationException("Diese Funktion wird noch nicht unterstützt");
  }
}
