package com.william.dev.socketshilos.cliente;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Observable;

import static java.lang.String.format;

public class Cliente extends Observable implements Runnable {
    // Varibles globales de la aplicación
    private int PORT;
    private String ADDRESS;
    private String nombreCliente;

    private byte[] buffer = new byte[1024];
    private InetAddress direccionServidor;
    private DatagramSocket socketUDP;
    private DatagramPacket respuestaServidor;

    public Cliente(int PORT, String ADDRESS, String nombreCliente) {
        this.PORT = PORT;
        this.ADDRESS = ADDRESS;
        this.nombreCliente = nombreCliente;

        //creamos el datagram socket
        try {
            socketUDP = new DatagramSocket();
            direccionServidor = InetAddress.getByName(ADDRESS);
            respuestaServidor = new DatagramPacket(buffer, buffer.length);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                // Esperamos alguna respuesta del servidor
                socketUDP.receive(respuestaServidor);
                String mensaje = new String(respuestaServidor.getData());

                // Limpiamos el buffer para próximos mensajes recibidos
                buffer = new byte[1024];

                // Asignamos nuestro buffer limpio
                respuestaServidor.setData(buffer);

                // Mandamos información a la vista a través del observer
                this.setChanged();
                this.notifyObservers(mensaje);
                this.clearChanged();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void enviarMensaje(String mensaje) {
        try {
            //convertimos los datos para poder enviarlo en el datragrama
            buffer = mensaje.getBytes();
            DatagramPacket respuesta = new DatagramPacket(buffer, buffer.length, direccionServidor, PORT);

            socketUDP.send(respuesta);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cerrarConexion() {
        enviarMensaje(format("%s dejó el chat", nombreCliente));
        socketUDP.close();
    }
}
