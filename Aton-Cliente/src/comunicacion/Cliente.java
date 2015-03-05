/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comunicacion;

import ejecucion.EjecucionRemota;
import ejecucion.Ejecutar;
import ejecucion.Orden;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author camilo
 */
public class Cliente {

    protected static Orden ordenActual;
    protected static ServerSocket serverSocket;
    protected static Socket socket;
    protected static int puerto;
    protected static String servidor;
    protected static EjecucionRemota ejecucionActual;

    public static void escuchar() throws IOException, ClassNotFoundException {
        serverSocket = null;
        serverSocket = new ServerSocket(puerto);
        int bufferSize = 0;

        socket = serverSocket.accept();
        InputStream is = socket.getInputStream();
        bufferSize = socket.getReceiveBufferSize();
        byte[] bytes = new byte[bufferSize];

        ByteArrayInputStream bs = new ByteArrayInputStream(bytes); // bytes es el byte[]
        ObjectInputStream ois = new ObjectInputStream(bs);
        Object obj = ois.readObject();

        if (obj instanceof Orden) {
            ordenActual = (Orden) obj;
            ejecutarOrden();
        }
        is.close();
        ois.close();
        bs.close();
        socket.close();
        serverSocket.close();
    }

    public static void ejecutarOrden() {
        ejecucionActual = new EjecucionRemota(ordenActual);
        ejecucionActual.start();
    }

    public static void enviarResultado(String resultado) {
        try {
            enviarObjeto(resultado);
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void enviarObjeto(Object o) throws IOException {
        ByteArrayOutputStream bs = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(bs);
        os.writeObject(o);  // this es de tipo DatoUdp
        os.close();
        byte[] bytes = bs.toByteArray(); // devuelve byte[]
        
        socket = new Socket(servidor, puerto);
        
        
    }
}
