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
package informacion;

import comunication.Comunicacion;
import identidad.ClientComputer;
import identidad.ClientUser;
import java.io.IOException;

/**
 *
 * @author Camilo Sampedro
 * @version 0.1.0
 */
public class Informacion {

    private static ClientUser usuario;
    private static ClientComputer equipo;

    /**
     * @return the usuario
     */
    public static ClientUser getUsuario() {
        return usuario;
    }

    /**
     * @param aUsuario the usuario to set
     */
    public static void setUsuario(ClientUser aUsuario) {
        usuario = aUsuario;
    }

    /**
     * @return the equipo
     */
    public static ClientComputer getEquipo() {
        return equipo;
    }

    /**
     * @param aEquipo the equipo to set
     */
    public static void setEquipo(ClientComputer aEquipo) {
        equipo = aEquipo;
    }

    public static void inicializar() throws IOException, ClassNotFoundException {   
        if (!ClientUser.isRoot()) {
            System.err.println("El usuario que ejecutó el servicio no es root.");
            System.err.println("Se debe ser root para ejecutar el servicio.");
            System.exit(1);
        }
        inicializarEquipo();
        Comunicacion.inicializar("localhost", 5978);
        Comunicacion.despertar();
    }

    private static void inicializarEquipo() {
        equipo = ClientComputer.initializeActualComputer();
    }

}
