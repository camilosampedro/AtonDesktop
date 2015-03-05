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

    protected String orden;
    protected boolean interrumpida;
    protected int estadoSalida;

    public Orden(String orden) {
        this.orden = orden;
    }

    public void interrumpir() {
        interrumpida = true;
    }

    public boolean estaInterrumpida() {
        return interrumpida;
    }

    public String obtenerOrden() {
        return orden;
    }

    public void asignarEstadoSalida(int estado) {
        estadoSalida = estado;
    }

    public int obtenerEstadoSalida() {
        return estadoSalida;
    }
}
