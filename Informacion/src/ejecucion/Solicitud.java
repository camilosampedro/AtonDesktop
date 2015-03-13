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
 * Clase usada para hacer solicitudes entre pares, preparando a ambos para la
 * comunicación.
 *
 * @author Camilo Sampedro
 * @version 0.1.0
 */
public class Solicitud implements Serializable {

    private final byte solicitud;

    public static final byte CONECCION_ROOT = 0;
    public static final byte CONECCION_USUARIO = 1;
    public static final byte IP = 2;
    public static final byte MAC = 3;
    public static final byte HOST = 4;
    public static final byte USUARIO = 5;

    public Solicitud(byte tipo) {
        this.solicitud = tipo;
    }

    public byte getTipo() {
        return solicitud;
    }
}
