/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejecucion;

import java.io.Serializable;

/**
 *
 * @author camilo
 */
public class Orden implements Serializable{

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
