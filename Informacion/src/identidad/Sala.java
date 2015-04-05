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
import exception.NoEncontrado;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Camilo Sampedro
 * @version 0.1.0
 */
public class Sala {

    protected ArrayList<Fila> filas;
    protected String nombre;
    protected int sufijoIPSala;
    protected boolean esHorizontal;

    public Sala(String nombre, boolean esHorizontal, int sufijoIP) {
        filas = new ArrayList();
        this.esHorizontal = esHorizontal;
        this.nombre = nombre;
        this.sufijoIPSala = sufijoIP;
    }

    public Fila obtenerFila(int numeroFila) throws NoEncontrado {
        return (Fila) filas.get(numeroFila);
    }

    public String obtenerNombre() {
        return nombre;
    }

    public Equipo obtenerEquipo(int numeroEquipo) throws NoEncontrado {
        Fila fila = this.buscarFilaEquipo(numeroEquipo);
        if (fila == null) {
            throw new exception.NoEncontrado(exception.NoEncontrado.EQUIPO, "obtenerEquipo");
        } else {
            return fila.obtenerEquipo(numeroEquipo);
        }
    }

    public void agregarEquipo(int fila, Equipo equipo) {
        filas.get(fila).agregarEquipo(equipo);
    }

    public void agregarFila(boolean esHorizontal) {
        filas.add(new Fila(esHorizontal));
    }

    public void apagarEquipo(int numeroEquipo) throws NoEncontrado {
        Fila fila = this.buscarFilaEquipo(numeroEquipo);
        if (fila != null) {
            String mac = fila.obtenerEquipo(numeroEquipo).obtenerMAC();
            Orden orden = new Orden(Funciones.ORDENDESPERTAREQUIPO(sufijoIPSala, mac));
            try {
                Ejecutar.ejecutar(orden);
            } catch (IOException ex) {
                Logger.getLogger(Sala.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                Logger.getLogger(Sala.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void apagarTodo() {
        for (Fila fila : filas) {
            fila.apagarTodo();
        }
    }

    public void notificar(String mensaje) {
        for (Fila fila : filas) {
            fila.notificar(mensaje);
        }
    }

    public void cambiarEstado(int numeroEquipo, boolean estado) {
        Fila fila = (Fila) buscarFilaEquipo(numeroEquipo);
        if (fila != null) {
            EquipoServidor equipo = (EquipoServidor) fila.obtenerEquipo(numeroEquipo);
        }
    }

    public void encenderTodo() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void encenderEquipo(int numeroEquipo) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Fila buscarFilaEquipo(int numeroEquipo) {
        for (Fila fila : filas) {
            if (fila.contieneEquipo(numeroEquipo)) {
                return fila;
            }
        }
        return null;
    }

    public int obtenerSufijoSala() {
        return this.sufijoIPSala;
    }

    public EquipoServidor buscarPorIP(String ip) {
        EquipoServidor equipo;
        for (Fila fila : filas) {
            equipo = fila.buscarPorIP(ip);
            if (equipo != null) {
                return equipo;
            }
        }
        return null;
    }

    public ArrayList<Fila> getFilas() {
        return (ArrayList<Fila>) filas.clone();
    }

    public boolean esHorizontal() {
        return esHorizontal;
    }

    public void agregarFila(Fila fila) {
        filas.add(fila);
    }
}
