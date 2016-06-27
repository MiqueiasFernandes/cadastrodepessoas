/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cadastrodepessoas.presenter;

import com.cadastrodepessoas.dao.AbstractPessoaDAO;
import com.cadastrodepessoas.model.Importa;
import com.cadastrodepessoas.model.Pessoa;
import com.cadastrodepessoas.presenter.patterns.strategy.IStrategyDesktop;
import com.cadastrodepessoas.presenter.patterns.strategy.IStrategyImportar;
import com.cadastrodepessoas.presenter.patterns.strategy.ImportarManagmentCollaboratorsSystem;
import com.cadastrodepessoas.presenter.patterns.strategy.ImportarSistemaA;
import com.cadastrodepessoas.presenter.patterns.strategy.ImportarXML;
import com.cadastrodepessoas.view.ImportarView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

/**
 *
 * @author mfernandes
 */
public class ImportarPresenter extends Importa<Pessoa> {

    private final IStrategyDesktop desktop;
    private final ImportarView view;
    private final AbstractPessoaDAO pessoaDAO;
    private IStrategyImportar strategyImportar;
    private final int timeout = 3000;
    private boolean terminou = false;

    public ImportarPresenter(AbstractPessoaDAO pessoaDAO, IStrategyDesktop desktop) {
        this.desktop = desktop;
        this.pessoaDAO = pessoaDAO;
        view = new ImportarView();

        view.getStrategyImportarCbx().addItem(new ImportarXML());
        view.getStrategyImportarCbx().addItem(new ImportarSistemaA());
        view.getStrategyImportarCbx().addItem(new ImportarManagmentCollaboratorsSystem());

        view.getIniciarBtn().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    iniciar(e);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(view, "Houve um erro.\n" + ex);
                }
            }
        });

        view.getImportarBtn().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    importar(e);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(view, "Houve um erro.\n" + ex);
                }
            }
        });

        desktop.addComponent(view);
        view.setVisible(true);
    }

    void iniciar(ActionEvent e) throws Exception {
        view.getImportarBtn().setEnabled(true);
        view.getStrategyImportarCbx().setEnabled(false);
        view.getTempoPrBar().setValue(timeout);
        strategyImportar = (IStrategyImportar) view.getStrategyImportarCbx().getSelectedItem();
        if (!strategyImportar.preparaParaImportar(desktop)) {
            JOptionPane.showMessageDialog(view, "houve uma falha, tente novamente");
        }
        view.getTotalPrBar().setMaximum(strategyImportar.getQuantidade());
    }

    void importar(ActionEvent e) throws Exception {
        view.getTotalPrBar().setValue(strategyImportar.getQuantidade());

        Thread thread = new Thread(() -> {

            try {
                ///ler
                view.getTotalLbl().setText("lendo");
                view.getUnidadeLbl().setText("...");
                view.getUnidadePrBar().setValue(0);

                strategyImportar.popularCollection(this);

                ///persistir
                importadosComSucesso = 0;
                importadosIncompletos = 0;
                view.getTotalLbl().setText("persistindo");
                view.getUnidadeLbl().setText("...");
                view.getUnidadePrBar().setValue(0);

                pessoaDAO.importar(this);

                fimDaImportacao(1);

            } catch (Exception ex) {
                view.getErroLbl().setText(ex.toString());
                addErro(ex);
                setContinuar(false);
                fimDaImportacao(2);
            }
        });

        thread.start();

        new Thread(() -> {
            int i = timeout;
            while (i > 0) {
                try {
                    view.getTempoPrBar().setValue(i);
                    Thread.sleep(1);
                } catch (InterruptedException ex) {
                    view.getErroLbl().setText(ex.toString());
                    addErro(ex);
                }
                i--;
            }
   //         setContinuar(false);
   //         fimDaImportacao(3);
        }).start();
    }

    @Override
    public void statusTotal(int importados) {
        view.getTotalPrBar().setValue(importados);
    }

    @Override
    public void statusUnitario(int importados) {
        view.getUnidadePrBar().setValue(importados);
    }

    public void fimDaImportacao(int modo) {
        if (!terminou) {
            terminou = true;
            try {
                switch (modo) {

                    case 1:
                        JOptionPane.showMessageDialog(view, "Todos "
                                + importadosComSucesso + " contatos foram importados com sucesso");
                        break;

                    case 2:
                        JOptionPane.showMessageDialog(view, "Um erro está impedindo da importação continuar, "
                                + importadosComSucesso + " contatos foram importados com sucesso");
                        break;

                    case 3:
                        JOptionPane.showMessageDialog(view, "O tempo de importação expirou, "
                                + importadosComSucesso + " contatos foram importados com sucesso");
                        break;
                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(view, "Há um erro, abortando\n" + ex);
            }
            view.setVisible(false);
            view.dispose();
        }
    }

}
