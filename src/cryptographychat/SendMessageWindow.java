package cryptographychat;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.net.Socket;

public class SendMessageWindow extends JFrame {
    private RSA rsa;
    private HomeWindow padre;
    
    public SendMessageWindow(HomeWindow padre, RSA rsa) {
        this.rsa = rsa;
        setSize(400, 480);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Enviar mensaje");
        this.padre = padre;
        loadComponents();
    }
    
    private void loadComponents() {
        JPanel jp = new JPanel();
        JLabel lb_ip = new JLabel("IP");
        JLabel lb_publica = new JLabel("LLave pública");
        JLabel lb_mensaje = new JLabel("Mensaje");
        JLabel lb_algoritmo = new JLabel("Encriptar con");
        JLabel firma = new JLabel("Firma digital");
        JTextField ip = new JTextField();
        JTextField publica = new JTextField();
        JTextArea mensaje = new JTextArea();
        JRadioButton des = new JRadioButton("DES", false);
        JRadioButton rsa = new JRadioButton("RSA", true);
        JCheckBox hash = new JCheckBox("Hash");
        JButton cancelar = new JButton("Cancelar");
        JButton enviar = new JButton("Enviar");
        ButtonGroup grupo = new ButtonGroup();
        JLabel lb_simetrica = new JLabel("Clave (8 caracteres)");
        JTextField simetrica = new JTextField();
        
        jp.setLayout(null);
        grupo.add(rsa);
        grupo.add(des);
        
        lb_ip.setBounds(210, 30, 20, 20);
        ip.setBounds(235, 30, 145, 20);
        lb_publica.setBounds(130, 60, 100, 20);
        publica.setBounds(235, 60, 145, 20);
        lb_mensaje.setBounds(20, 90, 100, 20);
        mensaje.setBounds(20, 115, 360, 150);
        lb_algoritmo.setBounds(20, 280, 100, 20);
        des.setBounds(30, 320, 60, 20);
        rsa.setBounds(30, 300, 60, 20);
        lb_simetrica.setBounds(50, 340, 160, 20);
        simetrica.setBounds(55, 360, 135, 20);
        firma.setBounds(200, 280, 100, 20);
        hash.setBounds(210, 300, 60, 20);
        cancelar.setBounds(80, 395, 100, 20);
        enviar.setBounds(220, 395, 100, 20);
        
        jp.add(lb_ip);
        jp.add(ip);
        jp.add(lb_publica);
        jp.add(publica);
        jp.add(lb_mensaje);
        jp.add(mensaje);
        jp.add(lb_algoritmo);
        jp.add(des);
        jp.add(rsa);
        jp.add(lb_simetrica);
        jp.add(simetrica);
        jp.add(firma);
        jp.add(hash);
        jp.add(cancelar);
        jp.add(enviar);
        
        this.add(jp);
        
        cancelar.addActionListener((ActionEvent a) -> {
            this.dispose();
            padre.setVisible(true);
        });
        enviar.addActionListener((ActionEvent a) -> {
            if(verifyFields(ip, publica, des, simetrica)) {
                try {
                    Socket socket = new Socket(ip.getText(), 8080);
                    ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                    Message mensaje_enviar;
                    int firma_digital;
                    if(hash.isSelected())
                        firma_digital = DigitalSignature.getDigitalSignature(mensaje.getText());
                    else
                        firma_digital = -1;
                    if(des.isSelected())
                        mensaje_enviar = new Message(this.rsa, ip.getText(), new BigInteger(publica.getText()), "DES", null,  DES.cipher(mensaje.getText(), simetrica.getText()), firma_digital);
                    else
                        mensaje_enviar = new Message(this.rsa, ip.getText(), new BigInteger(publica.getText()), "RSA", this.rsa.cifrar(mensaje.getText(), new BigInteger(publica.getText())), null, firma_digital);
                    
                    oos.writeObject(mensaje_enviar);
                    socket.close();
                    
                } catch (IOException ex) {
                    //ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error en la conexión", "Algo salió mal :(", JOptionPane.ERROR_MESSAGE);
                }
                
            }
            else {
                JOptionPane.showMessageDialog(null, "Llena los campos correctamente", "Inválido", JOptionPane.ERROR_MESSAGE);
            }   
        });
    }
    
    private boolean verifyFields(JTextField ip, JTextField publica, JRadioButton des, JTextField simetrica) {
        if(ip.getText().length() == 0 || publica.getText().length() == 0)
            return false;
        if(!MyTools.isIP(ip.getText()) || !MyTools.isNumber(publica.getText()))
            return false;
        if(des.isSelected() && simetrica.getText().length() != 8)
            return false;
        return true;
    }
}
