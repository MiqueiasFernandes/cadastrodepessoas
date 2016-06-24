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
public interface IUsuarioDAO {

    public void add(Usuario usuario) throws Exception;

    public void carregaUsuarios() throws IOException;

    public boolean contains(String name);

    public Usuario getUsuarioByName(String name);

    public int count() throws Exception;
}
