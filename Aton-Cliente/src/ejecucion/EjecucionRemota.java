package ejecucion;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Camilo Sampedro
 */
public class EjecucionRemota extends Thread {

    /**
     * Orden a ejecutar.
     */
    protected Orden orden;

    public static final boolean SUDO = true;
    public boolean USER = false;

    /**
     * Resultado de la ejecución.
     */
    protected String resultado = null;

    public EjecucionRemota(Orden orden) {
        this.orden = orden;
    }

    /**
     * Ejecuta el hilo.
     */
    @Override
    public void run() {
        try {
            resultado = Ejecutar.ejecutar(orden);
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(EjecucionRemota.class.getName()).log(Level.SEVERE, null, ex);
        }
        Cliente.
    }

    /**
     * @return resultado de la ejecución.
     */
    public String obtenerResultado() {
        return resultado;
    }

}
