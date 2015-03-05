/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logs;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author camilo
 */
public class CreadorLog {

    protected static File archivoLog;
    public static final byte INFORMACION = 0;
    public static final byte ADVERTENCIA = 1;
    public static final byte ERROR = 2;

    public static void asignarArchivoLog(String ruta) {
        archivoLog = new File(ruta);
    }

    public static void agregarALog(byte tipo, String mensaje) {
        switch (tipo) {
            case INFORMACION:
                agregarALog("   [INFO] " + mensaje);
                break;
            case ADVERTENCIA:
                agregarALog("  +[WARN] " + mensaje);
                break;
            case ERROR:
                agregarALog("*  [ERROR] " + mensaje);
                break;
            default:
                agregarALog(mensaje);
                break;
        }
    }

    private static void agregarALog(String mensaje) {
        FileWriter fw = null;
        try {
            if (!archivoLog.exists()) {
                archivoLog.createNewFile();
            }
            fw = new FileWriter(archivoLog.getAbsoluteFile());
            try (BufferedWriter bw = new BufferedWriter(fw)) {
                bw.write(mensaje);
            }
        } catch (IOException ex) {
            System.err.println("Ocurrió un error al intentar crear el log");
            Logger.getLogger(CreadorLog.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fw.close();
            } catch (IOException ex) {
                System.err.println("Ocurrió un error al intentar cerrar el flujo del log");
                Logger.getLogger(CreadorLog.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
