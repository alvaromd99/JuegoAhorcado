/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package daw;

import java.util.ArrayList;
import java.util.Arrays;
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
                case 1 -> {
                    boolean stop = false;

                    do {
                        wordToGuess = JOptionPane.showInputDialog("Introduce "
                                + "la palabra para jugar: ");

                        if (Objects.isNull(wordToGuess)) {
                            stop = true;
                        } else {
                            vowel = getFirstVowel(wordToGuess);

                            if (getFirstVowel(wordToGuess)
                                    == Character.MIN_VALUE) {
                                JOptionPane.showMessageDialog(null, "Error. La"
                                        + " palabra para jugar tiene que "
                                        + "contener al menos una vocal.");
                            } else {
                                stop = true;
                            }
                        }
                    } while (!stop);

                    // Se termina el juego si el usuario le da a cancelar
                    if (Objects.isNull(wordToGuess)) {
                        JOptionPane.showMessageDialog(null, "Juego cancelado.");
                        break;
                    }

                    // ENSEÑAR LA PALABRA
                    System.out.println(wordToGuess);

                    userWord = hideWordToGuess(wordToGuess, vowel);

                    playGame(wordToGuess.toLowerCase(), userWord.toLowerCase(),
                            vowel);
                }
                case 2 -> {
                    wordToGuess = Palabras.
                            eliminarAcentos(Palabras.getRandomWord());
                    vowel = getFirstVowel(wordToGuess);
                    userWord = hideWordToGuess(wordToGuess, vowel);

                    // ENSEÑAR LA PALABRA
                    System.out.println(wordToGuess);

                    playGame(wordToGuess, userWord, vowel);
                }
                case 3 ->
                    System.out.println("Hasta luego");
                default ->
                    JOptionPane.showMessageDialog(null, "Opcion invalida");
            }
        } while (opcion != 3 && opcion != Integer.MIN_VALUE);
    }

    /**
     * Juega una partida de ahoracado, el usuario va diciendo letras hasta que
     * la palabra del usuario sea igual o haya gastado las siete vidas
     *
     * @param word palabra que tenemos que adivinar
     * @param userWord palabra que va viendo el usuario
     * @param vowel vocal que damos como pista, ya aparecera en la palabra del
     * usuario
     */
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
            isWord = false;
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
                    // En caso de que pulse cancelar terminamos el juego
                    break;
                } else if (temp.equals("")) {
                    temp = "0";
                }

                ch = temp.charAt(0);

                if (Character.isLetter(ch)) {
                    isWord = true;
                } else {
                    JOptionPane.showMessageDialog(null, "No has introducido"
                            + " una letra.");
                }
            } while (!isWord);

            // Cancelamos el juego
            if (Objects.isNull(temp)) {
                JOptionPane.showMessageDialog(null, "Juego cancelado.");
                break;
            }

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
                if (!usedLetters.contains(ch)) {
                    usedLetters.add(ch);
                }
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
     * @return una lista con todas las letras del abecedario español
     */
    public static ArrayList<Character> fillAlphabetChar() {
        Character[] letters = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
            'k', 'l', 'm', 'n', 'ñ', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
            'w', 'x', 'y', 'z'};
        return new ArrayList<>(Arrays.asList(letters));
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
