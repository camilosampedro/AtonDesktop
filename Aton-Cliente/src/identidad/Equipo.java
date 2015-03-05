package identidad;

import ejecucion.Ejecutar;
import ejecucion.Funciones;
import ejecucion.Orden;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import logs.CreadorLog;

/**
 *
 * @author camilo
 */
public class Equipo implements Serializable {

    protected int numero;
    protected static String mac;
    protected static String ip;
    protected static String hostname;
    protected boolean enUso;
    private Usuario usuario;
    public boolean NOUSADO = false;
    public boolean ENCENDIDO = true;
    public boolean NOENCENDIDO = false;

    public static String obtenerIP() {
        if (Usuario.esRoot() & (ip == null | ip.equals(""))) {
            try {
                Orden orden = new Orden(Funciones.ORDENIP);
                Ejecutar.ejecutar(orden);
                ip = orden.getResultado();
                // Si la terminal está en inglés:
                //Ejecutar.ejecutar("ifconfig eth0 2>/dev/null|awk '/inet addr:/ {print $2}'|sed 's/addr://'");
            } catch (IOException | InterruptedException ex) {
                Logger.getLogger(Equipo.class.getName()).log(Level.SEVERE, null, ex);
                CreadorLog.agregarALog(CreadorLog.ERROR, "Error al buscar la IP del equipo");
            }
        }
        return ip;
    }

    public static String obtenerMAC() {
        if (Usuario.esRoot() & (mac == null | mac.equals(""))) {
            try {
                Orden orden = new Orden(Funciones.ORDENMAC);
                Ejecutar.ejecutar(orden);
                mac = orden.getResultado();
            } catch (IOException | InterruptedException ex) {
                Logger.getLogger(Equipo.class.getName()).log(Level.SEVERE, null, ex);
                CreadorLog.agregarALog(CreadorLog.ERROR, "Error al buscar la MAC del equipo");
                return null;
            }
        }
        return mac;
    }

    public static String obtenerHostName() {
        try {
            Orden orden = new Orden(Funciones.ORDENHOST);
            Ejecutar.ejecutar(orden);
            hostname = orden.getResultado();
        } catch (IOException ex) {
            Logger.getLogger(Equipo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(Equipo.class.getName()).log(Level.SEVERE, null, ex);
        }
        return hostname;
    }

    public static void asignarHostName(String hostname) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Usuario obtenerUsuario() {
        return usuario;
    }

    public void asignarIP(String ip) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
