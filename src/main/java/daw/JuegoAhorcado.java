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
            opcion = getOption(menu);
            
            if (opcion == Integer.MIN_VALUE) break;
            
            switch (opcion) {
                case 1 ->
                    System.out.println("Op 1");
                case 2 -> {
                    wordToGuess = Palabras.getRandomWord();
                    vowel = getFirstVowel(wordToGuess);
                }
                case 3 ->
                    System.out.println("Hasta luego");
                default ->
                    JOptionPane.showMessageDialog(null, "Opcion invalida");
            }
        } while (opcion != 3 && opcion != Integer.MIN_VALUE);
    }

    /**
     * Pide al usuario, usando joption, una opcion mostrando un menu
     *
     * @param menu el menu que vamos a mostrar
     * @return el numero entero que representa la opcion que ha elegido el
     * usuario
     */
    public static int getOption(String menu) {
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
    
    /**
     * Devuelve la primera vocal que hay en un string
     * 
     * @param str el string donde vamos a buscar la vocal
     * @return  la primera vocal que hemos encontrado en la palabra o el valor
     *          minimo de Character si no hemos encontrado vocales
     */
    public static char getFirstVowel(String str) {
        char ch = Character.MIN_VALUE;
        
        for (int i = 0; i < str.length(); i++) {
            if("AEIOUaeiou".indexOf(str.charAt(i)) != -1) {
                ch = str.charAt(i);
            }
        }
        return ch;
    }
    
    public static String hideWordToGuess(String str, char vowel) {
        String res = "";
        // valueof -> char[] to String
        for (int i = 0; i < str.length(); i++) {
            
        }
        return res;
    }
    
    
    /**
     * Devuelve un string que es la palabra que hemos pasado pero poniendo un
     * espacio entre cada uno de los caracteres
     * hola -> h o l a
     * 
     * @param str el string que queremos separar
     * @return el string con espacios entre cada uno de los caracteres
     */
    public static String showStringWithSpaces(String str) {
        return String.join(" ", str.split(""));
    }
}
