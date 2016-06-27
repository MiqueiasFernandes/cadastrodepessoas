/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cadastrodepessoas.presenter.patterns.memento;

import com.cadastrodepessoas.dao.AbstractPessoaDAO;
import com.cadastrodepessoas.presenter.patterns.memento.zelador.Zelador;
import com.cadastrodepessoas.presenter.patterns.observer.IObservador;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenuItem;

/**
 *
 * @author mfernandes
 */
public class Desfazer implements IObservador<AbstractPessoaDAO> {

    private int oldSize;
    private final Zelador zelador;
    private final AbstractPessoaDAO pessoaDAO;
    private final JMenuItem menuItem;

    public Desfazer(Zelador zelador, AbstractPessoaDAO pessoaDAO, JMenuItem menuItem) throws Exception {
        this.zelador = zelador;
        this.pessoaDAO = pessoaDAO;
        this.menuItem = menuItem;
        oldSize = zelador.getSize();
        pessoaDAO.addObservador(this);

        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                desfazer(e);
            }
        });

    }

    @Override
    public void atualiza(AbstractPessoaDAO dado) {

        if (this.oldSize != zelador.getSize()) {
            oldSize = zelador.getSize();
            menuItem.setText(zelador.getUltimoEstadoSalvo().toString());
        }
    }

    public void desfazer(ActionEvent e) {
        Memento memento = zelador.getUltimoEstadoSalvo();
        memento.getCommand().desfaz(memento);
    }

}
