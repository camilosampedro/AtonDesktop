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

import execution.Result;
import execution.Request;
import identity.ClientComputer;
import identity.ClientUser;
import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Camilo Sampedro
 * @version 0.2.0
 */
public class ServerComunicator extends Comunicator {

    private static ServerComunicator serverComunicatorInstance;

    public static void inicializar(int puerto) {
        ServerComunicator.port = puerto;
        serverComunicatorInstance = new ServerComunicator();
    }

    public static void despertar() {
        serverComunicatorInstance.start();
    }

    @Override
    protected void openChannel() throws SocketException {
        Object[] objetoRecibido = null;
        InetAddress direccion = null;
        try {
            objetoRecibido = listen();
            direccion = (InetAddress) objetoRecibido[0];
            SendableObject sendable = (SendableObject) objetoRecibido[1];
            if (objetoRecibido[1] instanceof Result) {
                Procesador.procesarResultado(direccion.getHostAddress(), (Result) objetoRecibido[1]);
                return;
            }
            if (objetoRecibido[1] instanceof Request) {
                Procesador.procesarSolicitud(direccion.getHostAddress(), (Request) objetoRecibido[1]);
                return;
            }
            if (objetoRecibido[1] instanceof ClientComputer) {
                Procesador.processComputer(direccion.getHostAddress(), (ClientComputer) objetoRecibido[1]);
                return;
            }
            if (objetoRecibido[1] instanceof ClientUser) {
                Procesador.procesarUsuario(direccion.getHostAddress(), (ClientUser) objetoRecibido[1]);
                return;
            }
        } catch (IOException ex) {
            Logger.getLogger(ServerComunicator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
