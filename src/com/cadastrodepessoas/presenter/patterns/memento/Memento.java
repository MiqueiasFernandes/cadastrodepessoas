/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cadastrodepessoas.presenter.patterns.memento;

import com.cadastrodepessoas.model.Pessoa;
import com.cadastrodepessoas.presenter.patterns.command.Command;
import com.cadastrodepessoas.presenter.patterns.state.AbstractState;
import com.cadastrodepessoas.view.ContatoView;

/**
 *
 * @author mfernandes
 */
public final class Memento {

    private AbstractState estado;
    private Pessoa pessoa;
    private ContatoView view;
    private Command commandBTNdireito;
    private Command commandBTNesquerdo;

    public Memento(AbstractState estado, Pessoa pessoa, ContatoView view, Command commandBTNdireito, Command commandBTNesquerdo) {
        this.estado = estado;
        this.pessoa = pessoa;
        this.view = view;
        this.commandBTNdireito = commandBTNdireito;
        this.commandBTNesquerdo = commandBTNesquerdo;
    }

    protected AbstractState getEstado() {
        return estado;
    }

    protected Pessoa getPessoa() {
        return pessoa;
    }

    protected ContatoView getView() {
        return view;
    }

    protected Command getCommandBTNdireito() {
        return commandBTNdireito;
    }

    protected Command getCommandBTNesquerdo() {
        return commandBTNesquerdo;
    }

}
