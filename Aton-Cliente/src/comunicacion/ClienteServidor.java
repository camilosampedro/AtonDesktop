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
import informacion.Informacion;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Camilo Sampedro
 * @version 0.1.0
 */
public class ClienteServidor extends Thread {

    protected static Orden ordenActual;
    protected static ServerSocket serverSocket;
    protected static Socket socket;
    protected static int puerto;
    protected static String servidor;
    protected static EjecucionRemota ejecucionActual;
    protected static Solicitud solicitudActual;
    protected static ClienteServidor cliente_servidor;
    private static boolean conectado;
    private static int intento;

    public static void inicializar(String server, int port) {
        servidor = server;
        puerto = port;
        while (!puertoDisponible() && puerto < port + 20) {
            puerto++;
        }
        cliente_servidor = new ClienteServidor();
        intento = 0;
    }

    private static boolean esAccesible() throws UnknownHostException, IOException {
        return InetAddress.getByName(servidor).isReachable(100);
    }

    private static boolean puertoDisponible() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void run() {
        while (true) {
            try {
                while (!esAccesible()) {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(ClienteServidor.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                escuchar();
            } catch (IOException ex) {
                Logger.getLogger(ClienteServidor.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ClienteServidor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static void despertar() throws IOException, ClassNotFoundException {
        if (Informacion.getUsuario().esRoot()) {
            enviarObjeto(new Solicitud(Solicitud.CONECCION_ROOT));
            escuchar();
            if (conectado) {
                enviarObjeto(Informacion.getEquipo().obtenerMAC());
                enviarObjeto(Informacion.getEquipo().obtenerIP());
                enviarObjeto(Informacion.getEquipo().obtenerHostName());
            }
        } else {
            enviarObjeto(new Solicitud(Solicitud.CONECCION_USUARIO));
            enviarObjeto("HOla");
            escuchar();
            if (conectado) {
                enviarObjeto(Informacion.getUsuario().obtenerNombreDeUsuario());
            }
        }
        cliente_servidor.start();
    }

    public static void escuchar() throws IOException, ClassNotFoundException {
        serverSocket = new ServerSocket(puerto);
        serverSocket.setReuseAddress(true);
        serverSocket.bind(new InetSocketAddress(puerto));
        socket = serverSocket.accept();
        InputStream is = socket.getInputStream();
        ObjectInputStream ois = new ObjectInputStream(is);

        Object obj = ois.readObject();

        if (obj instanceof Orden) {
            ordenActual = (Orden) obj;
            ejecutarOrden();
        }
        if (obj instanceof Solicitud) {
            solicitudActual = (Solicitud) obj;
            ejecutarSolicitud();
        }
    }

    public static void ejecutarOrden() {
        ejecucionActual = new EjecucionRemota(ordenActual);
        ejecucionActual.start();
    }

    public static void enviarResultado(String resultado) {
        try {
            enviarObjeto(resultado);
        } catch (IOException ex) {
            Logger.getLogger(ClienteServidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void enviarObjeto(Object objeto) throws IOException {
        try {
            socket = new Socket(servidor, puerto);
            socket.setReuseAddress(true);
            socket.bind(new InetSocketAddress(puerto));
            OutputStream os = socket.getOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(os);
            oos.writeObject(objeto);
            oos.close();
            os.close();
            socket.close();
            intento = 0;
        } catch (ConnectException ex) {
            try {
                Thread.sleep(1000);
                if (intento < 10) {
                    enviarObjeto(objeto);
                }
            } catch (InterruptedException ex1) {
                Logger.getLogger(ClienteServidor.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
    }

    private static void ejecutarSolicitud() {
        try {
            String resultado;
            boolean enviar = true;
            switch (solicitudActual.getTipo()) {
                case Solicitud.CONECCION_ROOT:
                    conectado = true;
                    enviar = false;
                    resultado = "";
                    break;
                case Solicitud.CONECCION_USUARIO:
                    conectado = true;
                    enviar = false;
                    resultado = "";
                    break;
                case Solicitud.HOST:
                    resultado = Informacion.getEquipo().obtenerHostName();
                    break;
                case Solicitud.IP:
                    resultado = Informacion.getEquipo().obtenerIP();
                    break;
                case Solicitud.MAC:
                    resultado = Informacion.getEquipo().obtenerMAC();
                    break;
                case Solicitud.USUARIO:
                    resultado = Informacion.getUsuario().obtenerNombreDeUsuario();
                    break;
                default:
                    resultado = "";
                    break;
            }
            if (enviar) {
                enviarObjeto("RESPUESTA");
                enviarObjeto(resultado);
            }
        } catch (IOException ex) {
            Logger.getLogger(ClienteServidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
