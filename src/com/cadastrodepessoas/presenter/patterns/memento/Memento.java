/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cadastrodepessoas.presenter.patterns.memento;

import com.cadastrodepessoas.model.Pessoa;
import com.cadastrodepessoas.presenter.patterns.command.Command;

/**
 *
 * @author mfernandes
 */
public final class Memento {

    private Pessoa pessoa;
    private String operacao;
    private Command command;

    public Memento(Pessoa pessoa, String operacao, Command command) {
        this.pessoa = pessoa;
        this.operacao = operacao;
        this.command = command;
    }

    protected Pessoa getPessoa() {
        return pessoa;
    }

    protected Command getCommand() {
        return command;
    }

    @Override
    public String toString() {
        return operacao;
    }
}
