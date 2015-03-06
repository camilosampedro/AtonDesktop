package ejecucion;

import java.io.Serializable;

/**
 *
 * @author Camilo Sampedro
 */
public class Solicitud implements Serializable {

    private final byte solicitud;

    public static final byte CONECCION_ROOT = 0;
    public static final byte CONECCION_USUARIO = 1;
    public static final byte IP = 2;
    public static final byte MAC = 3;
    public static final byte HOST = 4;
    public static final byte USUARIO = 5;

    public Solicitud(byte tipo) {
        this.solicitud = tipo;
    }

    public byte getTipo() {
        return solicitud;
    }
}
