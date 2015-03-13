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
package ejecucion;

import java.io.Serializable;

/**
 * Clase que contiene la información de una orden: Comando, resultado y estado
 * de salida.
 *
 * @author Camilo Sampedro
 * @version 0.1.0
 */
public class Orden implements Serializable {

    private String orden;
    private boolean interrumpida;
    private int estadoSalida;
    private String resultado;

    public Orden(String orden) {
        this.orden = orden;
    }

    /**
     * @return the orden
     */
    public String getOrden() {
        return orden;
    }

    /**
     * @param orden the orden to set
     */
    public void setOrden(String orden) {
        this.orden = orden;
    }

    /**
     * @return the interrumpida
     */
    public boolean isInterrumpida() {
        return interrumpida;
    }

    /**
     * @param interrumpida the interrumpida to set
     */
    public void interrumpir(boolean interrumpida) {
        this.interrumpida = interrumpida;
    }

    /**
     * @return the estadoSalida
     */
    public int getEstadoSalida() {
        return estadoSalida;
    }

    /**
     * @param estadoSalida the estadoSalida to set
     */
    public void setEstadoSalida(int estadoSalida) {
        this.estadoSalida = estadoSalida;
    }

    /**
     * @return the resultado
     */
    public String getResultado() {
        return resultado;
    }

    /**
     * @param resultado the resultado to set
     */
    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

}
