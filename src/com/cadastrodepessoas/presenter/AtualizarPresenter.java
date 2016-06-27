/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cadastrodepessoas.presenter;

import com.cadastrodepessoas.dao.AbstractPessoaDAO;
import com.cadastrodepessoas.model.Pessoa;
import com.cadastrodepessoas.presenter.patterns.chain.NonoDigitoProcessor;
import com.cadastrodepessoas.presenter.patterns.singleton.LogSingleton;
import com.cadastrodepessoas.presenter.patterns.strategy.IStrategyDesktop;
import java.util.Iterator;
import javax.swing.JOptionPane;

/**
 *
 * @author mfernandes
 */
public class AtualizarPresenter {

    private int atualizados = 0, falha = 0;

    public AtualizarPresenter(AbstractPessoaDAO pessoaDAO, IStrategyDesktop desktop) throws Exception {
        if (JOptionPane.showConfirmDialog(desktop.getComponent(),
                "Todos contatos da lista considerados completos vão receber o NONO digito",
                "Confirmar UpGrade", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {

            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        atualizar(pessoaDAO, desktop);
                        fimDaAtualizacao(desktop, null);
                    } catch (Exception ex) {
                        fimDaAtualizacao(desktop, ex);
                    }
                }
            }).start();

            JOptionPane.showMessageDialog(desktop.getComponent(),
                    "O processo iniciou em segundo plano, você será notificado quando terminar");
        }
    }

    void atualizar(AbstractPessoaDAO pessoaDAO, IStrategyDesktop desktop) throws Exception {
        NonoDigitoProcessor processor = new NonoDigitoProcessor();
        Iterator<Pessoa> iterator = pessoaDAO.getIteratorParaConsulta();
        while (iterator.hasNext()) {
            Pessoa pessoa = iterator.next();
            try {
                String novo = processor.getTelefoneAtualizado(pessoa);

                if (novo == null) {
                    if (pessoa.getTelefone() != null && pessoa.getTelefone().length() > 8) {
                        continue;
                    }
                    LogSingleton.getInstancia().corrigirContatoLog(pessoa.getNome(),
                            new Exception("Erro ao alterar telefone " + pessoa.getTelefone() + " para nono digito"));
                    falha++;
                    continue;
                }
                atualizaPessoa(pessoaDAO, pessoa, novo);
            } catch (Exception ex) {
                LogSingleton.getInstancia().corrigirContatoLog(pessoa.getNome(), ex);
                falha++;
            }
        }
    }

    void atualizaPessoa(AbstractPessoaDAO dao, Pessoa pessoa, String telefone) throws Exception {
        String nome = pessoa.getNome();
        Pessoa nova = new Pessoa(nome, telefone);
        dao.altera(nova, nome);
        atualizados++;
    }

    void fimDaAtualizacao(IStrategyDesktop desktop, Exception exception) {
        try {
            if (falha > 0) {
                JOptionPane.showMessageDialog(desktop.getComponent(), "Hove "
                        + falha + " falha(s) durante a alteração, consulte o log para mais detalhes.\n" + exception);
            } else {
                JOptionPane.showMessageDialog(desktop.getComponent(),
                        +atualizados + " contatos foram atualizados com sucesso");
                LogSingleton.getInstancia().getLogDAO().append(atualizados
                        + " contatos foram atualizados para o nono digito");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(desktop.getComponent(),
                    "Um erro está impedindo a atualização de gravar em logs, tente novamente.\n" + ex
            );
        }
    }

}
