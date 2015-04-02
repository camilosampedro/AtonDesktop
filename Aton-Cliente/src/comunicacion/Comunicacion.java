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
import identidad.UsuarioCliente;
import informacion.Informacion;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Camilo Sampedro
 * @version 0.1.0
 */
public class Comunicacion extends Thread implements ClienteServidor {

    protected static Orden ordenActual;
    protected static ServerSocket serverSocket;
    protected static Socket socket;
    protected static int puerto;
    protected static String servidor;
    protected static EjecucionRemota ejecucionActual;
    protected static Solicitud solicitudActual;
    protected static Comunicacion cliente_servidor;
    private static boolean conectado;

    public static void inicializar(String server, int port) {
        servidor = server;
        puerto = port;
        cliente_servidor = new Comunicacion();
    }

    private static boolean esAccesible() throws UnknownHostException, IOException {
        return InetAddress.getByName(servidor).isReachable(100);
    }

//    private static boolean puertoDisponible() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
    @Override
    public void run() {
        while (true) {
            try {
                while (!esAccesible()) {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Comunicacion.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                escuchar();
            } catch (IOException ex) {
                Logger.getLogger(Comunicacion.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static void despertar() throws IOException, ClassNotFoundException {
        cliente_servidor.enviar(new Solicitud(Solicitud.CONEXION));
        cliente_servidor.escuchar();
        cliente_servidor.enviar(Informacion.getEquipo());
        cliente_servidor.start();
    }

    @Override
    public String escuchar() throws SocketException {
        DatagramPacket packet;
        DatagramSocket socketEscucha;
        byte[] data;    // Para los datos ser enviados en paquetes
        int clientPort;
        int packetSize = Enviable.TAMAÑOPAQUETE;
        InetAddress address;
        String str;

        socketEscucha = new DatagramSocket(puerto);
        data = new byte[packetSize];

        // Crea paquetes para recibir el mensaje
        packet = new DatagramPacket(data, packetSize);
        //System.out.println("Esperando para recibir los paquetes");

        try {

            // Esperar indefinidamente a que el paquete llegue
            socketEscucha.receive(packet);

        } catch (IOException ie) {
            System.out.println(" No pudo recibir :" + ie.getMessage());
            System.exit(0);
        }

        // Obtener datos del cliente para poder hacer echo a los datos
        address = packet.getAddress();
        clientPort = packet.getPort();

        // Imprime en pantalla la cadena que fue recibida en la consola del server
        str = new String(data, 0, packet.getLength());
        if (!address.getHostName().equals(servidor)) {
            return "";
        }
        return str;
    }

    public static void ejecutarOrden() {
        ejecucionActual = new EjecucionRemota(ordenActual);
        ejecucionActual.start();
    }

    public static void enviarResultado(Resultado resultado) {
        try {
            cliente_servidor.enviar(resultado);
        } catch (UnknownHostException ex) {
            Logger.getLogger(Comunicacion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SocketException ex) {
            Logger.getLogger(Comunicacion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void enviar(Enviable o) throws UnknownHostException, SocketException {
        DatagramSocket socket; // Como se envian los paquetes
        DatagramPacket packet; // Lo que se envia en los paquetes
        InetAddress address;   // A donde se envian los paquetes
        String messageSend;    // Mensaje a ser enviado
        String messageReturn;  // Lo que se obtiene del Server
        int packetSize = Enviable.TAMAÑOPAQUETE;
        byte[] data;

        // Obtener la direccion IP del  Server
        address = InetAddress.getByName(servidor);
        socket = new DatagramSocket();

        //data = new byte[packetSize];
        messageSend = o.generarCadena();
        data = messageSend.getBytes();
        // messageSend.getBytes(0, messageSend.length(), data, 0);
        if (data.length > packetSize) {
            return;
        }

        // recordar a los datagramas guardar los bytes
        packet = new DatagramPacket(data, data.length, address, puerto);
        System.out.println(" Tratando de enviar el paquete ");

        try {
            // envia el paquete

            socket.send(packet);

        } catch (IOException ie) {
            System.out.println("No pudo ser enviado  :" + ie.getMessage());
            System.exit(0);
        }
    }

    public static void enviarObjeto(Enviable o) throws UnknownHostException, SocketException {
        cliente_servidor.enviar(o);
    }

}
