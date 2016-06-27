/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cadastrodepessoas.presenter.patterns.state;

import com.cadastrodepessoas.model.Pessoa;
import com.cadastrodepessoas.presenter.patterns.command.Fechar;
import com.cadastrodepessoas.presenter.patterns.command.Salvar;
import com.cadastrodepessoas.presenter.patterns.memento.ContatoPresenter;
import com.cadastrodepessoas.view.ContatoView;
import javax.swing.JOptionPane;

/**
 *
 * @author mfernandes
 */
public class Incluindo extends AbstractState {

    public Incluindo(ContatoPresenter presenter) {
        super(presenter);

        ContatoView view = presenter.getView();
        view.setTitle("Incluindo Contato");
        view.getEsquerdoBtn().setText("Fechar");
        view.getDireitoBtn().setText("Salvar");

        habilitarTXTS(true);
        limpaCampos();

        presenter.setCommandBTNesquerdo(new Fechar());
        presenter.setCommandBTNdireito(new Salvar());
    }

    @Override
    public void salvar() throws Exception {

        String nome = presenter.getView().getNomeTxt().getText();
        String telefone = presenter.getView().getTelefoneTxt().getText();

        Pessoa pessoa = new Pessoa(nome, telefone);

        if (presenter.getPessoaDAO().add(pessoa)) {
            JOptionPane.showMessageDialog(presenter.getView(), "O contato foi adicionado com sucesso");
        } else {
            JOptionPane.showMessageDialog(presenter.getView(), "O contato não foi adicionado");
        }

        presenter.setEstado(new Incluindo(presenter));
    }

    @Override
    public void fechar() {
        presenter.getView().setVisible(false);
        presenter.getView().dispose();
    }

    @Override
    public void cancelar() {
///////////////////////////////////////////////////////////////////////////////
    }

    @Override
    public void editar() {
//////////////////////////////////////////////////////////////////////////////
    }

    @Override
    public void excluir() {
///////////////////////////////////////////////////////////////////////////////
    }

}
