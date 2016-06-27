/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cadastrodepessoas.model;

import com.cadastrodepessoas.presenter.patterns.strategy.IStrategySort;
import com.cadastrodepessoas.presenter.patterns.strategy.SortNome;

/**
 *
 * @author mfernandes
 */
public class Pessoa implements Comparable<Pessoa> {

    private IStrategySort sort;
    private final String nome;
    private final String telefone;

    public Pessoa(String pNome, String pTelefone) {
        this.sort = new SortNome();
        this.nome = pNome;
        this.telefone = pTelefone;
    }

    public String getNome() {
        return this.nome;
    }

    public String getTelefone() {
        return this.telefone;
    }

    public String getTelefoneApresentativo() {
        if (telefone == null || telefone.isEmpty() || telefone.length() < 8) {
            return telefone;
        }
        switch (telefone.length()) {
            case 8:
                return telefone.substring(0, 5) + "-" + telefone.substring(5);
            case 9:
                return telefone.substring(0, 6) + "-" + telefone.substring(6);
            case 11:
                return "(" + telefone.substring(0, 2) + ") " + telefone.substring(2, 7) + "-" + telefone.substring(7);
            default:
                return telefone;
        }
    }

    @Override
    public String toString() {
        return this.nome + "," + this.telefone;
    }

    @Override
    public int compareTo(Pessoa pessoa) {
        return sort.compare(this, pessoa);
    }

    public void setSort(IStrategySort sort) {
        this.sort = sort;
    }

}
