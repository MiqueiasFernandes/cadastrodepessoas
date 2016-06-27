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
public class Salvar implements Command {

    AbstractPessoaDAO pessoaDAO;
    String novoNome;
    ContatoPresenter presenter;

    @Override
    public void execute(ContatoPresenter presenter) throws Exception {
        presenter.getZelador().adicionaMemento(presenter.criaMemento(this));
        presenter.getEstado().salvar();
        presenter.getZelador().adicionaMemento(presenter.criaMemento(this));
        novoNome = presenter.getPessoa().getNome();
        this.presenter = presenter;
    }

    @Override
    public void desfaz(Memento memento) {
        presenter.setMemento(memento);
        try {
            pessoaDAO.altera(presenter.getPessoa(), novoNome);
        } catch (Exception ex) {

        }
    }

}
