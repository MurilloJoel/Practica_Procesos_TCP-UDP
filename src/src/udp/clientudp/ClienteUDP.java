package src.udp.clientudp;

import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Joel Murillo Masa
 */
public class ClienteUDP extends Thread implements Serializable {
    private String apodo;
    private final List<String> listaMensaje = new ArrayList<>();
    private List<ClienteUDP> listaHistorial = new ArrayList<>();

    public ClienteUDP() {
    }

    public String getApodo() {
        return apodo;
    }

    public void setApodo(String apodo) {
        this.apodo = apodo;
    }

    public List<String> getListaMensaje() {
        return listaMensaje;
    }

    public List<ClienteUDP> getListaHistorial() {
        return listaHistorial;
    }

    @Override
    public void run() {
        int puerto = 51832;
        try (DatagramSocket socket = new DatagramSocket()) {
            // Enviar el objeto Cliente al servidor
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(this);
            oos.flush();
            byte[] datosEnvio = bos.toByteArray();

            DatagramPacket envio = new DatagramPacket(
                    datosEnvio, datosEnvio.length, InetAddress.getByName("localhost"), puerto);
            socket.send(envio);

            // Recibir la lista de clientes actualizada
            byte[] datos = new byte[1024];
            DatagramPacket recibido = new DatagramPacket(datos, datos.length);
            socket.receive(recibido);

            ByteArrayInputStream bais = new ByteArrayInputStream(recibido.getData());
            ObjectInputStream ois = new ObjectInputStream(bais);
            listaHistorial = (List<ClienteUDP>) ois.readObject();

            System.out.println("Historial recibido: " + listaHistorial.size() + " clientes.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
