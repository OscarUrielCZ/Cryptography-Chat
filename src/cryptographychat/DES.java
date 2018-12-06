package cryptographychat;

public class DES { // Data Encryption Standard

    private static final int[] initialPermutationTable = {
        58, 50, 42, 34, 26, 18, 10, 2,
        60, 52, 44, 36, 28, 20, 12, 4,
        62, 54, 46, 38, 30, 22, 14, 6,
        64, 56, 48, 40, 32, 24, 16, 8,
        57, 49, 41, 33, 25, 17, 9, 1,
        59, 51, 43, 35, 27, 19, 11, 3,
        61, 53, 45, 37, 29, 21, 13, 5,
        63, 55, 47, 39, 31, 23, 15, 7
    };
    private static final int[] finalPermutacionTable = {
        40, 8, 48, 16, 56, 24, 64, 32,
        39, 7, 47, 15, 55, 23, 63, 31,
        38, 6, 46, 14, 54, 22, 62, 30,
        37, 5, 45, 13, 53, 21, 61, 29,
        36, 4, 44, 12, 52, 20, 60, 28,
        35, 3, 43, 11, 51, 19, 59, 27,
        34, 2, 42, 10, 50, 18, 58, 26,
        33, 1, 41, 9, 49, 17, 57, 25
    };
    private static final int[] expansionTable = {
        32, 1, 2, 3, 4, 5,
        4, 5, 6, 7, 8, 9,
        8, 9, 10, 11, 12, 13,
        12, 13, 14, 15, 16, 17,
        16, 17, 18, 19, 20, 21,
        20, 21, 22, 23, 24, 25,
        24, 25, 26, 27, 28, 29,
        28, 29, 30, 31, 32, 1
    };
    private static final int[] parityDropTable = {
        57, 49, 41, 33, 25, 17, 9, 1,
        58, 50, 42, 34, 26, 18, 10, 2,
        59, 51, 43, 35, 27, 19, 11, 3,
        60, 52, 44, 36, 63, 55, 47, 39,
        31, 23, 15, 7, 62, 54, 46, 38,
        30, 22, 14, 6, 61, 53, 45, 37,
        29, 21, 13, 5, 28, 20, 12, 4
    };
    private static final int[] compressionTable = {
        14, 17, 11, 24, 1, 5, 3, 28,
        15, 6, 21, 10, 23, 19, 12, 4,
        26, 8, 16, 7, 27, 20, 13, 2,
        41, 52, 31, 37, 47, 55, 30, 40,
        51, 45, 33, 48, 44, 49, 39, 56,
        34, 53, 46, 42, 50, 36, 29, 32
    };
    private static final String[][] sBox1 = {
        {"1110", "0100", "1101", "0001", "0010", "1111", "1011", "1000", "0011", "1010", "0110", "1100", "0101", "1001", "0000", "0111"},
        {"0000", "1111", "0111", "0100", "1110", "0010", "1101", "1010", "0011", "0110", "1100", "1011", "1001", "0101", "0011", "1000"},
        {"0100", "0001", "1110", "1000", "1101", "0110", "0010", "1011", "1111", "1100", "1001", "0111", "0011", "1010", "0101", "0000"},
        {"1111", "1100", "1000", "0010", "0100", "1001", "0001", "0111", "0101", "1011", "0011", "1110", "1010", "0000", "0110", "1101"}
    };
    private static final String[][] sBox2 = {
        {"1111", "0001", "1000", "1110", "0110", "1011", "0011", "0100", "1001", "0111", "0010", "1101", "1100", "0000", "0101", "1010"},
        {"0011", "1101", "0100", "0111", "1111", "0010", "1000", "1110", "1100", "0000", "0001", "1010", "0110", "1001", "1011", "0101"},
        {"0000", "1110", "0111", "1011", "1010", "0100", "1101", "0001", "0101", "1000", "1100", "0110", "1001", "0011", "0010", "1111"},
        {"1101", "1000", "1010", "0001", "0011", "1111", "0100", "0010", "1011", "0110", "0111", "1100", "0000", "0101", "1110", "1001"}
    };
    private static final String[][] sBox3 = {
        {"1010", "0000", "1001", "1110", "0110", "0011", "1111", "0101", "0001", "1101", "1100", "0111", "1011", "0100", "0010", "1000"},
        {"1101", "0111", "0000", "1001", "0011", "0100", "0110", "1010", "0010", "1000", "0101", "1110", "1100", "1011", "1111", "0001"},
        {"1101", "0110", "0100", "1001", "1000", "1111", "0011", "0000", "1011", "0001", "0010", "1100", "0101", "1010", "1110", "0111"},
        {"0001", "1010", "1101", "0000", "0110", "1001", "1000", "0111", "0100", "1111", "1110", "0011", "1011", "0101", "0010", "1100"}
    };
    private static final String[][] sBox4 = {
        {"0111", "1101", "1110", "0011", "0000", "0110", "1001", "1010", "0001", "0010", "1000", "0101", "1011", "1100", "0100", "1111"},
        {"1101", "1000", "1011", "0101", "0110", "1111", "0000", "0011", "0100", "0111", "0010", "1100", "0001", "1010", "1110", "1001"},
        {"1010", "0110", "1001", "0000", "1100", "1011", "0111", "1101", "1111", "0001", "0011", "1110", "0101", "0010", "1000", "0100"},
        {"0011", "1111", "0000", "0110", "1010", "0001", "1101", "1000", "1001", "0100", "0101", "1011", "1100", "0111", "0010", "1110"}
    };
    private static final String[][] sBox5 = {
        {"0010", "1100", "0100", "0001", "0111", "1010", "1011", "0110", "1000", "0101", "0011", "1111", "1101", "0000", "1110", "1001"},
        {"1110", "1011", "0010", "1100", "0100", "0111", "1101", "0001", "0101", "0000", "1111", "1010", "0011", "1001", "1000", "0110"},
        {"0100", "0010", "0001", "1011", "1010", "1101", "0111", "1000", "1111", "1001", "1100", "0101", "0110", "0011", "0000", "1110"},
        {"1011", "1000", "1100", "0111", "0001", "1110", "0010", "1101", "0110", "1111", "0000", "1001", "1010", "0100", "0101", "0011"}
    };
    private static final String[][] sBox6 = {
        {"1100", "0001", "1010", "1111", "1001", "0010", "0110", "1000", "0000", "1101", "0011", "0100", "1110", "0111", "0101", "1011"},
        {"1010", "1111", "0100", "0010", "0111", "1100", "1001", "0101", "0110", "0001", "1101", "1110", "0000", "1011", "0011", "1000"},
        {"1001", "1110", "1111", "0101", "0010", "1000", "1100", "0011", "0111", "0000", "0100", "1010", "0001", "1101", "1011", "0110"},
        {"0100", "0011", "0010", "1100", "1001", "0101", "1111", "1010", "1011", "1110", "0001", "0111", "1010", "0000", "1000", "1101"}
    };
    private static final String[][] sBox7 = {
        {"0100", "1011", "0010", "1110", "1111", "0000", "1000", "1101", "0011", "1100", "1001", "0111", "0101", "1010", "0110", "0110"},
        {"1101", "0000", "1011", "0111", "0100", "1001", "0001", "1010", "1110", "0011", "0101", "1100", "0010", "1111", "1000", "0110"},
        {"0001", "0100", "1011", "1101", "1100", "0011", "0111", "1110", "1010", "1111", "0110", "1000", "0000", "0101", "1001", "0010"},
        {"0110", "1011", "1101", "1000", "0001", "0100", "1010", "0111", "1001", "0101", "0000", "1111", "1110", "0010", "0011", "1100"}
    };
    private static final String[][] sBox8 = {
        {"1101", "0010", "1000", "0100", "0110", "1111", "1011", "0001", "1010", "1001", "0011", "1110", "0101", "0000", "1100", "0111"},
        {"0001", "1111", "1101", "1000", "1010", "0011", "0111", "0100", "1100", "0101", "0110", "1011", "1010", "1110", "1001", "0010"},
        {"0111", "1011", "0100", "0001", "1001", "1100", "1110", "0010", "0000", "0110", "1010", "1010", "1111", "0011", "0101", "1000"},
        {"0010", "0001", "1110", "0111", "0100", "1010", "1000", "1101", "1111", "1100", "1001", "1001", "0011", "0101", "0110", "1011"}
    };
    private static final int[] straightTable = {
        16, 7, 20, 21, 29, 12, 28, 17,
        1, 15, 23, 26, 5, 18, 31, 10,
        2, 8, 24, 14, 32, 27, 3, 9,
        19, 13, 30, 6, 22, 11, 4, 25
    };

    public static int[] cipher(String text, String key) {
        String output = "";
        String aux;

        key = Binary.getBinary(key.substring(0, 8));
        while (text.length() % 8 != 0) {
            text += " "; // hacemos que el texto tenga palabras de 8 exactas
        }
        for (int i = 0; i < text.length(); i += 8) {
            aux = Binary.getBinary(text.substring(i, i + 8));
            output += wordCipher(aux, key);
        }

        return Binary.getBytes(output);
    }

    public static String reverseCipher(int[] ciphered, String key) {
        String output = "";
        String aux;
        int i, j = 0;

        key = Binary.getBinary(key.substring(0, 8));
        while (j < ciphered.length) {
            aux = "";
            for (i = 0; i < 8; i++) {
                aux += Binary.getBinary(ciphered[j]);
                j++;
            }
            output += wordReverseCipher(aux, key);
        }

        return Binary.getValue(output);
    }

    public static String wordCipher(String input, String mykey) { // input 64 bits, mykey 64 bits
        String output = initialPermutation(input);
        String[] keys = keyGenerator(mykey);
        String aux = output;

        for (int i = 0; i < 16; i++) {
            output = mixer(aux, keys[i]);
            if (i != 15) {
                aux = swapper(output, rightmost(aux, 32));
            }
        }

        return finalPermutation(output + rightmost(aux, 32));
    }

    public static String wordReverseCipher(String input, String mykey) { // input 64 bits, mykey 64 bits
        String output = initialPermutation(input);
        String[] keys = keyGenerator(mykey);
        String aux = output;

        for (int i = 15; i >= 0; i--) {
            output = mixer(aux, keys[i]);
            if (i != 0) {
                aux = swapper(output, rightmost(aux, 32));
            }
        }

        return finalPermutation(output + rightmost(aux, 32));
    }

    private static String initialPermutation(String input) { // input 64 bits
        return permute(input, initialPermutationTable, 64);
    }

    private static String finalPermutation(String input) { // input 64 bits
        return permute(input, finalPermutacionTable, 64);
    }

    private static String mixer(String input, String key) { // input 64 bits, key 48 bits
        String functionResult = function(rightmost(input, 32), key);
        String output = whitener(leftmost(input, 32), functionResult);

        return output;
    }

    private static String swapper(String leftmost, String rightmost) { // leftmost and rightmost 32 bits
        return rightmost + leftmost;
    }

    private static String function(String input, String key) { // input 32 bits / key 48 bits
        String expanded = expansion(input); // 48 bits
        String whitened = whitener(expanded, key); // 48 bits
        String sBoxed = sBoxes(whitened); // 32 bits
        String straighted = straight(sBoxed); // 32 bits

        return straighted;
    }

    private static String straight(String input) { // input 32 bits
        return permute(input, straightTable, 32);
    }

    private static String sBoxes(String input) { // input 48 bits
        String sbox1 = rowcolumnsboxes(input.substring(0, 6), sBox1);
        String sbox2 = rowcolumnsboxes(input.substring(6, 12), sBox2);
        String sbox3 = rowcolumnsboxes(input.substring(12, 18), sBox3);
        String sbox4 = rowcolumnsboxes(input.substring(18, 24), sBox4);
        String sbox5 = rowcolumnsboxes(input.substring(24, 30), sBox5);
        String sbox6 = rowcolumnsboxes(input.substring(30, 36), sBox6);
        String sbox7 = rowcolumnsboxes(input.substring(36, 42), sBox7);
        String sbox8 = rowcolumnsboxes(input.substring(42, 48), sBox8);

        return sbox1 + sbox2 + sbox3 + sbox4 + sbox5 + sbox6 + sbox7 + sbox8;
    }

    private static String rowcolumnsboxes(String input, String[][] table) { // input 6 bits
        int row = Binary.getInteger("" + input.charAt(0) + input.charAt(5));
        int column = Binary.getInteger("" + input.charAt(1) + input.charAt(2) + input.charAt(3) + input.charAt(4));

        return table[row][column];
    }

    private static String whitener(String input, String key) { // input and key 48 bits
        String[] output = initArray("0", input.length());

        for (int i = 0; i < input.length(); i++) {
            output[i] = Binary.xor("" + input.charAt(i), "" + key.charAt(i));
        }

        return joinArray(output, "");
    }

    private static String expansion(String input) {
        String output[] = initArray("0", 48);

        for (int i = 0; i < 48; i++) {
            output[i] = "" + input.charAt(expansionTable[i] - 1);
        }

        return joinArray(output, "");
    }

    private static String permute(String input, int[] table, int outputSize) {
        String[] output = initArray("0", outputSize);
        int index;

        for (int i = 0; i < outputSize; i++) {
            index = getIndexOf(i + 1, table);

            if (input.charAt(i) == '1' && index >= 0) {
                output[index] = "1";
            }
        }

        return joinArray(output, "");
    }

    public static String[] keyGenerator(String keyWithParities) { // keyWithParities 64 bits
        String[] keys = new String[16];
        String cipherKey = permute(keyWithParities, parityDropTable, 56);
        String leftKey = leftmost(cipherKey, 28);
        String rightKey = rightmost(cipherKey, 28);
        String sl1, sl2;

        for (int i = 1; i <= 16; i++) {
            if (i == 1 || i == 2 || i == 9 || i == 16) {
                sl1 = shiftLeft(leftKey, 1);
                sl2 = shiftLeft(rightKey, 1);
            } else {
                sl1 = shiftLeft(leftKey, 2);
                sl2 = shiftLeft(rightKey, 2);
            }

            keys[i - 1] = compression(sl1, sl2);

            leftKey = sl1;
            rightKey = sl2;
        }

        return keys;
    }

    private static String compression(String left, String right) {
        return permute(left + right, compressionTable, 48);
    }

    public static String shiftLeft(String middleKey, int numShifts) {
        String[] output = middleKey.split("");
        String aux;
        int i, j;

        for (i = 0; i < numShifts; i++) {
            aux = "" + output[0];
            for (j = 1; j < middleKey.length(); j++) {
                output[j - 1] = output[j];
            }
            output[27] = aux;
        }

        return joinArray(output, "");
    }

    public static String leftmost(String input, int size) {
        return input.substring(0, size);
    }

    public static String rightmost(String input, int size) {
        return input.substring(input.length() - size, input.length());
    }

    private static int getIndexOf(int value, int[] array) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == value) {
                return i;
            }
        }

        return -1;
    }

    private static String[] initArray(String value, int size) {
        String[] myarray = new String[size];

        for (int i = 0; i < size; i++) {
            myarray[i] = value;
        }

        return myarray;
    }

    private static void bitPosition(String input) {
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == '1') {
                System.out.print(i + " ");
            }
        }
    }

    private static String joinArray(String[] array, String element) {
        String finalString = "";

        for (int i = 0; i < array.length; i++) {
            finalString += array[i];
            if (i != array.length - 1) {
                finalString += element;
            }
        }

        return finalString;
    }
}
