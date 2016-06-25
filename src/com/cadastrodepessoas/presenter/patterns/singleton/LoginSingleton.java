/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cadastrodepessoas.presenter.patterns.singleton;

import com.cadastrodepessoas.dao.IODAO;
import com.cadastrodepessoas.dao.IUsuarioDAO;
import com.cadastrodepessoas.model.Usuario;
import com.cadastrodepessoas.presenter.patterns.strategy.IStrategyLogin;
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
        usuarios = carregaDAO("UsuarioDAO", "usuario/", true);
        usuarios.carregaUsuarios();
    }

    public void adicionaUsuario(IStrategyLogin desktop, boolean administrador) {
        LoginView userView = new LoginView();
        userView.setTitle("Adicionar Usuario");
        userView.getProntoBTN().addActionListener((ActionEvent e) -> {
            if (userView.validarCampos()) {
                try {
                    String nome = userView.getUsuarioTXT().getText();
                    String senha = new String(userView.getSenhaTXT().getPassword());
                    Usuario usuario = new Usuario(nome, senha, administrador);

                    if (usuarios.contains(nome)) {
                        JOptionPane.showMessageDialog(userView,
                                "Esse usuario já existe, tente outro");
                        return;
                    }

                    usuarios.add(usuario);
                    userView.setVisible(false);
                    userView.dispose();
                    LogSingleton.getInstancia().addUsuario(usuario);
                    JOptionPane.showMessageDialog(userView,
                            "O usuario foi adicionado com sucesso");
                    if (administrador) {
                        login(desktop);
                    }
                } catch (Exception ex) {
                    throw new RuntimeException(
                            "ERRO: Não foi possivel adicionar usuario\n" + ex);
                }
            }
        });
        desktop.getDesktop().addComponent(userView);
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

    public void logoff() throws Exception {
        LogSingleton.getInstancia().loginUsuario(usuarioLogado, true);
        usuarioLogado = null;
    }

    public void login(IStrategyLogin desktop) throws Exception {
        if (hasUsuarios()) {
            if (view == null) {
                view = new LoginView();
                view.getProntoBTN().addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        prontoBtn(e, view, desktop);
                    }
                });
                desktop.getDesktop().addComponent(view);
                view.setTitle("Login");
                view.setVisible(true);
            } else {
                view.toFront();
                view.setVisible(true);
            }
            JOptionPane.showMessageDialog(view, "Faça login para continuar");
        } else {
            adicionaUsuario(desktop, true);
        }
    }

    private void prontoBtn(ActionEvent e, LoginView view, IStrategyLogin desktop) {
        if (logar(view)) {
            view.alertUser("Bem Vindo " + usuarioLogado.getNome());
            view.setVisible(false);
            view.dispose();
            this.view = null;
            try {
                desktop.getDesktop().setTextNotificacao("Cadastro de Pessoas - " + LoginSingleton.getInstancia().getUsuario().getNome());
                LogSingleton.getInstancia().loginUsuario(usuarioLogado, false);
                desktop.continuar();
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

    public boolean autenticar(IStrategyLogin logavel) throws Exception {
        if (!estaLogado()) {
            login(logavel);
        }
        return estaLogado();
    }

    public Usuario getUsuarioLogado(IStrategyLogin logavel) throws Exception {
        autenticar(logavel);
        return usuarioLogado;
    }

    public Usuario getUsuario() {
        return usuarioLogado;
    }

    public boolean hasUsuarios() throws Exception {
        return usuarios.count() > 0;
    }

}
