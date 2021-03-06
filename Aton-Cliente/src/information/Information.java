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
package information;

import comunication.ClientComunicator;
import identity.ClientComputer;
import identity.ClientUser;
import international.LanguagesController;
import java.io.IOException;

/**
 *
 * @author Camilo Sampedro
 * @version 0.1.0
 */
public class Information {

    private static ClientUser actualUser;
    private static ClientComputer actualComputer;
    private static final String logFile = "logFile.log";

    /**
     * @return the usuario
     */
    public static ClientUser getActualUser() {
        return actualUser;
    }

    /**
     * @param aUsuario the usuario to set
     */
    public static void setActualUser(ClientUser aUsuario) {
        actualUser = aUsuario;
    }

    /**
     * @return the equipo
     */
    public static ClientComputer getActualComputer() {
        return actualComputer;
    }

    /**
     * @param newComputer the equipo to set
     */
    public static void setActualComputer(ClientComputer newComputer) {
        actualComputer = newComputer;
    }

    public static void initialize(String language) throws IOException, ClassNotFoundException {   
        LanguagesController.initializeLanguage(language);
        
//        if (!ClientUser.isRoot()) {
//            System.err.println(LanguagesController.getWord("RootError"));
//            System.exit(1);
//        }
        logs.LogCreator.asignarArchivoLog(logFile);
        initializeActualComputer();
        ClientComunicator.initialize(LanguagesController.getWord("serverIP"), 5978);
        ClientComunicator.wakeUp();
    }

    private static void initializeActualComputer() {
        actualComputer = ClientComputer.initializeActualComputer();
    }

}
