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

import java.util.ArrayList;

/**
 *
 * @author camilo
 * @version 0.1.0
 */
public class Fila {

    private ArrayList<EquipoServidor> equipos;
    private boolean esHorizontal;

    public Fila(boolean esHorizontal) {
        equipos = new ArrayList();
        this.esHorizontal = esHorizontal;
    }

    public Equipo obtenerEquipo(int numeroEquipo) {
        return equipos.get(numeroEquipo);
    }

    public void notificar(String mensaje) {
    }

    public void agregarEquipo(Equipo equipo) {
        equipos.add((EquipoServidor) equipo);
    }

    public void encenderTodo() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void apagarTodo() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean contieneEquipo(int numeroEquipo) {
        for (EquipoServidor equipo : equipos) {
            if (equipo.getNumeroEquipo() == numeroEquipo) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<EquipoServidor> getEquipos() {
        return equipos;
    }

    public boolean esHorizontal() {
        return esHorizontal;
    }

    public EquipoServidor buscarPorIP(String ip) {
        for (EquipoServidor equipo : equipos){
            if (equipo.obtenerIP().equals(ip)){
                return equipo;
            }
        }
        return null;
    }
}
