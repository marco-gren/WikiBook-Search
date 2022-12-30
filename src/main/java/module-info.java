module host.zettelkastenjavafx {
  requires javafx.controls;
  requires javafx.fxml;
  requires java.xml;
  requires javafx.web;
  requires json.simple;
  opens host.zettelkastenjavafx to javafx.fxml;
  exports host.zettelkastenjavafx;
  opens wikibooksWebView to javafx.fxml;
  exports wikibooksWebView ;

}