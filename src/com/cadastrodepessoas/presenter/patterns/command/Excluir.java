/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cadastrodepessoas.presenter.patterns.command;

import com.cadastrodepessoas.presenter.patterns.memento.ContatoPresenter;

/**
 *
 * @author mfernandes
 */
public class Excluir implements Command {

    @Override
    public void execute(ContatoPresenter presenter) throws Exception {
        presenter.getEstado().excluir();
        presenter.getZelador().adicionaMemento(presenter.criaMemento());
    }

}
