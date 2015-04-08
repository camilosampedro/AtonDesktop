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
package comunication;

import execution.Request;
import ejecucion.EjecucionRemota;
import execution.Order;
import execution.Result;
import informacion.Informacion;
import java.io.IOException;
import java.net.ConnectException;
import java.net.ServerSocket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Camilo Sampedro
 * @version 0.1.5
 */
public class Comunicacion extends Comunicator {

    protected static String ipServidor;
    protected static EjecucionRemota ejecucionActual;
    protected static Request solicitudActual;
    protected static Comunicacion cliente_servidor;

    /**
     * Inicializa las variables estáticas
     *
     * @param ipServidor Servidor donde se centraliza todo el control
     * @param puertoComunicacion Puerto por el cual se enviarán y se recibirán
     * los mensajes
     */
    public static void inicializar(String ipServidor, int puertoComunicacion) {
        Comunicacion.ipServidor = ipServidor;
        Comunicacion.port = puertoComunicacion;
        cliente_servidor = new Comunicacion();
    }

    /**
     * Solicita conexión con el servidor y abre el hilo de escucha.
     *
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static void despertar() throws IOException, ClassNotFoundException {
        Comunicacion.enviarObjeto(new Request(Request.CONECTION));
//        cliente_servidor.listen();
        Comunicacion.enviarObjeto(Informacion.getEquipo());
        cliente_servidor.start();
    }

    /**
     * Envia el resultado de una ejecución.
     *
     * @param resultado Result a enviar.
     */
    public static void enviarResultado(Result resultado) {
        try {
            Comunicacion.enviarObjeto(resultado);
        } catch (UnknownHostException ex) {
            Logger.getLogger(Comunicacion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SocketException ex) {
            Logger.getLogger(Comunicacion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Comunicacion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Envía el objeto a la dirección ip del servidor.
     *
     * @throws java.net.ConnectException
     * @see Comunicator
     * @param o Objeto a enviar.
     * @throws UnknownHostException
     * @throws SocketException
     */
    public static void enviarObjeto(SendableObject o) throws UnknownHostException, SocketException, IOException, ConnectException {
        sendObject(o, ipServidor);
    }

    /**
     * Abre el canal de escucha y procesa el objeto llega por este.
     *
     * @throws SocketException
     */
    @Override
    protected void openChannel() throws SocketException {
        Object[] objetoRecibido = null;
        try {
            objetoRecibido = listen();
        } catch (IOException ex) {
            Logger.getLogger(Comunicacion.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (objetoRecibido[1] instanceof Order) {
            Procesador.procesarOrden((String) objetoRecibido[0], (Order) objetoRecibido[1]);
            return;
        }
        if (objetoRecibido[1] instanceof Request) {
            Procesador.procesarSolicitud((String) objetoRecibido[0], (Request) objetoRecibido[1]);
            return;
        }
    }

}
