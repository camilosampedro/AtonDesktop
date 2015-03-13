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

/**
 *
 * @author Camilo Sampedro
 */
public class EquipoServidor implements Equipo {

    public static final boolean OCUPADO = true;
    public static final boolean LIBRE = false;
    public static final boolean ENCENDIDO = true;
    public static final boolean NOENCENDIDO = false;
    private boolean estadoUso;
    private boolean estadoPoder;
    private int numeroEquipo;

    @Override
    public String obtenerIP() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String obtenerMAC() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String obtenerHostName() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Usuario obtenerUsuario() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void asignarUsuario(Usuario usuario) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void asignarIP(String ip) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void asignarMAC(String mac) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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

}
