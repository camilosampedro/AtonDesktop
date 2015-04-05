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
package comunicacion;

import ejecucion.EjecucionRemota;
import ejecucion.Orden;
import ejecucion.Resultado;
import ejecucion.Solicitud;
import informacion.Informacion;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Camilo Sampedro
 */
class Procesador {

    static void procesarOrden(String remitente, Orden orden) {
        EjecucionRemota ejecucion = new EjecucionRemota(orden);
        ejecucion.start();
    }

    static void procesarSolicitud(String remitente, Solicitud solicitud) {
        switch (solicitud.getTipo()) {
            case Solicitud.CONEXION:
                try {
                    Comunicacion.enviarObjeto(Informacion.getEquipo());
                } catch (UnknownHostException ex) {
                    Logger.getLogger(Procesador.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SocketException ex) {
                    Logger.getLogger(Procesador.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case Solicitud.HOST:
                Resultado resultado = new Resultado(Informacion.getEquipo().obtenerHostname());
                try {
                    Comunicacion.enviarObjeto(resultado);
                } catch (UnknownHostException ex) {
                    Logger.getLogger(Procesador.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SocketException ex) {
                    Logger.getLogger(Procesador.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case Solicitud.USUARIO:
                try {
                    Comunicacion.enviarObjeto(informacion.Informacion.getUsuario());
                } catch (UnknownHostException ex) {
                    Logger.getLogger(Procesador.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SocketException ex) {
                    Logger.getLogger(Procesador.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            default:
                System.err.println("Llegó una solicitud no esperada.");
                break;
        }
    }

}