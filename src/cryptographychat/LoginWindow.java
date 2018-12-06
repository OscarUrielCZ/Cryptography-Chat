package cryptographychat;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.math.BigInteger;
import javax.swing.*;

class LoginWindow extends JFrame {
    public LoginWindow() {
        this.setSize(280, 190);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setTitle("Inicio de sesión");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.loadComponents();
    }
    
    private void loadComponents() {
        JPanel jp = new JPanel(new GridLayout(3, 1));
        JPanel jp0 = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        JPanel jp1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel jp2 = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        JTextField nombre = new JTextField(21);
        JTextField privada = new JTextField(21);
        JLabel lb1 = new JLabel("Nombre");
        JLabel lb2 = new JLabel("LLave privada");
        JButton cancelar = new JButton("Cancelar");
        JButton aceptar = new JButton("Aceptar");
        
        cancelar.addActionListener((ActionEvent a) -> {
            PrincipalWindow pw = new PrincipalWindow();
            this.dispose();
            pw.setVisible(true);
        });
        aceptar.addActionListener((ActionEvent a) -> {
            String name = nombre.getText();
            String privated = privada.getText();
            if (name.length() == 0 || privated.length() == 0) {
                JOptionPane.showMessageDialog(null, "Debes rellenar todos los campos", "Inválido", JOptionPane.ERROR_MESSAGE);
            }
            else {
                if(!MyTools.isNumber(privated)) {
                    JOptionPane.showMessageDialog(null, "La llave privada no es valida", "Inválido", JOptionPane.ERROR_MESSAGE);
                }
                else {
                    User u = CryptographyChat.getUser(name, new BigInteger(privated));
                    if(u != null) {
                        HomeWindow hw = new HomeWindow(u);
                        this.dispose();
                        hw.setVisible(true);
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "Nombre o llave incorrectos", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
                   
            }
        });
        jp0.add(lb1);
        jp0.add(nombre);
        jp1.add(lb2);
        jp1.add(privada);
        jp2.add(cancelar);
        jp2.add(aceptar);
        jp.add(jp0);
        jp.add(jp1);
        jp.add(jp2);
        this.add(jp);
    }
}
