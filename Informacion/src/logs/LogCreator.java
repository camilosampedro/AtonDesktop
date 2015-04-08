/*
 * The MIT License
 *
 * Copyright 2015 Camilo Sampedro.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
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
 * @author Camilo Sampedro
 * @version 0.1.0
 */
public class LogCreator {

    protected static File archivoLog;
    public static final byte INFORMACION = 0;
    public static final byte ADVERTENCIA = 1;
    public static final byte ERROR = 2;

    public static void asignarArchivoLog(String ruta) {
        archivoLog = new File(ruta);
    }

    public static void addToLog(byte tipo, String mensaje) {
        switch (tipo) {
            case INFORMACION:
                agregarALog("   [INFO] " + mensaje);
                System.out.println("   [INFO] " + mensaje);
                break;
            case ADVERTENCIA:
                agregarALog("  +[WARN] " + mensaje);
                System.out.println("  +[WARN] " + mensaje);
                break;
            case ERROR:
                agregarALog("*  [ERROR] " + mensaje);
                System.err.println("*  [ERROR] " + mensaje);
                break;
            default:
                agregarALog(mensaje);
                System.out.println("mensaje");
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
            Logger.getLogger(LogCreator.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fw.close();
            } catch (IOException ex) {
                System.err.println("Ocurrió un error al intentar cerrar el flujo del log");
                Logger.getLogger(LogCreator.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
