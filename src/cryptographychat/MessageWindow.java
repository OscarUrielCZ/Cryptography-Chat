package cryptographychat;

import javax.swing.*;

public class MessageWindow extends JFrame {
    private HomeWindow padre;
    private User receptor;
    private Message mensaje;
    
    public MessageWindow(HomeWindow padre, User receptor, Message mensaje) {
        this.receptor = receptor;
        this.mensaje = mensaje;
        setSize(400, 480);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Mensaje de " + mensaje.obtenerRemitente().obtenerNombre());
        this.padre = padre;
        loadComponents();
    }
    
    private void loadComponents() {
        JPanel jp = new JPanel();
        JLabel lb_ip = new JLabel("Nombre: " + mensaje.obtenerRemitente().obtenerNombre());
        JLabel lb_mensaje = new JLabel("Mensaje");
        JLabel lb_algoritmo = new JLabel("Algoritmo: " + mensaje.obtenerAlgoritmo());
        JTextArea mensaje = new JTextArea();
        JButton cancelar = new JButton("Cancelar");
        JButton enviar = new JButton("Enviar");
        JTextField simetrica = new JTextField();
        
        jp.setLayout(null);
        
        lb_ip.setBounds(210, 30, 20, 20);
        lb_mensaje.setBounds(20, 90, 100, 20);
        mensaje.setBounds(20, 115, 360, 150);
        lb_algoritmo.setBounds(20, 280, 100, 20);
        simetrica.setBounds(55, 360, 135, 20);
        cancelar.setBounds(80, 395, 100, 20);
        enviar.setBounds(220, 395, 100, 20);
        
        jp.add(lb_ip);
        jp.add(lb_mensaje);
        jp.add(mensaje);
        jp.add(lb_algoritmo);
        jp.add(simetrica);
        jp.add(cancelar);
        jp.add(enviar);
        
        this.add(jp);
    }
}
