package cryptographychat;

import java.io.Serializable;
import java.math.BigInteger;

public class Message implements Serializable {
    private static final long serialVersionUID = 6529685098267757690L;
    private RSA rsa;
    private String destIP;
    private BigInteger publicDest;
    private BigInteger[] messageRSA;
    private int[] messageDES;
    private String algorithm;
    private int hash;
    
    public Message(RSA rsa, String destIP, BigInteger publicDest, String algorithm, BigInteger[] messageRSA, int[] messageDES, int hash) {
        this.rsa = rsa;
        this.destIP = destIP;
        this.publicDest = publicDest;
        this.messageRSA = messageRSA;
        this.messageDES = messageDES;
        this.algorithm = algorithm;
        this.hash = hash;
    }
    
    public RSA getRSA() {
        return rsa;
    }
    
    public String getAlgorithm() {
        return algorithm;
    }
    
    public String getMessage() {
        String string = "";
        if(algorithm.equals("DES"))
            for(Integer i: messageDES)
                string += Integer.toString(i);
        else
            for(BigInteger b: messageRSA)
                string += b.toString();
        
        return string;
    }
    
    public int[] getDESMessage() {
        return messageDES;
    }
    
    public BigInteger[] getRSAMessage() {
        return messageRSA;
    }
    
    public int getHash() {
        return hash;
    }
    
    public String toString() {
        String string = rsa.getPublicKey() + ": ";
        if(algorithm.equals("DES"))
            for(Integer i: messageDES)
                string += Integer.toString(i);
        else
            for(BigInteger b: messageRSA)
                string += b.toString();
        
        if(string.length() > 50)
            string = string.substring(0, 47) + "...";
        
        return string;
    }
}
