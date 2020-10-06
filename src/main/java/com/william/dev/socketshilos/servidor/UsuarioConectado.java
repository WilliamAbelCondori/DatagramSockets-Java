package com.william.dev.socketshilos.servidor;

import java.net.InetAddress;

public class UsuarioConectado {
    private int puerto;
    private InetAddress address;

    public UsuarioConectado(int puerto, InetAddress address) {
        this.puerto = puerto;
        this.address = address;
    }

    public int getPuerto() {
        return puerto;
    }

    public void setPuerto(int puerto) {
        this.puerto = puerto;
    }

    public InetAddress getAddress() {
        return address;
    }

    public void setAddress(InetAddress address) {
        this.address = address;
    }
}
