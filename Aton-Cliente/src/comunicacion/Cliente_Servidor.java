package comunicacion;

import ejecucion.Solicitud;
import ejecucion.EjecucionRemota;
import ejecucion.Orden;
import identidad.Equipo;
import identidad.Usuario;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Camilo Sampedro
 */
public class Cliente_Servidor extends Thread {

    protected static Orden ordenActual;
    protected static ServerSocket serverSocket;
    protected static Socket socket;
    protected static int puerto;
    protected static String servidor;
    protected static EjecucionRemota ejecucionActual;
    protected static Solicitud solicitudActual;
    protected static Cliente_Servidor cliente_servidor;
    private static boolean conectado;
    private static int intento;

    public static void inicializar(String server, int port) {
        servidor = server;
        puerto = port;
        while (!puertoDisponible() && puerto < port + 20){
            puerto ++;
        }
        cliente_servidor = new Cliente_Servidor();
        intento = 0;
    }

    private static boolean esAccesible() throws UnknownHostException, IOException {
        return InetAddress.getByName(servidor).isReachable(100);
    }

    private static boolean puertoDisponible() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void run() {
        while (true) {
            try {
                while (!esAccesible()) {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Cliente_Servidor.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                escuchar();
            } catch (IOException ex) {
                Logger.getLogger(Cliente_Servidor.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Cliente_Servidor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static void despertar() throws IOException, ClassNotFoundException {
        if (Usuario.esRoot()) {
            enviarObjeto(new Solicitud(Solicitud.CONECCION_ROOT));
            escuchar();
            if (conectado) {
                enviarObjeto(Equipo.obtenerMAC());
                enviarObjeto(Equipo.obtenerIP());
                enviarObjeto(Equipo.obtenerHostName());
            }
        } else {
            enviarObjeto(new Solicitud(Solicitud.CONECCION_USUARIO));
            enviarObjeto("HOla");
            escuchar();
            if (conectado) {
                enviarObjeto(Usuario.obtenerNombreDeUsuario());
            }
        }
        cliente_servidor.start();
    }

    public static void escuchar() throws IOException, ClassNotFoundException {
        serverSocket = new ServerSocket(puerto);
        serverSocket.setReuseAddress(true);
        serverSocket.bind(new InetSocketAddress(puerto));
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
        try {
            socket = new Socket(servidor, puerto);
            socket.setReuseAddress(true);
            socket.bind(new InetSocketAddress(puerto));
            OutputStream os = socket.getOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(os);
            oos.writeObject(objeto);
            oos.close();
            os.close();
            socket.close();
            intento = 0;
        } catch (ConnectException ex) {
            try {
                Thread.sleep(1000);
                if (intento < 10) {
                    enviarObjeto(objeto);
                }
            } catch (InterruptedException ex1) {
                Logger.getLogger(Cliente_Servidor.class.getName()).log(Level.SEVERE, null, ex1);
            }
        }
    }

    private static void ejecutarSolicitud() {
        try {
            String resultado;
            boolean enviar = true;
            switch (solicitudActual.getTipo()) {
                case Solicitud.CONECCION_ROOT:
                    conectado = true;
                    enviar = false;
                    resultado = "";
                    break;
                case Solicitud.CONECCION_USUARIO:
                    conectado = true;
                    enviar = false;
                    resultado = "";
                    break;
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
            if (enviar) {
                enviarObjeto("RESPUESTA");
                enviarObjeto(resultado);
            }
        } catch (IOException ex) {
            Logger.getLogger(Cliente_Servidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
