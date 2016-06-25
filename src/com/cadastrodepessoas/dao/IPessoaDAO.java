/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cadastrodepessoas.dao;

import com.cadastrodepessoas.model.Importa;
import com.cadastrodepessoas.model.Pessoa;
import java.util.TreeSet;

/**
 *
 * @author mfernandes
 */
public interface IPessoaDAO {

    public abstract boolean add(Pessoa pessoa) throws Exception;

    public abstract void importar(Importa<Pessoa> importa) throws Exception;

    public abstract boolean altera(Pessoa novaPessoa, String nome) throws Exception;

    public abstract void carregaPessoas() throws Exception;

    public abstract boolean contains(Pessoa pessoa) throws Exception;

    public abstract Pessoa getPessoaByName(String nome) throws Exception;

    public abstract TreeSet<Pessoa> getTreeSet() throws Exception;

    public boolean remove(String nome) throws Exception;
}
