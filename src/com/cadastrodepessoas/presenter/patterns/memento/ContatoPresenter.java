/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cadastrodepessoas.presenter.patterns.memento;

import com.cadastrodepessoas.dao.AbstractPessoaDAO;
import com.cadastrodepessoas.model.Pessoa;
import com.cadastrodepessoas.presenter.patterns.command.Command;
import com.cadastrodepessoas.presenter.patterns.memento.zelador.Zelador;
import com.cadastrodepessoas.presenter.patterns.observer.IObservador;
import com.cadastrodepessoas.presenter.patterns.state.AbstractState;
import com.cadastrodepessoas.presenter.patterns.state.Incluindo;
import com.cadastrodepessoas.presenter.patterns.state.Vizualizando;
import com.cadastrodepessoas.presenter.patterns.strategy.IStrategyDesktop;
import com.cadastrodepessoas.view.ContatoView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

/**
 *
 * @author mfernandes
 */
public class ContatoPresenter implements IObservador<AbstractPessoaDAO> {

    private AbstractState estado;
    private Pessoa pessoa;
    private ContatoView view;
    private Command commandBTNdireito;
    private Command commandBTNesquerdo;
    private AbstractPessoaDAO pessoaDAO;
    private IStrategyDesktop desktop;
    private Zelador zelador;

    public ContatoPresenter(Pessoa pessoa, AbstractPessoaDAO pessoaDAO,
            IStrategyDesktop desktop, Zelador zelador) throws Exception {
        this.pessoa = pessoa;
        this.pessoaDAO = pessoaDAO;
        this.desktop = desktop;
        this.zelador = zelador;
        view = new ContatoView();
        pessoaDAO.addObservador(this);

        if (pessoa == null) {
            estado = new Incluindo(this);
        } else {
            estado = new Vizualizando(this);
        }

        view.getEsquerdoBtn().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    btnEsquerdo(e);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(view, "Erro: " + ex);
                }
            }
        });

        view.getDireitoBtn().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    btnDireito(e);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(view, "Erro: " + ex);
                }
            }
        });

        desktop.addComponent(view);
        view.setVisible(true);
    }

    public void btnEsquerdo(ActionEvent e) throws Exception {
        commandBTNesquerdo.execute(this);
    }

    public void btnDireito(ActionEvent e) throws Exception {
        commandBTNdireito.execute(this);
    }

    public AbstractState getEstado() {
        return estado;
    }

    public void setEstado(AbstractState estado) {
        this.estado = estado;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public ContatoView getView() {
        return view;
    }

    public Command getCommandBTNdireito() {
        return commandBTNdireito;
    }

    public void setCommandBTNdireito(Command commandBTNdireito) {
        this.commandBTNdireito = commandBTNdireito;
    }

    public Command getCommandBTNesquerdo() {
        return commandBTNesquerdo;
    }

    public void setCommandBTNesquerdo(Command commandBTNesquerdo) {
        this.commandBTNesquerdo = commandBTNesquerdo;
    }

    public AbstractPessoaDAO getPessoaDAO() {
        return pessoaDAO;
    }

    public Zelador getZelador() {
        return zelador;
    }

    public Memento criaMemento() {
        return new Memento(estado, pessoa, view, commandBTNdireito, commandBTNesquerdo);
    }

    public void setMemento(Memento memento) {
        this.estado = memento.getEstado();
        this.pessoa = memento.getPessoa();
        this.view = memento.getView();
        this.commandBTNesquerdo = memento.getCommandBTNesquerdo();
        this.commandBTNdireito = memento.getCommandBTNdireito();
    }

    @Override
    public void atualiza(AbstractPessoaDAO dado) {
        try {
            if (pessoa != null && !dado.contains(pessoa)) {
                if (JOptionPane.YES_OPTION
                        == JOptionPane.showConfirmDialog(view,
                                "Esta pessoa foi excluida, "
                                + "deseja abrir um nova janela para inclui-la  novamente?",
                                "Reincluir pessoa", JOptionPane.YES_NO_OPTION)) {
                    {
                        view.setVisible(false);
                        view.dispose();
                        ContatoPresenter presenter = new ContatoPresenter(null, pessoaDAO, desktop, zelador);
                        presenter.getView().getNomeTxt().setText(pessoa.getNome());
                        presenter.getView().getTelefoneTxt().setText(pessoa.getTelefone());
                    }
                }
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, "Não foi possivel acompanhar atualização de dados.\n" + ex);
        }
    }

}
