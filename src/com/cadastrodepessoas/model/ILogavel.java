/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cadastrodepessoas.model;

import com.cadastrodepessoas.view.MainView;

/**
 *
 * @author mfernandes
 */
public interface ILogavel {

    MainView getMainView();

    void continuar() throws Exception;
}
