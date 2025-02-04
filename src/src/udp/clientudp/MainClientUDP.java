package src.udp.clientudp;

import javax.swing.*;
import java.util.Arrays;
import java.util.List;

/**
 * @author Joel Murillo Masa
 */

public class MainClientUDP {
    private JPanel mainPanel;
    private JLabel label_Usuario;
    private JTextField tb_User;
    private JButton but_see;
    private JButton but_send;
    private JTextField tb_Message;
    private JTextArea tb_History;
    private final ClienteUDP clienteUDP = new ClienteUDP();

    public MainClientUDP() {
        but_send.addActionListener(e -> {
            if (clienteUDP.getApodo() == null) {
                crearUsuario();
                System.out.println(clienteUDP.getApodo());
            }
            subirMensaje();
            System.out.println(Arrays.toString(clienteUDP.getListaMensaje().toArray()));
            if (!clienteUDP.getApodo().equalsIgnoreCase("")) {
                clienteUDP.start();
            }
        });
        but_see.addActionListener(e -> tb_History.append(devolverHistorial(clienteUDP.getListaHistorial())));
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("MainClientUDP");
        frame.setContentPane(new MainClientUDP().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private String devolverHistorial(List<ClienteUDP> listaHistorial) {
        String texto = "";
        for (ClienteUDP client : listaHistorial) {
            texto += client.getApodo() + "\n" + client.getListaMensaje() + "\n";
        }

        return texto;
    }

    public void crearUsuario() {
        clienteUDP.setApodo(tb_User.getText());
    }

    public void subirMensaje() {
        clienteUDP.getListaMensaje().add(tb_Message.getText());
    }
}
