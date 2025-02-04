package src.tcp.client;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Joel Murillo Masa
 */
public class Cliente extends Thread implements Serializable {
    private String name;
    private List<String> messageList = new ArrayList<>();
    private List<Cliente> historyList = new ArrayList<>();

    public Cliente() {
    }

    public String getApodo() {
        return name;
    }

    public void setApodo(String name) {
        this.name = name;
    }

    public List<String> getMessageList() {
        return messageList;
    }

    public void setMessageList(List<String> messageList) {
        this.messageList = messageList;
    }

    public List<Cliente> getHistoryList() {
        return historyList;
    }

    public void setHistoryList(List<Cliente> historyList) {
        this.historyList = historyList;
    }

    @Override
    public void run() {
        int puerto = 51832;
        Socket socket;
        try {
            socket = new Socket("localhost", puerto);
            System.out.println("Conectado.");
            //Streams
            OutputStream output = socket.getOutputStream();
            ObjectOutputStream objOutput = new ObjectOutputStream(output);
            InputStream input = socket.getInputStream();
            ObjectInputStream objInput = new ObjectInputStream(input);

            historyList = (List<Cliente>) objInput.readObject();
            for (Cliente cliente : historyList) {
                if (cliente.getApodo().equals(this.name)) {
                    throw new RuntimeException("Ya hay un usuario con ese apodo");
                }
            }
            objOutput.writeObject(this);
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
