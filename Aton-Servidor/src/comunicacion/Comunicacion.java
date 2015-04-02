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

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Camilo Sampedro
 */
public class Comunicacion extends Thread implements ClienteServidor {

    static int serverPort;
    static String ipServidor;

    public Comunicacion(int puerto, String servidor) {
        serverPort = puerto;
        ipServidor = servidor;
    }

    @Override
    public String escuchar() throws SocketException {
        DatagramPacket packet;
        DatagramSocket socket;
        byte[] data;    // Para los datos ser enviados en paquetes
        int clientPort;
        int packetSize = Enviable.TAMAÑOPAQUETE;
        InetAddress address;
        String str;

        socket = new DatagramSocket(serverPort);
        data = new byte[packetSize];

        // Crea paquetes para recibir el mensaje
        packet = new DatagramPacket(data, packetSize);
        System.out.println("Esperando para recibir los paquetes");

        try {

            // Esperar indefinidamente a que el paquete llegue
            socket.receive(packet);

        } catch (IOException ie) {
            System.out.println(" No pudo recibir :" + ie.getMessage());
            System.exit(0);
        }

        // Obtener datos del cliente para poder hacer echo a los datos
        address = packet.getAddress();
        clientPort = packet.getPort();

        // Imprime en pantalla la cadena que fue recibida en la consola del server
        str = new String(data, 0, 0, packet.getLength());
        return str;
    }

    public void enviarObjeto(Enviable objeto) throws UnknownHostException, SocketException {
        DatagramSocket socket; // Como se envian los paquetes
        DatagramPacket packet; // Lo que se envia en los paquetes
        InetAddress address;   // A donde se envian los paquetes
        String messageSend;    // Mensaje a ser enviado
        String messageReturn;  // Lo que se obtiene del Server
        int packetSize = Enviable.TAMAÑOPAQUETE;
        byte[] data;

        // Obtener la direccion IP del  Server
        address = InetAddress.getByName(ipServidor);
        socket = new DatagramSocket();

        data = new byte[packetSize];
        messageSend = objeto.generarCadena();
        messageSend.getBytes(0, messageSend.length(), data, 0);

        // recordar a los datagramas guardar los bytes
        packet = new DatagramPacket(data, data.length, address, serverPort);
        System.out.println(" Tratando de enviar el paquete ");

        try {
            // envia el paquete

            socket.send(packet);

        } catch (IOException ie) {
            System.out.println("No pudo ser enviado  :" + ie.getMessage());
            System.exit(0);
        }

        //El paquete es reinicializado para usarlo para recibir
        packet = new DatagramPacket(data, data.length);

        try {
            // Recibe el paquete del server

            socket.receive(packet);

        } catch (IOException iee) {
            System.out.println("No lo pudo recibir  : " + iee.getMessage());
            System.exit(0);
        }

        // mostrar el mensaje recibido
        messageReturn = new String(packet.getData(), 0);
        System.out.println("Mensaje devuelto : " + messageReturn.trim());
    }

    @Override
    public void run() {
        while (true) {
            try {
                escuchar();
            } catch (SocketException ex) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex1) {
                    Logger.getLogger(Comunicacion.class.getName()).log(Level.SEVERE, null, ex1);
                }
            }
        }
    }

}
