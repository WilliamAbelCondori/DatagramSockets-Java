package com.william.dev.socketshilos.servidor;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class Servidor extends Observable implements Runnable {
    // Variables globales de la Clase
    private int PORT;
    private byte[] buffer = new byte[1024];
    private List<UsuarioConectado> usuariosConectados = new ArrayList<>();
    private DatagramSocket socketUDP;
    private DatagramPacket request;

    public Servidor(int PORT) {
        this.PORT = PORT;
        try {
            socketUDP = new DatagramSocket(PORT);
            request = new DatagramPacket(buffer, buffer.length);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            print("Servidor Conectado");
            while (true) {
                // Recibimos los datos
                socketUDP.receive(request);

                String mensaje = new String(request.getData());

                // Limpiamos el buffer para próximos mensajes recibidos
                buffer = new byte[1024];

                // Asignamos nuestro buffer limpio
                request.setData(buffer);

                // Agregamos el nuevo usuario a nuestra lista
                agregarUsuarioConectado(request.getPort(), request.getAddress());

                // Mandamos información a la vista a través del observer
                this.setChanged();
                this.notifyObservers(mensaje);
                this.clearChanged();

                enviarMensajeBroadcastIgnorando(mensaje, request.getPort(), request.getAddress());
            }
        } catch (IOException e) {
            print(e.getLocalizedMessage());
            e.printStackTrace();
        }
    }

    private void agregarUsuarioConectado(int PORT, InetAddress address) {
        if (!existeUsuarioContectado(PORT, address)) {
            usuariosConectados.add(new UsuarioConectado(PORT, address));
            print(String.format("registrado Port %s Address %s", PORT, address));
        }
    }

    private boolean existeUsuarioContectado(int PORT, InetAddress address) {
        boolean existeUsuario = false;
        for (UsuarioConectado usuarioContectado : usuariosConectados) {
            if (usuarioContectado.getPuerto() == PORT && usuarioContectado.getAddress().equals(address)) {
                existeUsuario = true;
                break;
            }
        }
        return existeUsuario;
    }

    // Nos permite reenviar mensaje a todos los clientes registradaos
    public void enviarMensajeBroadcast(String mensaje) {
        buffer = mensaje.getBytes();
        for (UsuarioConectado usuarioConectado : usuariosConectados) {
            DatagramPacket response = new DatagramPacket(buffer, buffer.length, usuarioConectado.getAddress(), usuarioConectado.getPuerto());
            try {
                socketUDP.send(response);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Nos permite reenviar mensaje a todos los clientes registradaos exceptuando al que envío el mensaje
    public void enviarMensajeBroadcastIgnorando(String mensaje, int ignorarPORT, InetAddress ignorarAddress) {
        buffer = mensaje.getBytes();
        for (UsuarioConectado usuarioConectado : usuariosConectados) {
            if (!(usuarioConectado.getPuerto() == ignorarPORT && usuarioConectado.getAddress() == ignorarAddress)) {
                DatagramPacket response = new DatagramPacket(buffer, buffer.length, usuarioConectado.getAddress(), usuarioConectado.getPuerto());
                try {
                    socketUDP.send(response);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void cerrarConexion() {
        socketUDP.close();
    }

    private void print(String message) {
        System.out.println(message);
    }
}
