/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cadastrodepessoas.presenter.patterns.command;

import com.cadastrodepessoas.dao.AbstractPessoaDAO;
import com.cadastrodepessoas.presenter.patterns.memento.ContatoPresenter;
import com.cadastrodepessoas.presenter.patterns.memento.Memento;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mfernandes
 */
public class Excluir implements Command {

    AbstractPessoaDAO pessoaDAO;
    ContatoPresenter presenter;

    @Override
    public void execute(ContatoPresenter presenter) throws Exception {
        presenter.getZelador().adicionaMemento(presenter.criaMemento(this));
        presenter.getEstado().excluir();
        pessoaDAO = presenter.getPessoaDAO();
        this.presenter = presenter;
    }

    @Override
    public void desfaz(Memento memento) {
        presenter.setMemento(memento);
        try {
            pessoaDAO.add(presenter.getPessoa());
        } catch (Exception ex) {

        }
    }

}
