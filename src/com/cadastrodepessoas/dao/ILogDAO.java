/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cadastrodepessoas.dao;

import com.cadastrodepessoas.model.Usuario;
import java.io.IOException;

/**
 *
 * @author mfernandes
 */
public interface ILogDAO {

    public void append(String mensagem) throws Exception;

    public void carregaArquivo(String path) throws IOException;

    public String getNomeCanonico();

    public void importaContatos(int sucesso, int incompletos, String userTime) throws Exception;

    public void exportaContatos(int sucesso, String userTime) throws Exception;

    public void importaContatosFalha(Exception ex, String userTime) throws Exception;

    public void exportaContatosFalha(Exception ex, String userTime) throws Exception;

    public void incluirContatoLog(String pessoa, Exception falha, String userTime) throws Exception;

    public void excluirContatoLog(String pessoa, Exception falha, String userTime) throws Exception;

    public void consultarContatoLog(String pessoa, Exception falha, String userTime) throws Exception;

    public void corrigirContatoLog(String pessoa, Exception falha, String userTime) throws Exception;

    public void addUsuario(Usuario usuario, Usuario novousuario, String time) throws Exception;

    public void loginUsuario(Usuario usuario, boolean saiu, String time) throws Exception;

}
