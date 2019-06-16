package cryptographychat;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import javax.swing.*;
import java.net.Socket;
import javax.swing.event.ListSelectionEvent;

public class HomeWindow extends JFrame implements Runnable {
    private RSA rsa = null;
    private String info;
    private final int LONGBITS = 10;
    private JList mensajes;
    private DefaultListModel modelo;
    
    public HomeWindow() {
        rsa = new RSA(LONGBITS);
        setSize(500, 600);
        setLocationRelativeTo(null);
        setResizable(false);
        setTitle("Chat de criptográfia OUCZ");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loadComponents();
        Thread mythread = new Thread(this);
        mythread.start();
    }
    
    public void run() {
        try {
            
            ServerSocket server = new ServerSocket(8080);
            while(true) {
                try {
                    Socket socket = server.accept();
                    ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                    Message mensaje_recibido = (Message)ois.readObject();
                    System.out.println(mensaje_recibido);
                    modelo.addElement(mensaje_recibido);
                    mensajes.setModel(modelo);
                    socket.close();
                } catch (IOException ex) {
                    //Logger.getLogger(HomeWindow.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(null, "Error en la conexión", "Algo salió mal :(", JOptionPane.ERROR_MESSAGE);
                } catch (ClassNotFoundException ex) {
                    //Logger.getLogger(HomeWindow.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(null, "Error en obtener el mensaje", "Algo salió mal :(", JOptionPane.ERROR_MESSAGE);
                }
                
            }
        } catch (IOException ex) {            
            //Logger.getLogger(HomeWindow.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Error en levantar el servidor", "Algo salió mal :(", JOptionPane.ERROR_MESSAGE);
        }
        
    }
    
    private void loadComponents() {
        info = getInfoString();     
        JPanel jp = new JPanel();
        ImageIcon mas = new ImageIcon("nuevo_mensaje.png");
        JButton llaves = new JButton("Generar llaves");
        JButton nuevo = new JButton();
        JLabel mismensajes = new JLabel("Mis mensajes");
        JLabel infolabel = new JLabel(info);
        mensajes = new JList();
        modelo = new DefaultListModel();
        
        nuevo.setSize(20, 20);
        nuevo.setIcon(new ImageIcon(mas.getImage().getScaledInstance(nuevo.getWidth() + 13, nuevo.getHeight() + 13, Image.SCALE_SMOOTH))); // agregando y ajustando tamaño de la imagen
        nuevo.setBackground(Color.white);
        
        mensajes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // solo se puede seleccionar un elemento
        
        jp.setLayout(null);
      
        mismensajes.setBounds(40, 40, 135, 25);
        llaves.setBounds(200, 30, 135, 25);
        nuevo.setBounds(415, 25, 35, 35);
        infolabel.setBounds(90, 525, 370, 20);
        mensajes.setBounds(50, 70, 400, 440);
        
        jp.add(mismensajes);
        jp.add(llaves);
        jp.add(nuevo);
        jp.add(infolabel);
        jp.add(mensajes);

        this.add(jp);
        
        llaves.addActionListener((ActionEvent a) -> {
            rsa.setValues(LONGBITS);
            info = getInfoString();
            infolabel.setText(info);
        });
        nuevo.addActionListener((ActionEvent a) -> {
            SendMessageWindow smw = new SendMessageWindow(this, rsa);
            this.dispose();
            smw.setVisible(true);
        });
        mensajes.addListSelectionListener((ListSelectionEvent a) -> {
            MessageWindow mw = new MessageWindow(this, this.rsa, (Message)mensajes.getSelectedValue());
            this.dispose();
            mw.setVisible(true);
        });
    }
    
    private String getInfoString() {
        return "Llave pública: " + rsa.getPublicKey() + "                 Llave privada: " + rsa.getPrivateKey();
    }
}