package comunicacion;

import ejecucion.EjecucionRemota;
import ejecucion.Orden;
import identidad.Equipo;
import identidad.Usuario;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author camilo
 */
public class Cliente_Servidor {

    protected static Orden ordenActual;
    protected static ServerSocket serverSocket;
    protected static Socket socket;
    protected static int puerto;
    protected static String servidor;
    protected static EjecucionRemota ejecucionActual;
    protected static Solicitud solicitudActual;

    public static void escuchar() throws IOException, ClassNotFoundException {
        serverSocket = new ServerSocket(puerto);
        socket = serverSocket.accept();
        InputStream is = socket.getInputStream();
        ObjectInputStream ois = new ObjectInputStream(is);

        Object obj = ois.readObject();

        if (obj instanceof Orden) {
            ordenActual = (Orden) obj;
            ejecutarOrden();
        }
        if (obj instanceof Solicitud) {
            solicitudActual = (Solicitud) obj;
            ejecutarSolicitud();
        }
    }

    public static void ejecutarOrden() {
        ejecucionActual = new EjecucionRemota(ordenActual);
        ejecucionActual.start();
    }

    public static void enviarResultado(String resultado) {
        try {
            enviarObjeto(resultado);
        } catch (IOException ex) {
            Logger.getLogger(Cliente_Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void enviarObjeto(Object objeto) throws IOException {
        socket = new Socket(servidor, puerto);
        OutputStream os = socket.getOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(os);
        oos.writeObject(objeto);
        oos.close();
        os.close();
        socket.close();
    }

    private static void ejecutarSolicitud() {
        try {
            String resultado;
            switch (solicitudActual.getTipo()) {
                case Solicitud.HOST:
                    resultado = Equipo.obtenerHostName();
                    break;
                case Solicitud.IP:
                    resultado = Equipo.obtenerIP();
                    break;
                case Solicitud.MAC:
                    resultado = Equipo.obtenerMAC();
                    break;
                case Solicitud.USUARIO:
                    resultado = Usuario.obtenerNombreDeUsuario();
                    break;
                default:
                    resultado = "";
                    break;
            }
            enviarObjeto("RESPUESTA");
            enviarObjeto(resultado);
        } catch (IOException ex) {
            Logger.getLogger(Cliente_Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
