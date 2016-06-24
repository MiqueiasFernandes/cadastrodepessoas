/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cadastrodepessoas;

import com.cadastrodepessoas.dao.IODAO;
import com.cadastrodepessoas.dao.IPessoaDAO;
import com.cadastrodepessoas.presenter.MainPresenter;
import com.cadastrodepessoas.presenter.patterns.observer.proxy.PessoasProxy;
import com.cadastrodepessoas.presenter.patterns.singleton.LogSingleton;
import com.cadastrodepessoas.presenter.patterns.singleton.LoginSingleton;

/**
 *
 * @author mfernandes
 */
public class Cadastrodepessoas extends IODAO<IPessoaDAO> {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        try {

            LogSingleton log = LogSingleton.getInstancia();
            LoginSingleton login = LoginSingleton.getInstancia();
            IPessoaDAO pessoas = new PessoasProxy();

            new MainPresenter(pessoas);

        } catch (Exception ex) {
            System.err.println(
                    "Houve um erro enquanto o programa inicializava\n"
                    + "um elemento necessario para o funcionamento do programa não está acessível\n"
                    + "a execução do programa será abortada até que seja configurado.\n"
                    + "verifique se a configuração de JAR's esta feita corretamente em data/dao.properties\n"
                    + "verifique se os arquivos necessarios em dao.properties estão disponíveis em data/\n"
                    + "contate o administrador com os detalhes do erro que seguem abaixo.\n"
                    + ex
            );
        }
    }
}
