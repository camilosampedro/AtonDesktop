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
package comunicacion;

/**
 *
 * @author Camilo Sampedro
 * @version 0.1.3
 */
public interface Enviable {

    //Constantes globales
    public static final String INICIOCUERPO = "*¡*{";
    public static final String FINCUERPO = "}*!*";
    public static final String INICIOCABECERA = "*[";
    public static final String FINCABECERA = "]*";
    public static final String SEPARADOR = "*(/)*";
    public static final String SEPARADORLISTA = "*(=)*";
    public static final int TAMAÑOPAQUETE = 2048;
    
    //Tipos de enviable
    public static final byte RESULTADO = 0;
    public static final byte EQUIPOSERVIDOR = 1;
    public static final byte ORDEN = 2;
    public static final byte SOLICITUD = 3;
    public static final byte USUARIOCLIENTE = 4;
    public static final byte EQUIPOCLIENTE = 5;

    public static final String TIPO[] = {"RESULTADO", "EQUIPOSERVIDOR", "ORDEN", "SOLICITUD", "USUARIOCLIENTE", "EQUIPOCLIENTE"};

    public String obtenerCabecera();

    public String obtenerCuerpo();

    public abstract Class obtenerClase();

    public String generarCadena();

}
