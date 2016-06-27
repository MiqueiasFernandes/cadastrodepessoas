/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cadastrodepessoas.presenter.patterns.state;

import com.cadastrodepessoas.model.Pessoa;
import com.cadastrodepessoas.presenter.patterns.command.Editar;
import com.cadastrodepessoas.presenter.patterns.command.Excluir;
import com.cadastrodepessoas.presenter.patterns.memento.ContatoPresenter;
import com.cadastrodepessoas.view.ContatoView;
import javax.swing.JOptionPane;

/**
 *
 * @author mfernandes
 */
public class Vizualizando extends AbstractState {

    public Vizualizando(ContatoPresenter presenter) {
        super(presenter);

        ContatoView view = presenter.getView();
        view.setTitle("Vizualizando Contato");
        view.getEsquerdoBtn().setText("Excluir");
        view.getDireitoBtn().setText("Editar");

        habilitarTXTS(false);
        preencheCampos();

        presenter.setCommandBTNesquerdo(new Excluir());
        presenter.setCommandBTNdireito(new Editar());
    }

    @Override
    public void editar() {
        presenter.setEstado(new Editando(presenter));
    }

    @Override
    public void excluir() throws Exception {
        if (JOptionPane.YES_OPTION
                == JOptionPane.showConfirmDialog(presenter.getView(),
                        "Você realmente deseja excluir o contato?",
                        "Confirmar Excluir", JOptionPane.YES_NO_OPTION)) {
            Pessoa pessoa = presenter.getPessoa();
            presenter.setPessoa(null);
            if (presenter.getPessoaDAO().remove(pessoa.getNome())) {
                JOptionPane.showMessageDialog(presenter.getView(), "O contato foi excluido");
            };
        }
        presenter.getView().setVisible(false);
        presenter.getView().dispose();
    }

    @Override
    public void salvar() {
//////////////////////////////////////////////////////////////////
    }

    @Override
    public void fechar() {
///////////////////////////////////////////////////////////////////
    }

    @Override
    public void cancelar() {
///////////////////////////////////////////////////////////////////
    }

}
