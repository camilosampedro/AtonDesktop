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

import comunicacion.Enviable;
import static comunicacion.Enviable.FINCUERPO;
import static comunicacion.Enviable.INICIOCUERPO;
import static comunicacion.Enviable.SEPARADOR;
import ejecucion.Ejecucion;
import ejecucion.Funciones;
import ejecucion.Orden;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import logs.CreadorLog;

/**
 * Implementación de la clase Equipo ubicada en los clientes.
 *
 * @author Camilo Sampedro
 * @version 0.1.5
 */
public class EquipoCliente implements Equipo, Enviable {

    public static EquipoCliente inicializarEquipoActual() {
        EquipoCliente equipo = new EquipoCliente();
        equipo.asignarIP(obtenerIPEquipoActual());
        equipo.asignarMAC(obtenerMacEquipoActual());
        for (UsuarioCliente usuario : UsuarioCliente.generarListaUsuarios()) {
            equipo.agregarUsuario(usuario);
        }
        System.out.println("Especificaciones equipo actual:");
        System.out.println("IP: " + equipo.ip + ", Mac: " + equipo.mac);
        return equipo;
    }

    private static String obtenerMacEquipoActual() {
//        if (UsuarioCliente.esRoot()) {
            try {
                Orden orden = new Orden(Funciones.ORDENMAC);
                Ejecucion.ejecutar(orden);
                return orden.getResultado().getResultado();
            } catch (IOException | InterruptedException ex) {
                Logger.getLogger(EquipoCliente.class.getName()).log(Level.SEVERE, null, ex);
                CreadorLog.agregarALog(CreadorLog.ERROR, "Error al buscar la MAC del equipo");
                return null;
            }
//        }
//        return null;
    }

    protected int numero;
    protected String mac;
    protected String ip;
    protected String hostname;
    protected boolean enUso;
    private ArrayList<UsuarioCliente> usuarios;

    @Override
    public String obtenerIP() {
        return this.ip;
    }

    public static String obtenerIPEquipoActual() {
//        if (UsuarioCliente.esRoot()) {
        try {
            Orden orden = new Orden(Funciones.ORDENIP);
            Ejecucion.ejecutar(orden);
            return orden.getResultado().getResultado();
                // Si la terminal está en inglés:
            //Ejecutar.ejecutar("ifconfig eth0 2>/dev/null|awk '/inet addr:/ {print $2}'|sed 's/addr://'");
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(EquipoCliente.class.getName()).log(Level.SEVERE, null, ex);
            CreadorLog.agregarALog(CreadorLog.ERROR, "Error al buscar la IP del equipo");
        }
//        }
        return null;
    }

    @Override
    public String obtenerMAC() {
        return this.mac;
    }

    @Override
    public String obtenerHostname() {
        try {
            Orden orden = new Orden(Funciones.ORDENHOST);
            Ejecucion.ejecutar(orden);
            hostname = orden.getResultado().getResultado();
        } catch (IOException ex) {
            Logger.getLogger(EquipoCliente.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(EquipoCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
        return hostname;
    }

    public void asignarHostName(String hostname) {
        this.hostname = hostname;
    }

    @Override
    public ArrayList<UsuarioCliente> obtenerUsuarios() {
        return usuarios;
    }

    @Override
    public void agregarUsuario(Usuario usuario) {
        usuarios.add((UsuarioCliente) usuario);
    }

    @Override
    public void asignarIP(String ip) {
        this.ip = ip;
    }

    @Override
    public void asignarMAC(String mac) {
        this.mac = mac;
    }

    @Override
    public String obtenerCabecera() {
        return INICIOCABECERA + TIPO[EQUIPOCLIENTE] + FINCABECERA;
    }

    @Override
    public String obtenerCuerpo() {
        return INICIOCUERPO + this.mac + SEPARADOR + this.ip + SEPARADOR + this.hostname + SEPARADOR + this.enUso + FINCUERPO;
    }

    public static EquipoCliente construirObjeto(String informacion) {
        int i = informacion.indexOf(INICIOCUERPO);
        int j = informacion.indexOf(FINCUERPO);
        String info = informacion.substring(i, j);
        StringTokenizer tokens = new StringTokenizer(info, SEPARADOR);
        return new EquipoCliente(tokens.nextToken(), tokens.nextToken(), tokens.nextToken(), Boolean.parseBoolean(tokens.nextToken()));
    }

    @Override
    public String generarCadena() {
        return obtenerCabecera() + obtenerCuerpo();
    }

    @Override
    public Class obtenerClase() {
        return EquipoCliente.class;
    }

    public EquipoCliente() {
        usuarios = new ArrayList();
    }

    public EquipoCliente(String mac, String ip, String hostname, boolean enUso) {
        this.mac = mac;
        this.ip = ip;
        this.hostname = hostname;
        this.enUso = enUso;
        this.usuarios = new ArrayList();
    }

}
