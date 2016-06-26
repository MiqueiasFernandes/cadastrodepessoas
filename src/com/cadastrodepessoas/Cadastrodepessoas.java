/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cadastrodepessoas;

import com.cadastrodepessoas.presenter.MainPresenter;

/**
 *
 * @author mfernandes
 */
public class Cadastrodepessoas {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        try {

             new MainPresenter();

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
