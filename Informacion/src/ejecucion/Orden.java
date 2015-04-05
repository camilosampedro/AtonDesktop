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

import comunicacion.Enviable;
import static comunicacion.Enviable.FINCUERPO;
import static comunicacion.Enviable.INICIOCUERPO;
import static comunicacion.Enviable.SEPARADOR;
import java.io.Serializable;
import java.util.StringTokenizer;

/**
 * Clase que contiene la información de una orden: Comando, resultado y estado
 * de salida.
 *
 * @author Camilo Sampedro
 * @version 0.1.5
 */
public class Orden implements Serializable, Enviable {

    private String orden;
    private boolean interrumpida;
    private int estadoSalida;
    private Resultado resultado;

    public Orden(String orden) {
        this.orden = orden;
    }

    public Orden(String orden, int estadoSalida, Resultado resultado) {
        this.orden = orden;
        this.estadoSalida = estadoSalida;
        this.resultado = resultado;
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
    public Resultado getResultado() {
        return resultado;
    }

    /**
     * @param resultado the resultado to set
     */
    public void setResultado(Resultado resultado) {
        this.resultado = resultado;
    }

    public boolean esExitoso() {
        switch (estadoSalida) {
            case 0:
                return true;
            default:
                return false;
        }
    }

    @Override
    public String obtenerCabecera() {
        return INICIOCABECERA + TIPO[ORDEN] + FINCABECERA;
    }

    @Override
    public String obtenerCuerpo() {
        return INICIOCUERPO + this.orden + SEPARADOR + this.estadoSalida + SEPARADOR + this.resultado + FINCUERPO;
    }

    @Override
    public Class obtenerClase() {
        return Orden.class;
    }

    public static Orden construirObjeto(String informacion) {
        int i = informacion.indexOf(INICIOCUERPO);
        int j = informacion.indexOf(FINCUERPO);
        String info = informacion.substring(i, j);
        StringTokenizer tokens = new StringTokenizer(info, SEPARADOR);
        return new Orden(tokens.nextToken(), Integer.parseInt(tokens.nextToken()), new Resultado(tokens.nextToken()));
    }

    @Override
    public String generarCadena() {
        return obtenerCabecera() + obtenerCuerpo();
    }

}
