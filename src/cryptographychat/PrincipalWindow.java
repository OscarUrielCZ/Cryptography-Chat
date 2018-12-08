package cryptographychat;

import javax.swing.*;
import java.awt.event.*;

public class PrincipalWindow extends JFrame {   
    public PrincipalWindow() {
        this.setSize(400, 350);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setTitle("Chat de criptografía OUCZ");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.loadComponents();
    }
    
    private void loadComponents() {
        JPanel jp = new JPanel();
        JButton iniciar = new JButton("Iniciar sesión");
        JButton registrar = new JButton("Registrarse");
        
        iniciar.addActionListener((ActionEvent a) -> {
            LoginWindow lw = new LoginWindow();
            this.dispose();
            lw.setVisible(true);
        });
        registrar.addActionListener((ActionEvent a) -> {
            SignUpWindow siw = new SignUpWindow();
            this.dispose();
            siw.setVisible(true);
        });
        jp.setLayout(null);
        iniciar.setBounds(125, 80, 150, 40);
        registrar.setBounds(125, 135, 150, 40);
        jp.add(iniciar);
        jp.add(registrar);
        this.add(jp);
    }
}