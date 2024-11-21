/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package daw;

import java.util.ArrayList;
import java.util.Objects;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;

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
                    wordToGuess = Palabras.
                            eliminarAcentos(Palabras.getRandomWord());
                    vowel = getFirstVowel(wordToGuess);
                    userWord = hideWordToGuess(wordToGuess, vowel);

                    System.out.println(wordToGuess);
                    System.out.println(userWord);

                    playGame(wordToGuess, userWord, vowel);
                }
                case 3 ->
                    System.out.println("Hasta luego");
                default ->
                    JOptionPane.showMessageDialog(null, "Opcion invalida");
            }
        } while (opcion != 3 && opcion != Integer.MIN_VALUE);
    }

    public static void playGame(String word, String userWord, char vowel) {
        int fallos = 0, index = 0;
        String text = "", temp = "";
        char ch = Character.MIN_VALUE;
        boolean isWord = false, win = false;

        ArrayList<Character> dispLetters = new ArrayList<>();
        ArrayList<Character> usedLetters = new ArrayList<>();

        dispLetters = fillAlphabetChar();
        dispLetters.remove(Character.valueOf(vowel));
        usedLetters.add(vowel);

        do {
            do {
                text = """
                   Letras disponibles:
                   %s
                   Letras usadas:
                   %s
                       
                   Fallos: %d / 7.
                   
                   Estado de la palabra:
                   
                   %s
                   
                   Introduce una letra:
                   """.formatted(dispLetters.toString(), usedLetters.toString(),
                        fallos, showStringWithSpaces(userWord));
                temp = JOptionPane.showInputDialog(text);

                // Controlamos que le de a cancelar
                if (Objects.isNull(temp)) {
                    temp = "";
                }

                ch = temp.charAt(0);

                if (Character.isLetter(ch)) {
                    isWord = true;
                } else {
                    JOptionPane.showMessageDialog(null, "No has introducido"
                            + " una letra.");
                }
            } while (!isWord);

            // buscamos la letra en la palabra
            index = word.indexOf(ch);

            if (index == -1) {
                fallos++;
                JOptionPane.showMessageDialog(null, "La letra NO se encuentra"
                        + " en la palabra. Intentelo de nuevo.");
            } else {
                ArrayList<Integer> ocuIndexes = findAllOcurIndex(word, ch);
                char[] arr = userWord.toCharArray();
                
                // Ponemos la letra en todos los sitios que corresponde, no solo
                // en la primera ocurrencia
                for (Integer i : ocuIndexes) {
                    arr[i] = ch;
                }
                userWord = String.valueOf(arr);
            }

            // Compobamos si el juego ha terminado
            if (userWord.equals(word)) {
                win = true;
                JOptionPane.showMessageDialog(null, "Has ganado!!");
            } else if (fallos == 7) {
                JOptionPane.showMessageDialog(null, "Has perdido :(");
            } else {
                // Hacemos el cambio de letra disponible a letra usada
                dispLetters.remove(Character.valueOf(ch));
                usedLetters.add(ch);
            }
        } while (fallos < 7 && !win);
    }

    /**
     * Devuelve una lista con el numero de todas las posiciones donde se
     * encuentre la vocal que hemos pasado dentro de una palabra
     *
     * @param word la palabra donde buscar la letra
     * @param letter la letra que vamos a buscar
     * @return una lista con todos los indices donde esta la letra dentro de la
     * palabra
     */
    public static ArrayList<Integer> findAllOcurIndex(String word,
            char letter) {
        ArrayList<Integer> resList = new ArrayList<>();

        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) == letter) {
                resList.add(i);
            }
        }
        return resList;
    }

    /**
     * Rellena una lista que pasamos por referencia con todas las letras del
     * abecedario en español (incluyendo ñ)
     *
     */
    public static ArrayList<Character> fillAlphabetChar() {
        ArrayList<Character> resList = new ArrayList<>();
        Character[] letters = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
            'k', 'l', 'm', 'n', 'ñ', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
            'w', 'x', 'y', 'z'};

        for (Character letter : letters) {
            resList.add(letter);
        }
        return resList;
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
     * vocales que coinciden con la vocal que hemos pasado tambien. Lo demas lo
     * pone como "_"
     *
     * @param word la palabra que vamos a usar como referencia
     * @param vowel la vocal que tenemos que mantener
     * @return la palabra solo mostrando la vocal en su posicion
     * conrrespondiente que coincide con la palabra
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
