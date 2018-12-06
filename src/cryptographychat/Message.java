package cryptographychat;

import java.io.Serializable;
import java.math.BigInteger;

public class Message implements Serializable {
    private User remitente;
    private String ip_dest;
    private BigInteger publica_dest;
    private BigInteger[] mensaje_rsa;
    private int[] mensaje_des;
    private String algoritmo;
    private int hash;
    
    public Message(User remitente, String ip_dest, BigInteger publica_dest, String algoritmo, BigInteger[] mensaje_rsa, int[] mensaje_des, int hash) {
        this.remitente = remitente;
        this.ip_dest = ip_dest;
        this.publica_dest = publica_dest;
        this.mensaje_rsa = mensaje_rsa;
        this.mensaje_des = mensaje_des;
        this.algoritmo = algoritmo;
        this.hash = hash;
    }
    
    public String toString() {
        String cadena = remitente.obtenerNombre();
        if(algoritmo.equals("DES"))
            for(Integer i: mensaje_des)
                cadena += Integer.toString(i);
        else
            for(BigInteger b: mensaje_rsa)
                cadena += b.toString();
        
        if(cadena.length() > 40)
            cadena = cadena.substring(0, 37) + "...";
        
        return cadena;
    }
}
