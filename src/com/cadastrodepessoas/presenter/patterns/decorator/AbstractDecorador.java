/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cadastrodepessoas.presenter.patterns.decorator;

/**
 *
 * @author mfernandes
 */
public abstract class AbstractDecorador implements Componente {

    Componente decorado;

    public AbstractDecorador(Componente decorado) {
        this.decorado = decorado;
    }

}
