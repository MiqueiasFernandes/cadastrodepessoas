/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cadastrodepessoas.presenter.patterns.strategy;

import com.cadastrodepessoas.model.Pessoa;

/**
 *
 * @author mfernandes
 */
public interface IStrategySort {

    int compare(Pessoa esta, Pessoa other);

}
