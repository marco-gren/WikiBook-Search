/**
 * Author: Marco Gren Matrikelnummer: 19745 Umgebung: IntelliJ, JDK 17, Windows 11 #
 * Letzte Änderung:
 * 04.11.2022
 * Aufgabe : LPSW, WS2022/2023, Aufgabenblatt3, Aufgabe 6
 **/
package SoftwareLabor.Ausgabe;

import SoftwareLabor.Zettelkasten;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class BinaryPersistency implements Persistency, Serializable {

  @Override
  public void save(String _dateiname, Zettelkasten _zk) {
    try (
      FileOutputStream fos =new FileOutputStream(_dateiname)){
        ObjectOutputStream oos=new ObjectOutputStream(fos);
        oos.writeObject(_zk);
      } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }



  @Override
  public Zettelkasten load(String _dateiname) {
    try(FileInputStream fis = new FileInputStream(_dateiname)){
      ObjectInputStream ois= new ObjectInputStream(fis);
      return (Zettelkasten) ois.readObject();
    } catch (IOException | ClassNotFoundException e) {
      throw new RuntimeException(e);
    }
  }
}
