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
package ejecucion;

/**
 *
 * Clase con todas las funciones necesarias y usadas por diferentes clases.
 *
 * @author Camilo Sampedro
 * @version 0.1.0
 */
public final class Funciones {

    public static final String ORDENIP = "ifconfig eth0 2>/dev/null|awk '/Direc. inet:/ {print $2}'|sed 's/inet://'";
    public static final String ORDENMAC = "ifconfig eth0 2>/dev/null|awk '/direcciónHW/ {print $5}'";
    public static final String ORDENHOST = "cat /etc/hostname";
    public static final String ORDENVERIFICACIONROOT = "id -u";
    public static final String ORDENUSUARIO = "whoamai";
    public static final String ORDEN_APAGAR = "shutdown -h now";
    public static final String ORDEN_OBTENER_IP = "ifconfig eth0 2>/dev/null|awk '/Direc. inet:/ {print $2}'|sed 's/inet://'";

    public static final String ORDENDESPERTAREQUIPO(int sufijoIPSala, String mac) {
        return "wakeonlan -i 192.168." + sufijoIPSala + ".255 " + mac;
    }

    public static String ORDENNOTIFICACION(String mensaje) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
