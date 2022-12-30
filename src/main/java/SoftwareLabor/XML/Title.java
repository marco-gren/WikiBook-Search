package SoftwareLabor.XML;

/**GEKLAUT VON:
 * Stellt verschiedene statische Methoden zum Validieren einer Zeichenkette
 * @author Jan Ameling
 * <p>
 * MatrikelNummer: 19936<br>
 * Hochschule Stralsund, Laborpraktikum Software WS22<br>
 * Projekt Zettelkasten<br>
 * @Date 15.10.2022
 */
public class Title {


    /**
     * Prueft ob ein Zeichen valide ist
     * <p>
     * Valide Zeichen sind a bis z, A bis Z sowie der Unterstrich
     * @param _char Das zu pruefende Zeichen
     * @return true, falls das Zeichen valide ist, ansonsten false
     */
    public static boolean isValidChar(char _char) {
        if((_char >= 'a' && _char <= 'z') || (_char >= 'A' && _char <= 'Z') || _char == '_')
            return true;
        return false;
    } }


