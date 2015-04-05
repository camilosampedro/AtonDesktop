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

import ejecucion.Solicitud;
import ejecucion.EjecucionRemota;
import ejecucion.Orden;
import ejecucion.Resultado;
import informacion.Informacion;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Camilo Sampedro
 * @version 0.1.0
 */
public class Comunicacion extends ClienteServidor {

    protected static ServerSocket serverSocket;
    protected static String servidor;
    protected static EjecucionRemota ejecucionActual;
    protected static Solicitud solicitudActual;
    protected static Comunicacion cliente_servidor;

    public static void inicializar(String server, int port) {
        servidor = server;
        puerto = port;
        cliente_servidor = new Comunicacion();
    }

    public static void despertar() throws IOException, ClassNotFoundException {
        Comunicacion.enviarObjeto(new Solicitud(Solicitud.CONEXION));
        cliente_servidor.escuchar();
        Comunicacion.enviarObjeto(Informacion.getEquipo());
        cliente_servidor.start();
    }

    public static void ejecutarOrden(Orden ordenActual) {
        ejecucionActual = new EjecucionRemota(ordenActual);
        ejecucionActual.start();
    }

    public static void enviarResultado(Resultado resultado) {
        try {
            Comunicacion.enviarObjeto(resultado);
        } catch (UnknownHostException ex) {
            Logger.getLogger(Comunicacion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SocketException ex) {
            Logger.getLogger(Comunicacion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void enviarObjeto(Enviable o) throws UnknownHostException, SocketException {
        enviarObjeto(o, servidor);
    }

    @Override
    protected void abrirCanal() throws SocketException {
        Object[] objetoRecibido = escuchar();
        if (objetoRecibido[1] instanceof Orden) {
            Procesador.procesarOrden((String) objetoRecibido[0], (Orden) objetoRecibido[1]);
            return;
        }
        if (objetoRecibido[1] instanceof Solicitud) {
            Procesador.procesarSolicitud((String) objetoRecibido[0], (Solicitud) objetoRecibido[1]);
            return;
        }
    }

}
