/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cadastrodepessoas.presenter.patterns.state;

import com.cadastrodepessoas.model.Pessoa;
import com.cadastrodepessoas.presenter.patterns.command.Cancelar;
import com.cadastrodepessoas.presenter.patterns.command.Salvar;
import com.cadastrodepessoas.presenter.patterns.memento.ContatoPresenter;
import com.cadastrodepessoas.view.ContatoView;
import javax.swing.JOptionPane;

/**
 *
 * @author mfernandes
 */
public class Editando extends AbstractState {

    public Editando(ContatoPresenter presenter) {
        super(presenter);

        ContatoView view = presenter.getView();
        view.setTitle("Editando Contato");
        view.getEsquerdoBtn().setText("Cancelar");
        view.getDireitoBtn().setText("Salvar");

        habilitarTXTS(true);
        preencheCampos();

        presenter.setCommandBTNesquerdo(new Cancelar());
        presenter.setCommandBTNdireito(new Salvar());
    }

    @Override
    public void salvar() throws Exception {
        String oldName = presenter.getPessoa().getNome();
        String nome = presenter.getView().getNomeTxt().getText();
        String telefone = presenter.getView().getTelefoneTxt().getText();

        Pessoa pessoa = new Pessoa(nome, telefone);

        if (presenter.getPessoaDAO().altera(pessoa, oldName)) {
            JOptionPane.showMessageDialog(presenter.getView(), "O contato foi alterado com sucesso");
            presenter.setPessoa(pessoa);
        } else {
            JOptionPane.showMessageDialog(presenter.getView(), "O contato não foi alterado");
        }

        presenter.setEstado(new Vizualizando(presenter));
    }

    @Override
    public void cancelar() {
        presenter.setEstado(new Vizualizando(presenter));
    }

    @Override
    public void fechar() {
///////////////////////////////////////////////////////////
    }

    @Override
    public void editar() {
/////////////////////////////////////////////////////////////
    }

    @Override
    public void excluir() {
////////////////////////////////////////////////////////////
    }

    @Override
    public String toString() {
        return "alterar " + presenter.getPessoa().getNome();
    }

}
