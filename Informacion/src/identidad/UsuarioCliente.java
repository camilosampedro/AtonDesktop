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
import ejecucion.Ejecucion;
import ejecucion.Funciones;
import ejecucion.Orden;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import logs.CreadorLog;

/**
 *
 * @author Camilo Sampedro
 * @version 0.1.2
 */
public class UsuarioCliente implements Usuario, Serializable, Enviable {

    // I. Variables generales.
    /**
     * Nombre del usuario que está ejecutando el servicio.
     */
    private String nombreDeUsuario;
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
    private String grupo;

    public UsuarioCliente() {
        estaVerificadoRoot = false;
        estaVerificadoBaneo = false;
        esRoot = esRoot();
        estaBaneado = verificarBaneo();
    }

    public UsuarioCliente(String nombreDeUsuario) {
        esRoot = nombreDeUsuario.endsWith("root");
        this.nombreDeUsuario = nombreDeUsuario;
    }

    // II. Métodos.
    /**
     * Verifica si el usuario que ejecutó el programa es root o no.
     *
     * @return True: El usuario es root. False: El usuario no es root.
     */
    public static boolean esRoot() {
        try {
            //"id - u" retorna 0 si el usuario es root.
            Orden orden = new Orden(Funciones.ORDENVERIFICACIONROOT);
            Ejecucion.ejecutar(orden);
            String resultado = orden.getResultado().getResultado().replace("\n", "").replace("\r", "");
            boolean esRoot2 = resultado.equals("0");
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

    public static ArrayList<UsuarioCliente> generarListaUsuarios() {
        try {
            ArrayList<UsuarioCliente> listaUsuarios = new ArrayList();
            Orden orden = new Orden(Funciones.ORDENLISTAUSUARIOS);
            Ejecucion.ejecutar(orden);
            String resultado = orden.getResultado().getResultado();
            StringTokenizer tokens = new StringTokenizer(resultado, "\n");
            ciclo:
            while (tokens.hasMoreTokens()){
                String nombreUsuario = tokens.nextToken();
                UsuarioCliente nuevoUsuario = new UsuarioCliente(nombreUsuario);
                for(UsuarioCliente usuario : listaUsuarios){
                    if (usuario.isEqual(nuevoUsuario)){
                        continue ciclo;
                    }
                }
                listaUsuarios.add(nuevoUsuario);
            }
            return listaUsuarios;
        } catch (IOException ex) {
            Logger.getLogger(UsuarioCliente.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(UsuarioCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Obtiene el nombre del usuario que está ejecutando el servicio.
     *
     * @return String con el nombre del usuario.
     */
    public static String obtenerNombreDeUsuarioEjecutor() {
        // Si ya fue calculado anteriormente, no se calcula de nuevo.
        try {
            Orden orden = new Orden(Funciones.ORDENUSUARIO);
            Ejecucion.ejecutar(orden);
            return orden.getResultado().getResultado();

        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(UsuarioCliente.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        System.err.println("Ocurrió un error obteniendo el nombre del usuario");
        CreadorLog.agregarALog(CreadorLog.ERROR, "Ocurrió un error intentando obtener el nombre del usuario");

        return "";
    }

    @Override
    public boolean isEqual(Usuario usuario) {
        return (getNombreDeUsuario().endsWith(usuario.obtenerNombreDeUsuario()));
    }

    @Override
    public boolean estaBaneado() {
        return estaBaneado;
    }

    private boolean verificarBaneo() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String obtenerNombreDeUsuario() {
        return this.nombreDeUsuario;
    }

    @Override
    public String obtenerCabecera() {
        return INICIOCABECERA + TIPO[USUARIOCLIENTE] + FINCABECERA;
    }

    @Override
    public String obtenerCuerpo() {
        return INICIOCUERPO + this.getNombreDeUsuario() + SEPARADOR + this.getGrupo() + FINCUERPO;
    }

    @Override
    public Class obtenerClase() {
        return UsuarioCliente.class;
    }

    public static UsuarioCliente construirObjeto(String informacion) {
        int i = informacion.indexOf(INICIOCUERPO);
        int j = informacion.indexOf(FINCUERPO);
        int k = informacion.indexOf(SEPARADOR);
        String usuario = informacion.substring(i, k);
        String grupon = informacion.substring(k, j);
        UsuarioCliente usuarion = new UsuarioCliente();
        usuarion.setNombreDeUsuario(usuario);
        usuarion.setGrupo(grupon);
        return new UsuarioCliente();
    }

    @Override
    public String generarCadena() {
        return obtenerCabecera() + obtenerCuerpo();
    }

    /**
     * @return the nombreDeUsuario
     */
    public String getNombreDeUsuario() {
        return nombreDeUsuario;
    }

    /**
     * @param nombreDeUsuario the nombreDeUsuario to set
     */
    public void setNombreDeUsuario(String nombreDeUsuario) {
        this.nombreDeUsuario = nombreDeUsuario;
    }

    /**
     * @return the grupo
     */
    public String getGrupo() {
        return grupo;
    }

    /**
     * @param grupo the grupo to set
     */
    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

}
