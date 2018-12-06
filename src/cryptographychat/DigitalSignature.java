package cryptographychat;

import java.math.BigInteger;

public class DigitalSignature {
  public static int getDigitalSignature(String text) {
    byte[] asciis = Binary.getASCIIs(text);
    String number = "";

    for(int i = 0; i < asciis.length; i++)
      number += Integer.toString((int)asciis[i]);

    return Math.abs(new BigInteger(number).hashCode());
  }
}