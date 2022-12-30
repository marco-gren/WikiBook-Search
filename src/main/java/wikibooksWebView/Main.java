/**
 * Author: Marco Gren
 * Matrikelnummer: 19745
 * Umgebung: IntelliJ, JDK 17, Windows 11
 * Letzte Änderung: 30.12.2022
 * Aufgabe : LPSW, WS2022/2023, Aufgabenblatt5
 **/
package wikibooksWebView;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

  public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {
      FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("GUIWikibooks.fxml"));
      Scene scene = new Scene(fxmlLoader.load());
      //Setzt den Titel
      stage.setTitle("Der Wikibooks-Browser");
      stage.getIcons().add(new Image("file:icon.png"));// Das neue Wikibooks Icon
      //Mindestgröße
      stage.setMinWidth(1280);
      stage.setMinHeight(800);
      stage.setScene(scene);
      stage.show();
    }

    public static void main(String[] args) {
      launch();
    }
  }

