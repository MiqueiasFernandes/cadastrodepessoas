/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cadastrodepessoas.presenter.patterns.command;

import com.cadastrodepessoas.presenter.patterns.memento.ContatoPresenter;
import com.cadastrodepessoas.presenter.patterns.memento.Memento;

/**
 *
 * @author mfernandes
 */
public interface Command {

    void execute(ContatoPresenter presenter) throws Exception;

    void desfaz(Memento memento);

}
