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
package execution;

import comunication.ClientComunicator;
import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import logs.LogCreator;

/**
 *
 * @author Camilo Sampedro
 * @version 0.1.0
 */
public class RemoteExecution extends Thread {

    /**
     * Order a execute.
     */
    protected Order order;

    public RemoteExecution(Order order) {
        this.order = order;
    }

    /**
     * Ejecuta el hilo.
     */
    @Override
    public void run() {
        try {
            Execution.execute(order);
            ClientComunicator.sendObject(order.getResult());
        } catch (IOException | InterruptedException ex) {
            LogCreator.addToLog(LogCreator.ERROR, "Se produjo un error en la ejecución de la orden.");
            Logger.getLogger(RemoteExecution.class.getName()).log(Level.SEVERE, null, ex);
            try {
                ClientComunicator.sendObject(new Result("ERROR"));
            } catch (UnknownHostException ex1) {
                Logger.getLogger(RemoteExecution.class.getName()).log(Level.SEVERE, null, ex1);
            } catch (SocketException ex1) {
                Logger.getLogger(RemoteExecution.class.getName()).log(Level.SEVERE, null, ex1);
            } catch (IOException ex1) {
                Logger.getLogger(RemoteExecution.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }

    }

    /**
     * @return resultado de la ejecución.
     */
    public Result getResult() {
        return order.getResult();
    }

}
