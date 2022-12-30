/**
 * Author: Marco Gren Matrikelnummer: 19745 Umgebung: IntelliJ, JDK 17, Windows 11 Letzte Änderung:
 * 22.10.2022 Aufgabe : LPSW, WS2022/2023, Aufgabenblatt2, Aufgabe 10
 **/
package SoftwareLabor.BibTex;

import SoftwareLabor.Medien.Buch;
import SoftwareLabor.Medien.CD;
import SoftwareLabor.Medien.ElektronischesMedium;
import SoftwareLabor.Medien.Medium;
import SoftwareLabor.Medien.Zeitschrift;

public class ImportBibTex {

  public static void main(String[] args) {

    System.out.println(parseBibTex(
        "@book{author = {-}, title = {Duden 01. Die deutsche Rechtschreibung}, publisher = {Bibliographisches Institut, Mannheim}, year = 2004, isbn = {3-411-04013-0}}").calculateRepresentation());
    System.out.println(parseBibTex(
        "@jourNal{title = {Der Spiegel}, issn = {0038-7452}, volume = 54, number = 6}").calculateRepresentation());
    System.out.println(parseBibTex(
        "@cd{title = {1}, artist = {Die Beatles}, label = { Apple (Bea (EMI))}}").calculateRepresentation());
    System.out.println(parseBibTex(
        "@elMed{ titel = {Hochschule Stralsund},link = {http://www.hochschule-stralsund.de}}").calculateRepresentation());

  }

  /**
   * Methode die, erkennt um welches Medium es sich im BibTex handelt und anschließend die
   * benötigten Methoden zur Konvertierung von BibTex zu Medium aufruft. Falls Tags beim BibTex
   * fehlen werden die Standardwerte gesetzt. Bei Zeichenketten ist der Standardwert "leer" und bei
   * Zahlen 0
   **/
  public static Medium parseBibTex(String _bibtex) {
    String[] medium = new String[]{"book", "cd", "elMed", "journal"};
    if (_bibtex.charAt(0) == '@') {
      _bibtex = _bibtex.substring(1);
    }
    for (int i = 0; i < medium.length; i++) {
      if (StringOperationen.StringKorrektur(medium[i], StringOperationen.tagLeser(_bibtex)).equals("book")) {
        Buch buch = new Buch(0, "leer", "leer", "leer", "leer");
        _bibtex = _bibtex.substring(StringOperationen.tagLeser(_bibtex).length()).strip();
        _bibtex = _bibtex.substring(1, _bibtex.length() - 1) + ",";
        bibToBook(_bibtex, buch);
        return buch;
      }
      if (StringOperationen.StringKorrektur(medium[i], StringOperationen.tagLeser(_bibtex))
          .equals("cd")) {
        CD cd = new CD("leer", "leer", "leer");
        _bibtex = _bibtex.substring(StringOperationen.tagLeser(_bibtex).length()).strip();
        _bibtex = _bibtex.substring(1, _bibtex.length() - 1) + ",";
        bibToCD(_bibtex, cd);
        return cd;
      }
      if (StringOperationen.StringKorrektur(medium[i], StringOperationen.tagLeser(_bibtex))
          .equals("journal")) {
        Zeitschrift zeitschrift = new Zeitschrift("leer", 0, 0, "leer");
        _bibtex = _bibtex.substring(StringOperationen.tagLeser(_bibtex).length()).strip();
        _bibtex = _bibtex.substring(1, _bibtex.length() - 1) + ",";
        bibToJournal(_bibtex, zeitschrift);
        return zeitschrift;
      }
      if (StringOperationen.StringKorrektur(medium[i], StringOperationen.tagLeser(_bibtex))
          .equals("elmed")) {
        ElektronischesMedium elMed = new ElektronischesMedium("leer", "leer");
        _bibtex = _bibtex.substring(StringOperationen.tagLeser(_bibtex).length()).strip();
        _bibtex = _bibtex.substring(1, _bibtex.length() - 1) + ",";
        bibToElMed(_bibtex, elMed);
        return elMed;
      }
    }
    System.out.println(
        "Medium nicht erkannt, bitte Medium überprüfen Medium muss an erster Stelle stehen.");
    return null;
  }

  /**
   * Methode die, für das Medium Buch den BibTex umwandelt, dafür werden Methoden aus der Klasse
   * StringOperationen aufgerufen
   **/
  private static void bibToBook(String _bibTex, Buch _newBook) {
    String[] tagsInBuch = new String[]{"author", "publisher", "title", "year", "isbn"};
    int counter = 0;
    while ((_bibTex.length() > 1) && counter <= 25) {
      counter++;
      for (int j = 0; j < tagsInBuch.length; j++) {
        String tag = StringOperationen.tagLeser(_bibTex);
        switch (StringOperationen.StringKorrektur(tagsInBuch[j], tag)) {
          case "author":
            _newBook.setVerfasser(StringOperationen.tagAuslesen(_bibTex)[0]);
            _bibTex = StringOperationen.tagAuslesen(_bibTex)[1];
            break;
          case "publisher":
            _newBook.setVerlag(StringOperationen.tagAuslesen(_bibTex)[0]);
            _bibTex = StringOperationen.tagAuslesen(_bibTex)[1];
            break;
          case "title":
            _newBook.setTitel(StringOperationen.tagAuslesen(_bibTex)[0]);
            _bibTex = StringOperationen.tagAuslesen(_bibTex)[1];
            break;
          case "year":
            _newBook.setErscheinungsjahr(
                Integer.parseInt(StringOperationen.tagAuslesen(_bibTex)[0]));
            _bibTex = StringOperationen.tagAuslesen(_bibTex)[1];
            break;
          case "isbn":
            _newBook.setIsbn(StringOperationen.tagAuslesen(_bibTex)[0]);
            _bibTex = StringOperationen.tagAuslesen(_bibTex)[1];
            break;
          case "ungueltigerTag":
            counter =28;
            System.out.println("In dem BibTex befindet sich ein nicht interpretierbarer Tag "+ tag+ ". Inhalt vom Buch wird mit Standardwerten gefüllt.");
            _newBook.setIsbn("leer");
            _newBook.setTitel("leer");
            _newBook.setVerlag("leer");
            _newBook.setErscheinungsjahr(0);
            _newBook.setVerfasser("leer");
            break;
        }
      }
    }
  }

  /**
   * Methode die, für das Medium CD den BibTex umwandelt, dafür werden Methoden aus der Klasse
   * StringOperationen aufgerufen
   **/
  private static void bibToCD(String _bibTex, CD _newCD) {
    String[] tagsInCD = new String[]{"artist", "label", "title"};
    int counter = 0;
    while ((_bibTex.length() > 1) && counter <= 25) {
      counter++;
      for (int j = 0; j < tagsInCD.length; j++) {
        String tag = StringOperationen.tagLeser(_bibTex);
        switch (StringOperationen.StringKorrektur(tagsInCD[j], tag)) {
          case "artist":
            _newCD.setKuenstler(StringOperationen.tagAuslesen(_bibTex)[0]);
            _bibTex = StringOperationen.tagAuslesen(_bibTex)[1];
            break;
          case "label":
            _newCD.setLabel(StringOperationen.tagAuslesen(_bibTex)[0]);
            _bibTex = StringOperationen.tagAuslesen(_bibTex)[1];
            break;
          case "title":
            _newCD.setTitel(StringOperationen.tagAuslesen(_bibTex)[0]);
            _bibTex = StringOperationen.tagAuslesen(_bibTex)[1];
            break;
          case "ungueltigerTag":
            counter =28;
            System.out.println("In dem BibTex befindet sich ein nicht interpretierbarer Tag "+ tag+ ". Inhalt vom CD wird mit Standardwerten gefüllt.");
            _newCD.setLabel("leer");
            _newCD.setTitel("leer");
            _newCD.setKuenstler("leer");
            break;
        }
      }
    }
  }

  /**
   * Methode die, für das Medium Zeitschrift den BibTex umwandelt, dafür werden Methoden aus der
   * Klasse StringOperationen aufgerufen
   **/
  private static void bibToJournal(String _bibTex, Zeitschrift _newJournal) {
    String[] tagsInZeitschrift = new String[]{"volume", "number", "title", "issn"};
    int counter = 0;
    while ((_bibTex.length() > 1) && counter <= 25) {
      counter++;
      for (int j = 0; j < tagsInZeitschrift.length; j++) {
        String tag = StringOperationen.tagLeser(_bibTex);

        switch (StringOperationen.StringKorrektur(tagsInZeitschrift[j], tag)) {
          case "volume":
            _newJournal.setVolume(Integer.parseInt(StringOperationen.tagAuslesen(_bibTex)[0]));
            _bibTex = StringOperationen.tagAuslesen(_bibTex)[1];
            break;
          case "number":
            _newJournal.setNummer(Integer.parseInt(StringOperationen.tagAuslesen(_bibTex)[0]));
            _bibTex = StringOperationen.tagAuslesen(_bibTex)[1];
            break;
          case "title":
            _newJournal.setTitel(StringOperationen.tagAuslesen(_bibTex)[0]);
            _bibTex = StringOperationen.tagAuslesen(_bibTex)[1];
            break;
          case "issn":
            _newJournal.setIssn(StringOperationen.tagAuslesen(_bibTex)[0]);
            _bibTex = StringOperationen.tagAuslesen(_bibTex)[1];
            break;
          case "ungueltigerTag":
            counter =28;
            System.out.println("In dem BibTex befindet sich ein nicht interpretierbarer Tag "+ tag+ ". Inhalt vom Zeitschrift wird mit Standardwerten gefüllt.");
            _newJournal.setIssn("leer");
            _newJournal.setTitel("leer");
            _newJournal.setVolume(0);
            _newJournal.setNummer(0);
            break;
        }

      }
    }
  }


  /**
   * Methode die, für das Medium elektronisches Medium den BibTex umwandelt, dafür werden Methoden
   * aus der Klasse StringOperationen aufgerufen
   **/
  private static void bibToElMed(String _bibTex, ElektronischesMedium _newElMed) {
    String[] tagsInElMed = new String[]{"url", "title"};
    int counter = 0;
    while ((_bibTex.length() > 1) && counter <= 25) {
      counter++;
      for (int j = 0; j < tagsInElMed.length; j++) {
        String tag = StringOperationen.tagLeser(_bibTex);
        switch (StringOperationen.StringKorrektur(tagsInElMed[j], tag)) {
          case "url":
            _newElMed.setUrl(StringOperationen.tagAuslesen(_bibTex)[0]);
            _bibTex = StringOperationen.tagAuslesen(_bibTex)[1];
            break;
          case "title":
            _newElMed.setTitel(StringOperationen.tagAuslesen(_bibTex)[0]);
            _bibTex = StringOperationen.tagAuslesen(_bibTex)[1];
            break;
          case "ungueltigerTag":
            counter =28;
            System.out.println("In dem BibTex befindet sich ein nicht interpretierbarer Tag "+ tag+ ". Inhalt vom elektronischen Medium wird mit Standardwerten gefüllt.");
            _newElMed.setUrl("leer");
            _newElMed.setTitel("leer");
            break;
        }
      }
    }
  }
}

