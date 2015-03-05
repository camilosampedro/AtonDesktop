/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comunicacion;

/**
 *
 * @author camilo
 */
public class Solicitud {
    private final byte solicitud;

    public static final byte IP = 0;
    public static final byte MAC = 1;
    public static final byte HOST = 2;
    public static final byte USUARIO = 3;

    public Solicitud(byte tipo) {
        this.solicitud = tipo;
    }

    public byte getTipo() {
        return solicitud;
    }
}
