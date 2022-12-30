/**
 * Author: Marco Gren Matrikelnummer: 19745 Umgebung: IntelliJ, JDK 17, Windows 11 Letzte Änderung:
 * 30.12.2022 Aufgabe : LPSW, WS2022/2023, Aufgabenblatt5
 **/
package wikibooksWebView;

import static wikibooksWebView.Syn2.findSynonyme;
import static wikibooksWebView.WikiBooks.getBook;
import static wikibooksWebView.WikiBooks.getUrl;

import SoftwareLabor.Ausgabe.BibTexPersistency;
import SoftwareLabor.Ausgabe.BinaryPersistency;
import SoftwareLabor.Ausgabe.Persistency;
import SoftwareLabor.Exception.MyWebException;
import SoftwareLabor.Medien.Medium;
import SoftwareLabor.Zettelkasten;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.concurrent.Worker.State;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import org.json.simple.parser.ParseException;

public class WikiBooksController {

  @FXML
  private Button btnExport;
  @FXML
  private ComboBox<String> cbHistory;
  @FXML
  private Button btnHinzufuegen;
  @FXML
  private Button btnImport;
  @FXML
  private Button btnLaden;
  @FXML
  private Button btnLoeschen;
  @FXML
  private Button btnSortieren;
  @FXML
  private Button btnSpeichern;
  @FXML
  private Button btnSuche;
  @FXML
  private Button btnSucheSynonyme;
  @FXML
  private Button btnVor;
  @FXML
  private Button btnZurueck;
  @FXML
  private Text datumAenderung;
  @FXML
  private ListView<String> lvMedien;
  @FXML
  private ListView<String> lvSynonyme;
  @FXML
  private Text nameBearbeiter;
  @FXML
  private Text regal;
  @FXML
  private TextField tfSuche;
  @FXML
  private MenuItem about;
  @FXML
  private MenuBar menubar;
  @FXML
  private WebView webView;
  @FXML
  private VBox vBox;
  private Zettelkasten zettelkasten = new Zettelkasten();
  private List<String> history = new ArrayList<>();// Wird zum Speichern des Verlaufes benötigt
  private int historyCounter = -1; //Wird zum Zählen der Synonyme verwendet
  private int position = 0;//Speichert die aktuelle Position im Verlauf


  @FXML
  void initialize() {
    WebEngine engine = webView.getEngine();
    engine.load("https://de.wikibooks.org");// Startseite wird geladen
    history.add("Hauptseite");
    historyCounter++;
    position = historyCounter;
    nameBearbeiter.setText("");
    datumAenderung.setText("");
    //Sperren der Button, da am Anfang keine Funktion ohne zusätzlichen Input haben
    btnVor.setDisable(true);
    btnZurueck.setDisable(true);
    cbHistory.setDisable(true);
    btnSucheSynonyme.setDisable(true);
    btnLoeschen.setDisable(true);
    // Bei Änderung der Seite egal durch welchen Input werden die Labels gesetzt
    engine.getLoadWorker().stateProperty().addListener((ov, oldState, newState) -> {
      if (newState == State.SUCCEEDED) {
        if(getCurrentPage(engine).contains("wikibooks.org")){
        setLabels(getCurrentPage(engine));
      }
      else {
        boxAlert(AlertType.ERROR,"KEINE SEITEN AUßERHALB VON WIKIBOOKS ZULÄSSIG!");
        engine.load("https://de.wikibooks.org");
      }}
      if (newState == State.FAILED) {
        try {
          throw new MyWebException("Error beim Laden");
        } catch (MyWebException e) {
          throw new RuntimeException(e);
        }
      }
    });
    //Observer für Tasteneingaben in dem Textfeld
    tfSuche.setOnKeyPressed(keyEvent -> {
      lvMedien.getItems()
          .clear();// bei neuer Eingabe wird die Listview mit den Medien zurückgesetzt.
      /* hier wird mit der Methode vergleichSuche, die Eingabe aus dem Textfeld vergleicht und die übereinstimmenden Medien wieder in der Listview angezeigt */
      for (String s : vergleichSuche()) {
        lvMedien.getItems().add(s);
      }
      // Beim Verwenden der Taste Eingabe wird der eingebene Suchbegriff gesucht ohne, dass dafür der Suche-Button betätigt werden muss.
      if (keyEvent.getCode() == KeyCode.ENTER) {
        searchStart(engine);
        keyEvent.consume();

      }
      // Die Taste F1 zeigt den About alert an. Aus dem Textfeld
      if (keyEvent.getCode() == KeyCode.F1) {
        infoAlertAbout();
      }
      keyEvent.consume();

    });
    // Die Taste F1 zeigt den Infobox "Über dieses Programm" alert an.
    vBox.setOnKeyPressed(keyEvent -> {
      if (keyEvent.getCode() == KeyCode.F1) {
        infoAlertAbout();
        keyEvent.consume();
      }
    });
    //Beim Ausführen von "Über dieses Programm" wird eine Infobox geöffnet
    about.setOnAction(actionEvent -> infoAlertAbout());

    //Suchbutton
    btnSuche.setOnAction(keyEvent -> {
      searchStart(engine);
      keyEvent.consume();
    });
    //Hinzufügbuttton
    btnHinzufuegen.setOnAction(keyEvent -> {
      //btnLoeschen.setDisable(false); //Löschfunktion ist nicht gewünscht
      setBtnHinzufuegen(engine);
      keyEvent.consume();
    });
    //Sortierbutton
    btnSortieren.setOnAction(keyEvent -> {
      setBtnSortieren();
      keyEvent.consume();
    });
    //Löschbutton
    btnLoeschen.setOnAction(keyEvent -> {
      setBtnLoeschen();
      keyEvent.consume();
    });
    //Export
    btnExport.setOnAction(keyEvent -> {
      setBtnExport();
      keyEvent.consume();
    });
    //Import
    btnImport.setOnAction(keyEvent -> {
      setBtnImport();
      keyEvent.consume();
    });
    //Speichern
    btnSpeichern.setOnAction(actionEvent -> {
      setBtnSpeichern();
      actionEvent.consume();
    });
    //Laden
    btnLaden.setOnAction(keyEvent -> {
      setBtnLaden();
      keyEvent.consume();
    });
    //Zurueck-Button
    btnZurueck.setOnAction(keyEvent -> {
      btnVor.setDisable(false);// Schaltet den Vor-Button frei.
      setBtnZurueck();
      setBtnSuche(engine);
      keyEvent.consume();
    });
    //Vor-Button
    btnVor.setOnAction(keyEvent -> {
      btnZurueck.setDisable(false);// Schaltet den Zurück-Button frei.
      try {
        setBtnVor();
      } catch (ParseException | IOException e) {
        throw new RuntimeException(e);
      }
      setBtnSuche(engine);
      keyEvent.consume();
    });
    //Combobox
    cbHistory.setOnAction(actionEvent -> {
      try {
        //Auswählen eines Elementes in der Combobox lädt das ausgewählte Element in den Vordergrund
        setCbHistoryOnClick(cbHistory.getSelectionModel().getSelectedIndex(), engine);
      } catch (ParseException | IOException e) {
        throw new RuntimeException(e);
      }
    });
    // Sucht für das aus der Listview Synonyme ausgewählte Synonyme weiter Synonyme
    btnSucheSynonyme.setOnAction(keyEvent -> synonymWeiterleitung(engine));
    // alternativ zum Synonyme Suchen-Button mit Doppelklick auf das gleiche Feld in dem Synonyme Listview werden für diese Synonyme angezeigt
    lvSynonyme.setOnMouseReleased(mouseEvent -> {
      if (mouseEvent.getClickCount() == 2) {//Löst bei Doppelklick aus
        synonymWeiterleitung(engine);
      }
      mouseEvent.consume();
    });
    lvSynonyme.setOnKeyPressed(keyEvent -> {
      if (keyEvent.getCode() == KeyCode.ENTER) {
        synonymWeiterleitung(engine);
      }
      if (keyEvent.getCode() == KeyCode.TAB) {
        if (btnSucheSynonyme.isDisabled()) {
          btnHinzufuegen.requestFocus();
        }
        keyEvent.consume();
      }
    });

    //KeyEvents die für das korrekte durch TABen benötigt werden.
    btnSuche.setOnKeyPressed(keyEvent -> {
      if (keyEvent.getCode() == KeyCode.TAB) {
        lvSynonyme.requestFocus();
      }
      keyEvent.consume();
    });
    btnSucheSynonyme.setOnKeyPressed(keyEvent -> {
      if (keyEvent.getCode() == KeyCode.TAB) {
        btnHinzufuegen.requestFocus();
      }
      keyEvent.consume();
    });
    menubar.setOnKeyPressed(keyEvent -> {
      if (keyEvent.getCode() == KeyCode.TAB) {
        if (btnZurueck.isDisabled()) {
          cbHistory.requestFocus();
        }
        if (btnZurueck.isDisabled() && btnVor.isDisabled()) {
          tfSuche.requestFocus();
        } else {
          btnZurueck.requestFocus();
        }
      }
      keyEvent.consume();
    });
    btnSpeichern.setOnKeyPressed(keyEvent -> {
      if (keyEvent.getCode() == KeyCode.TAB) {
        menubar.requestFocus();
      }
      keyEvent.consume();
    });
  }

  /**
   * Die Methode searchStart führt die Suche aus dem Textfeld durch, unabhängig von der Art der
   * Ausführung(Enter oder Suchen-Button).
   **/
  private void searchStart(WebEngine engine) {
    //überprüft die Eingabe(nicht Blank) und leite diese an die Webengine weiter.
    setBtnSuche(engine);
    //überprüft an welcher Postion sich der User befindet. Wenn dieser nicht an der letzten Stelle ist, wird die History mit der aktuellen Eingabe ersetzt.
    bearbeitenHistory();
    //Synonymesuche wird durchgeführt und im Listview Synonyme angezeigt.
    tfSucheSynonyme(tfSuche.getText());
    // fügt die Eingabe aus dem Textfeld der History hinzu.
    historyAdd(tfSuche.getText());
  }

  /**
   * Die Methode synonymWeiterleitung nimmt die ausgewählten Elemente aus dem Listview Synonyme und
   * führt eine Suche mit diesen durch, dabei wird im Hintergrund die History angepasst und für das
   * gesuchte Wort neue Synonyme angezeigt.
   **/
  private void synonymWeiterleitung(WebEngine engine) {
    if (lvSynonyme.getSelectionModel().getSelectedItem() != null && !lvSynonyme.getSelectionModel()
        .getSelectedItem().equals("<Keine>")) {
      lvSynonyme.setDisable(false);
      tfSuche.setText(lvSynonyme.getSelectionModel().getSelectedItem());
      tfSucheSynonyme((tfSuche.getText()));
      bearbeitenHistory();
      setBtnSuche(engine);
      historyAdd(tfSuche.getText());

    } else {
      Alert alert = new Alert(AlertType.WARNING);
      alert.setHeaderText(null);
      alert.getDialogPane().setContentText("Kein Synonym zum Weitersuchen ausgewählt.");
      alert.show();

    }
  }

  /**
   * Die Methode setBtnSuche bildet mit der Eingabe aus dem Textfeld eine URL, die überprüft wird.
   * Wenn die Überprüfung erfolgreich war, wird die neue Seite in der WebEngine geladen.
   **/
  private void setBtnSuche(WebEngine _engine) {
    String temp = getUrl(tfSuche.getText());
    if (!temp.isBlank()) {
      _engine.load(temp);
    }
  }

  /**
   * Die Methode setBtnHinzufuegen fügt ein Wikibuch Medium zum Zettelkasten hinzu. Dazu wird in
   * einer if-Abfrag ein Medium den Zettelkasten hinzugefügt. Wenn dies erfolgreich wird die Medium
   * Listview mit dem neuen Medium angezeigt.
   **/
  private void setBtnHinzufuegen(WebEngine _engine) {
    if (zettelkasten.addMedium(getBook(getCurrentPage(_engine)))) {
      refreshListviewMedien();
    }
  }

  /**
   * Die Methode setBtnSortieren prüft die Sortierung vom Zettelkasten, wenn der Zettelkasten
   * unsortiert ist, wird zunächst absteigend sortiert. bei einem weiteren betätigen des
   * Sortierbuttons wird aufsteigend sortiert. Jedes weitere ausführen wechselt die Sortierung von
   * auf zu absteigend und umgekehrt.
   **/
  private void setBtnSortieren() {
    if (zettelkasten.getSortiert() == 1) {
      zettelkasten.sort("aufsteigend");
      refreshListviewMedien();
    } else {
      zettelkasten.sort("absteigend");
      refreshListviewMedien();
    }

  }

  /**
   * Die Methode setBtnLoeschen prüft, ob ein Medium aus der Listview Medien ausgewählt worden ist
   * und löscht dieses dann aus dem Zettelkasten und lädt den Zettelkasten erneut in die Listview
   * Medien.
   **/
  private void setBtnLoeschen() {
    if (lvMedien.getSelectionModel().getSelectedItem() != null) {
      zettelkasten.dropMedium(lvMedien.getSelectionModel().getSelectedItem());
      refreshListviewMedien();
    }
  }

  /**
   * Die Methode setBtnLaden lädt einen vorher gespeicherten Zettelkasten wieder in das Programm
   * ein.
   **/
  public void setBtnLaden() {
    btnLoeschen.setDisable(false);
    Persistency binaryPersistency = new BinaryPersistency();
    try {
      zettelkasten = binaryPersistency.load("binary.ser");
    } catch (RuntimeException e) {
      btnLoeschen.setDisable(true);
      boxAlert(AlertType.ERROR, "Datei binary.ser konnte nicht geladen werden.");
    }
    refreshListviewMedien();
  }

  private static void boxAlert(AlertType error, String s) {
    Alert alert = new Alert(error);
    alert.setHeaderText(null);
    alert.getDialogPane().setContentText(s);
    alert.showAndWait();
  }

  /**
   * Die Methode setBtnSpeichern speichert den aktuellen Zettelkasten in eine Datei "binary.ser".
   * Falls bereits eine andere Datei mit demselben Namen vorhanden ist, wird diese überschrieben.
   **/
  private void setBtnSpeichern() {
    Persistency binaryPersistency = new BinaryPersistency();
    try {
      binaryPersistency.save("binary.ser", zettelkasten);
      boxAlert(AlertType.INFORMATION, "Der Zettelkasten wurde als binary.ser gespeichert.");
    } catch (RuntimeException exception) {
      boxAlert(AlertType.ERROR, "Datei binary.ser konnte nicht gespeichert werden.");
    }
  }

  /**
   * Die Methode setBtnImport importiert einen Zettelkasten mithilfe von Bibtex. Dafür muss ein
   * Zettelkasten vorher als "bibtex.txt" gespeichert werden.
   **/
  private void setBtnImport() {
    btnLoeschen.setDisable(false);
    Persistency bibTexPersistency = new BibTexPersistency();
    try {
      zettelkasten = bibTexPersistency.load("bibtex.txt");
    } catch (RuntimeException e) {
      btnLoeschen.setDisable(true);
      boxAlert(AlertType.ERROR, "Datei bibtex.txt konnte nicht geladen werden.");
    }
    refreshListviewMedien();
  }

  /**
   * Die Methode setBtnExport exportiert den aktuellen Zettelkasten in einem Bibtexformat als
   * "bibtex.txt".
   **/
  private void setBtnExport() {
    Persistency bibTexPersistency = new BibTexPersistency();
    try {
      bibTexPersistency.save("bibtex.txt", zettelkasten);
      boxAlert(AlertType.INFORMATION, "Der Zettelkasten wurde als bibtex.txt exportiert.");
    } catch (RuntimeException exception) {
      boxAlert(AlertType.ERROR, "Datei bibtex.txt konnte nicht exportiert werden.");
    }
  }

  /**
   * Die Methode setBtnZurueck lädt beim Betätigen des Buttons "Zurueck", eins in der History
   * gespeichertes Element und ruft dieses erneut auf.
   **/
  private void setBtnVor() throws ParseException, IOException {
    String vorherigesSynonyme;
    if (position > 0) {
      position--;
      //Damit bei Position 0 kein Vor mehr möglich ist.
    }
    btnVor.setDisable(position == 0);
    //Wieder Laden des Elementes
    vorherigesSynonyme = history.get(position);
    cbHistory.getSelectionModel().select(position);
    tfSuche.setText(vorherigesSynonyme);
    tfSucheSynonyme(vorherigesSynonyme);
  }

  /**
   * Die Methode setBtnVor ermöglicht es mit dem Button-Vor durch die History zu gehen. Diese
   * Methode kann nur aufgerufen werden, wenn vorher die setBtnZurueck aufgerufen worden ist, sonst
   * bleibt der Button deaktiviert, weil der User sich auf dem neusten Element befindet und nicht
   * weiter vor in der History kann.
   **/
  private void setBtnZurueck() {

    String vorherigesSynonyme;
    if (position  <= historyCounter) {
      position++;
      vorherigesSynonyme = history.get(position);
      tfSuche.setText(vorherigesSynonyme);
      cbHistory.getSelectionModel().select(position);
      tfSucheSynonyme(vorherigesSynonyme);
    }
    if (position  == historyCounter) {
      btnZurueck.setDisable(true);
    }
  }

  /**
   * Die Methode setCbHistoryOnClick lädt das vom User ausgewählt Element in der Combobox in den
   * Vordergrund. Das geschieht, indem die Position des Elementes übergeben wird.
   * @param _index Index des gewünschten Elementes in History**/
  private void setCbHistoryOnClick(int _index, WebEngine _engine)
      throws ParseException, IOException {
    if(_index!=-1){//Combobox erkennt manchmal -1 als Wert.
    String vorherigesSynonyme;
    vorherigesSynonyme = history.get(_index);
    tfSuche.setText(vorherigesSynonyme);
    cbHistory.getSelectionModel().select(_index);
    tfSucheSynonyme(vorherigesSynonyme);
      cbHistory.getSelectionModel().select(_index);// Zeigt das ausgewählte Element nach außen an
      setBtnSuche(_engine);
    }}


  /**
   * Die Methode bearbeitenHistory wird verwendet, wenn der User in der History zurückgegangen ist
   * und ein neues Element sucht oder ein Synonym öffnet. Dann wird mit der Methode die History bis
   * zu der Stelle entfernt und die neue Eingabe hinzugefügt.
   **/
  private void bearbeitenHistory() {
    if (position != 0) {//Prüft ob der User innerhalb der History ist oder beim neusten Element.
      history = history.subList(position,
          history.size());//Erstellt eine Subliste mit allen Element bis zur aktuellen Position.
      historyCounter = history.size();
    }

  }

  /**
   * Die Methode setCbHistory wird zum Aktualisieren und Aktivieren der Combobox verwendet. Diese
   * Methode funktioniert ähnlich wie refreshListviewMedien, mit dem Unterschied, dass das aktuelle
   * Element von der Combobox angezeigt wird.
   **/
  private void setCbHistory() {
    cbHistory.setDisable(false);
    cbHistory.getItems().clear();
    for (String s : history) {
      cbHistory.getItems().add(s);
    }
    cbHistory.getSelectionModel().select(history.get(0));
  }

  /**
   * Die Methode tfSucheSynonyme wird ein String übergeben. Dieser String wird dann als Suchbegriff
   * für die findSynonyme verwendet. Diese Methode gibt eine Arraylist<String> zurück. Diese
   * Arraylist wird dann in die Listview eingeladen.
   **/
  private void tfSucheSynonyme(String _auswahl) {
    lvSynonyme.getItems().clear();
    if (_auswahl != null) {
      //Prüfung, ob es sich um das von uns gesetzte Keine handelt.
      if (!_auswahl.equalsIgnoreCase("<Keine>")) {
        btnSucheSynonyme.setDisable(false);
        lvSynonyme.setDisable(false);
        if (!_auswahl.isBlank()) {
          ArrayList<String> synonyme = null;
          try {
            synonyme = findSynonyme(
                _auswahl);
          } catch (ParseException | IOException e) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setHeaderText(null);
            alert.getDialogPane().setContentText("Fehler beim Zugriff auf den Wortschatzserver.");
            alert.show();
          }
          // ruft die Methode von der Klasse Syn2 auf
          if (synonyme != null) {
            synonyme.sort(null);// Sortierung von A zu Z
            // Setzen der Synonyme in die Listview
            for (String s : synonyme) {
              lvSynonyme.getItems().add(s);
            }
          } else {
            // Falls keine Synonyme gefunden werden wird "<Keine>" gesetzt und die Suchfunktion deaktiviert.
            lvSynonyme.getItems().add("<Keine>");
            btnSucheSynonyme.setDisable(true);
            lvSynonyme.setDisable(true);
          }
        }
      } else {
        btnSucheSynonyme.setDisable(true);
        lvSynonyme.setDisable(true);

      }
    }
  }

  /**
   * Die Methode infoAlertAbout erstellt und ruft ein Informationsfenster auf.
   **/
  private void infoAlertAbout() {
    Alert alert = new Alert(AlertType.INFORMATION);
    alert.setHeaderText(null);// Entfernen des Header
    alert.getDialogPane().setContentText("""
        Alle redaktionellen Inhalte stammen von den Internetseiten der Projekte Wikibooks und Wortschatz.
                
        Die von Wikibooks bezogenen Inhalte unterliegen seit dem 22. Juni 2009 unter der Lizenz CC-BY-SA 3.0 Unported zur Verfügung.
        Eine deutschsprachige Dokumentation für Weiternutzer findet man in den Nutzungsbedingungen der Wikimedia Foundation.
        Für alle Inhalte von Wikibooks galt bis zum 22. Juni 2009 standardmäßig die GNU FDL 
        (GNU Free Documentation License, engl. für GNU-Lizenz für freie Dokumentation).
        Der Text der GNU FDL ist unter https://de.wikipedia.org/wiki/Wikipedia:GNU_Free_Documentation_License verfügbar.
                
        Die von Wortschatz (https://wortschatz.uni-leipzig.de/) bezogenen Inhalte sind urheberrechtlich geschützt.
        Sie werden hier für wissenschaftliche Zwecke eingesetzt und dürfen darüber hinaus in keiner Weise genutzt werden.
                
        Dieses Programm ist nur zur Nutzung durch den Programmierer selbst gedacht. Dieses Programm dient
        der Demonstration und dem Erlernen von Prinzipien der Programmierung mit Java.
        Eine Verwendung des Programms für andere Zwecke verletzt möglicherweise die Urheberrechte der 
        Originalautoren der redaktionellen Inhalte und ist daher untersagt.""");
    alert.show();
  }

  /**
   * Die Methode setLabels ruft die Methode getLabels von der Klasse Wikibooks auf und setzt diese
   * unter der Webview ein. Falls es sich bei der aktuellen Seite nicht um ein Buch handelt werden
   * die Felder mit Leerzeichen besetzt.
   **/
  private boolean setLabels(String _url) {
    ArrayList<String> label = WikiBooks.getLabels(_url);
    nameBearbeiter.setText(" ");
    datumAenderung.setText(" ");
    regal.setText(" ");
    if (label.size() > 0) {
      regal.setText(label.get(0));
      nameBearbeiter.setText(label.get(1));
      datumAenderung.setText(label.get(2));
      return true;
    }
    return false;
  }

  /**
   * Die Methode refreshListviewMedien enfernt alle Elemente aus dem Listview Medien und liest den
   * Zettelkasten erneut ein. Dieses wird bei Hinzufueg-, Sortiert- und Loeschoperationen benötigt
   **/
  private void refreshListviewMedien() {
    lvMedien.getItems().clear();
    for (Medium m : zettelkasten) {
      lvMedien.getItems().add(m.getTitel());
    }
  }

  /**
   * Die Methode vergleichSuche vergleicht die Eingabe aus dem Textfeld mit den aktuellen Medien im
   * Zettelkasten, falls die Eingabe im Textfeld eine übereinstimmung mit einem oder mehreren Medien
   * hat. Werden diese der Arraylist vergleich hinzugefügt und in der Listview Medien angezeigt.
   * Sollte es keine Übereinstimmung mit den aktullen Medien im Zettelkasten geben wird "<Keine>" in
   * dem Listview angezeigt.
   * @return Arraylist mit den Medien die zur Eingabe aus Textfeld passen
   **/
  private ArrayList<String> vergleichSuche() {
    ArrayList<String> vergleich = new ArrayList<>();
    String vergleichString = tfSuche.getText();
    for (Medium wikiBuch : zettelkasten) {
      String wikiBuchtitel = wikiBuch.getTitel();
      if (wikiBuchtitel.toLowerCase().contains(vergleichString.toLowerCase())) {
        vergleich.add(wikiBuchtitel);
      }
    }
    if (vergleich.size() == 0) {
      vergleich.add("<Keine>");
    }
    return vergleich;
  }

  /**
   * Die Methode getCurrentPage gibt aktuelle Position von der WebEngine als String wieder.
   * @return String aktuelle Position von der WebEngine als String
   **/
  private static String getCurrentPage(WebEngine _engine) {
    System.out.println(_engine.getLocation());
    return _engine.getLocation();
  }

  /**
   * Die Methode historyAdd fügt die Eingabe aus dem Textfeld in die History Arraylist und lädt die
   * Combobox mit der neuen Eingabe.
   **/
  private void historyAdd(String _tfText) {
    if (_tfText != null && !_tfText.isBlank()) {
      btnZurueck.setDisable(false);
      history.add(0, _tfText);
      setCbHistory();
      historyCounter++;
      position = 0;
      btnVor.setDisable(true);
    }
  }
}