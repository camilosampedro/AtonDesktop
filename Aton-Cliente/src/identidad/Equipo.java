/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package identidad;

import ejecucion.Ejecutar;
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
    protected boolean enUso;
    private Usuario usuario;
    public boolean NOUSADO = false;
    public boolean ENCENDIDO = true;
    public boolean NOENCENDIDO = false;

    public static String obtenerIP() {
        if (Usuario.esRoot() & (ip == null | ip.equals(""))) {
            try {
                ip = Ejecutar.ejecutar(new Orden("ifconfig eth0 2>/dev/null|awk '/Direc. inet:/ {print $2}'|sed 's/inet://'"));
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
                mac = Ejecutar.ejecutar(new Orden("ifconfig eth0 2>/dev/null|awk '/direcciónHW/ {print $5}'"));
            } catch (IOException | InterruptedException ex) {
                Logger.getLogger(Equipo.class.getName()).log(Level.SEVERE, null, ex);
                CreadorLog.agregarALog(CreadorLog.ERROR, "Error al buscar la MAC del equipo");
            }
        }
    }

    public static String obtenerHostName() {

    }

    public static void asignarHostName() {

    }

    public Usuario obtenerUsuario() {
        return usuario;
    }

    public void asignarUsuario(Usuario usuario) {

    }

    public void asignarIP(String ip) {

    }

    public void asignarMAC(String mac) {

    }

    public void asignarNombre(String nombre) {

    }
}
