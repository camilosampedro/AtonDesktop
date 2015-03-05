package identidad;

import ejecucion.Ejecutar;
import ejecucion.Orden;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import logs.CreadorLog;

/**
 * Contiene la información del usuario que ejecuta el servicio.
 * @author Camilo Sampedro
 */
public class Usuario implements Serializable{

    // I. Variables generales.
    
    /**
     * Nombre del usuario que está ejecutando el servicio.
     */
    protected static String nombreDeUsuario;
    /**
     * Contiene información de si el usuario es root o no.
     */
    private static boolean esRoot = false;
    /**
     * Previene la verificación de root múltiple, si ya está verificada.
     */
    protected static boolean estaVerificadoRoot = false;

    // II. Métodos.
    
    /**
     * Verifica si el usuario que ejecutó el programa es root o no.
     * @return True: El usuario es root. False: El usuario no es root.
     */
    public static boolean esRoot() {
        // Si ya fue calculado anteriormente, no se calcula de nuevo.
        if (estaVerificadoRoot = false) {
            try {
                //"id - u" retorna 0 si el usuario es root.
                esRoot = Ejecutar.ejecutar(new Orden("id -u")).equals("0");
                estaVerificadoRoot = true;
                return esRoot;
            } catch (IOException ex) {
                System.err.println("Ocurrió n error intentando verificar si se es root: I/O");
                Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
            }
            CreadorLog.agregarALog(CreadorLog.ERROR, "Ocurrió un error intentando verificar si se es root");
            return false;
        }
        return esRoot;
    }

    /**
     * Obtiene el nombre del usuario que está ejecutando el servicio.
     * @return String con el nombre del usuario.
     */
    public static String obtenerNombreDeUsuario() {
        // Si ya fue calculado anteriormente, no se calcula de nuevo.
        if (nombreDeUsuario == null | nombreDeUsuario.equals("")) {
            try {
                nombreDeUsuario = Ejecutar.ejecutar(new Orden("whoamai"));
                return nombreDeUsuario;
            } catch (IOException | InterruptedException ex) {
                Logger.getLogger(Usuario.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.err.println("Ocurrió un error obteniendo el nombre del usuario");
            CreadorLog.agregarALog(CreadorLog.ERROR, "Ocurrió un error intentando obtener el nombre del usuario");
        }
        return nombreDeUsuario;
    }

}
