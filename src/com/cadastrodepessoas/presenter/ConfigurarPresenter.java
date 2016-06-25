/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cadastrodepessoas.presenter;

import com.cadastrodepessoas.dao.ILogDAO;
import com.cadastrodepessoas.dao.IODAO;
import com.cadastrodepessoas.presenter.patterns.singleton.LogSingleton;
import com.cadastrodepessoas.presenter.patterns.singleton.LoginSingleton;
import com.cadastrodepessoas.presenter.patterns.strategy.IStrategyDesktop;
import com.cadastrodepessoas.presenter.patterns.strategy.IStrategyLogin;
import com.cadastrodepessoas.view.ConfigurarView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JOptionPane;

/**
 *
 * @author mfernandes
 */
public final class ConfigurarPresenter extends IODAO<ILogDAO> implements IStrategyLogin {

    private final ConfigurarView view;
    private final LoginSingleton login;
    private final IStrategyDesktop desktop;

    public ConfigurarPresenter(IStrategyDesktop desktop) throws Exception {
        this.desktop = desktop;
        view = new ConfigurarView();
        this.login = LoginSingleton.getInstancia();

        view.getNovoBtn().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    adicionarUsuario(e);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(view, "Houve um erro ao adicionar usuario\n" + ex);
                }
            }
        });

        view.getLogofBtn().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    logoff(e);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(view, "Houve um erro ao fazer logoff\n" + ex);
                }
            }
        });

        view.getTipoLogDAOCbx().addItem(LogSingleton.getInstancia().getLogDAO());
        view.getTipoLogDAOCbx().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                alteraLogDAO();
            }
        });

        if (login.autenticar(this)) {
            configurarView();
        }

        desktop.addComponent(view);
        view.setMaximum(true);
        view.pack();
        view.setVisible(true);
    }

    void adicionarUsuario(ActionEvent e) throws Exception {
        if (login.getUsuarioLogado(this).isAdministrador()) {
            login.adicionaUsuario(this, false);
        } else {
            JOptionPane.showMessageDialog(view, "É necessario ser administrador");
        }
    }

    void logoff(ActionEvent e) throws Exception {
        login.logoff();
        this.view.getUsrNameLbl().setText("...");
        this.view.getUsrSenhaLbl().setText("***");
        this.view.getAdministradorCbx().setSelected(false);
        login.autenticar(this);
    }

    void configurarView() {
        configuraTabUsuarios();
        configuraTabLog();
        view.setVisible(true);
        view.pack();
    }

    void configuraTabUsuarios() {
        this.view.getUsrNameLbl().setText(login.getUsuario().getNome());
        this.view.getUsrSenhaLbl().setText(login.getUsuario().getSenhaOfuscada());
        this.view.getAdministradorCbx().setSelected(login.getUsuario().isAdministrador());
        this.view.getNovoBtn().setEnabled(login.getUsuario().isAdministrador());
    }

    void configuraTabLog() {
        try {
            File path = new File("data/log/");
            if (path.exists()) {
                ////lista os plugins da pasta, caso não exista na lista adiciona-os
                for (String string : path.list()) {
                    if (string.contains(".jar")) {
                        ILogDAO dao = carregaDAO(string.replace(".jar", ""), "log/", false);
                        boolean add = true;
                        for (int i = 0; i < view.getTipoLogDAOCbx().getItemCount(); i++) {
                            if (view.getTipoLogDAOCbx().getItemAt(i).toString()
                                    .equals(dao.toString())) {
                                add = false;
                                break;
                            }
                        }
                        if (add) {
                            this.view.getTipoLogDAOCbx().addItem(dao);
                        }
                    }
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, "Não foi possível listar plugins\n" + ex);
        }
    }

    public void alteraLogDAO() {
        try {
            LogSingleton.getInstancia().setLogDAO((ILogDAO) view.getTipoLogDAOCbx().getSelectedItem());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, "não foi possivel alterar o tipo de log.\n" + ex);
        }
    }

    @Override
    public void continuar() throws Exception {
        configurarView();
    }

    @Override
    public IStrategyDesktop getDesktop() {
        return desktop;
    }

}
