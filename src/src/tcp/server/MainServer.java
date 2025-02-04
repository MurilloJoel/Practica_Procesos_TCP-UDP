package src.tcp.server;

import src.tcp.client.Cliente;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Joel Murillo Masa
 */
public class MainServer {
    public static void main(String[] args) {
        int puerto = 51832;
        ServerSocket server;
        List<Cliente> clients = new ArrayList<>();
        try {
            server = new ServerSocket(puerto);
            while (true) {
                Socket clientSocket = server.accept();
                System.out.println("Escuchando cliente ....");
                InputStream input = clientSocket.getInputStream();
                ObjectInputStream objInput = new ObjectInputStream(input);
                OutputStream output = clientSocket.getOutputStream();
                ObjectOutputStream objOutput = new ObjectOutputStream(output);
                //Send the history
                objOutput.writeObject(clients);
                Cliente client = (Cliente) objInput.readObject();
                System.out.println(client.getApodo());
                System.out.println(client.getMessageList());
                clients.add(client);

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
