/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cadastrodepessoas.presenter.patterns.command;

import com.cadastrodepessoas.presenter.patterns.memento.ContatoPresenter;
import com.cadastrodepessoas.presenter.patterns.memento.Memento;

public class Fechar implements Command {

    @Override
    public void execute(ContatoPresenter presenter) throws Exception {
        presenter.getEstado().fechar();
    }

    @Override
    public void desfaz(Memento memento) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
