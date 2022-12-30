/**
 * Author: Marco Gren
 * Matrikelnummer: 19745
 * Umgebung: IntelliJ, JDK 17, Windows 11
 * Letzte Änderung: 19.10.2022
 * Aufgabe : LPSW, WS2017/2018, Aufgabenblatt2, Aufgabe 10
 **/
package SoftwareLabor.BibTex;

public class StringOperationen {
  /**Die Methode Stringvergleich in der zwei Strings miteinander verglichen werden, je nachdem wie die Abweichung von dem String _eingabe zum String _original ist. Wird ein Integerwert zurückgeben. Wert 0 bedeutet der String _eingabe und _original sind identisch. Dafür wird erst die Länge vom String _original ermittelt und in einem Integer(hier abweichung) gespeichert, danach wird der ein Character abgleich durchgeführt. Für jedes übereinstimmendes Character wird der Integer abweichung dekrementiert. Sollten die Strings unterschiedliche längen besitzen, ergänzt die Methode den kürzeren String mit Leerzeichen bis die Strings gleich lang sind.**/
  public static int StringVergleich(String _original, String _eingabe) {
    int abweichung = _original.length();
    _original=_original.toLowerCase();
    _eingabe=_eingabe.toLowerCase().strip();
    if (_original.equals(_eingabe)) {
      return 0;
    }
    if (laengenUnterschiedStrings(_original, _eingabe) != 0) {
      if (_original.length() < _eingabe.length()) {
        int platzhalter = _original.length();
        StringBuilder _originalBuilder = new StringBuilder(_original);
        for (int i = 0; i <= (_eingabe.length() - platzhalter); i++) {
          _originalBuilder.append(" ");

        }
        _original = _originalBuilder.toString();

        for (int i = 0; i < _eingabe.length(); i++) {
          if (_original.charAt(i) == _eingabe.charAt(i)) {
            abweichung--;
          }
        }
      }
      else  {
        int platzhalter = _eingabe.length();
        StringBuilder _eingabeBuilder = new StringBuilder(_eingabe);
        for (int i = 0; i <= (_original.length() - platzhalter); i++) {
          _eingabeBuilder.append(" ");
        }
        _eingabe = _eingabeBuilder.toString();
        for (int i = 0; i < _original.length(); i++) {

          if (_original.charAt(i) == _eingabe.charAt(i)) {
            abweichung--;
          }
        }
      }
    } else {
      for (int i = 0; i < _original.length(); i++) {

        if (_original.charAt(i) == _eingabe.charAt(i)) {
          abweichung--;
        }

      }
    }
    return abweichung;
  }
  /**Die Methode laengenUnterschiedStrings gibt den längen Unterschied der Strings als Integer aus**/
  public static int laengenUnterschiedStrings(String _original, String _eingabe) {

    if (_original.length() < _eingabe.length()) {
      return _eingabe.length() - _original.length();
    } else {
      return _original.length() - _eingabe.length();
    }
  }
  /**Die Methode tagLeser liest aus dem String _eingabe den ersten Tag aus. Dafür wird der String von vorausgehenden Leerzeichen befreit und alle Buchstaben in dem String werden in Kleinbuchstaben umgewandelt, danach wird Character für Character durchgegangen und in einem String gespeichert, bis es kein Buchstabe mehr ist. **/
  public static String tagLeser(String _eingabe) {
    _eingabe = _eingabe.stripLeading().toLowerCase();
    String tag = "";
    for (int i = 0; i < _eingabe.length(); i++) {
      if (_eingabe.charAt(i) >= 'a' && _eingabe.charAt(i) <= 'z') {
        tag = tag + _eingabe.charAt(i);
      } else {
        break;
      }
    }
    return tag;
  }
  /**Die Methode StringKorrektur interpretiert den String _eingabe und gleicht diesen mit dem String _original ab. In dieser Methode mit dem String _original bestimmt, welches case ausgeführt werden soll. Für die Überprüfung wird die Methode Stringvergleich wiederverwendet, jedoch wird hier nicht nur ein Begriff überprüft, sondern ein Array mit möglichen Synonymen, Übersetzungen oder Rechtschreib- und Tippfehlern. Wenn die Prüfung erfolgreich läuft, wird der interpretierte Wert zurückgeben.  **/
  public static String StringKorrektur(String _original, String _eingabe) {
    int abweichung = 999;
    _original = _original.toLowerCase();
    _eingabe = _eingabe.toLowerCase();
    switch (_original) {
      case "book":
        String[] buchsammelbegriffe = {"buch", "book", "bock", "boch", "bok", "buh", "uch", "ook",
            "bach"};
        for (int i = 0; i < buchsammelbegriffe.length; i++) {
          if (StringVergleich(buchsammelbegriffe[i], _eingabe) <= abweichung) {
            abweichung = StringVergleich(buchsammelbegriffe[i], _eingabe);
          }
          if (abweichung <= 1) {
            return "book";
          }
        }

        break;

      case "journal":
        String[] journalsammelbegriffe = {"journal", "zeitschrift", "zeit", "zeitung", "hournal",
            "jornal", "zitung", "zeitschrieft",};
        for (int j = 0; j < journalsammelbegriffe.length; j++) {
          if (StringVergleich(journalsammelbegriffe[j], _eingabe) <= abweichung) {
            abweichung = StringVergleich(journalsammelbegriffe[j], _eingabe);
          }
        }
        if (abweichung < 2) {
          return "journal";
        }
        break;

      case "cd":
        String[] cdsammelbegriffe = {"CD", "CD-ROM", "cd", "cdrom"};
        for (int i = 0; i < cdsammelbegriffe.length; i++) {
          if (StringVergleich(cdsammelbegriffe[i], _eingabe) <= abweichung) {
            abweichung = StringVergleich(cdsammelbegriffe[i], _eingabe);
          }
          if (abweichung < 1) {
            return "cd";
          }
        }

        break;

      case "elmed":
        String[] elmedsammelbegriffe = {"elektronisches Medium", "digital medium",
            "electronic medium", "elmed"};
        for (int i = 0; i < elmedsammelbegriffe.length; i++) {
          if (StringVergleich(elmedsammelbegriffe[i], _eingabe) <= abweichung) {
            abweichung = StringVergleich(elmedsammelbegriffe[i], _eingabe);
          }
          if (abweichung < 3) {
            return "elmed";
          }
        }

        break;
      case "title":
        String[] titelsammelbegriffe = {"titel", "titl", "teitle", "tietel"};
        for (int i = 0; i < titelsammelbegriffe.length; i++) {
          if (StringVergleich(titelsammelbegriffe[i], _eingabe) <= abweichung) {
            abweichung = StringVergleich(titelsammelbegriffe[i], _eingabe);
          }
          if (abweichung <= 1) {
            return "title";
          }
        }

        break;
      case "author":
        String[] authorsammelbegriffe = {"verfasser", "author", "autor"};
        for (int i = 0; i < authorsammelbegriffe.length; i++) {
          if (StringVergleich(authorsammelbegriffe[i], _eingabe) <= abweichung) {
            abweichung = StringVergleich(authorsammelbegriffe[i], _eingabe);
          }
          if (abweichung < 2) {
            return "author";
          }
        }

        break;
      case "publisher":
        String[] publishersammelbegriffe = {"verlag", "publischer", "publisher", "punisher","publisher"};
        for (int i = 0; i < publishersammelbegriffe.length; i++) {
          if (StringVergleich(publishersammelbegriffe[i], _eingabe) <= abweichung) {
            abweichung = StringVergleich(publishersammelbegriffe[i], _eingabe);
          }
          if (abweichung < 3) {
            return "publisher";
          }
        }

        break;
      case "number":
        String[] numbersammelbegriffe = {"nummer", "numer", "nuber", "mubmer"};
        for (int i = 0; i < numbersammelbegriffe.length; i++) {
          if (StringVergleich(numbersammelbegriffe[i], _eingabe) <= abweichung) {
            abweichung = StringVergleich(numbersammelbegriffe[i], _eingabe);
          }
          if (abweichung < 3) {
            return "number";
          }
        }
        break;
      case "issn":
        abweichung = StringVergleich("issn", _eingabe);
        if (abweichung <= 1) {
          return "issn";
        }

        break;
      case "isbn":
        abweichung = StringVergleich("isbn", _eingabe);
        if (abweichung <= 1) {
          return "isbn";
        }

        break;
      case "artist":
        String[] artistsammelbegriffe = {"künstler", "kuenstler", "artist", "artiste"};
        for (int i = 0; i < artistsammelbegriffe.length; i++) {
          if (StringVergleich(artistsammelbegriffe[i], _eingabe) <= abweichung) {
            abweichung = StringVergleich(artistsammelbegriffe[i], _eingabe);
          }
          if (abweichung < 3) {
            return "artist";
          }
        }

        break;
      case "label":
        String[] labelsammelbegriffe = {"label", "lebel", "kabel"};
        for (int i = 0; i < labelsammelbegriffe.length; i++) {
          if (StringVergleich(labelsammelbegriffe[i], _eingabe) <= abweichung) {
            abweichung = StringVergleich(labelsammelbegriffe[i], _eingabe);
          }
          if (abweichung < 3) {
            return "label";
          }
        }

        break;
      case "url":
        String[] urlsammelbegriffe = {"url", "link", "internetseite"};
        for (int i = 0; i < urlsammelbegriffe.length; i++) {
          if (StringVergleich(urlsammelbegriffe[i], _eingabe) <= abweichung) {
            abweichung = StringVergleich(urlsammelbegriffe[i], _eingabe);
          }
          if (abweichung < 3) {
            return "url";
          }
        }

        break;
      case "volume":
        String[] volumesammelbegriffe = {"volume", "blume"};
        for (int i = 0; i < volumesammelbegriffe.length; i++) {
          if (StringVergleich(volumesammelbegriffe[i], _eingabe) <= abweichung) {
            abweichung = StringVergleich(volumesammelbegriffe[i], _eingabe);
          }
          if (abweichung < 3) {
            return "volume";
          }
        }

        break;
      case "year":
        String[] yearsammelbegriffe = {"year", "yeah", "jahr", "erscheinungsjahr"};
        for (int i = 0; i < yearsammelbegriffe.length; i++) {
          if (StringVergleich(yearsammelbegriffe[i], _eingabe) <= abweichung) {
            abweichung = StringVergleich(yearsammelbegriffe[i], _eingabe);
          }
          if (abweichung < 1) {
            return "year";
          }
        }
      default:
        return "ungueltigerTag";

    }
    return "leer";
  }
  /**Die Methode tagAuslesen erkennt die Trennzeichen, mit dem Feststellen dieser wird der Inhalt freigelegt und im String "inhaltVomTag" gespeichert. Danach wird der _eingabe String um den ausgelesenen Inhalt und dessen Trennzeichen gekürzt. Diese neue String _eingabe wird zusammen mit dem Inhalt des Tags in einem String Array gespeichert. Dabei ist die Position vom Tag 0 und von der verkürzten _eingabe die Position 1 **/
  public static String[] tagAuslesen(String _eingabe) {
    _eingabe = _eingabe.substring(_eingabe.indexOf("=") + 1);
    String trennzeichenAnfang, trennzeichenEnde;
    String inhaltVomTag = "";
    trennzeichenAnfang = String.valueOf(_eingabe.strip().charAt(0));
    if (trennzeichenAnfang.equals("{")) {
      trennzeichenEnde = "},";
      inhaltVomTag = _eingabe.substring(_eingabe.indexOf(trennzeichenAnfang) + 1,
          _eingabe.indexOf(trennzeichenEnde));
      _eingabe = _eingabe.substring(_eingabe.indexOf(trennzeichenEnde) + 2);
    }
    if (trennzeichenAnfang.equals("(")) {
      trennzeichenEnde = "),";
      inhaltVomTag = _eingabe.substring(_eingabe.indexOf(trennzeichenAnfang) + 1,
          _eingabe.indexOf(trennzeichenEnde));
      _eingabe = _eingabe.substring(_eingabe.indexOf(trennzeichenEnde) + 2);
    }
    if (trennzeichenAnfang.equals("[")) {
      trennzeichenEnde = "],";
      inhaltVomTag = _eingabe.substring(_eingabe.indexOf(trennzeichenAnfang) + 1,
          _eingabe.indexOf(trennzeichenEnde));
      _eingabe = _eingabe.substring(_eingabe.indexOf(trennzeichenEnde) + 2);
    }
    if (trennzeichenAnfang.equals("\"")) {
      trennzeichenEnde = trennzeichenAnfang + ",";
      inhaltVomTag = _eingabe.substring(_eingabe.indexOf(trennzeichenAnfang) + 1,
          _eingabe.indexOf(trennzeichenEnde));
      _eingabe = _eingabe.substring(_eingabe.indexOf(trennzeichenEnde) + 2);
    }
    if (trennzeichenAnfang.equals("'")) {
      trennzeichenEnde = trennzeichenAnfang + ",";
      inhaltVomTag = _eingabe.substring(_eingabe.indexOf(trennzeichenAnfang) + 1,
          _eingabe.indexOf(trennzeichenEnde));
      _eingabe = _eingabe.substring(_eingabe.indexOf(trennzeichenEnde) + 2);
    }
    if(_eingabe.length()>0){
      if (_eingabe.strip().charAt(0) >= '1' &&
          _eingabe.strip().charAt(0) <= '9') {
        trennzeichenEnde = ",";
        inhaltVomTag = _eingabe.substring(_eingabe.indexOf(trennzeichenAnfang),
            _eingabe.indexOf(trennzeichenEnde));
        _eingabe = _eingabe.substring(_eingabe.indexOf(trennzeichenEnde) + 1);
      }}
    String[] rueckgabe = new String[]{inhaltVomTag, _eingabe};
    return rueckgabe;
  }
}



