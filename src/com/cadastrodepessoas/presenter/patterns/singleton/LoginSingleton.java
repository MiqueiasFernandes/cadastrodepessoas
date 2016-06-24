/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cadastrodepessoas.presenter.patterns.singleton;

import com.cadastrodepessoas.dao.IODAO;
import com.cadastrodepessoas.dao.IUsuarioDAO;
import com.cadastrodepessoas.model.ILogavel;
import com.cadastrodepessoas.model.Usuario;
import com.cadastrodepessoas.view.LoginView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

/**
 *
 * @author mfernandes
 */
public final class LoginSingleton extends IODAO<IUsuarioDAO> {

    private final IUsuarioDAO usuarios;
    private static LoginSingleton instancia;
    private Usuario usuarioLogado;
    private LoginView view;

    private LoginSingleton() throws Exception {
        usuarios = carregaDAOLog("UsuarioDAO");
    }

    public void adicionaUsuario(ILogavel logavel, boolean administrador) {
        LoginView userView = new LoginView();
        userView.setTitle("Adicionar Usuario");
        userView.getProntoBTN().addActionListener((ActionEvent e) -> {
            if (userView.validarCampos()) {
                try {
                    String nome = userView.getUsuarioTXT().getText();
                    String senha = new String(userView.getSenhaTXT().getPassword());
                    Usuario usuario = new Usuario(nome, senha, administrador);

                    usuarios.add(usuario);
                    userView.setVisible(false);
                    userView.dispose();
                    //  LogSingleton.getInstancia().addUsuario(usuario);
                    JOptionPane.showMessageDialog(userView,
                            "O usuario foi adicionado com sucesso");
                } catch (Exception ex) {
                    throw new RuntimeException(
                            "ERRO: Não foi possivel adicionar usuario\n" + ex);
                }
            }
        });
        logavel.getMainView().getDesktopPane().add(userView);
        userView.setVisible(true);
        userView.toFront();
    }

    public static LoginSingleton getInstancia() throws Exception {
        if (instancia == null) {
            instancia = new LoginSingleton();
        }
        return instancia;
    }

    public boolean estaLogado() {
        return usuarioLogado != null;
    }

    public void logoff() {
        usuarioLogado = null;
    }

    public void login(ILogavel logavel) throws Exception {
        if (usuarios.count() >= 1) {
            if (view == null) {
                view = new LoginView();
                view.getProntoBTN().addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        prontoBtn(e, view, logavel);
                    }
                });
                logavel.getMainView().getDesktopPane().add(view);
                view.setTitle("Login");
                view.setVisible(true);
            } else {
                view.toFront();
                view.setVisible(true);
            }
        } else {
            adicionaUsuario(logavel, true);
            login(logavel);
        }
    }

    private void prontoBtn(ActionEvent e, LoginView view, ILogavel logavel) {
        if (logar(view)) {
            view.alertUser("Bem Vindo " + usuarioLogado.getNome());
            view.setVisible(false);
            view.dispose();
            this.view = null;
            try {
                logavel.continuar();
            } catch (Exception ex) {
                throw new RuntimeException(
                        "ERRO: Não foi possivel continuar apos login\n" + ex);
            }
        }
    }

    private boolean logar(LoginView view) {
        String nome = view.getUsuarioTXT().getText();
        Usuario usuario = usuarios.getUsuarioByName(nome);
        if (usuario == null) {
            view.alertUser("Usuario inválido");
            return false;
        }
        String senha = new String(view.getSenhaTXT().getPassword());
        if (comparaSenhas(usuario.getSenha(), senha)) {
            usuarioLogado = usuario;
            return true;
        } else {
            view.alertUser("Senha inválida");
        }
        return false;
    }

    private boolean comparaSenhas(String senha1, String senha2) {
        return senha1.equals(senha2);
    }

    public void autenticar(ILogavel logavel) throws Exception {
        if (!estaLogado()) {
            login(logavel);
        }
    }

    public Usuario getUsuarioLogado(ILogavel logavel) throws Exception {
        autenticar(logavel);
        return usuarioLogado;
    }

    public Usuario getUsuario() {
        return usuarioLogado;
    }

}
