/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cadastrodepessoas.presenter.patterns.state;

import com.cadastrodepessoas.model.Pessoa;
import com.cadastrodepessoas.presenter.patterns.memento.ContatoPresenter;
import com.cadastrodepessoas.view.ContatoView;

/**
 *
 * @author mfernandes
 */
public abstract class AbstractState {

    protected ContatoPresenter presenter;

    public AbstractState(ContatoPresenter presenter) {
        this.presenter = presenter;
    }

    public abstract void salvar() throws Exception;

    public abstract void fechar() throws Exception;

    public abstract void cancelar() throws Exception;

    public abstract void editar() throws Exception;

    public abstract void excluir() throws Exception;

    public void preencheCampos() {
        Pessoa pessoa = presenter.getPessoa();
        ContatoView view = presenter.getView();
        view.getNomeTxt().setText(pessoa.getNome());
        view.getTelefoneTxt().setText(pessoa.getTelefone());
    }

    public void limpaCampos() {
        ContatoView view = presenter.getView();
        view.getNomeTxt().setText(null);
        view.getTelefoneTxt().setText(null);
    }

    public void habilitarTXTS(boolean habilitar) {
        ContatoView view = presenter.getView();
        view.getNomeTxt().setEnabled(habilitar);
        view.getTelefoneTxt().setEnabled(habilitar);
    }

}
