/**
 * Author: Marco Gren Matrikelnummer: 19745 Umgebung: IntelliJ, JDK 17, Windows 11 #
 * Letzte Änderung:
 * 12.11.2022
 * Aufgabe : LPSW, WS2022/2023, Aufgabenblatt 3, Aufgabe 6
 **/
package SoftwareLabor.Ausgabe;

import SoftwareLabor.BibTex.ImportBibTex;
import SoftwareLabor.Medien.Medium;
import SoftwareLabor.Zettelkasten;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class BibTexPersistency implements Persistency, Serializable {

  @Override
  public void save(String _dateiname,
      Zettelkasten _zk) {
    try {
      PrintWriter out = new PrintWriter(_dateiname, StandardCharsets.UTF_8);
      for (Medium medium : _zk) {
        out.write(medium.calculateBibRepresentation()+"\n");
      }
      out.flush();
      out.close();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

  }

  @Override
  public Zettelkasten load(String _dateiname) {
    String Read;
    Zettelkasten importierterZettelkasten=new Zettelkasten();
    ArrayList<Medium> importierteMedien= new ArrayList<>();
    try{
      BufferedReader buffRead = new BufferedReader(new FileReader(_dateiname));
      while((Read = buffRead.readLine()) != null){
        importierteMedien.add(ImportBibTex.parseBibTex(Read));
      }
      for(Medium medium:importierteMedien){
        importierterZettelkasten.addMedium(medium);
      }
      return importierterZettelkasten;
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
  }
