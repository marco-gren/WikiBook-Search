/**
 * Author: Marco Gren Matrikelnummer: 19745 Umgebung: IntelliJ,JDK 17, Windows 11 Letzte Änderung:
 * 12.11.2022 Aufgabe : LPSW, WS2022/2023, Aufgabenblatt3
 **/
package SoftwareLabor;


import SoftwareLabor.Ausgabe.BibTexPersistency;
import SoftwareLabor.Ausgabe.BinaryPersistency;
import SoftwareLabor.Ausgabe.HumanReadablePersistency;
import SoftwareLabor.Ausgabe.Persistency;
import SoftwareLabor.Medien.Buch;
import SoftwareLabor.Medien.CD;
import SoftwareLabor.Medien.ElektronischesMedium;
import SoftwareLabor.Medien.Medium;
import SoftwareLabor.Medien.Zeitschrift;

public class Bibliothek {

  /**
   * In der main-Methode wird eine Beispielausgabe gespeichert und mit calculateRepresentation in
   * der Konsole ausgeben
   **/
  public static void main(String[] args) {
    Zettelkasten zettelkasten = new Zettelkasten();
    zettelkasten.addMedium(new CD("Apple (Bea (EMI))", "The Beatles", "Der Spiegel"));
    zettelkasten.addMedium(
        new Buch(2004, "Bibliographisches Institut, Mannheim", "-", "3-411-04013-0",
            "Duden 01. Die deutsche Rechtschreibung"));
    zettelkasten.addMedium(new CD("Apple (Bea (EMI))", "The Beatles", "1"));
    zettelkasten.addMedium(new Zeitschrift("0038-7452", 54, 6, "Der Spiegel"));
    zettelkasten.addMedium(new Zeitschrift("0038-7452", 54, 6, "Der Spiegel"));
    zettelkasten.addMedium(new ElektronischesMedium("http://www.hochschule-stralsund.de",
        "Hochschule Stralsund"));

    for (Medium medium : zettelkasten) {
      System.out.println(medium.calculateRepresentation());

    }
    System.out.println("\n");
    zettelkasten.sort("absteigend");
    for (Medium medium : zettelkasten) {
      System.out.println(medium.calculateRepresentation());


    }
    System.out.println("\n");
    zettelkasten.sort("aufsteigend");
    for (Medium medium : zettelkasten) {
      System.out.println(medium.calculateRepresentation());

    }
    System.out.println("\n");

    Persistency hu = new HumanReadablePersistency();
    hu.save("human", zettelkasten);
    hu = new BinaryPersistency();
    hu.save("binary", zettelkasten);
    hu.load("binary");
    hu = new BibTexPersistency();
    hu.save("bibtex", zettelkasten);
    zettelkasten = hu.load("bibtex");

    for (Medium medium : zettelkasten) {
      System.out.println(medium.calculateRepresentation());

    }
    System.out.println("\n");
    zettelkasten.sort("absteigend");
    for (Medium medium : zettelkasten) {
      System.out.println(medium.calculateRepresentation());


    }
    System.out.println("\n");
  }


}


