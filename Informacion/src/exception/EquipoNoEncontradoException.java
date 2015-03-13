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
public class EquipoNoEncontradoException extends Exception {

    public EquipoNoEncontradoException(String mensaje) {
        super("Equipo no encontrado: " + mensaje);
    }
}
