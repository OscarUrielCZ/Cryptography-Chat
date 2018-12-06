package cryptographychat;

import java.io.Serializable;
import java.math.BigInteger;

public class User implements Serializable {
    private String nombre;
    private RSA rsa;
    
    // Constructor
    
    public User(String nombre, RSA rsa) {
        this.nombre = nombre;
        this.rsa = rsa;
    }
    
    // setters y getters
    
    public String obtenerNombre() {
        return nombre;
    }
    
    public BigInteger obtenerPrivada() {
        return rsa.obtenerLlavePrivada();
    }
    
    public BigInteger obtenerPublica() {
        return rsa.obtenerLlavePublica();
    }
    
    public RSA obtenerRSA() {
        return rsa;
    }
    
    public String toString() {
        return nombre + " " + rsa.obtenerLlavePublica();
    }
}
