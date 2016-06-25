/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cadastrodepessoas.dao;

import java.io.FileInputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Properties;

/**
 *
 * @author mfernandes
 * @param <T>
 */
public abstract class IODAO<T> {

    protected T carregaDAO(String dao, String path, boolean getPropriedade) throws Exception {
        T classeDAO;
        String daoOption = dao;

        if (getPropriedade) {
            Properties properties = new Properties();
            FileInputStream fis = new FileInputStream("data/dao.properties");
            properties.load(fis);
            daoOption = properties.getProperty(dao);
        }

        ClassLoader classLoader
                = new URLClassLoader(
                        new URL[]{new URL("file:data/" + path + daoOption + ".jar")},
                        ClassLoader.getSystemClassLoader());
        Class classe
                = Class.forName("com.cadastrodepessoas.dao." + daoOption, true, classLoader);

        Object object = classe.newInstance();
        classeDAO = (T) object;
        return classeDAO;
    }

}
