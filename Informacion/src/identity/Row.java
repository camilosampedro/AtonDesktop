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

import java.util.ArrayList;

/**
 *
 * @author camilo
 * @version 0.1.0
 */
public class Row {

    private ArrayList<ServerComputer> computers;
    private boolean isHorizontal;

    public Row(boolean isHorizontal) {
        computers = new ArrayList();
        this.isHorizontal = isHorizontal;
    }

    public ServerComputer getComputer(int computerNumber) {
        return computers.get(computerNumber);
    }

    public void notify(String message) {
        throw new UnsupportedOperationException();
    }

    public void addComputer(Computer computer) {
        computers.add((ServerComputer) computer);
    }

    public void turnOnAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void turnOffAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean containsComputer(int computerNumber) {
        for (ServerComputer computer : computers) {
            if (computer.getComputerNumber() == computerNumber) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<ServerComputer> getComputers() {
        return computers;
    }

    public boolean isHorizontal() {
        return this.isHorizontal;
    }

    public ServerComputer findByIP(String ip) {
        for (ServerComputer computer : computers) {
            if (computer.getIP().equals(ip)) {
                return computer;
            }
        }
        return null;
    }
}
