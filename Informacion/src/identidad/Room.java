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
package identidad;

import execution.Execution;
import execution.Function;
import execution.Order;
import exception.NotFound;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Camilo Sampedro
 * @version 0.1.0
 */
public class Room {

    protected ArrayList<Row> rows;
    protected String name;
    protected int roomIPSufix;
    protected boolean isHorizontal;

    public Room(String name, boolean isHorizontal, int ipSufix) {
        rows = new ArrayList();
        this.isHorizontal = isHorizontal;
        this.name = name;
        this.roomIPSufix = ipSufix;
    }

    public Row getRow(int rowNumber) throws NotFound {
        return (Row) rows.get(rowNumber);
    }

    public String getName() {
        return name;
    }

    public Computer getComputer(int computerNumber) throws NotFound {
        Row row = this.findComputerRow(computerNumber);
        if (row == null) {
            throw new exception.NotFound(exception.NotFound.COMPUTER, "Room.getComputer()");
        } else {
            return row.getComputer(computerNumber);
        }
    }

    public void addComputer(int row, Computer computer) {
        rows.get(row).addComputer(computer);
    }

    public void addRow(boolean isHorizontal) {
        rows.add(new Row(isHorizontal));
    }

    public void turnOffComputer(int computerNumber) throws NotFound {
        Row fila = this.findComputerRow(computerNumber);
        if (fila != null) {
            String mac = fila.getComputer(computerNumber).getMac();
            Order orden = new Order(Function.COMPUTER_WAKEUP_ORDER(roomIPSufix, mac));
            try {
                Execution.execute(orden);
            } catch (IOException ex) {
                Logger.getLogger(Room.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                Logger.getLogger(Room.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void turnOffAll() {
        for (Row fila : rows) {
            fila.turnOffAll();
        }
    }

    public void notify(String mensaje) {
        for (Row fila : rows) {
            fila.notify(mensaje);
        }
    }

    public void changePowerState(int computerNumber, boolean powerState) {
        Row row = (Row) findComputerRow(computerNumber);
        if (row != null) {
            ServerComputer computer = (ServerComputer) row.getComputer(computerNumber);
            if (computer != null) {
                computer.setPowerState(powerState);
            }
        }
    }

    public void turnOnAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void turnOnComputer(int computerNumber) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Row findComputerRow(int computerNumber) {
        for (Row row : rows) {
            if (row.containsComputer(computerNumber)) {
                return row;
            }
        }
        return null;
    }

    public int getRoomIPSufix() {
        return this.roomIPSufix;
    }

    public ServerComputer findByIP(String ip) {
        ServerComputer computer;
        for (Row row : rows) {
            computer = row.findByIP(ip);
            if (computer != null) {
                return computer;
            }
        }
        return null;
    }

    public ArrayList<Row> getRows() {
        return (ArrayList<Row>) rows.clone();
    }

    public boolean isHorizontal() {
        return this.isHorizontal;
    }

    public void addRow(Row row) {
        rows.add(row);
    }
}
