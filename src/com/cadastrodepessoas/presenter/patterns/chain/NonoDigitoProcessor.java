/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cadastrodepessoas.presenter.patterns.chain;

import com.cadastrodepessoas.model.Pessoa;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Properties;

/**
 *
 * @author mfernandes
 */
public class NonoDigitoProcessor {

    ArrayList<ITratador> tratadores;

    public NonoDigitoProcessor() throws Exception {
        tratadores = new ArrayList<>();
        Properties properties = new Properties();
        FileInputStream fis = new FileInputStream("data/nonodigito.properties");
        properties.load(fis);
        EstadoProcessor processor = new EstadoProcessor();
        for (Object object : properties.keySet()) {

            String estado = (String) object;
            String digito = properties.getProperty(estado);

            tratadores.add(new ITratador() {
                @Override
                public boolean accept(Pessoa pessoa) {
                    return estado.equals(processor.getEstado(pessoa));
                }

                @Override
                public String tratar(Pessoa pessoa) {
                    String telefone = pessoa.getTelefone().replaceAll("\\D+", "");
                    /// 9985 0280 == 8
                    if (telefone.length() == 8) {
                        return digito + telefone;
                    }
                    /// 28 9985 0280 == 10
                    if (telefone.length() == 10) {
                        return telefone.substring(0, 2) + digito + telefone.substring(2);
                    }
                    return null;
                }
            });
        }
    }

    public String getTelefoneAtualizado(Pessoa pessoa) {

        for (ITratador tratador : tratadores) {
            if (tratador.accept(pessoa)) {
                return tratador.tratar(pessoa);
            }
        }
        return null;
    }

}
