/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cadastrodepessoas.presenter;

import com.cadastrodepessoas.dao.AbstractPessoaDAO;
import com.cadastrodepessoas.presenter.patterns.abstractfactory.IFabricaDAO;
import com.cadastrodepessoas.presenter.patterns.abstractfactory.IODAO;
import com.cadastrodepessoas.presenter.patterns.proxy.PessoasProxy;
import com.cadastrodepessoas.presenter.patterns.singleton.LogSingleton;
import com.cadastrodepessoas.presenter.patterns.singleton.LoginSingleton;
import com.cadastrodepessoas.presenter.patterns.strategy.IStrategyDesktop;
import com.cadastrodepessoas.presenter.patterns.strategy.IStrategyLogin;
import com.cadastrodepessoas.view.MainView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

/**
 *
 * @author mfernandes
 */
public final class MainPresenter extends IODAO<IFabricaDAO> implements IStrategyLogin {

    private final MainView view;
    private final AbstractPessoaDAO pessoaDAO;

    public MainPresenter() throws Exception {

        this.view = new MainView();

        try {
            ///o dao de log deve poder ser diferente e alterado em tempo de execução
            IFabricaDAO fabricaLog = carregaDAO("fabricaLog", "", true);
            LogSingleton.getInstancia().setLogDAO(fabricaLog.criaLogDAO(), false);

            IFabricaDAO fabrica = carregaDAO("fabrica", "", true);
            LoginSingleton.getInstancia().setUsuarioDAO(fabrica.criaUsuarioDAO());
            pessoaDAO = new PessoasProxy(fabrica, view);
        } catch (Exception ex) {
            throw new Exception("Não Foi possivel carregar DAO's", ex);
        }

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
                try {
                    configurarMenu(e);
                } catch (Exception ex) {
                    alertUser("ERRO: " + ex);
                }
            }
        });

        view.getSairJMenu().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sairMenu(e);
            }
        });

        view.getImportarMenu().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                importarMenu(e);
            }
        });

        view.getExportarMenu().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    exportarMenu(e);
                } catch (Exception ex) {
                    alertUser("ERRO: " + ex);
                }
            }
        });

        view.getAtualizarMenu().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    atualizarMenu(e);
                } catch (Exception ex) {
                    alertUser("ERRO: " + ex);
                }
            }
        });

        if (!LoginSingleton.getInstancia().hasUsuarios()) {
            JOptionPane.showMessageDialog(view, "Cadastre o usuario administrador");
        }

        autenticar();

        view.setLocationRelativeTo(null);
        view.setVisible(true);
    }

    public boolean autenticar() {
        try {
            return LoginSingleton.getInstancia().autenticar(this);
        } catch (Exception ex) {
            alertUser("ERRO: " + ex);
        }
        return false;
    }

    public void alertUser(String message) {
        JOptionPane.showMessageDialog(view, message);
    }

    public void importarMenu(ActionEvent e) {
        if (autenticar()) {
            new ImportarPresenter(pessoaDAO, view);
        }
    }

    public void exportarMenu(ActionEvent e) throws Exception {
        if (autenticar()) {
            new ExportarPresenter(pessoaDAO, view);
        }
    }

    void adicionarMenu(ActionEvent e) {
        if (autenticar()) {

        }
    }

    void listarMenu(ActionEvent e) {
        if (autenticar()) {
            new ListarPresenter(pessoaDAO, view);
        }
    }

    void atualizarMenu(ActionEvent e) throws Exception {
        new AtualizarPresenter(pessoaDAO, view);
    }

    void configurarMenu(ActionEvent e) throws Exception {
        if (autenticar()) {
            new ConfigurarPresenter(view);
        }
    }

    public void sairMenu(ActionEvent e) {
        view.setVisible(false);
        view.dispose();
    }

    @Override
    public void continuar() throws Exception {
        pessoaDAO.carregaPessoas();
    }

    @Override
    public IStrategyDesktop getDesktop() {
        return view;
    }

}
