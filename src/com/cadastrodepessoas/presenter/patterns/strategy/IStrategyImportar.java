/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cadastrodepessoas.presenter.patterns.strategy;

import com.cadastrodepessoas.model.Importa;
import com.cadastrodepessoas.model.Pessoa;
import java.util.Iterator;

/**
 *
 * @author mfernandes
 */
public interface IStrategyImportar {

    public boolean preparaParaImportar(IStrategyDesktop dektop) throws Exception;

    public void popularCollection(Importa<Pessoa> importa) throws Exception;

    public int getQuantidade() throws Exception;
}
