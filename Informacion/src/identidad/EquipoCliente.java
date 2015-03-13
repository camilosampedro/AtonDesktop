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
package identidad;

import ejecucion.Ejecutar;
import ejecucion.Funciones;
import ejecucion.Orden;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import logs.CreadorLog;

/**
 * Implementación de la clase Equipo ubicada en los clientes.
 *
 * @author Camilo Sampedro
 * @version 0.1.0
 */
public class EquipoCliente implements Equipo {

    protected int numero;
    protected String mac;
    protected String ip;
    protected String hostname;
    protected boolean enUso;
    private UsuarioCliente usuario;

    @Override
    public String obtenerIP() {
        if (usuario.esRoot() & (ip == null | ip.equals(""))) {
            try {
                Orden orden = new Orden(Funciones.ORDENIP);
                Ejecutar.ejecutar(orden);
                ip = orden.getResultado();
                // Si la terminal está en inglés:
                //Ejecutar.ejecutar("ifconfig eth0 2>/dev/null|awk '/inet addr:/ {print $2}'|sed 's/addr://'");
            } catch (IOException | InterruptedException ex) {
                Logger.getLogger(EquipoCliente.class.getName()).log(Level.SEVERE, null, ex);
                CreadorLog.agregarALog(CreadorLog.ERROR, "Error al buscar la IP del equipo");
            }
        }
        return ip;
    }

    @Override
    public String obtenerMAC() {
        if (usuario.esRoot() & (mac == null | mac.equals(""))) {
            try {
                Orden orden = new Orden(Funciones.ORDENMAC);
                Ejecutar.ejecutar(orden);
                mac = orden.getResultado();
            } catch (IOException | InterruptedException ex) {
                Logger.getLogger(EquipoCliente.class.getName()).log(Level.SEVERE, null, ex);
                CreadorLog.agregarALog(CreadorLog.ERROR, "Error al buscar la MAC del equipo");
                return null;
            }
        }
        return mac;
    }

    @Override
    public String obtenerHostName() {
        try {
            Orden orden = new Orden(Funciones.ORDENHOST);
            Ejecutar.ejecutar(orden);
            hostname = orden.getResultado();
        } catch (IOException ex) {
            Logger.getLogger(EquipoCliente.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(EquipoCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
        return hostname;
    }

    public void asignarHostName(String hostname) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Usuario obtenerUsuario() {
        return (Usuario) usuario;
    }

    @Override
    public void asignarUsuario(Usuario usuario) {
        this.usuario = (UsuarioCliente) usuario;
    }

    @Override
    public void asignarIP(String ip) {
        this.ip = ip;
    }

    @Override
    public void asignarMAC(String mac) {
        this.mac = mac;
    }

}
