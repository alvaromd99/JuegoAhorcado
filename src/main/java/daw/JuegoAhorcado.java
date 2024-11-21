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

        do {
            opcion = getOption(menu);
        } while (opcion != 3 && opcion != Integer.MIN_VALUE);
    }
    
    /**
     * Pide al usuario, usando joption, una opcion mostrando un menu
     * 
     * @param menu el menu que vamos a mostrar
     * @return  el numero entero que representa la opcion que ha elegido el 
     *          usuario
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
}
