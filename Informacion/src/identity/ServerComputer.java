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
import execution.Execution;
import execution.Function;
import execution.Order;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Camilo Sampedro
 * @version 0.1.6
 */
public class ServerComputer extends Computer {

    public static final boolean OCUPIED_STATE = true;
    public static final boolean FREE_STATE = false;
    public static final boolean POWERED_ON_STATE = true;
    public static final boolean POWERED_OFF_STATE = false;
    public static final String IP_PROPERTY = "ip";
    public static final String MAC_PROPERTY = "mac";
    public static final String ACTUAL_ORDER_PROPERTY = "actual order";
    public static final String USE_STATE_PROPERTY = "use state";
    public static final String POWER_STATE_PROPERTY = "power state";
    
    private final PropertyChangeSupport propertyChangeSupport;
    private boolean useState;
    private boolean powerState;
    private int computerNumber;
    private String ip;
    private String mac;
    private String hostname;
    private ArrayList<User> users;
    private ArrayList<String[]> results;
    private Order actualOrder;
    private Room parentRoom;

    /**
     * Get the value of results
     *
     * @return the value of results
     */
    public ArrayList<String[]> getResults() {
        return (ArrayList<String[]>) results.clone();
    }

    /**
     * Add the value of results
     *
     * @param result new value of results
     */
    public void addResult(String[] result) {
        this.results.add(result);
    }

    /**
     * Get the value of actualOrder
     *
     * @return the value of actualOrder
     */
    public Order getActualOrder() {
        return actualOrder;
    }

    /**
     * Set the value of actualOrder
     *
     * @param actualOrder new value of actualOrder
     */
    public void setActualOrder(Order actualOrder) {
        this.actualOrder = actualOrder;
    }

    

    public ServerComputer(Room container) {
        this.results = new ArrayList();
        this.users = new ArrayList();
        parentRoom = container;
        propertyChangeSupport = new PropertyChangeSupport(this);
    }

    public ServerComputer(String ip, String mac, int numeroEquipo, boolean estadoPoder, boolean estadoUso, Room sala) {
        this.results = new ArrayList();
        this.users = new ArrayList();
        this.ip = ip;
        this.mac = mac;
        this.computerNumber = numeroEquipo;
        this.powerState = estadoPoder;
        this.useState = estadoUso;
        this.parentRoom = sala;
        propertyChangeSupport = new PropertyChangeSupport(this);
    }

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
        return this.hostname;
    }

    @Override
    public ArrayList<User> getUsers() {
        return users;
    }

    @Override
    public void addUser(User user) {
        users.add(user);
    }

    @Override
    public void setIP(String ip) {
        String oldValue = this.ip;
        this.ip = ip;
        propertyChangeSupport.firePropertyChange(IP_PROPERTY, oldValue, this.ip);
    }

    @Override
    public void setMac(String mac) {
        String oldValue = this.mac;
        this.mac = mac;
        propertyChangeSupport.firePropertyChange(MAC_PROPERTY, oldValue, this.mac);
    }

    /**
     * @return the useState
     */
    public boolean isUsed() {
        return useState;
    }

    /**
     * @param useState the useState to set
     */
    public void setUseState(boolean useState) {
        boolean oldValue = this.useState;
        this.useState = useState;
        propertyChangeSupport.firePropertyChange(USE_STATE_PROPERTY, oldValue, this.useState);
    }

    /**
     * @return the powerState
     */
    public boolean isPoweredOn() {
        return powerState;
    }

    /**
     * @param powerState the powerState to set
     */
    public void setPowerState(boolean powerState) {
        boolean oldValue = this.powerState;
        this.powerState = powerState;
        propertyChangeSupport.firePropertyChange(POWER_STATE_PROPERTY, oldValue, this.powerState);
    }

    /**
     * @return the computerNumber
     */
    public int getComputerNumber() {
        return computerNumber;
    }

    /**
     * @param computerNumber the computerNumber to set
     */
    public void setComputerNumber(int computerNumber) {
        this.computerNumber = computerNumber;
    }

    public boolean turnOn() {
        Order order = new Order(Function.COMPUTER_WAKEUP_ORDER(parentRoom.getRoomIPSufix(), this.mac));
        try {
            Execution.execute(order);
            return order.isSuccessful();
        } catch (IOException ex) {
            Logger.getLogger(ServerComputer.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(ServerComputer.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public String getHead() {
        return HEADSTART + TYPE[SERVERCOMPUTER] + HEADEND;
    }

    @Override
    public String getBody() {
        return BODYSTART + ip + SEPARATOR + mac + SEPARATOR + computerNumber
                + SEPARATOR + powerState + SEPARATOR + useState + SEPARATOR
                + parentRoom.getName() + SEPARATOR + parentRoom.isHorizontal
                + SEPARATOR + parentRoom.roomIPSufix + BODYEND;
    }

    public static ServerComputer buildObject(String informacion) {
        int i = informacion.indexOf(BODYSTART) + BODYSTART.length();
        int j = informacion.indexOf(BODYEND);
        String info = informacion.substring(i, j);
        StringTokenizer tokens = new StringTokenizer(info, SEPARATOR);
        //Probar
        return new ServerComputer(tokens.nextToken(), tokens.nextToken(), Integer.parseInt(tokens.nextToken()), Boolean.parseBoolean(tokens.nextToken()), Boolean.parseBoolean(tokens.nextToken()), new Room(tokens.nextToken(), Boolean.parseBoolean(tokens.nextToken()), Integer.parseInt(tokens.nextToken())));
    }

    public void copyComputer(ClientComputer computer) {
        this.useState = computer.onUse;
        this.mac = computer.mac;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public void addPropertyChangeListener(PropertyChangeListener l) {
        propertyChangeSupport.addPropertyChangeListener(l);
    }

    public void removePropertyChangeListener(PropertyChangeListener l) {
        propertyChangeSupport.removePropertyChangeListener(l);
    }
}
