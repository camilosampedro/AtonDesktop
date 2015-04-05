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
package informacion;

import comunicacion.Comunicacion;
import ejecucion.Solicitud;
import exception.NoEncontrado;
import identidad.EquipoServidor;
import identidad.Fila;
import identidad.Sala;
import identidad.Salon;
import java.io.FileWriter;
import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

/**
 *
 * @author Camilo Sampedro
 * @version 0.3.0
 */
public class Informacion extends Thread {

    private static ArrayList<EquipoServidor> equiposConectados;
    private static ArrayList<Salon> salones;

    private static String rutaInformacion = "salones.xml";

    /**
     * Get the value of rutaInformacion
     *
     * @return the value of rutaInformacion
     */
    public static String getRutaInformacion() {
        return rutaInformacion;
    }

    /**
     * Set the value of rutaInformacion
     *
     * @param rutaInformacion new value of rutaInformacion
     */
    public static void setRutaInformacion(String rutaInformacion) {
        Informacion.rutaInformacion = rutaInformacion;
    }

    private static boolean modo;

    /**
     * Get the value of modo
     *
     * @return the value of modo
     */
    public static boolean isModoConsola() {
        return modo;
    }

    public static final boolean MODOCONSOLA = true;
    public static final boolean MODOGRAFICO = false;

    @Override
    public void run() {
        for (EquipoServidor equipo : equiposConectados) {
            try {
                Solicitud solicitud = new Solicitud(Solicitud.CONEXION);
                if (!Comunicacion.esAccesible(equipo.obtenerIP())) {
                    equiposConectados.remove(equipo);
                } else {
                    Comunicacion.enviarObjeto(solicitud, equipo.obtenerIP());
                }
            } catch (UnknownHostException ex) {
                Logger.getLogger(Informacion.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SocketException ex) {
                Logger.getLogger(Informacion.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Informacion.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        try {
            Thread.sleep(100);
        } catch (InterruptedException ex) {
            Logger.getLogger(Informacion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void agregarEquipoConectado(String ip) throws NoEncontrado {
        EquipoServidor equipo = null;
        for (Salon salon : salones) {
            equipo = salon.buscarPorIP(ip);
            if (equipo != null) {
                break;
            }
        }
        if (equipo == null) {
            throw new NoEncontrado(NoEncontrado.EQUIPO, ip);
        }
        equiposConectados.add(equipo);
    }

    public static EquipoServidor buscarEquipo(String ip) throws NoEncontrado {
        EquipoServidor equipo = null;
        for (Salon salon : salones) {
            equipo = salon.buscarPorIP(ip);
            if (equipo != null) {
                break;
            }
        }
        if (equipo == null) {
            throw new NoEncontrado(NoEncontrado.EQUIPO, ip);
        }
        return equipo;
    }

    public static void inicializar(boolean modo) {
        Informacion.modo = modo;
        Comunicacion.inicializar(5901);
        Comunicacion.despertar();
        if (!Informacion.modo) {
            leerDatos();
        }
    }

    public static void generarXML() {
        Document documento;
        Element raiz = new Element("Salones");
        documento = new Document(raiz);
        for (Salon salon : salones) {
            Element salone = new Element("Salon");
            salone.setAttribute("Nombre", salon.getNombre());
            for (Sala sala : salon.getSalas()) {
                Element salae = new Element("Sala");
                salae.setAttribute("Nombre", sala.obtenerNombre());
                salae.setAttribute("Horizontal", Boolean.toString(sala.esHorizontal()));
                salae.addContent(new Element("SufijoIP").setText(Integer.toString(sala.obtenerSufijoSala())));
                for (Fila fila : sala.getFilas()) {
                    Element filae = new Element("Fila");
                    for (EquipoServidor equipo : fila.getEquipos()) {
                        Element equipoe = new Element("Equipo");
                        equipoe.setAttribute("Numero", Integer.toString(equipo.getNumeroEquipo()));
                        equipoe.addContent(new Element("IP").setText(equipo.obtenerIP()));
                        equipoe.addContent(new Element("Mac").setText(equipo.obtenerMAC()));
                        equipoe.addContent(new Element("Hostname").setText(equipo.obtenerHostname()));
                        for (String[] resultados : equipo.getResultados()) {
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
            xmlOutput.output(documento, new FileWriter(rutaInformacion));
        } catch (IOException ex) {
            Logger.getLogger(Informacion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void leerDatos() {
        java.io.File archivo = new java.io.File(rutaInformacion);
        SAXBuilder saxBuilder = new SAXBuilder();
        try {
            Document documento = saxBuilder.build(archivo);
            Element raiz = documento.getRootElement();
            List<Element> elementosSalon = raiz.getChildren("Salon");
            for (Element salone : elementosSalon) {
                Salon salon = new Salon(salone.getAttributeValue("Nombre"));
                List<Element> elementosSala = salone.getChildren("Sala");
                for (Element salae : elementosSala) {
                    Sala sala = new Sala(salae.getAttributeValue("Nombre"), Boolean.parseBoolean(salae.getAttributeValue("Horizontal")));
                    List<Element> elementosFila = salae.getChildren("Fila");
                    for (Element filae : elementosFila) {
                        Fila fila = new Fila(sala.esHorizontal());
                        List<Element> elementosEquipo = filae.getChildren("Equipo");
                        for (Element equipoe : elementosEquipo) {
                            EquipoServidor equipo = new EquipoServidor(sala);
                            equipo.setNumeroEquipo(Integer.parseInt(equipoe.getAttributeValue("Numero")));
                            equipo.asignarIP(equipoe.getChildText("IP"));
                            equipo.asignarMAC(equipoe.getChildText("Mac"));
                            equipo.asignarHostname(equipoe.getChildText("Hostname"));
                            List<Element> elementosEjecucion = equipoe.getChildren("Ejecucion");
                            for (Element ejecucione : elementosEjecucion) {
                                String[] resultado = {ejecucione.getAttributeValue("Orden"), ejecucione.getAttributeValue("Resultado")};
                                equipo.addResultado(resultado);
                            }
                        }
                    }
                }
                salones.add(salon);
            }
        } catch (JDOMException ex) {
            Logger.getLogger(Informacion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Informacion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
