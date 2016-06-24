/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cadastrodepessoas.view;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 *
 * @author mfernandes
 */
public class LoginView extends javax.swing.JInternalFrame {

    /**
     * Creates new form LoginView
     */
    public LoginView() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        prontoBTN = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        usuarioTXT = new javax.swing.JTextField();
        senhaTXT = new javax.swing.JPasswordField();

        setMaximumSize(new java.awt.Dimension(35, 30));

        prontoBTN.setText("PRONTO");

        jLabel1.setText("usuario:");

        jLabel2.setText("senha:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(prontoBTN)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(senhaTXT)
                            .addComponent(usuarioTXT, javax.swing.GroupLayout.DEFAULT_SIZE, 221, Short.MAX_VALUE))))
                .addContainerGap(15, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(usuarioTXT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(senhaTXT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(prontoBTN)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public boolean validarCampos() {
        if (usuarioTXT.getText().isEmpty() || senhaTXT.getPassword().length < 1) {
            JOptionPane.showMessageDialog(this, "Os campos não podem ser nulos");
            return false;
        }
        if (usuarioTXT.getText().length() > 8) {
            JOptionPane.showMessageDialog(this, "O tamanho maximo de nome é 8");
            return false;
        }
        if (senhaTXT.getPassword().length < 3) {
            JOptionPane.showMessageDialog(this, "O tamanho minimo de senha é 3");
            return false;
        }
        boolean containsLetra = false, containsNumber = false;

        for (char c : senhaTXT.getPassword()) {

            Character.isLetter(c);
            containsLetra = true;

            Character.isDigit(c);
            containsNumber = true;

            if (containsLetra && containsNumber) {
                return true;
            }
        }

        if (!containsLetra || !containsNumber) {
            JOptionPane.showMessageDialog(this, "Use letras e numeros na senha");
            return false;
        }
        return false;
    }

    public void alertUser(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    public JButton getProntoBTN() {
        return prontoBTN;
    }

    public JPasswordField getSenhaTXT() {
        return senhaTXT;
    }

    public JTextField getUsuarioTXT() {
        return usuarioTXT;
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JButton prontoBTN;
    private javax.swing.JPasswordField senhaTXT;
    private javax.swing.JTextField usuarioTXT;
    // End of variables declaration//GEN-END:variables
}
