package cryptographychat;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.math.BigInteger;

class SignUpWindow extends JFrame {
    public SignUpWindow() {
        setSize(280, 180);
        setLocationRelativeTo(null);
        setResizable(false);
        setTitle("Registro");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.loadComponents();
    }
    
    private void loadComponents() {
        JPanel jp = new JPanel(new GridLayout(3, 1));
        JPanel jp0 = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        JPanel jp1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel jp2 = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
        JTextField nombre = new JTextField(21);
        JTextField longitud = new JTextField(4);
        JLabel lb1 = new JLabel("Nombre ");
        JLabel lb2 = new JLabel("Tamaño de numeros primos ");
        JButton cancelar = new JButton("Cancelar");
        JButton aceptar = new JButton("Aceptar");
        
        cancelar.addActionListener((ActionEvent a) -> {
            PrincipalWindow pw = new PrincipalWindow();
            this.dispose();
            pw.setVisible(true);
        });
        aceptar.addActionListener((ActionEvent a) -> {
            String name = nombre.getText();
            String len = longitud.getText();
            if (name.length() == 0 || len.length() == 0) {
                JOptionPane.showMessageDialog(null, "Debes rellenar todos los campos", "Invalido", JOptionPane.ERROR_MESSAGE);
            }
            else {
                if(!MyTools.isInteger(len)) {
                    JOptionPane.showMessageDialog(null, "El tamaño de los numeros primos debe ser un entero", "Invalido", JOptionPane.ERROR_MESSAGE);
                }
                else {
                    RSA rsa = new RSA(Integer.parseInt(len));
                    PrincipalWindow pw = new PrincipalWindow();
                    
                    CryptographyChat.addUser(new User(name, rsa));
                    JOptionPane.showMessageDialog(null, name + ", tus llaves son:\n" + "Pública: " + rsa.obtenerLlavePublica() + "\nPrivada: " + rsa.obtenerLlavePrivada());
                    this.dispose();
                    pw.setVisible(true);
                }
            }
        });
        jp0.add(lb1);
        jp0.add(nombre);
        jp1.add(lb2);
        jp1.add(longitud);
        jp2.add(cancelar);
        jp2.add(aceptar);
        jp.add(jp0);
        jp.add(jp1);
        jp.add(jp2);
        this.add(jp);
    }
}