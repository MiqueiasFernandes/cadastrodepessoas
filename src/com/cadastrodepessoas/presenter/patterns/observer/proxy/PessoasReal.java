/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cadastrodepessoas.presenter.patterns.observer.proxy;

import com.cadastrodepessoas.dao.IODAO;
import com.cadastrodepessoas.dao.IPessoaDAO;
import com.cadastrodepessoas.model.Pessoa;
import java.util.TreeSet;

/**
 *
 * @author mfernandes
 */
public class PessoasReal extends IODAO<IPessoaDAO> implements IPessoaDAO {

    private final IPessoaDAO pessoaDAO;

    public PessoasReal() throws Exception {
        pessoaDAO = carregaDAOLog("PessoaDAO");
    }

    @Override
    public boolean add(Pessoa pessoa) throws Exception {
        return pessoaDAO.add(pessoa);
    }

    @Override
    public void addAll(TreeSet<Pessoa> pessoas) throws Exception {
        pessoaDAO.addAll(pessoas);
    }

    @Override
    public boolean altera(Pessoa novaPessoa, String nome) throws Exception {
        return pessoaDAO.altera(novaPessoa, nome);
    }

    @Override
    public void carregaPessoas() throws Exception {
        pessoaDAO.carregaPessoas();
    }

    @Override
    public boolean contains(Pessoa p) throws Exception {
        return pessoaDAO.contains(p);
    }

    @Override
    public Pessoa getPessoaByName(String nome) throws Exception {
        return pessoaDAO.getPessoaByName(nome);
    }

    @Override
    public TreeSet<Pessoa> getTreeSet() throws Exception {
        return pessoaDAO.getTreeSet();
    }

    @Override
    public boolean remove(String nome) throws Exception {
        return pessoaDAO.remove(nome);
    }

}
