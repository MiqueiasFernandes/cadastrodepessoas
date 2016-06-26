/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cadastrodepessoas.presenter.patterns.iterator;

import java.util.TreeSet;

/**
 *
 * @author mfernandes
 */
public interface IColecao<T> {

    IIterator<T> getIterator();

    TreeSet<T> getColecaoReal();
}
