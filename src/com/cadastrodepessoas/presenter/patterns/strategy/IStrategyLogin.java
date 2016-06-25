/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cadastrodepessoas.presenter.patterns.strategy;

/**
 *
 * @author mfernandes
 */
public interface IStrategyLogin {

    IStrategyDesktop getDesktop();

    void continuar() throws Exception;
}
