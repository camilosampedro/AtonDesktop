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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Clase encargada de ejecutar una orden y de verificar si fue interrumpida o no
 * mientras es ejecutada.
 *
 * @author Camilo Sampedro
 * @version 0.1.0
 */
public class Ejecucion {

    /**
     * Ejecuta la orden solicitada en el intérprete de comandos con el que se
     * ejecutó el servicio (Bash en Linux, por ejemplo).
     *
     * @param orden Orden a ejecutar, esta tiene la información de cuándo es
     * suspendida. Aquí se almacenará también el estado de salida de la orden.
     * @throws IOException
     * @throws InterruptedException externamente.
     */
    public static void ejecutar(Orden orden) throws IOException, InterruptedException {
        
        //Miniscript de ejecución del comando, para evitar problemas con los pipelines
        String[] comando = {
            "/bin/sh",
            "-c",
            orden.getOrden()
        };
        Process p = Runtime.getRuntime().exec(comando);

        BufferedReader stdInput = new BufferedReader(new InputStreamReader(
                p.getInputStream()));

        BufferedReader stdError = new BufferedReader(new InputStreamReader(
                p.getErrorStream()));
        p.waitFor();
        String s;
        String resultado = "";
        int i = 0;
        while ((s = stdInput.readLine()) != null) {
            if (i == 0) {
                resultado = s;
                i++;
            } else {
                resultado += "\n" + s;
            }
        }

        orden.setResultado(new Resultado(resultado));
        orden.setEstadoSalida(p.exitValue());
    }
}
