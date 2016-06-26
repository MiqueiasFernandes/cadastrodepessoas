/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cadastrodepessoas.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeSet;

/**
 *
 * @author mfernandes
 */
public abstract class Importa<T> {

    protected boolean continuar = true;
    protected int importadosComSucesso;
    protected int importadosIncompletos;
    protected TreeSet<T> collection;
    protected ArrayList<Exception> erros;

    public Importa() {
        this.erros = new ArrayList<>();
        collection = new TreeSet<T>();
    }

    public TreeSet<T> getCollection() {
        return collection;
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

    public boolean podeContinuar() {
        return continuar;
    }

    public void setContinuar(boolean continuar) {
        this.continuar = continuar;
    }

}
