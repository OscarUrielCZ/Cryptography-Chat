package cryptographychat;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import javax.swing.*;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HomeWindow extends JFrame implements Runnable {
    private User user = null;
    private JList mensajes;
    private DefaultListModel modelo;
    
    public HomeWindow(User user) {
        this.user = user;
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
            
            ServerSocket server = new ServerSocket(3080);
            while(true) {
                try {
                    Socket socket = server.accept();
                    ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                    Message mensaje_recibido = (Message)ois.readObject();
                    System.out.println(mensaje_recibido);
                    modelo.addElement(mensaje_recibido.toString());
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
            Logger.getLogger(HomeWindow.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Error en levantar el servidor", "Algo salió mal :(", JOptionPane.ERROR_MESSAGE);
        }
        
    }
    
    private void loadComponents() {
        JPanel jp = new JPanel();
        ImageIcon mas = new ImageIcon("nuevo_mensaje.png");
        JButton info = new JButton("Mi información");
        JButton nuevo = new JButton();
        JButton salir = new JButton("Cerrar sesión");
        JLabel autor = new JLabel("Creado por Oscar Uriel Cortés Zanabria - 2CM3");
        mensajes = new JList();
        modelo = new DefaultListModel();
        
        nuevo.setSize(20, 20);
        nuevo.setIcon(new ImageIcon(mas.getImage().getScaledInstance(nuevo.getWidth() + 13, nuevo.getHeight() + 13, Image.SCALE_SMOOTH))); // agregando y ajustando tamaño de la imagen
        nuevo.setBackground(Color.white);
        
        mensajes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // solo se puede seleccionar un elemento
        
        jp.setLayout(null);
        salir.setBounds(50, 30, 130, 25);
        info.setBounds(220, 30, 135, 25);
        nuevo.setBounds(415, 25, 35, 35);
        autor.setBounds(160, 525, 320, 20);
        mensajes.setBounds(50, 70, 400, 440);
        
        jp.add(salir);
        jp.add(info);
        jp.add(nuevo);
        jp.add(autor);
        jp.add(mensajes);

        this.add(jp);
        
        salir.addActionListener((ActionEvent a) -> {
            PrincipalWindow pw = new PrincipalWindow();
            user = null;
            this.dispose();
            pw.setVisible(true);
        });
        info.addActionListener((ActionEvent a) -> {
            InfoWindow iw = new InfoWindow(this, user);
            this.dispose();
            iw.setVisible(true);
        });
        nuevo.addActionListener((ActionEvent a) -> {
            SendMessageWindow smw = new SendMessageWindow(this, user);
            this.dispose();
            smw.setVisible(true);
        });
    }
}
