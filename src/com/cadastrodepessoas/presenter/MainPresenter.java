/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cadastrodepessoas.presenter;

import com.cadastrodepessoas.dao.IPessoaDAO;
import com.cadastrodepessoas.presenter.patterns.singleton.LoginSingleton;
import com.cadastrodepessoas.presenter.patterns.strategy.IStrategyLogin;
import com.cadastrodepessoas.view.MainView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

/**
 *
 * @author mfernandes
 */
public final class MainPresenter implements IStrategyLogin {

    private final MainView view;
    private final IPessoaDAO pessoas;

    public MainPresenter(IPessoaDAO pessoas, MainView view) {
        this.view = view;
        this.pessoas = pessoas;

        autenticar();

        view.getAdicionarJMenu().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adicionarMenu(e);
            }
        });

        view.getListarJMenu().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listarMenu(e);
            }
        });

        view.getConfigurarJMenu().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                configurarMenu(e);
            }
        });

        view.getSairJMenu().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sairMenu(e);
            }
        });

        view.setVisible(true);
    }

    public boolean autenticar() {
        try {
            return LoginSingleton.getInstancia().autenticar(this);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, "ERRO: " + ex);
        }
        return false;
    }

    void adicionarMenu(ActionEvent e) {
        if (autenticar()) {

        }
    }

    void listarMenu(ActionEvent e) {
        if (autenticar()) {

        }
    }

    void configurarMenu(ActionEvent e) {
        if (autenticar()) {

        }
    }

    void sairMenu(ActionEvent e) {
        view.setVisible(false);
        view.dispose();
    }

    @Override
    public MainView getMainView() {
        return view;
    }

    @Override
    public void continuar() throws Exception {
        pessoas.carregaPessoas();
    }

}
