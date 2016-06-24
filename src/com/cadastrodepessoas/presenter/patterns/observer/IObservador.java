/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cadastrodepessoas.presenter.patterns.observer;

/**
 *
 * @author mfernandes
 */
public interface IObservador<T> {

    public void atualiza(T dado);
}
