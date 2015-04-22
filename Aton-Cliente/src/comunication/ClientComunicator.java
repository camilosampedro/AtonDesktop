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
import execution.Order;
import execution.Result;
import identity.ClientUser;
import information.Information;
import international.LanguagesController;
import java.io.IOException;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Camilo Sampedro
 * @version 0.1.5
 */
public class ClientComunicator extends Comunicator {

    protected static String serverIP;
    protected static ClientComunicator clientComunicatorInstance;

    /**
     * Inicializa las variables estáticas
     *
     * @param serverIP Servidor donde se centraliza todo el control
     * @param comunicationPort Puerto por el cual se enviarán y se recibirán los
     * mensajes
     */
    public static void initialize(String serverIP, int comunicationPort) {
        ClientComunicator.serverIP = serverIP;
        ClientComunicator.port = comunicationPort;
        clientComunicatorInstance = new ClientComunicator();
    }

    /**
     * Solicita conexión con el servidor y abre el hilo de escucha.
     *
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static void wakeUp() throws IOException, ClassNotFoundException {
        ClientComunicator.sendObject(new Request(Request.CONECTION));
        ClientComunicator.sendObject(Information.getActualComputer());
        for (ClientUser user : ClientUser.generateUserList()) {
            ClientComunicator.sendObject(user);
        }
        clientComunicatorInstance.start();
    }

    /**
     * Envia el resultado de una ejecución.
     *
     * @param result Result a enviar.
     */
    public static void sendResult(Result result) {
        try {
            ClientComunicator.sendObject(result);
        } catch (UnknownHostException ex) {
            Logger.getLogger(ClientComunicator.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SocketException ex) {
            Logger.getLogger(ClientComunicator.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ClientComunicator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Envía el objeto a la dirección ip del servidor.
     *
     * @throws java.net.ConnectException
     * @see Comunicator
     * @param object Objeto a enviar.
     * @throws UnknownHostException
     * @throws SocketException
     */
    public static void sendObject(SendableObject object) throws UnknownHostException, SocketException, IOException, ConnectException {
        Comunicator.sendObject(object, serverIP);
    }

    /**
     * Abre el canal de escucha y procesa el objeto llega por este.
     *
     * @throws SocketException
     */
    @Override
    protected void openChannel() throws SocketException {
        Object[] receivedObject;
        try {
            receivedObject = listen();
            InetAddress direccion = (InetAddress) receivedObject[0];
            if (!direccion.getHostAddress().equals(serverIP)){
                logs.LogCreator.addToLog(logs.LogCreator.WARNING, LanguagesController.getWord("Message is not from server"));
                return;
            }
            if (receivedObject[1] instanceof Order) {
                Processor.processOrder(direccion.getHostAddress(), (Order) receivedObject[1]);
                return;
            }
            if (receivedObject[1] instanceof Request) {
                Processor.processRequest(direccion.getHostAddress(), (Request) receivedObject[1]);
            }
        } catch (IOException ex) {
            Logger.getLogger(ClientComunicator.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(ClientComunicator.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
