package ejecucion;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Clase encargada de ejecutar una orden y de verificar si fue interrumpida o no
 * mientras es ejecutada.
 *
 * @author Camilo Sampedro
 */
public class Ejecutar {

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
        StringBuilder output = new StringBuilder();
        // Ejecución de la orden.
        Process p = Runtime.getRuntime().exec(orden.getOrden());
        p.waitFor();
        BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line;

        // Lectura, línea por línea, mientras no sea interrumpida.
        while ((line = reader.readLine()) != null && !orden.isInterrumpida()) {
            // CADA LÍNEA DEBERÍA SER ENVIADA DE NUEVO AL EJECUTOR.
            output.append(line).append("\n");
        }

        orden.setResultado(output.toString());

        // Lectura del estado de salida.
        p = Runtime.getRuntime().exec("echo $?");
        p.waitFor();
        reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
        line = reader.readLine();

        orden.setEstadoSalida(Integer.parseInt(line));
    }
}
