/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exception;

/**
 *
 * @author Camilo Sampedro
 * @version 0.1.0
 */
public class NoEncontrado extends Exception {

    public static final byte EQUIPO = 0;
    public static final byte FILA = 1;
    public static final String[] textos = {"Equipo", "Fila"};

    public NoEncontrado(byte tipo, String mensaje) {

        super(textos [tipo] + " no encontrad@: " + mensaje);

    }
}
