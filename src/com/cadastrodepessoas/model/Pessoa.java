/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cadastrodepessoas.model;

/**
 *
 * @author mfernandes
 */
public class Pessoa implements Comparable<Pessoa> {

    private final String nome;
    private final String telefone;

    public Pessoa(String pNome, String pTelefone) {
        this.nome = pNome;
        this.telefone = pTelefone;
    }

    public String getNome() {
        return this.nome;
    }

    public String getTelefone() {
        return this.telefone;
    }

    @Override
    public String toString() {
        return this.nome + "," + this.telefone;
    }

    @Override
    public int compareTo(Pessoa pessoa) {
        return nome.compareTo(pessoa.nome);
    }
}
