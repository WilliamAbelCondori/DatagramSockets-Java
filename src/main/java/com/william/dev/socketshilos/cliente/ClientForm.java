package com.william.dev.socketshilos.cliente;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.Observable;
import java.util.Observer;

import static java.lang.String.*;

/**
 * @author William
 */
public class ClientForm extends javax.swing.JFrame implements Observer {
    // Variables globales del programa
    Cliente cliente;
    private static int clienteNro = 0;
    String nombreCliente;
    Thread hilo;

    public ClientForm(int clienteNro) {
        initComponents();

        // Incrementamos la cantidad de clientes contectados
        this.clienteNro = clienteNro + 1;
        nombreCliente = "Cliente " + this.clienteNro;
        clienteLabel.setText(nombreCliente);

        cliente = new Cliente(5000, "192.168.1.113", nombreCliente);
        cliente.addObserver(this);
        hilo = new Thread(cliente);
        hilo.start();

        cliente.enviarMensaje(format("%s Conectado", nombreCliente));
    }

    @Override
    public void update(Observable o, Object arg) {
        chatTextArea.append("\n" + arg.toString());
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        chatTextArea = new javax.swing.JTextArea();
        clienteLabel = new javax.swing.JLabel();
        enviarButton = new javax.swing.JButton();
        mensajeTextField = new javax.swing.JTextField();
        nuevoClienteButton = new javax.swing.JButton();
        cerrarSesionButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        chatTextArea.setColumns(20);
        chatTextArea.setRows(5);
        jScrollPane1.setViewportView(chatTextArea);

        clienteLabel.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        clienteLabel.setText("Cliente");

        enviarButton.setText("Enviar");
        enviarButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                enviarButtonActionPerformed(evt);
            }
        });

        nuevoClienteButton.setText("+ Nuevo Cliente ");
        nuevoClienteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nuevoClienteButtonActionPerformed(evt);
            }
        });

        cerrarSesionButton.setText("Cerrar Sesión");
        cerrarSesionButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cerrarSesionButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(15, 15, 15)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                .addGroup(layout.createSequentialGroup()
                                                        .addComponent(mensajeTextField)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                        .addComponent(enviarButton))
                                                .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(clienteLabel)
                                                .addGap(39, 39, 39)
                                                .addComponent(nuevoClienteButton)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(cerrarSesionButton)))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(13, 13, 13)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(clienteLabel)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(nuevoClienteButton)
                                                .addComponent(cerrarSesionButton)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 365, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(enviarButton)
                                        .addComponent(mensajeTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(20, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cerrarSesionButtonActionPerformed(java.awt.event.ActionEvent evt) {
        cliente.cerrarConexion();
        hilo.interrupt();
        setVisible(false);
    }

    private void nuevoClienteButtonActionPerformed(java.awt.event.ActionEvent evt) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ClientForm(clienteNro).setVisible(true);
            }
        });
    }

    private void enviarButtonActionPerformed(java.awt.event.ActionEvent evt) {
        String mensaje = mensajeTextField.getText().toString();
        cliente.enviarMensaje(nombreCliente + ": " + mensaje);
        chatTextArea.append(format("\nYo: %s", mensaje));

        // Limpiamos nuestro campo de texto
        mensajeTextField.setText("");
    }


    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ClientForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ClientForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ClientForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ClientForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ClientForm(clienteNro).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cerrarSesionButton;
    private javax.swing.JTextArea chatTextArea;
    private javax.swing.JLabel clienteLabel;
    private javax.swing.JButton enviarButton;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField mensajeTextField;
    private javax.swing.JButton nuevoClienteButton;
    // End of variables declaration//GEN-END:variables
}
