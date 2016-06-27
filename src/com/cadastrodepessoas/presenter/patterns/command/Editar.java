/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cadastrodepessoas.presenter.patterns.command;

import com.cadastrodepessoas.presenter.patterns.memento.ContatoPresenter;
import com.cadastrodepessoas.presenter.patterns.memento.Memento;

public class Editar implements Command {

    private ContatoPresenter presenter;
    
    @Override
    public void execute(ContatoPresenter presenter) throws Exception {
       this.presenter = presenter;
        presenter.getZelador().adicionaMemento(presenter.criaMemento(this));
        presenter.getEstado().editar();
    }

    @Override
    public void desfaz(Memento memento) {
       presenter.setMemento(memento);
       presenter.carregar();
    }

}
