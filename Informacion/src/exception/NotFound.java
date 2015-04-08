/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exception;

/**
 *
 * @author Camilo Sampedro
 * @version 0.1.0
 */
public class NotFound extends Exception {

    public static final byte COMPUTER = 0;
    public static final byte ROW = 1;
    public static final String[] texts = {"Computer", "Row"};

    public NotFound(byte type, String message) {

        super(texts [type] + " not found: " + message);

    }
}
