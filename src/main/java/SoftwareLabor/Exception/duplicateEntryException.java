/**
 * Author: Marco Gren
 * Matrikelnummer: 19745
 * Umgebung: IntelliJ, JDK 17, Windows 11
 * Letzte Änderung: 12.11.2022
 * Aufgabe : LPSW, WS2017/2018, Aufgabenblatt3, Aufgabe 6
 * Aufgabe: Exception für HumanReadablePersistency
 **/
package SoftwareLabor.Exception;

public class duplicateEntryException extends RuntimeException {
public duplicateEntryException(String _errorMessage){
  super(_errorMessage);
}
}


