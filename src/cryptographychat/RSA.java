package cryptographychat;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Random;

public class RSA implements Serializable { // Rivest, Shamir y Adleman
	private BigInteger e; // llave públca
        private BigInteger n; // módulo
        private BigInteger d; // llave privada

    // constructores
    public RSA(int longBitsPrimos) {
        setValues(longBitsPrimos);
    }

    public RSA(BigInteger e, BigInteger d, BigInteger n) {
        this.e = e;
        this.d = d;
        this.n = n;
    }

    // setters y getters
    public void setValues(int longBitsPrimos) {
        BigInteger p, q, phi;

        p = new BigInteger(longBitsPrimos, 100, new Random());
        do {
            q = new BigInteger(longBitsPrimos, 100, new Random());
        } while (p.compareTo(q) == 0); // Mientras sean iguales p y q

        n = p.multiply(q);
        phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));

        do {
            e = new BigInteger(2 * longBitsPrimos, new Random());
        } while (e.gcd(phi).compareTo(BigInteger.ONE) != 0 || e.compareTo(phi) != -1); // mientras el mcd sea diferente de 1 o e sea mayor a phi

        d = e.modInverse(phi);
    }
    
    public BigInteger getPublicKey() {
        return e;
    }

    public BigInteger getPrivateKey() {
        return d;
    }
    
    public BigInteger obtenerModulo() {
        return n;
    }

    // operaciones
    public BigInteger[] cifrar(String texto) {
        byte[] asciis = Binary.getASCIIs(texto);
        BigInteger[] cifrado = new BigInteger[asciis.length];
        byte[] aux = new byte[1];

        for (int i = 0; i < asciis.length; i++) {
            aux[0] = asciis[i];
            cifrado[i] = new BigInteger(aux).modPow(e, n);
        }

        return (cifrado);
    }
    
    public BigInteger[] cifrar(String texto, BigInteger publica_dest) {
        byte[] asciis = Binary.getASCIIs(texto);
        BigInteger[] cifrado = new BigInteger[asciis.length];
        byte[] aux = new byte[1];

        for (int i = 0; i < asciis.length; i++) {
            aux[0] = asciis[i];
            cifrado[i] = new BigInteger(aux).modPow(publica_dest, n);
        }

        return (cifrado);
    }

    public String decifrar(BigInteger[] cifrado) {
        char[] texto = new char[cifrado.length];

        for (int i = 0; i < cifrado.length; i++) {
            texto[i] = (char) cifrado[i].modPow(d, n).intValue();
        }

        return new String(texto);
    }
}
