/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cadastrodepessoas.presenter.patterns.proxy;

import com.cadastrodepessoas.dao.AbstractPessoaDAO;
import com.cadastrodepessoas.model.Importa;
import com.cadastrodepessoas.model.Pessoa;
import com.cadastrodepessoas.presenter.patterns.abstractfactory.IFabricaDAO;
import com.cadastrodepessoas.presenter.patterns.singleton.LoginSingleton;
import com.cadastrodepessoas.presenter.patterns.strategy.IStrategyDesktop;
import com.cadastrodepessoas.presenter.patterns.strategy.IStrategyLogin;
import com.cadastrodepessoas.view.MainView;
import java.util.Iterator;

/**
 *
 * @author mfernandes
 */
public class PessoasProxy extends AbstractPessoaDAO implements IStrategyLogin {

    PessoasReal pessoasReal;
    MainView view;

    public PessoasProxy(IFabricaDAO fabrica, MainView view) throws Exception {
        pessoasReal = new PessoasReal(fabrica);
        this.view = view;
    }

    public boolean autenticar() throws Exception {
        if (!LoginSingleton.getInstancia().estaLogado()) {
            LoginSingleton.getInstancia().autenticar(this);
        } else {
            return true;
        }
        return false;
    }

    @Override
    public void continuar() throws Exception {
        carregaPessoas();
    }

    @Override
    public boolean add(Pessoa pessoa) throws Exception {
        if (autenticar()) {
            return pessoasReal.add(pessoa);
        } else {
            throw new Exception("É necessario estar autenticado para efetuar esta operação");
        }
    }

    @Override
    public boolean altera(Pessoa novaPessoa, String nome) throws Exception {
        if (autenticar()) {
            return pessoasReal.altera(novaPessoa, nome);
        } else {
            throw new Exception("É necessario estar autenticado para efetuar esta operação");
        }
    }

    @Override
    public void carregaPessoas() throws Exception {
        if (autenticar()) {
            pessoasReal.carregaPessoas();
        }
    }

    @Override
    public boolean contains(Pessoa pessoa) throws Exception {
        if (autenticar()) {
            return pessoasReal.contains(pessoa);
        } else {
            throw new Exception("É necessario estar autenticado para efetuar esta operação");
        }
    }

    @Override
    public Pessoa getPessoaByName(String nome) throws Exception {
        if (autenticar()) {
            return pessoasReal.getPessoaByName(nome);
        } else {
            throw new Exception("É necessario estar autenticado para efetuar esta operação");
        }
    }

    @Override
    public Iterator<Pessoa> getIteratorParaConsulta() throws Exception {
        if (autenticar()) {
            return pessoasReal.getIteratorParaConsulta();
        } else {
            throw new Exception("É necessario estar autenticado para efetuar esta operação");
        }
    }

    @Override
    public boolean remove(String nome) throws Exception {
        if (autenticar()) {
            return pessoasReal.remove(nome);
        } else {
            throw new Exception("É necessario estar autenticado para efetuar esta operação");
        }
    }

    @Override
    public void importar(Importa<Pessoa> importa) throws Exception {
        if (autenticar()) {
            pessoasReal.importar(importa);
        } else {
            throw new Exception("É necessario estar autenticado para efetuar esta operação");
        }
    }

    @Override
    public IStrategyDesktop getDesktop() {
        return view;
    }

}
