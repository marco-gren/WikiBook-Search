/**
 * Author: Marco Gren
 * Matrikelnummer: 19745
 * Umgebung: IntelliJ, JDK 17, Windows 11
 * Letzte Änderung: 03.12.2022
 * Aufgabe : LPSW, WS2022/2023, Aufgabenblatt4, Aufgabe 5,6,7
 **/
package SoftwareLabor.XML;
import SoftwareLabor.Medien.Medium;
import SoftwareLabor.Medien.WikiBuch;
import SoftwareLabor.Zettelkasten;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) { // Konfiguriert mit Java_Standard c
        Zettelkasten zettelkasten=  new Zettelkasten();
        ArrayList<WikiBuch> wikiBuchArrayList = WikibooksMediumCreater.parseXML2WikiBuch(args);
        //Hinzufügen der Medien aus dem WikibuchArraylist
        for (WikiBuch s:wikiBuchArrayList) {
            zettelkasten.addMedium(s);
        }//Ausgeben der Medien
        for(Medium medium : zettelkasten){
            System.out.println(medium.calculateRepresentation());
        }
    }
}
