/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cadastrodepessoas.presenter;

import com.cadastrodepessoas.dao.IPessoaDAO;
import com.cadastrodepessoas.presenter.patterns.singleton.LoginSingleton;
import com.cadastrodepessoas.view.MainView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

/**
 *
 * @author mfernandes
 */
public class MainPresenter {

    private final MainView view;
    private final IPessoaDAO pessoas;

    public MainPresenter(IPessoaDAO pessoas, MainView view) {
        this.view = view;
        this.pessoas = pessoas;

        try {
            pessoas.carregaPessoas();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, ex);
        }

        view.getAdicionarJMenu().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        view.getListarJMenu().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        view.getConfigurarJMenu().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        view.getSairJMenu().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        view.setVisible(true);
    }

    void adicionarMenu(ActionEvent e) {

    }

    void listarMenu(ActionEvent e) {

    }

    void configurarMenu(ActionEvent e) {

    }

    void sairMenu(ActionEvent e) {
        view.setVisible(false);
        view.dispose();
    }

}
