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

import comunication.ServerComunicator;
import execution.Request;
import exception.NotFound;
import identity.ServerComputer;
import identity.Row;
import identity.Room;
import identity.Salon;
import gui.SalonsGUI;
import international.LanguagesController;
import java.io.FileWriter;
import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import timer.TimerTaskChecker;

/**
 *
 * @author Camilo Sampedro
 * @version 0.3.0
 */
public class Information extends Thread {

    private static ArrayList<ServerComputer> conectedComputers;
    private static ArrayList<Salon> salons;
    public static SalonsGUI mainInterface;

    private static String informationPath = "informacion.xml";
    private static String logFile = "logServer.log";

    /**
     * Get the value of informationPath
     *
     * @return the value of informationPath
     */
    public static String getRutaInformacion() {
        return informationPath;
    }

    /**
     * Set the value of informationPath
     *
     * @param informationPath new value of informationPath
     */
    public static void setInformationPath(String informationPath) {
        Information.informationPath = informationPath;
    }

    private static boolean mode;

    /**
     * Get the value of modo
     *
     * @return the value of modo
     */
    public static boolean isConsoleMode() {
        return mode;
    }

    public static final boolean CONSOLEMODE = true;
    public static final boolean GRAPHICMODE = false;

    private static void startClock() {
        TimerTask timerTask = new TimerTaskChecker();
        //running timer task as daemon thread
        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(timerTask, 0, 3 * 1000);
        System.out.println("TimerTask started");
    }

    public static void contactComputers() throws SocketException, IOException {
        for (ServerComputer computer : conectedComputers) {
            Request request = new Request(Request.CONECTION);
            ServerComunicator.sendObject(request, computer.getIP());
        }
    }

    @Override
    public void run() {
        for (ServerComputer equipo : conectedComputers) {
            try {
                Request solicitud = new Request(Request.CONECTION);
                if (!ServerComunicator.isReachable(equipo.getIP())) {
                    conectedComputers.remove(equipo);
                } else {
                    ServerComunicator.sendObject(solicitud, equipo.getIP());
                }
            } catch (UnknownHostException ex) {
                Logger.getLogger(Information.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SocketException ex) {
                Logger.getLogger(Information.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Information.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        try {
            Thread.sleep(100);
        } catch (InterruptedException ex) {
            Logger.getLogger(Information.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void agregarEquipoConectado(String ip) throws NotFound {
        ServerComputer equipo = null;
        for (Salon salon : salons) {
            equipo = salon.findByIP(ip);
            if (equipo != null) {
                break;
            }
        }
        if (equipo == null) {
            throw new NotFound(NotFound.COMPUTER, ip);
        }
        conectedComputers.add(equipo);
    }

    public static ServerComputer findByIP(String ip) throws NotFound {
        ServerComputer equipo = null;
        for (Salon salon : salons) {
            equipo = salon.findByIP(ip);
            if (equipo != null) {
                break;
            }
        }
        if (equipo == null) {
            throw new NotFound(NotFound.COMPUTER, ip);
        }
        return equipo;
    }

    public static void initialize(boolean modo, String language) {
        salons = new ArrayList();
        conectedComputers = new ArrayList();
        Information.mode = modo;
        ServerComunicator.inicializar(5978);
        logs.LogCreator.asignarArchivoLog(logFile);
        leerDatos();
        LanguagesController.initializeLanguage(language);
        if (!Information.mode) {
            mainInterface = new SalonsGUI(salons);
            mainInterface.setVisible(true);
        }
        startClock();
        ServerComunicator.despertar();
    }

    public static void checkComputers() throws SocketException, IOException {
        for(ServerComputer computer: conectedComputers){
            Request request = new Request(Request.CONECTION);
            comunication.Comunicator.sendObject(request, computer.getIP());
        }
    }

    public static void generarXML() {
        Document documento;
        Element raiz = new Element("Salones");
        documento = new Document(raiz);
        for (Salon salon : salons) {
            Element salone = new Element("Salon");
            salone.setAttribute("Nombre", salon.getName());
            for (Room sala : salon.getRooms()) {
                Element salae = new Element("Sala");
                salae.setAttribute("Nombre", sala.getName());
                salae.setAttribute("Horizontal", Boolean.toString(sala.isHorizontal()));
                salae.addContent(new Element("SufijoIP").setText(Integer.toString(sala.getRoomIPSufix())));
                for (Row fila : sala.getRows()) {
                    Element filae = new Element("Fila");
                    for (ServerComputer equipo : fila.getComputers()) {
                        Element equipoe = new Element("Equipo");
                        equipoe.setAttribute("Numero", Integer.toString(equipo.getComputerNumber()));
                        equipoe.addContent(new Element("IP").setText(equipo.getIP()));
                        equipoe.addContent(new Element("Mac").setText(equipo.getMac()));
                        equipoe.addContent(new Element("Hostname").setText(equipo.getHostname()));
                        for (String[] resultados : equipo.getResults()) {
                            String orden = resultados[0];
                            String resultado = resultados[1];
                            Element resultadoe = new Element("Ejecucion");
                            resultadoe.addContent(new Element("Orden").setText(orden));
                            resultadoe.addContent(new Element("Resultado").setText(resultado));
                            equipoe.addContent(resultadoe);
                        }
                        filae.addContent(equipoe);
                    }
                    salae.addContent(filae);
                }
                salone.addContent(salae);
            }
            raiz.addContent(salone);
        }

        XMLOutputter xmlOutput = new XMLOutputter();
        try {
            xmlOutput.output(documento, System.out);
            xmlOutput.setFormat(Format.getPrettyFormat());
            xmlOutput.output(documento, new FileWriter(informationPath));
        } catch (IOException ex) {
            Logger.getLogger(Information.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void leerDatos() {
        java.io.File archivo = new java.io.File(informationPath);
        SAXBuilder saxBuilder = new SAXBuilder();
        try {
            Document documento = saxBuilder.build(archivo);
            Element raiz = documento.getRootElement();
            List<Element> elementosSalon = raiz.getChildren("Salon");
            for (Element salone : elementosSalon) {
                Salon salon = new Salon(salone.getAttributeValue("Nombre"));
                List<Element> elementosSala = salone.getChildren("Sala");
                for (Element salae : elementosSala) {
                    Room sala = new Room(salae.getAttributeValue("Nombre"), Boolean.parseBoolean(salae.getAttributeValue("Horizontal")), Integer.parseInt(salae.getAttributeValue("SufijoIP")));
                    List<Element> elementosFila = salae.getChildren("Fila");
                    for (Element filae : elementosFila) {
                        Row fila = new Row(sala.isHorizontal());
                        List<Element> elementosEquipo = filae.getChildren("Equipo");
                        for (Element equipoe : elementosEquipo) {
                            ServerComputer equipo = new ServerComputer(sala);
                            equipo.setComputerNumber(Integer.parseInt(equipoe.getAttributeValue("Numero")));
                            equipo.setIP(equipoe.getChildText("IP"));
                            equipo.setMac(equipoe.getChildText("Mac"));
                            equipo.setHostname(equipoe.getChildText("Hostname"));
                            List<Element> elementosEjecucion = equipoe.getChildren("Ejecucion");
                            for (Element ejecucione : elementosEjecucion) {
                                String[] resultado = {ejecucione.getAttributeValue("Orden"), ejecucione.getAttributeValue("Resultado")};
                                equipo.addResult(resultado);
                            }
                            fila.addComputer(equipo);
                        }
                        sala.addRow(fila);
                    }
                    salon.addRoom(sala);
                }
                salons.add(salon);
            }
        } catch (JDOMException ex) {
            Logger.getLogger(Information.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Information.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
