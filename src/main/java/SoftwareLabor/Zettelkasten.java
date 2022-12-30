/**
 * Author: Marco Gren Matrikelnummer: 19745 Umgebung: IntelliJ, JDK 17, Windows 11 # Letzte
 * Änderung: 03.12.2022 Aufgabe : LPSW, WS2022/2023, Aufgabenblatt3, Aufgabe 4,5,7
 **/
package SoftwareLabor;


import SoftwareLabor.Exception.duplicateEntryException;
import SoftwareLabor.Medien.Buch;
import SoftwareLabor.Medien.CD;
import SoftwareLabor.Medien.ElektronischesMedium;
import SoftwareLabor.Medien.Medium;
import SoftwareLabor.Medien.WikiBuch;
import SoftwareLabor.Medien.Zeitschrift;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Scanner;

public class Zettelkasten implements Iterable<Medium>, Serializable {

  private final ArrayList<Medium> medium;
  private int sortiert;//Integer Parameter der verwendet wird, um festzustellen, ob die Arraylist von Medium schon sortiert worden ist und wie sie sortiert worden ist.

  private void setSortiert(int sortiert) {
    this.sortiert = sortiert;
  }

  public int getSortiert() {
    return sortiert;
  }

  public Zettelkasten() {
    this.medium = new ArrayList<>();
  }

  /**
   * Die Methode addMedium fügt ein Medium(Buch,CD,Zeitschrift,ElMed) einer Arraylist hinzu. Dabei
   * werden je nach Medium verschiedene Überprüfungen durchgeführt. Beim Hinzufügen eines Mediums
   * wird der Integer sortiert auf 0 gesetzt(unsortiert).
   **/
  public boolean addMedium(Medium _medium) {
    if(_medium != null){

    if (_medium.getTitel().length() < 1) {
      throw new IllegalArgumentException("Titel ungültig");
    }
    //Bereich für Hinzufügen eines Buches
    if ((_medium instanceof Buch)) {
      Buch buch = (Buch) _medium;
      if (buch.getVerfasser().isBlank()) {
        throw new IllegalArgumentException("Verfasser vom Buch:" + buch.getTitel()
            + " darf nicht leer sein. Bitte Eingabe korrigieren!");
      }
      if (buch.getVerlag().isBlank()) {
        throw new IllegalArgumentException("Verlag vom Buch:" + buch.getTitel()
            + " darf nicht leer sein. Bitte Eingabe korrigieren!");
      }
      if (buch.getIsbn().isBlank()) {
        throw new IllegalArgumentException("ISBN vom Buch:" + buch.getTitel()
            + " darf nicht leer sein. Bitte Eingabe korrigieren!");
      }
      if (buch.checkISBN10(buch.converterISBN(buch.getIsbn())) || buch.checkISBN13(
          buch.converterISBN(buch.getIsbn()))) {
        medium.add(_medium);
        setSortiert(0);
        return true;
      }

    } //Bereich für Hinzufügen einer CD
    else if ((_medium instanceof CD)) {
      CD cd = (CD) _medium;
      if (cd.getKuenstler().isBlank()) {
        throw new IllegalArgumentException("Künstler der CD:" + cd.getTitel()
            + "darf nicht leer sein. Bitte Eingabe korrigieren!");
      }
      if (cd.getLabel().isBlank()) {
        throw new IllegalArgumentException(
            "Label der CD:" + cd.getTitel() + "darf nicht leer sein. Bitte Eingabe korrigieren!");
      }
      medium.add(_medium);
      setSortiert(0);
      return true;
    }
    //Bereich für Hinzufügen eines elektronischen Mediums
    else if ((_medium instanceof ElektronischesMedium)) {
      ElektronischesMedium elMed = (ElektronischesMedium) _medium;
      if (elMed.checkURL(elMed.getUrl())) {
        medium.add(_medium);
        setSortiert(0);
        return true;
      } else {
        throw new IllegalArgumentException("Ungültige URL. Bitte Eingabe überprüfen!");
      }
    } //Bereich für Hinzufügen eines Wikibuches
    else if ((_medium instanceof WikiBuch)) {
      WikiBuch wikiBuch = (WikiBuch) _medium;
      if (wikiBuch.checkURL(wikiBuch.getUrl())) {
        medium.add(_medium);
        setSortiert(0);
        return true;
      } else {
        throw new IllegalArgumentException("Ungültige URL. Bitte Eingabe überprüfen!");
      }
    }//Bereich für Hinzufügen einer Zeitschrift
    else if ((_medium instanceof Zeitschrift)) {
      Zeitschrift zeitschrift = (Zeitschrift) _medium;
      if (zeitschrift.getIssn().isBlank()) {
        throw new IllegalArgumentException("ISSN von der Zeitschrift: " + zeitschrift.getTitel()
            + " darf nicht leer sein. Bitte Eingabe überprüfen!");
      }
      if (zeitschrift.getIssn().isBlank()) {
        throw new IllegalArgumentException("ISSN von der Zeitschrift: " + zeitschrift.getTitel()
            + " darf nicht leer sein. Bitte Eingabe überprüfen!");
      }
      medium.add(_medium);
      setSortiert(0);
      return true;
    } else {
      throw new IllegalStateException("Unexpected value: " + _medium);
    }
  }return false;}


  /**
   * Die Methode dropMedium nimmt einen String entgegen und prüft den Zettelkasten auf diesen String
   * als Titel, falls dieser keinen passenden Eintrag findet wird eine Fehlermeldung zurückgegeben.
   * Sollten sich mehrere Einträge mit dem gleichen Titel im Zettelkasten befinden wird die
   * "duplicateEntryException" ausgeben. Wenn es beim Titel nur eine Übereinstimmung gibt, wird
   * dieser entfernt. Um Medien mit den gleichen Titeln zu entfernen, muss die Methode mit mehreren
   * Parametern nochmal aufgerufen werden.
   */
  public void dropMedium(String _titel) {
    if (_titel == null) {
      throw new IllegalArgumentException("Parameter darf nicht null sein");
    }
    String titel;
    int counter = 0;//Counter wird benötigt, um festzustellen, wie oft ein Titel in dem Zettelkasten vorkommt.
    int positionArraylist = 0;
    for (int i = 0; i < medium.size(); i++) {
      titel = medium.get(i).getTitel();
      //Titel wird hier überprüft
      if (_titel.equals(titel)) {
        counter++;
        positionArraylist = i;
      }
    }
    //Auswertung des Counters
    if (counter > 1) {
      throw new duplicateEntryException("Es wurden mehrere Einträge mit dem Titel: \"" + _titel
          + "\" gefunden.");//duplicateEntry()
    }
    if (counter == 1) {
      medium.remove(positionArraylist);
    }
    //Wenn der Titel nicht gefunden wird
  //  throw new IllegalArgumentException("Übergebener Parameter: \"" + _titel + "\" nicht gefunden");
  }

  /**
   * Diese Methode dropMedium nimmt zusätzlich zum String auch zwei Booleanwerte entgegen. Wenn der
   * erste Booleanwert auf true gesetzt wird und der zweite Wert auf false, nur ein Medium mit
   * diesem Titel gelöscht. Es werden erstmal alle verfügbaren Medien, in gleichen Zettelkasten, in
   * der Console mit einer Nummer ausgegeben. Danach muss der User einen Input mit der Nummer von
   * dem Medium welches gelöscht werden soll. Wenn alle Medien mit dem Titel entfernt werden sollen,
   * muss der zweite Booleanwert true gesetzt werden. Danach werden alle Medien mit dem Titel
   * entfernt. Hierbei wird der Wert vom ersten Boolean nicht berücksichtigt.
   **/
  public void dropMedium(String _titel, Boolean _deleteOne, Boolean _deleteAll) {
    if (_titel == null) {
      throw new IllegalArgumentException("Parameter darf nicht null sein");
    }
    String titel;
    //Hier findet deleteAll statt. Dabei wird in einer Schleife die Arraylist durchgegangen und das Element mit dem Titel _titel entfernt.
    //Nach dem Entfernen wird die Schleife zurückgesetzt und so lange ausgeführt bis kein Medium mit dem Titel _titel vorkommt.
    if (_deleteAll.equals(true)) {
      for (int i = 0; i < medium.size(); i++) {
        titel = medium.get(i).getTitel();
        if (_titel.equals(titel)) {
          medium.remove(i);
          i = 0;
        }
      }
      System.out.println("Es wurden alle Einträge mit dem Titel:" + _titel + " gelöscht.");
    }

    //Hier findet das Entfernen eines Mediums statt. Dafür wird eine Arraylist angelegt, in dieser werden die Positionen der Medien mit dem gleichen Titel gespeichert.
    // In der Schleife wird diese Arraylist mit allen Positionen der Medien gefüllt, welche den Titel _titel besitzen und eine Ausgabe mit calculateRepresentation und der Position
    // vom Medium. Danach wird der User aufgefordert die Nummer der Position von dem zu entfernen Medium einzugeben.
    if (_deleteOne.equals(true)) {
      ArrayList<Integer> deletable = new ArrayList<>();
      for (int i = 0; i < medium.size(); i++) {
        titel = medium.get(i).getTitel();
        if (_titel.equals(titel)) {
          System.out.println(i + ":");
          System.out.println(medium.get(i).calculateRepresentation() + "\n");
          deletable.add(i);
        }
      }
      Scanner scn = new Scanner(System.in);//Eingabe vom User
      System.out.println("Welches Medium soll entfernt werden ? Bitte die Nummer eingeben.");
      int delete = scn.nextInt();
      for (Integer integer : deletable) {
        if (integer
            == delete) {//Verhindert, dass Nummern, die nicht von den Medien sind, eingeben werden können bzw. eine Fehlermeldung geworfen wird.
          medium.remove(delete);
          System.out.println("Es wurde " + delete + " entfernt.");
        }
      }
      throw new IllegalArgumentException(delete + " nicht gefunden.");
    }
    throw new IllegalArgumentException("Übergebener Parameter: \"" + _titel + "\" nicht gefunden");
  }


  /**
   * Die Methode findMedium nimmt ein String, der den zu suchenden Titel beinhaltet, entgegen.
   * Danach wird in dem Zettelkasten nach diesem String durchsucht. Um die gefundenen Medien
   * abspeichern zu können wird eine Arraylist erstellt und bei jeder übereinstimmung hinzugefügt.
   **/
  public Medium[] findMedium(String _titel) {
    String titel;
    ArrayList<Medium> mediumGleicherTitel = new ArrayList<>();// Speichert die Medien mit gewünschtem Titel
    for (Medium value : medium) {
      titel = value.getTitel();
      if (_titel.equals(titel)) {
        mediumGleicherTitel.add(value);
      }
    }
    if (mediumGleicherTitel.size() != 0) {
      Medium[] mediumArray = new Medium[mediumGleicherTitel.size()];
      mediumArray = mediumGleicherTitel.toArray(
          mediumArray); //Hier wird die Arraylist in ein Array umgewandelt
      return mediumArray;
    }

    throw new IllegalArgumentException("Medium mit dem Titel: \"" + _titel + "\" nicht gefunden.");

  }

  /**
   * Die Methode sort nimmt einen String entgegen, in dem der Sortierparameter übergeben wird
   * (aufsteigend oder absteigend). Bevor eine Sortierung stattfindet wird, mit der Methode
   * sortCheck geprüft, ob nicht bereits eine gewünschte Sortierung vorliegt. Danach findet eine
   * Fallunterscheidung statt, nach welchem Parameter sortiert werden soll. Für die Sortierung wird
   * sort von Collections verwendet. Dafür wurde in der Klasse Medium eine compareTo für den
   * Vergleich erstellt. Nach dem Sortieren wird der Integer sortiert auf 1 oder zu gesetzt. Mit
   * diesem Wert lässt sich die Sortierung feststellen.
   **/
  public void sort(String _sortierparameter) {
   //mit sortCheck wird geprüft ob, die Elemente nicht schon in sortierter Reihenfolge übergeben wurde.
      _sortierparameter = _sortierparameter.toLowerCase();
      if (_sortierparameter.equals("absteigend")) {
        medium.sort(Medium::compareTo);
        setSortiert(1);

      }
      if (_sortierparameter.equals("aufsteigend")) {
        medium.sort(Medium::compareTo);
        Collections.reverse(
            medium);//Es wird erst aufsteigend sortiert und dann mit reverse die Reihenfolge geändert.
        setSortiert(2);

      }

  }

  /**
   * Die Methode sortCheck wird von sort aufgerufen. Diese prüft nur, ob bereits die gewünschte
   * Sortierung vorhanden ist. Das geschieht, durch eine Fallunterscheidung des Sortierparameters.
   * In den einzelnen Fällen wird mit dem Getter des Integers sortiert gearbeitet. Sollten
   * Sortierparameter und Integer übereinstimmen wird ein true zurückgegeben. Bei keiner
   * übereinstimmung ein false. Bei einem ungültigen Sortierparameter wird eine Fehlermeldung
   * zurückgeben.
   **/
  private boolean sortCheck(String _sortierparameter) {
    switch (_sortierparameter) {
      case ("aufsteigend") -> {
        if (getSortiert() == 1) {
          System.out.println("Keine Sortierung notwendig");
          return true;
        }
        return false;
      }
      case ("absteigend") -> {
        if (getSortiert() == 2) {
          System.out.println("Keine Sortierung notwendig");
          return true;
        }
        return false;
      }
    }
    throw new IllegalArgumentException("Der Sortierparameter: \"" + _sortierparameter
        + "\" wird nicht erkannt. Bitte verwenden Sie nur \"aufsteigend\" oder \"absteigend\" .");
  }


  @Override
  public Iterator<Medium> iterator() {
    return medium.iterator();
  }
}
