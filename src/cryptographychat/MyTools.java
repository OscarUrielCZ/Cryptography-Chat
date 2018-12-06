package cryptographychat;

public class MyTools {
    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        }
        catch(NumberFormatException e) {
            return false;
        }
        
        return true;
    }
    
    public static boolean isNumber(String s) {
        for(int i = 0; i < s.length(); i++)
            if(!Character.isDigit(s.charAt(i)))
                return false;
        return true;
    }
    
    public static boolean isIP(String ip) {
        String[] numbers;
        int i, aux;
        
        if(ip.length() - ip.replace(".", "").length() != 3) // retorna falso si no hay 3 puntos
            return false;
        
        numbers = ip.split(".");
        
        for(i = 0; i < numbers.length; i++) {
            System.out.println(numbers[i]);
            try {
                aux = Integer.parseInt(numbers[i]);
            }
            catch(Exception e) { // retorna falso si no es un entero
                return false;
            }
            if(0 > aux || aux >= 256) // retorna falso si no está entre 0 y 256
                return false;
        }
        
        return true;
    }
}
