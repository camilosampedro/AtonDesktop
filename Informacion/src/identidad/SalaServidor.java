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
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Camilo Sampedro
 * @version 0.1.0
 */
public class SalaServidor implements Sala {

    protected ArrayList<FilaServidor> filas;
    protected String nombre;
    protected int sufijoIPSala;

    public SalaServidor() {
        filas = new ArrayList();
    }

    @Override
    public Fila obtenerFila(int numeroFila) {
        return (Fila) filas.get(numeroFila);
    }

    @Override
    public String obtenerNombre() {
        return nombre;
    }

    @Override
    public Equipo obtenerEquipo(int numeroEquipo) {
        Fila fila = this.buscarFilaEquipo(numeroEquipo);
        if (fila == null) {
            return null;
        } else {
            return fila.obtenerEquipo(numeroEquipo);
        }
    }

    @Override
    public void agregarEquipo(int fila, Equipo equipo) {
        filas.get(fila).agregarEquipo(equipo);
    }

    @Override
    public void agregarFila(boolean esHorizontal) {
        filas.add(new FilaServidor(esHorizontal));
    }

    @Override
    public void apagarEquipo(int numeroEquipo) {
        Fila fila = this.buscarFilaEquipo(numeroEquipo);
        if (fila != null) {
            String mac = fila.obtenerEquipo(numeroEquipo).obtenerMAC();
            Orden orden = new Orden(Funciones.ORDENDESPERTAREQUIPO(sufijoIPSala, mac));
            try {
                Ejecutar.ejecutar(orden);
            } catch (IOException ex) {
                Logger.getLogger(SalaServidor.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                Logger.getLogger(SalaServidor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void apagarTodo() {
        for (FilaServidor fila : filas) {
            fila.apagarTodo();
        }
    }

    @Override
    public void notificar(String mensaje) {
        for (FilaServidor fila : filas) {
            fila.notificar(mensaje);
        }
    }

    @Override
    public void cambiarEstado(int numeroEquipo, boolean estado) {
        FilaServidor fila = (FilaServidor) buscarFilaEquipo(numeroEquipo);
        if (fila != null) {
            EquipoServidor equipo = (EquipoServidor) fila.obtenerEquipo(numeroEquipo);
        }
    }

    @Override
    public void encenderTodo() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void encenderEquipo(int numeroEquipo) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Fila buscarFilaEquipo(int numeroEquipo) {
        for (FilaServidor fila : filas) {
            if (fila.contieneEquipo(numeroEquipo)) {
                return fila;
            }
        }
        return null;
    }

}
