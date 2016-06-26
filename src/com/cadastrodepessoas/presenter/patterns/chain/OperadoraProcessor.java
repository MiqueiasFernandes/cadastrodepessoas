/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cadastrodepessoas.presenter.patterns.chain;

import com.cadastrodepessoas.model.Pessoa;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

/**
 *
 * @author mfernandes
 */
public class OperadoraProcessor {

    ArrayList<ITratador> tratadores;

    public OperadoraProcessor() throws FileNotFoundException, IOException {
        tratadores = new ArrayList<>();
        Properties properties = new Properties();
        FileInputStream fis = new FileInputStream("data/operadoras.properties");
        properties.load(fis);

        for (Object object : properties.keySet()) {

            String operadora = (String) object;
            String regex = properties.getProperty(operadora);

            tratadores.add(new ITratador() {
                @Override
                public boolean accept(Pessoa pessoa) {
                    if (pessoa == null || pessoa.getTelefone() == null || pessoa.getTelefone().isEmpty()) {
                        return false;
                    }
                    return pessoa.getTelefone().replaceAll("\\D+", "").matches(regex);
                }

                @Override
                public String tratar(Pessoa pessoa) {
                    return operadora;
                }
            });

        }
    }

    public String getOperadora(Pessoa pessoa) {
        for (ITratador tratador : tratadores) {
            if (tratador.accept(pessoa)) {
                return tratador.tratar(pessoa);
            }
        }
        return null;
    }
}
