package ejecucion;

import comunicacion.Cliente_Servidor;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import logs.CreadorLog;

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

    public EjecucionRemota(Orden orden) {
        this.orden = orden;
    }

    /**
     * Ejecuta el hilo.
     */
    @Override
    public void run() {
        try {
            Ejecutar.ejecutar(orden);
            try {
                Cliente_Servidor.enviarObjeto(orden);
            } catch (IOException ex) {
                CreadorLog.agregarALog(CreadorLog.ERROR, "Se produjo al enviar el resultado de la orden.");
                Logger.getLogger(EjecucionRemota.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (IOException | InterruptedException ex) {
            CreadorLog.agregarALog(CreadorLog.ERROR, "Se produjo un error en la ejecución de la orden.");
            Logger.getLogger(EjecucionRemota.class.getName()).log(Level.SEVERE, null, ex);
            try {
                Cliente_Servidor.enviarObjeto("ERROR");
            } catch (IOException ex1) {
                CreadorLog.agregarALog(CreadorLog.ERROR, "Se produjo un error al enviar el resultado de la orden, cuando ya había un error.");
                Logger.getLogger(EjecucionRemota.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }

    }

    /**
     * @return resultado de la ejecución.
     */
    public String obtenerResultado() {
        return orden.getResultado();
    }

}
