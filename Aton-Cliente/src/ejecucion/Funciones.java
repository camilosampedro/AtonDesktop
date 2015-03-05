/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejecucion;

/**
 *
 * @author camilo
 */
public final class Funciones {
    public static final String ORDENIP = "ifconfig eth0 2>/dev/null|awk '/Direc. inet:/ {print $2}'|sed 's/inet://'";
    public static final String ORDENMAC = "ifconfig eth0 2>/dev/null|awk '/direcciónHW/ {print $5}'";
    public static final String ORDENHOST = "cat /etc/hostname";
    public static final String ORDENROOT = "id -u";
    public static final String ORDENUSUARIO = "whoamai";
}
