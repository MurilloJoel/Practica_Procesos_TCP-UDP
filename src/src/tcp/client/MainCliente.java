package src.tcp.client;

import javax.swing.*;
import java.util.Arrays;
import java.util.List;

/**
 * @author Joel Murillo Masa
 */

public class MainCliente extends JFrame {
    private final Cliente cliente = new Cliente();
    private JPanel mainPanel;
    private JLabel label_Message;
    private JTextField tb_Message;
    private JButton but_Send;
    private JLabel label_User;
    private JTextField tb_User;
    private JLabel lb_textoHistorial;
    private JButton but_see;
    private JTextArea tb_History;

    public MainCliente() {

        but_Send.addActionListener(e -> {
            if (cliente.getApodo() == null) {
                crearUsuario();
                System.out.println(cliente.getApodo());
            }
            subirMensaje();
            System.out.println(Arrays.toString(cliente.getMessageList().toArray()));
            //cliente.start();
            if (!cliente.getApodo().equalsIgnoreCase("")) {
                cliente.start();
            }
        });


        but_see.addActionListener(e -> tb_History.append(devolverHistorial(cliente.getHistoryList())));

    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("MainClient");
        frame.setContentPane(new MainCliente().mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);


    }

    private String devolverHistorial(List<Cliente> listaHistorial) {
        String texto = "";
        for (Cliente client : listaHistorial) {
            texto += client.getApodo() + "\n" + client.getMessageList() + "\n";
        }
        //Para que salgan el historial con formato html
        return texto;
    }

    public void crearUsuario() {
        cliente.setApodo(tb_User.getText());
    }

    public void subirMensaje() {
        cliente.getMessageList().add(tb_Message.getText());
    }


}
