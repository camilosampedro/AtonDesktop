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

import exception.NoEncontrado;
import java.util.ArrayList;

/**
 *
 * @author Camilo Sampedro
 * @version 0.1.0
 */
public class Salon {

    private ArrayList<Sala> salas;
    private String nombre;

    public Salon(String nombre) {
        salas = new ArrayList();
        this.nombre = nombre;
    }

    public void encender() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void apagar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void notificar(String mensaje) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void agregarSala(Sala sala) {
        salas.add(sala);
    }

    public String getNombre() {
        return nombre;
    }

    public EquipoServidor buscarPorIP(String ip) {
        EquipoServidor equipo;
        for (Sala sala : salas) {
            equipo = sala.buscarPorIP(ip);
            if (equipo != null){
                return equipo;
            }
        }
        return null;
    }
    
    public ArrayList<Sala> getSalas(){
        return (ArrayList<Sala>) salas.clone();
    }
}
