package cryptographychat;

import java.awt.event.ActionEvent;
import javax.swing.*;

public class MessageWindow extends JFrame {
    private HomeWindow padre;
    private User user;
    private Message mensaje;
    public MessageWindow(HomeWindow padre, User user, Message mensaje) {
        this.padre = padre;
        this.user = user;
        this.mensaje = mensaje;
        setSize(400, 460);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Mensaje de " + mensaje.obtenerRemitente().obtenerNombre());
        this.padre = padre;
        loadComponents();
    }
    
    private void loadComponents() {
        JPanel jp = new JPanel();
        JLabel lb_nombre = new JLabel("Nombre: " + mensaje.obtenerRemitente().obtenerNombre());
        JLabel lb_publica = new JLabel("LLave pública: " + mensaje.obtenerRemitente().obtenerPublica());
        JLabel lb_mensaje = new JLabel("Mensaje");
        JLabel lb_algoritmo = new JLabel("Algoritmo: " + mensaje.obtenerAlgoritmo());
        JTextArea mensaje = new JTextArea();
        JButton cancelar = new JButton("Cancelar");
        JButton decifrar = new JButton("Descifrar");
        JButton firma = new JButton("Verificar firma digital");
        JLabel lb_simetrica = new JLabel("Clave (sólo DES)");
        JTextField simetrica = new JTextField();
        
        jp.setLayout(null);
        mensaje.setText(this.mensaje.obtenerMensaje());
        
        lb_nombre.setBounds(20, 30, 150, 20);
        lb_publica.setBounds(20, 60, 150, 20);
        lb_mensaje.setBounds(20, 90, 100, 20);
        mensaje.setBounds(20, 115, 360, 150);
        lb_algoritmo.setBounds(20, 280, 120, 20);
        lb_simetrica.setBounds(20, 310, 160, 20);
        simetrica.setBounds(25, 330, 135, 20);
        cancelar.setBounds(70, 375, 100, 20);
        firma.setBounds(170, 310, 180, 20);
        decifrar.setBounds(200, 375, 130, 20);
        
        jp.add(lb_nombre);
        jp.add(lb_publica);
        jp.add(lb_mensaje);
        jp.add(mensaje);
        jp.add(lb_algoritmo);
        jp.add(lb_simetrica);
        jp.add(simetrica);
        jp.add(cancelar);
        jp.add(firma);
        jp.add(decifrar);
        
        this.add(jp);
        
        cancelar.addActionListener((ActionEvent a) -> {
            this.dispose();
            padre.setVisible(true);
        });
        decifrar.addActionListener((ActionEvent a) -> {
             if(this.mensaje.obtenerAlgoritmo().equals("DES") && simetrica.getText().length() == 8)
                 mensaje.setText(DES.reverseCipher(this.mensaje.obtenerMensajeDES(), simetrica.getText()));
             else if(this.mensaje.obtenerAlgoritmo().equals("RSA"))
                 mensaje.setText(user.obtenerRSA().decifrar(this.mensaje.obtenerMensajeRSA()));
             else
                 JOptionPane.showMessageDialog(null, "Llena los campos correctamente", "Inválido", JOptionPane.ERROR_MESSAGE);
        });
        firma.addActionListener((ActionEvent a) -> {
            if(this.mensaje.obtenerHash() == -1) {
                JOptionPane.showMessageDialog(null, "El mensaje no tiene firma digital", "No hay firma digital", JOptionPane.WARNING_MESSAGE);
            } else {
                if(DigitalSignature.getDigitalSignature(mensaje.getText()) == this.mensaje.obtenerHash())
                    JOptionPane.showMessageDialog(null, "El mensaje fue enviado por tu conocido" , "Confirmado", JOptionPane.PLAIN_MESSAGE);
                else
                    JOptionPane.showMessageDialog(null, "El mensaje no fue enviado por tu conocido" , "No confirmado", JOptionPane.WARNING_MESSAGE);
            }
        });
    }
    
}
