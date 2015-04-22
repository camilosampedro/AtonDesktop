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
package gui;

import exception.NotFound;
import identity.Room;
import identity.ServerComputer;
import international.LanguagesController;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

/**
 *
 * @author Camilo Sampedro
 */
public class RoomEditorTableModel implements TableModel {

    private Room room;

    public RoomEditorTableModel(Room room) {
        this.room = room;
    }

    @Override
    public int getRowCount() {
        return room.getComputerCount();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return LanguagesController.getWord("Row");
            case 1:
                return LanguagesController.getWord("Number");
            case 2:
                return LanguagesController.getWord("IP");
            case 3:
                return LanguagesController.getWord("Mac");
            default:
                return null;
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return Integer.class;
            case 1:
                return Integer.class;
            case 2:
                return String.class;
            case 3:
                return String.class;
            default:
                return null;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return true;
            case 1:
                return true;
            case 2:
                return true;
            case 3:
                return true;
            default:
                return false;
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return room.getRowNumber(room.findComputerRow(rowIndex));
            case 1: {
                try {
                    return ((ServerComputer) room.getComputer(columnIndex)).getComputerNumber();
                } catch (NotFound ex) {
                    Logger.getLogger(RoomEditorTableModel.class.getName()).log(Level.SEVERE, null, ex);
                    return null;
                }
            }
            case 2: {
                try {
                    return ((ServerComputer) room.getComputer(columnIndex)).getIP();
                } catch (NotFound ex) {
                    Logger.getLogger(RoomEditorTableModel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            case 3: {
                try {
                    return ((ServerComputer) room.getComputer(columnIndex)).getMac();
                } catch (NotFound ex) {
                    Logger.getLogger(RoomEditorTableModel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            default:
                return null;
        }
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addTableModelListener(TableModelListener l) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void removeTableModelListener(TableModelListener l) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
