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
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import logs.CreadorLog;

/**
 *
 * @author Camilo Sampedro
 * @version 0.1.0
 */
public class UsuarioCliente implements Usuario, Serializable {

    // I. Variables generales.
    /**
     * Nombre del usuario que está ejecutando el servicio.
     */
    protected String nombreDeUsuario;
    /**
     * Contiene información de si el usuario es root o no.
     */
    private final boolean esRoot;
    /**
     * Previene la verificación de root múltiple, si ya está verificada.
     */
    protected boolean estaVerificadoRoot;
    /**
     * Contiene información de si el usuario está baneado o no dentro del
     * sistema.
     */
    protected boolean estaBaneado;
    /**
     * Previene la verificación de baneo múltiple, si ya está verificada.
     */
    protected boolean estaVerificadoBaneo;

    public UsuarioCliente() {
        estaVerificadoRoot = false;
        estaVerificadoBaneo = false;
        esRoot = esRoot();
        estaBaneado = verificarBaneo();
    }

    // II. Métodos.
    /**
     * Verifica si el usuario que ejecutó el programa es root o no.
     *
     * @return True: El usuario es root. False: El usuario no es root.
     */
    public boolean esRoot() {
        // Si ya fue calculado anteriormente, no se calcula de nuevo.
        if (estaVerificadoRoot) {
            return esRoot;
        }
        try {
            //"id - u" retorna 0 si el usuario es root.
            Orden orden = new Orden(Funciones.ORDENVERIFICACIONROOT);
            Ejecutar.ejecutar(orden);
            boolean esRoot2 = orden.getResultado().equals("0");
            estaVerificadoRoot = true;
            return esRoot2;
        } catch (IOException ex) {
            System.err.println("Ocurrió n error intentando verificar si se es root: I/O");
            Logger.getLogger(UsuarioCliente.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(UsuarioCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
        CreadorLog.agregarALog(CreadorLog.ERROR, "Ocurrió un error intentando verificar si se es root");
        return false;
    }

    /**
     * Obtiene el nombre del usuario que está ejecutando el servicio.
     *
     * @return String con el nombre del usuario.
     */
    @Override
    public String obtenerNombreDeUsuario() {
        // Si ya fue calculado anteriormente, no se calcula de nuevo.
        if (nombreDeUsuario == null | nombreDeUsuario.equals("")) {
            try {
                Orden orden = new Orden(Funciones.ORDENUSUARIO);
                Ejecutar.ejecutar(orden);
                nombreDeUsuario = orden.getResultado();
                return nombreDeUsuario;

            } catch (IOException | InterruptedException ex) {
                Logger.getLogger(UsuarioCliente.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
            System.err.println("Ocurrió un error obteniendo el nombre del usuario");
            CreadorLog.agregarALog(CreadorLog.ERROR, "Ocurrió un error intentando obtener el nombre del usuario");
        }
        return nombreDeUsuario;
    }

    @Override
    public boolean isEqual(Usuario usuario) {
        return (nombreDeUsuario.endsWith(usuario.obtenerNombreDeUsuario()));
    }

    @Override
    public boolean estaBaneado() {
        return estaBaneado;
    }

    private boolean verificarBaneo() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
