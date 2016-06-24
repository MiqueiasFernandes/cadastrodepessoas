/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cadastrodepessoas.presenter.patterns.observer.proxy;

import com.cadastrodepessoas.dao.IPessoaDAO;
import com.cadastrodepessoas.model.Pessoa;
import com.cadastrodepessoas.presenter.patterns.observer.AbstractObservado;
import com.cadastrodepessoas.presenter.patterns.singleton.LoginSingleton;
import com.cadastrodepessoas.presenter.patterns.strategy.IStrategyLogin;
import com.cadastrodepessoas.view.MainView;
import java.util.TreeSet;

/**
 *
 * @author mfernandes
 */
public class PessoasProxy extends AbstractObservado<IPessoaDAO> implements IPessoaDAO, IStrategyLogin {

    PessoasReal pessoasReal;
    MainView view;

    public PessoasProxy(MainView view) throws Exception {
        pessoasReal = new PessoasReal();
        this.view = view;
    }

    public boolean autenticar() throws Exception {
        if (!LoginSingleton.getInstancia().estaLogado()) {
            LoginSingleton.getInstancia().autenticar(this);
        }
        return true;
    }

    @Override
    public MainView getMainView() {
        return view;
    }

    @Override
    public void continuar() throws Exception {
        carregaPessoas();
    }

    @Override
    public boolean add(Pessoa pessoa) throws Exception {
        autenticar();
        return pessoasReal.add(pessoa);
    }

    @Override
    public void addAll(TreeSet<Pessoa> pessoas) throws Exception {
        autenticar();
        pessoasReal.addAll(pessoas);
    }

    @Override
    public boolean altera(Pessoa novaPessoa, String nome) throws Exception {
        autenticar();
        return pessoasReal.altera(novaPessoa, nome);
    }

    @Override
    public void carregaPessoas() throws Exception {
        autenticar();
        pessoasReal.carregaPessoas();
        notifyObservers(this);
    }

    @Override
    public boolean contains(Pessoa pessoa) throws Exception {
        autenticar();
        return pessoasReal.contains(pessoa);
    }

    @Override
    public Pessoa getPessoaByName(String nome) throws Exception {
        autenticar();
        return pessoasReal.getPessoaByName(nome);
    }

    @Override
    public TreeSet<Pessoa> getTreeSet() throws Exception {
        autenticar();
        return pessoasReal.getTreeSet();
    }

    @Override
    public boolean remove(String nome) throws Exception {
        autenticar();
        return pessoasReal.remove(nome);
    }

}
