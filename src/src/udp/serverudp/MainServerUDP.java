package src.udp.serverudp;

import src.udp.clientudp.ClienteUDP;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Joel Murillo Masa
 */
public class MainServerUDP {
    public static void main(String[] args) {
        int puerto = 51832;
        DatagramSocket server;
        List<ClienteUDP> clientes = new ArrayList<>();
        try {
            server = new DatagramSocket(puerto);
            while (true) {
                byte[] datos = new byte[1024];
                DatagramPacket recibido = new DatagramPacket(datos, datos.length);
                server.receive(recibido);
                System.out.println("Escuchando cliente ....");

                ByteArrayInputStream bais = new ByteArrayInputStream(recibido.getData());
                ObjectInputStream ois = new ObjectInputStream(bais);
                ClienteUDP cliente = (ClienteUDP) ois.readObject();
                boolean apodoExistente = clientes.stream()
                        .anyMatch(c -> c.getApodo().equals(cliente.getApodo()));
                if (apodoExistente) {
                    System.out.println("Apodo ya existe: " + cliente.getApodo());
                    continue;
                }
                clientes.add(cliente);
                System.out.println("Cliente agregado: " + cliente.getApodo());

                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(bos);
                oos.writeObject(clientes);
                oos.flush();
                byte[] datosEnviar = bos.toByteArray();

                DatagramPacket envio = new DatagramPacket(
                        datosEnviar, datosEnviar.length, recibido.getAddress(), recibido.getPort());
                server.send(envio);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
