/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cadastrodepessoas.presenter;

import com.cadastrodepessoas.dao.AbstractPessoaDAO;
import com.cadastrodepessoas.model.Pessoa;
import com.cadastrodepessoas.model.TableSorter;
import com.cadastrodepessoas.presenter.patterns.observer.IObservador;
import com.cadastrodepessoas.presenter.patterns.strategy.IStrategyDesktop;
import com.cadastrodepessoas.presenter.patterns.strategy.IStrategySort;
import com.cadastrodepessoas.presenter.patterns.strategy.SortNome;
import com.cadastrodepessoas.presenter.patterns.strategy.SortTelefone;
import com.cadastrodepessoas.view.ListarView;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author mfernandes
 */
public class ListarPresenter implements IObservador<AbstractPessoaDAO> {

    private ListarView view;
    private AbstractPessoaDAO pessoaDAO;
    private IStrategySort sortDefault;
    private boolean orderDefault = true;

    public ListarPresenter(AbstractPessoaDAO pessoaDAO, IStrategyDesktop desktop) {
        this.pessoaDAO = pessoaDAO;
        this.view = new ListarView();
        this.sortDefault = new SortNome();

        view.getNovoBtn().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                novo(e);
            }
        });

        view.getPesquisarBtn().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pesquisar(e);
            }
        });

        view.getVisualizarBtn().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                vizualizar(e);
            }
        });

        view.getBuscarTxt().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                limparCampo(e);
            }
        });

        view.getBuscarTxt().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pesquisar(e);
            }
        });

        popular();

        desktop.addComponent(view);
        view.setVisible(true);
    }

    void popular() {
        JTable tabela = view.getTabela();

        DefaultTableModel tableModel = new DefaultTableModel(
                new String[]{"NOME", "TELEFONE"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        TableSorter sorter = new TableSorter(tableModel) {
            @Override
            public void setSortingStatus(int column, int status) {
                setSortingStatus2(column, status);
                if (column == 0) {
                    if (status == 1) {
                        carregaContatos(pessoaDAO, new SortNome(), true);
                    }
                    if (status == -1) {
                        carregaContatos(pessoaDAO, new SortNome(), false);
                    }
                }
                if (column == 1) {
                    if (status == 1) {
                        carregaContatos(pessoaDAO, new SortTelefone(), true);
                    }
                    if (status == -1) {
                        carregaContatos(pessoaDAO, new SortTelefone(), false);
                    }
                }
            }

            @Override
            public Object getValueAt(int rowIndex, int columnIndex) {
                return null;
            }
        };
        sorter.setTableHeader(tabela.getTableHeader());

        tabela.getTableHeader().setToolTipText(
                "Clique no nome da coluna para ordenar ASC ou DESC");

        tabela.setModel(tableModel);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        carregaContatos(pessoaDAO, sortDefault, true);
    }

    void carregaContatos(AbstractPessoaDAO pessoaDAO, IStrategySort sort, boolean ascendente) {
        try {
            sortDefault = sort;
            orderDefault = ascendente;
            Iterator<Pessoa> iterator = pessoaDAO.getIteratorParaConsulta();
            JTable tabela = view.getTabela();
            DefaultTableModel model = (DefaultTableModel) tabela.getModel();
            model.setNumRows(0);

            ArrayList<Pessoa> pessoas = new ArrayList<>();

            while (iterator.hasNext()) {
                Pessoa pessoa = iterator.next();
                pessoa.setSort(sort);
                pessoas.add(pessoa);
            }

            Collections.sort(pessoas);

            if (!ascendente) {
                Collections.reverse(pessoas);
            }

            for (Pessoa pessoa : pessoas) {
                model.addRow(
                        new String[]{pessoa.getNome(), pessoa.getTelefoneApresentativo()});
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, "Não foi possivel carregar os dados\n" + ex);
        }
    }

    void novo(ActionEvent e) {

    }

    void limparCampo(MouseEvent e) {
        if ("buscar...".equals(view.getBuscarTxt().getText())) {
            view.getBuscarTxt().setText(null);
        }
    }

    void pesquisar(ActionEvent e) {
        String palavra = view.getBuscarTxt().getText();
        JTable tabela = view.getTabela();
        DefaultTableModel model = (DefaultTableModel) tabela.getModel();

        for (int linha = 0; linha < model.getRowCount(); linha++) {

            String nome = model.getValueAt(linha, 0).toString();
            String telefone = model.getValueAt(linha, 1).toString();

            if (nome.equalsIgnoreCase(palavra) || telefone.equals(palavra)) {
                if (tabela.getSelectedRow() != linha) {
                    tabela.scrollRectToVisible(new Rectangle(0,
                            linha * tabela.getRowHeight(), tabela.getWidth(), tabela.getHeight()));
                    tabela.setRowSelectionInterval(linha, linha);
                }
            }
        }
    }

    void vizualizar(ActionEvent e) {

    }

    @Override
    public void atualiza(AbstractPessoaDAO dado) {
        carregaContatos(dado, sortDefault, orderDefault);
       // view.repaint();
    }

}
