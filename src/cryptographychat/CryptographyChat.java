package cryptographychat;

/**
 *
 * @author oscar
 */

import java.io.*;
import java.util.ArrayList;
import java.math.BigInteger;
import javax.swing.JOptionPane;

public class CryptographyChat {

    private static ArrayList<User> users = new ArrayList<>();
    
    private static String fileusersname = "usuarios.txt";
    
    public static void main(String[] args) {
        PrincipalWindow pw = new PrincipalWindow();
        
        loadUsers();
        pw.setVisible(true);
    }
    
    public static void addUser(User user) {
        FileOutputStream fos;
        ObjectOutputStream oos;
        
        for(User u: users) {
            if(u.obtenerPrivada().toString().equals(user.obtenerPrivada().toString()) || u.obtenerPublica().toString().equals(user.obtenerPublica())) {
                JOptionPane.showMessageDialog(null, "La llave generada ya existe, intenta de nuevo", "Algo salió mal :(", JOptionPane.WARNING_MESSAGE);
                return;
            }             
        } 
        
        try {
            fos = new FileOutputStream(fileusersname);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(user);
            oos.close();
        }
        catch(IOException e) {
            JOptionPane.showMessageDialog(null, "No se ha podidio registrar el usuario", "Algo salió mal :(", JOptionPane.ERROR_MESSAGE);
            //e.printStackTrace();
            return;
        }
        users.add(user);
    }
    
    public static User getUser(String name, BigInteger privated) {
        for(User u: users)
            if(name.equals(u.obtenerNombre()) && privated.compareTo(u.obtenerPrivada()) == 0)
                return u;
        return null;
    }

    private static void loadUsers() {
        FileInputStream fis;
        ObjectInputStream ois;
        User temp;
        try {
            fis = new FileInputStream(fileusersname);
            ois = new ObjectInputStream(fis);
        }
        catch(IOException e) {
            System.out.println("Error al abrir archivo.");
            return;
        }
        do {
            try {
                temp = (User)ois.readObject();
                users.add(temp);
            }
            catch(IOException | ClassNotFoundException e) {
                temp = null;
            }
        } while(temp != null);
        
        try { ois.close(); }
        catch(IOException e) {}
    }
}
