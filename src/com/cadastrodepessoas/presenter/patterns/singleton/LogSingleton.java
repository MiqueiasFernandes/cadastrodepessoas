/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cadastrodepessoas.presenter.patterns.singleton;

import com.cadastrodepessoas.dao.ILogDAO;
import com.cadastrodepessoas.dao.IODAO;
import com.cadastrodepessoas.model.Usuario;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 *
 * @author mfernandes
 */
public final class LogSingleton extends IODAO<ILogDAO> {

    private static final String logpPath = "data/log";
    private final ILogDAO logDAO;
    private static LogSingleton singleton;

    private LogSingleton() throws Exception {
        logDAO = carregaDAOLog("LogDAO");
        logDAO.carregaArquivo(logpPath);
    }

    public static LogSingleton getInstancia() throws Exception {
        if (singleton == null) {
            singleton = new LogSingleton();
        }
        return singleton;
    }

    public String getTime() {
        Locale ptBR = new Locale("pt", "BR");
        SimpleDateFormat formatador
                = new SimpleDateFormat("'('dd'/'MM'/'yyyy' 'HH':'mm':'ss')'", ptBR);
        String time = formatador.format(Calendar.getInstance().getTime());
        return time;
    }

    public void carregaArquivo() throws IOException, Exception {
        getLogDAO().carregaArquivo(logpPath);
    }

    String getUsertTime() throws Exception {
        return " por " + LoginSingleton.getInstancia().getUsuario()
                + " em " + getTime();
    }

    public void importaContatos(int sucesso, int incompletos) throws Exception {
        getLogDAO().importaContatos(sucesso, incompletos, getUsertTime());
    }

    public void exportaContatos(int sucesso) throws Exception {
        getLogDAO().exportaContatos(sucesso, getUsertTime());
    }

    public void importaContatosFalha(Exception ex) throws Exception {
        getLogDAO().importaContatosFalha(ex, getUsertTime());
    }

    public void exportaContatosFalha(Exception ex) throws Exception {
        getLogDAO().exportaContatosFalha(ex, getUsertTime());
    }

    public void incluirContatoLog(String pessoa, Exception falha) throws Exception {
        getLogDAO().incluirContatoLog(pessoa, falha, getUsertTime());
    }

    public void excluirContatoLog(String pessoa, Exception falha) throws Exception {
        getLogDAO().excluirContatoLog(pessoa, falha, getUsertTime());
    }

    public void consultarContatoLog(String pessoa, Exception falha) throws Exception {
        getLogDAO().consultarContatoLog(pessoa, falha, getUsertTime());
    }

    public void corrigirContatoLog(String pessoa, Exception falha) throws Exception {
        getLogDAO().corrigirContatoLog(pessoa, falha, getUsertTime());
    }

    public void addUsuario(Usuario usuario) throws Exception {
        getLogDAO().addUsuario(LoginSingleton.getInstancia().getUsuario(), usuario, getTime());
    }

    public void loginUsuario(Usuario usuario, boolean saiu) throws Exception {
        getLogDAO().loginUsuario(usuario, saiu, getTime());
    }

    public ILogDAO getLogDAO() throws Exception {
        if (logDAO == null) {
            throw new Exception("Erro ao gerir arquivo de log, consulte o administrador.");
        }
        return this.logDAO;
    }

}
