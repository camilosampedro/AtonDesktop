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

import static comunication.SendableObject.BODYEND;
import static comunication.SendableObject.BODYSTART;
import static comunication.SendableObject.SEPARATOR;
import execution.Execution;
import execution.Function;
import execution.Order;
import international.LanguagesController;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import logs.LogCreator;

/**
 * Implementación de la clase Equipo ubicada en los clientes.
 *
 * @author Camilo Sampedro
 * @version 0.1.5
 */
public class ClientComputer extends Computer {

    protected int number;
    protected String mac;
    protected String ip;
    protected String hostname;
    protected boolean onUse;
    private final ArrayList<ClientUser> users;

    public ClientComputer() {
        users = new ArrayList();
    }

    public ClientComputer(String mac, String ip, String hostname, boolean enUso) {
        this.mac = mac;
        this.ip = ip;
        this.hostname = hostname;
        this.onUse = enUso;
        this.users = new ArrayList();
    }

    public static ClientComputer initializeActualComputer() {
        ClientComputer computer = new ClientComputer();
        computer.setIP(getActualComputerIP());
        computer.setMac(getActualComputerMAC());
        for (ClientUser singleUser : ClientUser.generateUserList()) {
            computer.addUser(singleUser);
        }
        System.out.println(LanguagesController.getWord("ActualIP") + ":" + computer.ip);
        System.out.println(LanguagesController.getWord("ActualMac") + ":" + computer.mac);
        return computer;
    }

    private static String getActualComputerMAC() {
        assert ClientUser.isRoot();
        try {
            Order order = new Order(Function.ALT_MAC_ORDER);
            Execution.execute(order);
            return order.getResult().getResult();
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(ClientComputer.class.getName()).log(Level.SEVERE, null, ex);
            LogCreator.addToLog(LogCreator.ERROR, "Error asking for computer's Mac");
            return null;
        }
    }

    public static String getActualComputerIP() {
        assert ClientUser.isRoot();
        try {
            Order orden = new Order(Function.ALT_IP_ORDER);
            Execution.execute(orden);
            return orden.getResult().getResult();
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(ClientComputer.class.getName()).log(Level.SEVERE, null, ex);
            LogCreator.addToLog(LogCreator.ERROR, "Error al buscar la IP del equipo");
        }
        return null;
    }

    //<editor-fold defaultstate="collapsed" desc="Getters and setters">
    @Override
    public String getIP() {
        return this.ip;
    }

    @Override
    public String getMac() {
        return this.mac;
    }

    @Override
    public String getHostname() {
        try {
            Order orden = new Order(Function.HOST_ORDER);
            Execution.execute(orden);
            hostname = orden.getResult().getResult();
        } catch (IOException ex) {
            Logger.getLogger(ClientComputer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(ClientComputer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    @Override
    public ArrayList<ClientUser> getUsers() {
        return users;
    }

    @Override
    public void addUser(User usuario) {
        users.add((ClientUser) usuario);
    }

    @Override
    public void setIP(String ip) {
        this.ip = ip;
    }

    @Override
    public void setMac(String mac) {
        this.mac = mac;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="SendableObject">
    @Override
    public String getHead() {
        return HEADSTART + TYPE[CLIENTCOMPUTER] + HEADEND;
    }

    @Override
    public String getBody() {
        return BODYSTART + this.mac + SEPARATOR + this.ip + SEPARATOR + this.hostname + SEPARATOR + this.onUse + BODYEND;
    }

    public static ClientComputer buildObject(String information) {
        int i = information.indexOf(BODYSTART) + BODYSTART.length();
        int j = information.indexOf(BODYEND);
        String info = information.substring(i, j);
        StringTokenizer tokens = new StringTokenizer(info, SEPARATOR);
        return new ClientComputer(tokens.nextToken(), tokens.nextToken(), tokens.nextToken(), Boolean.parseBoolean(tokens.nextToken()));
    }
    //</editor-fold>

}
