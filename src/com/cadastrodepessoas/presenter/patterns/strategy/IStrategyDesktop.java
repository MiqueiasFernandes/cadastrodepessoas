/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cadastrodepessoas.presenter.patterns.strategy;

import java.awt.Component;

/**
 *
 * @author mfernandes
 */
public interface IStrategyDesktop {

    public void addComponent(Component componente);

    public Component getComponent();

    public void setTextNotificacao(String text);

}
