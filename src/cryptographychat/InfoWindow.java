package cryptographychat;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
//import java.math.BigInteger;

public class InfoWindow extends JFrame {
    private User user;
    private HomeWindow padre;
    
    public InfoWindow(HomeWindow padre,User user) {
        this.user = user;
        setSize(300, 250);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Mi información");
        this.padre = padre;
        loadComponents();
    }
    
    private void loadComponents() {
        JPanel jp = new JPanel();
        JLabel lb_nombre = new JLabel("Nombre");
        JLabel lb_publica = new JLabel("Llave Pública");
        JLabel lb_privada = new JLabel("Llave Privada");
        JLabel lb_ip = new JLabel("IP");
        JTextField txt_nombre = new JTextField(user.obtenerNombre());
        JTextField txt_publica = new JTextField(user.obtenerPublica().toString());
        JTextField txt_privada = new JTextField(user.obtenerPrivada().toString());
        JTextField txt_ip = new JTextField();
        JButton aceptar = new JButton("Aceptar");
        
        try {
            txt_ip.setText(InetAddress.getLocalHost().getHostAddress());
        } catch(Exception e) {
            txt_ip.setText("No se pudo obtener IP");
        }
        
        jp.setLayout(null);
        
        lb_nombre.setBounds(56, 20, 65, 20);
        lb_publica.setBounds(20, 50, 90, 20);
        lb_privada.setBounds(20, 80, 100, 20);
        lb_ip.setBounds(98, 110, 30, 20);
        txt_nombre.setBounds(130, 20, 145, 20);
        txt_publica.setBounds(130, 50, 145, 20);
        txt_privada.setBounds(130, 80, 145, 20);
        txt_ip.setBounds(130, 110, 145, 20);
        aceptar.setBounds(90, 160, 100, 25);
        
        jp.add(lb_nombre);
        jp.add(lb_publica);
        jp.add(lb_privada);
        jp.add(lb_ip);
        jp.add(txt_nombre);
        jp.add(txt_publica);
        jp.add(txt_privada);
        jp.add(txt_ip);
        jp.add(aceptar);
        
        this.add(jp);
        
        aceptar.addActionListener((ActionEvent a) -> {
            this.dispose();
            padre.setVisible(true);
        });
    }
}
