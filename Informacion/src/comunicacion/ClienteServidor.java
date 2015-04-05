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

import ejecucion.Orden;
import ejecucion.Resultado;
import ejecucion.Solicitud;
import identidad.EquipoCliente;
import identidad.EquipoServidor;
import identidad.UsuarioCliente;
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
public abstract class ClienteServidor extends Thread {

    protected static int puerto;

    public Object[] escuchar() throws SocketException {
        DatagramPacket packet;
        DatagramSocket socket;
        byte[] data;    // Para los datos ser enviados en paquetes
        int clientPort;
        int packetSize = Enviable.TAMAÑOPAQUETE;
        InetAddress address;
        String str;

        socket = new DatagramSocket(puerto);
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

        // Creación del String
        str = new String(data, packet.getOffset(), packet.getLength());
        Object[] retorno = {address, recuperarEnviable(str)};

        return retorno;
    }

    private Enviable recuperarEnviable(String str) {
        int i = str.indexOf(Enviable.INICIOCABECERA);
        int j = str.indexOf(Enviable.FINCABECERA);
        String cabecera = str.substring(i, j);
        Enviable objeto = null;
        if (cabecera.equals(Enviable.TIPO[Enviable.EQUIPOCLIENTE])) {
            objeto = EquipoCliente.construirObjeto(str);
        } else if (cabecera.equals(Enviable.TIPO[Enviable.EQUIPOSERVIDOR])) {
            objeto = EquipoServidor.construirObjeto(str);
        } else if (cabecera.equals(Enviable.TIPO[Enviable.ORDEN])) {
            objeto = Orden.construirObjeto(str);
        } else if (cabecera.equals(Enviable.TIPO[Enviable.RESULTADO])) {
            objeto = Resultado.construirObjeto(str);
        } else if (cabecera.equals(Enviable.TIPO[Enviable.SOLICITUD])) {
            objeto = Solicitud.construirObjeto(str);
        } else if (cabecera.equals(Enviable.TIPO[Enviable.USUARIOCLIENTE])) {
            objeto = UsuarioCliente.construirObjeto(str);
        }
        return objeto;
    }

    @Override
    public void run() {
        while (true) {
            try {
                abrirCanal();
            } catch (SocketException ex) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex1) {
                    Logger.getLogger(ClienteServidor.class.getName()).log(Level.SEVERE, null, ex1);
                }
            }
        }
    }

    protected abstract void abrirCanal() throws SocketException;

    public static void enviarObjeto(Enviable o, String ipDestino) throws UnknownHostException, SocketException {
        DatagramSocket socket; // Como se envian los paquetes
        DatagramPacket packet; // Lo que se envia en los paquetes
        InetAddress address;   // A donde se envian los paquetes
        String messageSend;    // Mensaje a ser enviado
        String messageReturn;  // Lo que se obtiene del Server
        int packetSize = Enviable.TAMAÑOPAQUETE;
        byte[] data;

        // Obtener la direccion IP del  Server
        address = InetAddress.getByName(ipDestino);
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

    public static boolean esAccesible(String ip) throws UnknownHostException, IOException {
        return InetAddress.getByName(ip).isReachable(100);
    }

}
