/**
 * Author: Marco Gren
 * Matrikelnummer: 19745
 * Umgebung: IntelliJ, JDK 17, Windows 11
 * Letzte Änderung: 11.11.2022
 * Aufgabe : LPSW, WS2022/2023, Aufgabenblatt1, Aufgabe 11
 **/
package SoftwareLabor.Medien;


import java.io.Serializable;

public class Buch extends Medium implements Serializable {

  private int erscheinungsjahr;
  private String verlag, isbn, verfasser;

  public Buch(int _erscheinungsjahr, String _verlag, String _verfasser, String _isbn,
      String _title) {
    super(_title);
    setIsbn(_isbn);
    setErscheinungsjahr(_erscheinungsjahr);
    setVerfasser(_verfasser);
    setVerlag(_verlag);
  }

  public void setErscheinungsjahr(int _erscheinungsjahr) {
    if (_erscheinungsjahr<0){
      throw new IllegalArgumentException("Erscheinungsjahr kann nicht negativ sein! Eingabe überprüfen!");
    }
    this.erscheinungsjahr = _erscheinungsjahr;
  }
/** Methode zum Setzen der ISBN, es wird eine Prüfung durchgeführt, ob es sich um eine ISBN handelt
 *  und dann wird noch unterschieden, ob es sich um ISBN 10 oder ISBN 13 handelt.
 *  Danach wird mit der Kombination der Methoden converterISBN und checkISBN durchgeführt.
 *  Wenn das erfolgreich ist (true) wird die ISBN gesetzt**/
  public void setIsbn(String _isbn) {

    if (_isbn.length() == 13) {
      if (true == checkISBN10(converterISBN(_isbn))) {

        this.isbn = _isbn;
      }
    }
    if (_isbn.length() == 17) {

      if (true == checkISBN13(converterISBN(_isbn))) {

        this.isbn = _isbn;
      }
    }


  }

  public void setVerfasser(String _verfasser) {

    this.verfasser = _verfasser;
  }


  public void setVerlag(String _verlag) {

    this.verlag = _verlag;
  }

  public int getErscheinungsjahr() {
    return erscheinungsjahr;
  }

  public String getIsbn() {
    return isbn;
  }

  public String getVerfasser() {
    return verfasser;
  }

  public String getVerlag() {
    return verlag;
  }

  /**Methode überprüft, ob die ISBN-10 korrekt ist, wenn dies der Fall ist, wird true zurückgegeben**/
  public static boolean checkISBN10(int[] _isbn) {
    int sum = 0;
    for (int i = 1; i <= _isbn.length; i++) {
      sum += i * _isbn[i - 1];
    }
    if (sum % 11 == 0) {
      return true;
    } else {
      return false;
    }
  }

  /**Methode überprüft, ob die ISBN-13 korrekt ist, wenn dies der Fall ist, wird true zurückgegeben**/
  public static boolean checkISBN13(int[] _isbn) {
    int sum = 0;
    for (int i = 1; i < _isbn.length; i++) {
      if (i % 2 == 0) {
        sum += _isbn[i - 1] * 3;
      } else {
        sum += _isbn[i - 1];
      }
    }
    int lastDigit = sum % 10;
    int check = (10 - lastDigit) % 10;
    if (_isbn[_isbn.length - 1] == check) {
      return true;
    } else {
      return false;
    }
  }

  /** Methode die String Eingabe der ISBN in eine int[] umwandelt(wird für die Prüfmethoden benötigt!) und direkt prüft, ob die Form der ISBN stimmt**/
  public int[] converterISBN(String _isbn) {
    int[] listISBN10 = new int[10];
    int[] listISBN13 = new int[13];
    String isbnOhneBindestricheAlsString = "";
    String[] isbnOhneBindestrich = _isbn.split("-");
    if (_isbn.length() == 13) {
      for (int i = 0; i < isbnOhneBindestrich.length; i++) {
        isbnOhneBindestricheAlsString += isbnOhneBindestrich[i];

      }
      for (int j = 0; j < isbnOhneBindestricheAlsString.length(); j++) {
        listISBN10[j] += Integer.parseInt(String.valueOf(isbnOhneBindestricheAlsString.charAt(j)));

      }

      return listISBN10;

    }
    if (_isbn.length() == 17) {
      for (int i = 0; i < isbnOhneBindestrich.length; i++) {
        isbnOhneBindestricheAlsString += isbnOhneBindestrich[i];

      }
      for (int j = 0; j < isbnOhneBindestricheAlsString.length(); j++) {
        listISBN13[j] += Integer.parseInt(String.valueOf(isbnOhneBindestricheAlsString.charAt(j)));

      }

      return listISBN13;

    } else {
      throw new IllegalArgumentException("Keine korrekete ISBN");
    }

  }
/**
 * Abstrakte Methode aus Medium die für die Ausgabe der Daten von dem Medium Buch
 *
 * @return
 **/
  @Override
  public String calculateRepresentation() {
    StringBuilder builder = new StringBuilder();
    builder.append("Titel: ").append(getTitel());
    builder.append("\nErscheinungsjahr: ").append(getErscheinungsjahr());
    builder.append("\nVerlag: ").append(getVerlag());
    builder.append("\nISBN: ").append(getIsbn());
    builder.append("\nVerfasser: ").append(getVerfasser());
    return builder.toString();
  }
  /**
   * Abstrakte Methode aus Medium die für die Ausgabe der Daten in BibTex von dem Medium Buch
   *
   **/
  @Override
  public String calculateBibRepresentation(){
    StringBuilder builder =new StringBuilder();
    builder.append("@book{");
    builder.append("author = {"+getVerfasser()+"}, ");
    builder.append("publisher = {"+getVerlag()+"}, ");
    builder.append("title = {"+getTitel()+"}, ");
    builder.append("year = "+getErscheinungsjahr()+", ");
    builder.append("isbn = {"+getIsbn()+"}").append("}");
    return builder.toString();
  }
}
