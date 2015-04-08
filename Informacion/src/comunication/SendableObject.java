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
package comunication;

/**
 *
 * @author Camilo Sampedro
 * @version 0.1.3
 */
public abstract class SendableObject {

    // <editor-fold defaultstate="collapsed" desc="Constants">
    // <editor-fold defaultstate="collapsed" desc="Construction constants">
    public static final String BODYSTART = "*¡*{";
    public static final String BODYEND = "}*!*";
    public static final String HEADSTART = "*[";
    public static final String HEADEND = "]*";
    public static final String SEPARATOR = "*(/)*";
    public static final String LISTSEPARATOR = "*(=)*";
    public static final int PACKETSIZE = 2048;
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="SendableObject types">
    public static final byte RESULT = 0;
    public static final byte SERVERCOMPUTER = 1;
    public static final byte ORDER = 2;
    public static final byte REQUEST = 3;
    public static final byte USERCLIENT = 4;
    public static final byte CLIENTCOMPUTER = 5;
    // </editor-fold>
// </editor-fold>

    public static final String[] TYPE = {"RESULT", "SERVERCOMPUTER", "ORDER", "REQUEST", "USERCLIENT", "CLIENTCOMPUTER"};

    public abstract String getHead();

    public abstract String getBody();

    public final String generateString(){
        return getHead() + getBody();
    }

}
