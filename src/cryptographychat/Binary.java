package cryptographychat;

public class Binary {

    public static String getBinary(String text) { // retorna el binario de una cadena
        byte[] asciis = getASCIIs(text);
        String finalString = "";

        for (int i = 0; i < asciis.length; i++) {
            finalString += getBinary(asciis[i]);
        }

        return finalString;
    }

    public static String getBinary(int num) { // retorna el binario de un entero
        return byter(Integer.toBinaryString(num));
    }

    public static String getBinary(char c) { // retorna el binario de un caracter
        return byter(getBinary((int) c));
    }

    public static int getInteger(String bits) { // retorna el entero de una cadena de 8 bits
        return (int) Integer.parseInt(bits, 2);
    }

    private static String byter(String bits) { // retorna una cadena con 8 bits
        String finalString = "";

        for (int i = bits.length(); i < 8; i++) {
            finalString += "0";
        }

        return finalString += bits;
    }

    public static byte[] getASCIIs(String text) { // retorna los valores ascii de cada caracter de una cadena
        return text.getBytes();
    }

    public static String getValue(String bits) { // retorna el texto de una cadena de 8 bits
        String finalString = "", auxString;

        for (int i = 0; i < bits.length(); i += 8) {
            auxString = bits.substring(i, i + 8); // obtenemos cadenas de 8 bits
            finalString += "" + (char) getInteger(auxString); // concatenamos el valor en ascii
        }

        return finalString;
    }

    public static int[] getBytes(String bits) { // retorna una arreglo de bytes del ascci de una cadena de bits
        String auxString;
        
        int[] output = new int[bits.length() / 8];
        for (int i = 0; i < bits.length(); i += 8) {
            auxString = bits.substring(i, i + 8); // obtenemos cadenas de 8 bits
            output[i / 8] = getInteger(auxString); // agregamos el valor en ascii
        }

        return output;
    }

    public static String xor(String a, String b) { // retorna el xor de a y b que son bits
        if (a.equals("1")) {
            if (b.equals("1")) {
                return "0";
            } else {
                return "1";
            }
        } else {
            if (b.equals("1")) {
                return "1";
            } else {
                return "0";
            }
        }
    }
}
