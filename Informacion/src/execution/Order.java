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
package execution;

import comunication.SendableObject;
import static comunication.SendableObject.BODYEND;
import static comunication.SendableObject.BODYSTART;
import static comunication.SendableObject.SEPARATOR;
import java.util.StringTokenizer;

/**
 * Clase que contiene la información de una order: Comando, result y estado de
 * salida.
 *
 * @author Camilo Sampedro
 * @version 0.1.5
 */
public class Order extends SendableObject {

    private String order;
    private boolean interrupted;
    private int exitState;
    private Result result;

    public Order(String order) {
        this.order = order;
    }

    public Order(String order, int exitState, Result result) {
        this.order = order;
        this.exitState = exitState;
        this.result = result;
    }

    /**
     * @return the order
     */
    public String getOrder() {
        return order;
    }

    /**
     * @param orden the order to set
     */
    public void setOrden(String orden) {
        this.order = orden;
    }

    /**
     * @return the interrupted
     */
    public boolean isInterrupted() {
        return interrupted;
    }

    /**
     * @param interrumpida the interrupted to set
     */
    public void interrumpir(boolean interrumpida) {
        this.interrupted = interrumpida;
    }

    /**
     * @return the exitState
     */
    public int getEstadoSalida() {
        return exitState;
    }

    /**
     * @param estadoSalida the exitState to set
     */
    public void setExitState(int estadoSalida) {
        this.exitState = estadoSalida;
    }

    /**
     * @return the result
     */
    public Result getResult() {
        return result;
    }

    /**
     * @param resultado the result to set
     */
    public void setResult(Result resultado) {
        this.result = resultado;
    }

    public boolean isSuccessful() {
        switch (exitState) {
            case 0:
                return true;
            default:
                return false;
        }
    }

    @Override
    public String getHead() {
        return HEADSTART + TYPE[ORDER] + HEADEND;
    }

    @Override
    public String getBody() {
        return BODYSTART + this.order + SEPARATOR + this.exitState + SEPARATOR + this.result + BODYEND;
    }

    public static Order buildObject(String informacion) {
        int i = informacion.indexOf(BODYSTART);
        int j = informacion.indexOf(BODYEND);
        String info = informacion.substring(i, j);
        StringTokenizer tokens = new StringTokenizer(info, SEPARATOR);
        return new Order(tokens.nextToken(), Integer.parseInt(tokens.nextToken()), new Result(tokens.nextToken()));
    }

}
