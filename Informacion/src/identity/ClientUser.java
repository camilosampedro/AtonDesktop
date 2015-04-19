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
package identity;

import comunication.SendableObject;
import execution.Execution;
import execution.Function;
import execution.Order;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import logs.LogCreator;

/**
 *
 * @author Camilo Sampedro
 * @version 0.1.2
 */
public class ClientUser extends User {

    // I. Variables generales.
    /**
     * Nombre del usuario que está ejecutando el servicio.
     */
    private String username;
    /**
     * Contiene información de si el usuario es root o no.
     */
    private final boolean isRoot;
    /**
     * Contiene información de si el usuario está baneado o no dentro del
     * sistema.
     */
    protected boolean isBanned;
    private String group;

    /**
     * Constructor de usuario vacío.
     */
    public ClientUser() {
        isRoot = isRoot();
        //isBanned = verificarBaneo();
    }

    /**
     * Constructor con nombre de usuario. Verifica si el usuario se llama root.
     *
     * @param username Nombre de usuario nuevo.
     */
    public ClientUser(String username) {
        isRoot = username.endsWith("root");
        this.username = username;
    }

    // II. Métodos.
    /**
     * Verifica si el usuario que ejecutó el programa es root o no.
     *
     * @return True: El usuario es root. False: El usuario no es root.
     */
    public static boolean isRoot() {
        try {
            //"id - u" retorna 0 si el usuario es root.
            Order orden = new Order(Function.ROOT_VERIFICATION_ORDER);
            Execution.execute(orden);

            //Se eliminan los quiebres y saltos de línea
            String resultado = orden.getResult().getResult().replace("\n", "").replace("\r", "");
            boolean esRoot2 = resultado.equals("0");
            return esRoot2;
        } catch (IOException ex) {
            LogCreator.addToLog(LogCreator.ERROR, "Ocurrió un error intentando verificar si se es root: I/O.");
            Logger.getLogger(ClientUser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            LogCreator.addToLog(LogCreator.ERROR, "Ocurrió un error intentando verificar si se es root: La operación fue interrumpida.");
            Logger.getLogger(ClientUser.class.getName()).log(Level.SEVERE, null, ex);
        }
        LogCreator.addToLog(LogCreator.ERROR, "Ocurrió un error desconocido intentando verificar si se es root.");
        return false;
    }

    /**
     * Genera una lista con los usuarios que están logueados en estos momentos.
     *
     * @return Lista con los usuarios logueados actualmente. Sin repeticiones.
     */
    public static ArrayList<ClientUser> generateUserList() {
        try {
            ArrayList<ClientUser> listaUsuarios = new ArrayList();

            //La orden "w -h | awk '{print $1}'" muestra los nombres de usuario.
            Order orden = new Order(Function.USER_LIST_ORDER);
            Execution.execute(orden);
            String resultado = orden.getResult().getResult();

            //Cada línea es un nombre de usuario.
            StringTokenizer tokens = new StringTokenizer(resultado, "\n");
            ciclo:
            while (tokens.hasMoreTokens()) {
                String nombreUsuario = tokens.nextToken();
                ClientUser nuevoUsuario = new ClientUser(nombreUsuario);
                //Verificar si existe.
                for (ClientUser usuario : listaUsuarios) {
                    if (usuario.isEqual(nuevoUsuario)) {
                        //Existe, no se agrega.
                        continue ciclo;
                    }
                }
                listaUsuarios.add(nuevoUsuario);
            }
            return listaUsuarios;
        } catch (IOException ex) {
            Logger.getLogger(ClientUser.class.getName()).log(Level.SEVERE, null, ex);
            LogCreator.addToLog(LogCreator.ERROR, "Error de I/O creando la lista de usuarios actuales.");
        } catch (InterruptedException ex) {
            Logger.getLogger(ClientUser.class.getName()).log(Level.SEVERE, null, ex);
            LogCreator.addToLog(LogCreator.ERROR, "La operación para listar los usuarios actuales fue interrumpida.");
        }
        LogCreator.addToLog(LogCreator.ERROR, "Ocurrió un error desconocido listando los usuarios");
        return null;
    }

    /**
     * Obtiene el nombre del usuario que está ejecutando el servicio.
     *
     * @return String con el nombre del usuario.
     */
    public static String getRunnerUsername() {
        try {
            //whoami muestra el nombre del usuario que ejecuta el programa.
            Order orden = new Order(Function.USER_IDENTIFIER_ORDER);
            Execution.execute(orden);
            return orden.getResult().getResult();
        } catch (IOException ex) {
            LogCreator.addToLog(LogCreator.ERROR, "Ocurrió un error de I/O intentando obtener el usuario que ejecutó el servicio.");
            Logger.getLogger(ClientUser.class
                    .getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            LogCreator.addToLog(LogCreator.ERROR, "La operación para averiguar el nombre del usuario ejecutor fue interrumpida.");
            Logger.getLogger(ClientUser.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        LogCreator.addToLog(LogCreator.ERROR, "Ocurrió un error desconocido intentando obtener el nombre del usuario");
        return null;
    }

    /**
     * Verifica si dos usuarios son iguales
     *
     * @param usuario User con el cual comparar.
     * @return true: son iguales | false: son diferentes
     */
    @Override
    public boolean isEqual(User usuario) {
        return (getUserName().equals(usuario.getUserName()));
    }

    /**
     * Verifica si el usuario está marcado como baneado localmente.
     *
     * @return true: está baneado | false: no está baneado
     */
    @Override
    public boolean estaBaneado() {
        return isBanned;
    }

    /**
     * Verifica si el usuario está baneado en el sistema.
     *
     * @deprecated
     * @return true: está baneado | false: no está baneado
     */
    private boolean verificarBaneo() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getUserName() {
        return this.username;
    }

    // <editor-fold defaultstate="collapsed" desc="Sendable">
    @Override
    public String getHead() {
        return HEADSTART + TYPE[USERCLIENT] + HEADEND;
    }

    @Override
    public String getBody() {
        return BODYSTART + this.getUserName() + SEPARATOR + this.getGrupo() + BODYEND;
    }

    /**
     * @param informacion
     * @return
     */
    public static ClientUser buildObject(String informacion) {
        int i = informacion.indexOf(BODYSTART) + BODYSTART.length();
        int j = informacion.indexOf(BODYEND);
        int k = informacion.indexOf(SEPARATOR);
        String usuario = informacion.substring(i, k);
        String grupon = informacion.substring(k, j);
        ClientUser usuarion = new ClientUser();
        usuarion.setNombreDeUsuario(usuario);
        usuarion.setGrupo(grupon);
        return new ClientUser();
    }

// </editor-fold>
    

    /**
     * @param nombreDeUsuario the nombreDeUsuario to set
     */
    public void setNombreDeUsuario(String nombreDeUsuario) {
        this.username = nombreDeUsuario;
    }

    /**
     * @return the grupo
     */
    public String getGrupo() {
        return group;
    }

    /**
     * @param grupo the grupo to set
     */
    public void setGrupo(String grupo) {
        this.group = grupo;
    }

}
