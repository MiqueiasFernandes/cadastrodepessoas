/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cadastrodepessoas.presenter.patterns.iterator;

import com.cadastrodepessoas.model.Pessoa;

/**
 *
 * @author mfernandes
 */
public class IteratorConcreto implements IIterator<Pessoa> {

    private final IColecao<Pessoa> colecao;
    private int cont;
    private final Pessoa[] pessoas;

    public IteratorConcreto(IColecao<Pessoa> colecao) {
        this.colecao = colecao;
        pessoas = colecao.getColecaoReal().toArray(new Pessoa[]{});
        this.cont = 0;
    }

    @Override
    public boolean hasNext() {
        return cont < colecao.getColecaoReal().size();
    }

    @Override
    public Pessoa getNext() {
        return pessoas[cont++];
    }

}
