/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cadastrodepessoas.presenter.patterns.observer;

import java.util.HashSet;

/**
 *
 * @author mfernandes
 * @param <T>
 */
public abstract class AbstractObservado<T> {

    private final HashSet<IObservador> observadores = new HashSet<>();

    public void addObservador(IObservador observador) throws Exception {
        observadores.add(observador);
    }

    public void notifyObservers(T dado) throws Exception {
        observadores.stream().forEach((observador) -> {
            observador.atualiza(dado);
        });
    }

}
