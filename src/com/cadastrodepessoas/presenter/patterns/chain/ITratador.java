/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cadastrodepessoas.presenter.patterns.chain;

import com.cadastrodepessoas.model.Pessoa;

/**
 *
 * @author mfernandes
 */
public interface ITratador {

    boolean accept(Pessoa pessoa);

    String tratar(Pessoa pessoa);

}
