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
import exception.NotFound;
import identity.ClientComputer;
import identity.ServerComputer;
import identity.ClientUser;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Camilo Sampedro
 * @version 0.1.0
 */
public class Procesador {

    static void processComputer(String remitente, ClientComputer computer) {
        try {
            ServerComputer equipoEncontrado = information.Information.findByIP(remitente);
            equipoEncontrado.copyComputer(computer);
            equipoEncontrado.setPowerState(ServerComputer.POWERED_ON_STATE);
        } catch (NotFound ex) {
            Logger.getLogger(Procesador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    static void procesarUsuario(String remitente, ClientUser usuario) {
        try {
            ServerComputer equipoEncontrado = information.Information.findByIP(remitente);
            equipoEncontrado.addUser(usuario);
        } catch (NotFound ex) {
            Logger.getLogger(Procesador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    ArrayList<Object[]> resultados;

    static void procesarSolicitud(String remitente, Request solicitud) {
        switch (solicitud.getType()) {
            case Request.CONECTION:
                try {
                    information.Information.agregarEquipoConectado(remitente);
                } catch (NotFound ex) {
                    Logger.getLogger(Procesador.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            default:
                System.err.println("Llegó una solicitud no esperada.");
                break;
        }
    }

    static void procesarResultado(String remitente, Result resultado) {
        try {
            ServerComputer equipo = information.Information.findByIP(remitente);
            String[] resultadoNuevo = {equipo.getActualOrder().getOrder(), resultado.getResult()};
            equipo.addResult(resultadoNuevo);
            equipo.setActualOrder(null);
        } catch (NotFound ex) {
            Logger.getLogger(Procesador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
