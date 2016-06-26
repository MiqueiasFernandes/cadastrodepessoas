/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cadastrodepessoas.presenter.patterns.abstractfactory;

import com.cadastrodepessoas.dao.ILogDAO;
import com.cadastrodepessoas.dao.IPessoaDAO;
import com.cadastrodepessoas.dao.IUsuarioDAO;

/**
 *
 * @author mfernandes
 */
public interface IFabricaDAO {

    IPessoaDAO criaPessoaDAO() throws Exception;

    IUsuarioDAO criaUsuarioDAO() throws Exception;

    ILogDAO criaLogDAO() throws Exception;

}
