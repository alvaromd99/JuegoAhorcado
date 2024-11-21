/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package daw;

import java.util.Objects;
import javax.swing.JOptionPane;

/**
 *
 * @author alvaro
 */
public class JuegoAhorcado {

    public static void main(String[] args) {
        String menu = """
                      --- Juego Ahorcado ---
                      Opciones (1 - 3):
                      1. Dos jugadores
                      2. Contra la maquina
                      3. Salir
                      
                      Introduzaca un opcion:
                      """;

        int opcion = 0;
        String wordToGuess = "";
        String userWord = "";
        char vowel = Character.MIN_VALUE;

        do {
            opcion = getOptionFromUser(menu);

            if (opcion == Integer.MIN_VALUE) {
                break;
            }

            switch (opcion) {
                case 1 ->
                    System.out.println("Op 1");
                case 2 -> {
                    wordToGuess = Palabras.getRandomWord();
                    vowel = getFirstVowel(wordToGuess);
                    userWord = hideWordToGuess(wordToGuess, vowel);
                    
                    System.out.println(wordToGuess);
                    System.out.println(userWord);
                }
                case 3 ->
                    System.out.println("Hasta luego");
                default ->
                    JOptionPane.showMessageDialog(null, "Opcion invalida");
            }
        } while (opcion != 3 && opcion != Integer.MIN_VALUE);
    }

    /**
     * Devuelve la primera vocal que hay en un string
     *
     * @param word el string donde vamos a buscar la vocal
     * @return la primera vocal que hemos encontrado en la palabra o el valor
     * minimo de Character si no hemos encontrado vocales
     */
    public static char getFirstVowel(String word) {
        for (int i = 0; i < word.length(); i++) {
            if ("AEIOUaeiou".indexOf(word.charAt(i)) != -1) {
                return word.charAt(i);
            }
        }
        return Character.MIN_VALUE;
    }
    
    /**
     * Devuelve la palabra que pasamos por parametro pero solo mostrando las
     * vocales que coinciden con la vocal que hemos pasado tambien. Lo demas 
     * lo pone como "_"
     * 
     * @param word la palabra que vamos a usar como referencia
     * @param vowel la vocal que tenemos que mantener
     * @return  la palabra solo mostrando la vocal en su posicion conrrespondiente
     *          que coincide con la palabra
     */
    public static String hideWordToGuess(String word, char vowel) {
        String res = "";
        // valueof -> char[] to String
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) == vowel) {
                res += vowel;
            } else {
                res += "_";
            }
        }
        return res;
    }
    
    /**
     * Pide al usuario, usando joption, una opcion mostrando un menu
     *
     * @param menu el menu que vamos a mostrar
     * @return el numero entero que representa la opcion que ha elegido el
     * usuario
     */
    public static int getOptionFromUser(String menu) {
        String tmp = "";
        int op = 0;

        tmp = JOptionPane.showInputDialog(menu);

        try {
            if (Objects.isNull(tmp)) {
                return Integer.MIN_VALUE;
            }

            op = Integer.parseInt(tmp);

        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(null, "Error, introduce una"
                    + " opcion valida.");
        }
        return op;
    }
    
    public static char getWordFromUser() {
        String res = "";
        
        do {            
            res = JOptionPane.showInputDialog("Introduzca una letra: ");
        } while (true);
    }

    /**
     * Devuelve un string que es la palabra que hemos pasado pero poniendo un
     * espacio entre cada uno de los caracteres hola -> h o l a
     *
     * @param word el string que queremos separar
     * @return el string con espacios entre cada uno de los caracteres
     */
    public static String showStringWithSpaces(String word) {
        return String.join(" ", word.split(""));
    }
}
