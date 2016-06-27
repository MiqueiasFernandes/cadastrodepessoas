/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cadastrodepessoas.presenter.patterns.proxy;

import com.cadastrodepessoas.dao.AbstractPessoaDAO;
import com.cadastrodepessoas.model.Importa;
import com.cadastrodepessoas.model.Pessoa;
import com.cadastrodepessoas.presenter.patterns.abstractfactory.IFabricaDAO;
import com.cadastrodepessoas.presenter.patterns.observer.IObservador;
import com.cadastrodepessoas.presenter.patterns.singleton.LogSingleton;
import java.util.Iterator;

/**
 *
 * @author mfernandes
 */
public class PessoasReal extends AbstractPessoaDAO {

    private final AbstractPessoaDAO pessoaDAO;
    private final LogSingleton logSingleton;

    public PessoasReal(IFabricaDAO fabrica) throws Exception {
        pessoaDAO = fabrica.criaPessoaDAO();
        logSingleton = LogSingleton.getInstancia();
    }

    public void levantarException(String operacao) throws Exception {
        throw new Exception("Houve um erro enquanto tentava " + operacao
                + "\nconsulte o log para mais detalhes.");
    }

    @Override
    public boolean add(Pessoa pessoa) throws Exception {
        boolean adicionou = false;
        try {
            adicionou = pessoaDAO.add(pessoa);
            if (adicionou) {
                pessoaDAO.notifyObservers(pessoaDAO);
                logSingleton.incluirContatoLog(pessoa.getNome(), null);
            }
        } catch (Exception ex) {
            logSingleton.incluirContatoLog(pessoa.getNome(), ex);
            levantarException("adicionar");
        }
        return adicionou;
    }

    @Override
    public void importar(Importa<Pessoa> importa) throws Exception {
        pessoaDAO.importar(importa);
        pessoaDAO.notifyObservers(this);
        if (importa.hasErros()) {
            Iterator<Exception> iteratorErros = importa.getIteratorErros();
            while (iteratorErros.hasNext()) {
                Exception next = iteratorErros.next();
                logSingleton.importaContatosFalha(next);
            }
            levantarException("importar");
        } else {
            logSingleton.importaContatos(importa.getImportadosComSucesso(), importa.getImportadosIncompletos());
        }
    }

    @Override
    public boolean altera(Pessoa novaPessoa, String nome) throws Exception {
        boolean alterou = false;
        try {
            alterou = pessoaDAO.altera(novaPessoa, nome);
            if (alterou) {
                pessoaDAO.notifyObservers(this);
                logSingleton.corrigirContatoLog(nome, null);
            }
        } catch (Exception ex) {
            logSingleton.corrigirContatoLog(nome, ex);
            levantarException("alterar");
        }
        return alterou;
    }

    @Override
    public void carregaPessoas() throws Exception {
        pessoaDAO.carregaPessoas();
    }

    @Override
    public boolean contains(Pessoa pessoa) throws Exception {
        boolean contem = false;
        try {
            contem = pessoaDAO.contains(pessoa);
            logSingleton.consultarContatoLog(pessoa.getNome(), null);
        } catch (Exception ex) {
            logSingleton.consultarContatoLog(pessoa.getNome(), ex);
            levantarException("consultar");
        }
        return contem;
    }

    @Override
    public Pessoa getPessoaByName(String nome) throws Exception {
        Pessoa pessoa = null;
        try {
            pessoa = pessoaDAO.getPessoaByName(nome);
            logSingleton.consultarContatoLog(nome, null);
        } catch (Exception ex) {
            logSingleton.consultarContatoLog(nome, ex);
            levantarException("consultar");
        }
        return pessoa;
    }

    @Override
    public Iterator<Pessoa> getIteratorParaConsulta() throws Exception {
        Iterator<Pessoa> pessoas = null;
        try {
            pessoas = pessoaDAO.getIteratorParaConsulta();
            logSingleton.consultarContatoLog("TODOAS PESSOAS", null);
        } catch (Exception ex) {
            logSingleton.consultarContatoLog("TODOAS PESSOAS", ex);
            levantarException("consultar");
        }
        return pessoas;
    }

    @Override
    public boolean remove(String nome) throws Exception {
        boolean removeu = false;
        try {
            removeu = pessoaDAO.remove(nome);
            if (removeu) {
                pessoaDAO.notifyObservers(this);
                logSingleton.excluirContatoLog(nome, null);
            }
        } catch (Exception ex) {
            logSingleton.excluirContatoLog(nome, ex);
            levantarException("remover");
        }
        return removeu;
    }

    @Override
    public void addObservador(IObservador observador) throws Exception {
        pessoaDAO.addObservador(observador);
    }

}
