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
import ejecucion.Ejecutar;
import ejecucion.Funciones;
import ejecucion.Orden;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Camilo Sampedro
 * @version 0.1.6
 */
public class EquipoServidor implements Equipo, Enviable {

    public static final boolean OCUPADO = true;
    public static final boolean LIBRE = false;
    public static final boolean ENCENDIDO = true;
    public static final boolean NOENCENDIDO = false;
    private boolean estadoUso;
    private boolean estadoPoder;
    private int numeroEquipo;
    private String ip;
    private String mac;
    private String hostname;
    private Usuario usuario;
    private ArrayList<String[]> resultados;
    private Orden ordenActual;

    /**
     * Get the value of resultados
     *
     * @return the value of resultados
     */
    public ArrayList<String[]> getResultados() {
        return (ArrayList<String[]>) resultados.clone();
    }

    /**
     * Add the value of resultados
     *
     * @param resultados new value of resultados
     */
    public void addResultado(String[] resultados) {
        this.resultados.add(resultados);
    }

    /**
     * Get the value of ordenActual
     *
     * @return the value of ordenActual
     */
    public Orden getOrdenActual() {
        return ordenActual;
    }

    /**
     * Set the value of ordenActual
     *
     * @param ordenActual new value of ordenActual
     */
    public void setOrdenActual(Orden ordenActual) {
        this.ordenActual = ordenActual;
    }

    private Sala sala;

    public EquipoServidor(Sala padre) {
        sala = padre;
    }

    public EquipoServidor(String ip, String mac, int numeroEquipo, boolean estadoPoder, boolean estadoUso, Sala sala) {
        this.ip = ip;
        this.mac = mac;
        this.numeroEquipo = numeroEquipo;
        this.estadoPoder = estadoPoder;
        this.estadoUso = estadoUso;
        this.sala = sala;
    }

    @Override
    public String obtenerIP() {
        return this.ip;
    }

    @Override
    public String obtenerMAC() {
        return this.mac;
    }

    @Override
    public String obtenerHostname() {
        return this.hostname;
    }

    @Override
    public Usuario obtenerUsuario() {
        if (esUsado()) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        } else {
            return (this.usuario);
        }
    }

    @Override
    public void asignarUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public void asignarIP(String ip) {
        this.ip = ip;
    }

    @Override
    public void asignarMAC(String mac) {
        this.mac = mac;
    }

    /**
     * @return the estadoUso
     */
    public boolean esUsado() {
        return estadoUso;
    }

    /**
     * @param estadoUso the estadoUso to set
     */
    public void setEstadoUso(boolean estadoUso) {
        this.estadoUso = estadoUso;
    }

    /**
     * @return the estadoPoder
     */
    public boolean estaEncendido() {
        return estadoPoder;
    }

    /**
     * @param estadoPoder the estadoPoder to set
     */
    public void setEstadoPoder(boolean estadoPoder) {
        this.estadoPoder = estadoPoder;
    }

    /**
     * @return the numeroEquipo
     */
    public int getNumeroEquipo() {
        return numeroEquipo;
    }

    /**
     * @param numeroEquipo the numeroEquipo to set
     */
    public void setNumeroEquipo(int numeroEquipo) {
        this.numeroEquipo = numeroEquipo;
    }

    public boolean encenderEquipo() {
        Orden orden = new Orden(Funciones.ORDENDESPERTAREQUIPO(sala.obtenerSufijoSala(), this.mac));
        try {
            Ejecutar.ejecutar(orden);
            return orden.esExitoso();
        } catch (IOException ex) {
            Logger.getLogger(EquipoServidor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(EquipoServidor.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public String obtenerCabecera() {
        return INICIOCABECERA + TIPO[EQUIPOSERVIDOR] + FINCABECERA;
    }

    @Override
    public String obtenerCuerpo() {
        return INICIOCUERPO + ip + SEPARADOR + mac + SEPARADOR + numeroEquipo + SEPARADOR + estadoPoder + SEPARADOR + estadoUso + SEPARADOR + sala.obtenerNombre() + SEPARADOR + sala.esHorizontal + FINCUERPO;
    }

    @Override
    public Class obtenerClase() {
        return EquipoServidor.class;
    }

    public static EquipoServidor construirObjeto(String informacion) {
        int i = informacion.indexOf(INICIOCUERPO);
        int j = informacion.indexOf(FINCUERPO);
        String info = informacion.substring(i, j);
        StringTokenizer tokens = new StringTokenizer(info, SEPARADOR);
        //Probar
        return new EquipoServidor(tokens.nextToken(), tokens.nextToken(), Integer.parseInt(tokens.nextToken()), Boolean.parseBoolean(tokens.nextToken()), Boolean.parseBoolean(tokens.nextToken()), new Sala(tokens.nextToken(), Boolean.parseBoolean(tokens.nextToken())));
    }

    @Override
    public String generarCadena() {
        return obtenerCabecera() + obtenerCuerpo();
    }

    public void copiarInformacion(EquipoCliente equipo) {
        this.estadoUso = equipo.enUso;
        this.mac = equipo.mac;
    }

    public void asignarHostname(String hostname) {
        this.hostname = hostname;
    }

}
