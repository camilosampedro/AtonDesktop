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

import execution.Order;
import execution.Result;
import execution.Request;
import identidad.ClientComputer;
import identidad.ServerComputer;
import identidad.ClientUser;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Camilo Sampedro
 */
public abstract class Comunicator extends Thread {

    protected static int port;
    protected static ServerSocket serverSocket;

    public Object[] listen() throws SocketException, IOException {
        System.out.println("Listen server has opened, waiting for messages...");
        Socket socket = serverSocket.accept();
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        InetAddress senderIP = socket.getInetAddress();
        String message = in.readLine();
        System.out.println("Client " + senderIP + " : " + message);
//        DatagramPacket packet;
//        DatagramSocket socket;
//        byte[] data;    // Para los datos ser enviados en paquetes
//        int clientPort;
//        int packetSize = SendableObject.PACKETSIZE;
//        InetAddress address;
//        String str;
//
//        socket = new DatagramSocket(port);
//        data = new byte[packetSize];
//
//        // Crea paquetes para recibir el mensaje
//        packet = new DatagramPacket(data, packetSize);
//
//        try {
//            // Esperar indefinidamente a que el paquete llegue
//            socket.receive(packet);
//        } catch (IOException ie) {
//            System.out.println(" No pudo recibir :" + ie.getMessage());
//            System.exit(0);
//        }
//
//        System.out.println("Llegó un paquete");
//        // Obtener datos del cliente para poder hacer echo a los datos
//        address = packet.getAddress();
//        clientPort = packet.getPort();

//        // Creación del String
//        str = new String(data, packet.getOffset(), packet.getLength());
        Object[] returnArray = {senderIP, recoverSendable(message)};

        return returnArray;
    }

    private SendableObject recoverSendable(String stringToConvert) {
        int i = stringToConvert.indexOf(SendableObject.HEADSTART);
        int j = stringToConvert.indexOf(SendableObject.HEADEND);
        String head = stringToConvert.substring(i, j);
        SendableObject object = null;
        if (head.equals(SendableObject.TYPE[SendableObject.CLIENTCOMPUTER])) {
            object = ClientComputer.buildObject(stringToConvert);
        } else if (head.equals(SendableObject.TYPE[SendableObject.SERVERCOMPUTER])) {
            object = ServerComputer.buildObject(stringToConvert);
        } else if (head.equals(SendableObject.TYPE[SendableObject.ORDER])) {
            object = Order.buildObject(stringToConvert);
        } else if (head.equals(SendableObject.TYPE[SendableObject.RESULT])) {
            object = Result.buildObject(stringToConvert);
        } else if (head.equals(SendableObject.TYPE[SendableObject.REQUEST])) {
            object = Request.buildObject(stringToConvert);
        } else if (head.equals(SendableObject.TYPE[SendableObject.USERCLIENT])) {
            object = ClientUser.construirObjeto(stringToConvert);
        }
        return object;
    }

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(port);
            while (true) {
                try {
                    openChannel();
                } catch (SocketException ex) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ex1) {
                        Logger.getLogger(Comunicator.class.getName()).log(Level.SEVERE, null, ex1);
                    }
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Comunicator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected abstract void openChannel() throws SocketException;

    public static void sendObject(SendableObject o, String ipDestino) throws UnknownHostException, SocketException, IOException, ConnectException {
        Socket socket = new Socket(ipDestino, port);
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader in
                = new BufferedReader(
                        new InputStreamReader(socket.getInputStream()));
        String message = o.generateString();
        System.out.println("Mensaje: " + message);
        out.println(message);
//        
////        DatagramSocket socket; // Como se envian los paquetes
////        DatagramPacket packet; // Lo que se envia en los paquetes
////        InetAddress address;   // A donde se envian los paquetes
////        String messageSend;    // Mensaje a ser enviado
////        String messageReturn;  // Lo que se obtiene del Server
////        int packetSize = SendableObject.PACKETSIZE;
////        byte[] data;
////
////        // Obtener la direccion IP del  Server
////        address = InetAddress.getByName(ipDestino);
////        socket = new DatagramSocket();
//
//        //data = new byte[packetSize];
////        messageSend = o.generateString();
////        data = messageSend.getBytes();
//        // messageSend.getBytes(0, messageSend.length(), data, 0);
//        if (data.length > packetSize) {
//            return;
//        }
//
//        // recordar a los datagramas guardar los bytes
//        packet = new DatagramPacket(data, data.length, address, port);
//        System.out.println(" Tratando de enviar el paquete a " + ipDestino);
//
//        try {
//            // envia el paquete
//
//            socket.send(packet);
//            System.out.println("Paquete enviado.");
//
//        } catch (IOException ie) {
//            System.out.println("No pudo ser enviado  :" + ie.getMessage());
//            System.exit(1);
//        }
    }

    public static boolean isReachable(String ip) throws UnknownHostException, IOException {
        return InetAddress.getByName(ip).isReachable(100);
    }

}
