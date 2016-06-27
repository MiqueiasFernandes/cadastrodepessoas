/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cadastrodepessoas.presenter.patterns.memento.zelador;

import com.cadastrodepessoas.presenter.patterns.memento.Memento;
import java.util.ArrayList;

/**
 *
 * @author mfernandes
 */
public class Zelador {

    ArrayList<Memento> estados;

    public Zelador() {
        estados = new ArrayList<>();
    }

    public void adicionaMemento(Memento memento) {
        estados.add(memento);
    }

    public Memento getUltimoEstadoSalvo() {
        if (estados.size() > 0) {
            return estados.get(estados.size() - 1);
        }
        return null;
    }

}
