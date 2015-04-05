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
import identidad.EquipoCliente;
import identidad.UsuarioCliente;
import java.io.IOException;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Camilo Sampedro
 * @version 0.2.0
 */
public class Comunicacion extends ClienteServidor {

    private static Comunicacion cliente_servidor;

    public static void inicializar(int puerto) {
        Comunicacion.puerto = puerto;
        cliente_servidor = new Comunicacion();
    }

    public static void despertar() {
        cliente_servidor.start();
    }

    @Override
    protected void abrirCanal() throws SocketException {
        Object[] objetoRecibido = null;
        try {
            objetoRecibido = escuchar();
        } catch (IOException ex) {
            Logger.getLogger(Comunicacion.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (objetoRecibido[1] instanceof Resultado) {
            Procesador.procesarResultado((String) objetoRecibido[0], (Resultado) objetoRecibido[1]);
            return;
        }
        if (objetoRecibido[1] instanceof Solicitud) {
            Procesador.procesarSolicitud((String) objetoRecibido[0], (Solicitud) objetoRecibido[1]);
            return;
        }
        if (objetoRecibido[1] instanceof EquipoCliente) {
            Procesador.procesarEquipo((String) objetoRecibido[0], (EquipoCliente) objetoRecibido[1]);
            return;
        }
        if (objetoRecibido[1] instanceof UsuarioCliente) {
            Procesador.procesarUsuario((String) objetoRecibido[0], (UsuarioCliente) objetoRecibido[1]);
            return;
        }
    }

}
