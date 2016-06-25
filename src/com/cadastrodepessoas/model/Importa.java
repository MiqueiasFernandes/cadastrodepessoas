/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cadastrodepessoas.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author mfernandes
 */
public abstract class Importa<T> {

    int importadosComSucesso;
    int importadosIncompletos;
    List<T> collection;
    ArrayList<Exception> erros;

    public Importa(List<T> collection) {
        this.collection = collection;
        this.erros = new ArrayList<>();
    }

    public List<T> getCollection() {
        return collection;
    }

    public void statusTotalPercent(int importados) {
        int cem = 100 / collection.size();
        statusTotal(cem * importados);
    }

    public abstract void statusTotal(int importados);

    public abstract void statusUnitario(int importados);

    public int getImportadosComSucesso() {
        return importadosComSucesso;
    }

    public void setImportadosComSucesso(int importadosComSucesso) {
        this.importadosComSucesso = importadosComSucesso;
    }

    public int getImportadosIncompletos() {
        return importadosIncompletos;
    }

    public void setImportadosIncompletos(int importadosIncompletos) {
        this.importadosIncompletos = importadosIncompletos;
    }

    public void addErro(Exception ex) {
        erros.add(ex);
    }

    public boolean hasErros() {
        return erros.size() > 0;
    }

    public Iterator<Exception> getIteratorErros() {
        return erros.iterator();
    }

}
