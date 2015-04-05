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

import ejecucion.Resultado;
import ejecucion.Solicitud;
import exception.NoEncontrado;
import identidad.EquipoCliente;
import identidad.EquipoServidor;
import identidad.UsuarioCliente;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Camilo Sampedro
 * @version 0.1.0
 */
public class Procesador {

    static void procesarEquipo(String remitente, EquipoCliente equipo) {
        try {
            EquipoServidor equipoEncontrado = informacion.Informacion.buscarEquipo(remitente);
            equipoEncontrado.copiarInformacion(equipo);
        } catch (NoEncontrado ex) {
            Logger.getLogger(Procesador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    static void procesarUsuario(String remitente, UsuarioCliente usuario) {
        try {
            EquipoServidor equipoEncontrado = informacion.Informacion.buscarEquipo(remitente);
            equipoEncontrado.asignarUsuario(usuario);
        } catch (NoEncontrado ex) {
            Logger.getLogger(Procesador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    ArrayList<Object[]> resultados;

    static void procesarSolicitud(String remitente, Solicitud solicitud) {
        switch (solicitud.getTipo()) {
            case Solicitud.CONEXION:
                try {
                    informacion.Informacion.agregarEquipoConectado(remitente);
                } catch (NoEncontrado ex) {
                    Logger.getLogger(Procesador.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            default:
                System.err.println("Llegó una solicitud no esperada.");
                break;
        }
    }

    static void procesarResultado(String remitente, Resultado resultado) {
        try {
            EquipoServidor equipo = informacion.Informacion.buscarEquipo(remitente);
            String[] resultadoNuevo = {equipo.getOrdenActual().getOrden(), resultado.getResultado()};
            equipo.addResultado(resultadoNuevo);
            equipo.setOrdenActual(null);
        } catch (NoEncontrado ex) {
            Logger.getLogger(Procesador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
