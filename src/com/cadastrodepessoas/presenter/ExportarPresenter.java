/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cadastrodepessoas.presenter;

import com.cadastrodepessoas.dao.IPessoaDAO;
import com.cadastrodepessoas.model.Pessoa;
import com.cadastrodepessoas.presenter.patterns.chain.EstadoProcessor;
import com.cadastrodepessoas.presenter.patterns.chain.OperadoraProcessor;
import com.cadastrodepessoas.presenter.patterns.singleton.LogSingleton;
import com.cadastrodepessoas.presenter.patterns.strategy.IStrategyDesktop;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

/**
 *
 * @author mfernandes
 */
public class ExportarPresenter {

    private Element root;
    private Document document;
    private File file;
    private int sucesso = 0, timeout = 5000;
    private boolean terminou = false;

    public ExportarPresenter(IPessoaDAO pessoaDAO, IStrategyDesktop desktop) {
        try {
            JFileChooser chooser = new JFileChooser();
            chooser.setApproveButtonText("salvar");
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    "arquivos XML", "xml");
            chooser.setFileFilter(filter);
            desktop.addComponent(chooser);
            int returnVal = chooser.showSaveDialog(chooser.getParent());
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                Thread thread = new Thread(() -> {
                    try {
                        exportar(pessoaDAO, chooser.getSelectedFile().toString());
                        fimDaExportacao(null, desktop);
                    } catch (Exception ex) {
                        fimDaExportacao(ex, desktop);
                    }
                });
                thread.start();
                new Thread(() -> {
                    int i = timeout;
                    try {
                        while (i > 0) {
                            Thread.sleep(1);
                            i--;
                        }
                    } catch (InterruptedException ex) {
                        fimDaExportacao(ex, desktop);
                    }
                    fimDaExportacao(new Exception("Tempo limite de exportação expirado"), desktop);
                }).start();
            }
        } catch (Exception ex) {
            fimDaExportacao(ex, desktop);
        }
    }

    void exportar(IPessoaDAO pessoaDAO, String arquivo) throws Exception {
        if (carregarArquivo(arquivo)) {
            Iterator<Pessoa> iterator = pessoaDAO.getIteratorParaConsulta();
            EstadoProcessor estadoProcessor = new EstadoProcessor();
            OperadoraProcessor operadoraProcessor = new OperadoraProcessor();
            while (iterator.hasNext()) {
                Pessoa next = iterator.next();
                String estado;
                if (null != (estado = isCompleto(next, estadoProcessor))) {
                    String operadora = operadoraProcessor.getOperadora(next);
                    addNoPessoa(next, operadora, estado);
                    sucesso++;
                }
            }
            salvar();
        } else {
            throw new Exception("Não foi possivel carregar o arquivo " + file + " para exportar");
        }
    }

    boolean carregarArquivo(String nome) throws ParserConfigurationException, SAXException, IOException {
        file = new File(nome + (nome.contains(".xml") ? "" : ".xml"));

        if (!file.exists()) {
            file.createNewFile();
        }

        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = null;
        dBuilder = dbFactory.newDocumentBuilder();
        document = dBuilder.newDocument();
        root = document.createElement("contatos");
        document.appendChild(root);
        return true;
    }

    public void addNoPessoa(Pessoa pessoa, String operadora, String uf) throws Exception {
        Element contato = document.createElement("contato");

        Element nome = document.createElement("Nome");
        nome.setTextContent(pessoa.getNome());

        Element telefone = document.createElement("Telefone");
        telefone.setTextContent(pessoa.getTelefone());
        contato.appendChild(telefone);

        Element oper = document.createElement("Operadora");
        Element es = document.createElement("UF");
        oper.setTextContent(operadora);
        es.setTextContent(uf);
        contato.appendChild(nome);
        contato.appendChild(oper);
        contato.appendChild(es);

        root.appendChild(contato);
    }

    public boolean salvar() throws TransformerConfigurationException, TransformerException {
        DOMSource source = new DOMSource(document);
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        StreamResult result = new StreamResult(file);
        transformer.transform(source, result);
        return true;
    }

    public String isCompleto(Pessoa pessoa, EstadoProcessor estadoProcessor) {
        if (pessoa == null || pessoa.getNome() == null || pessoa.getNome().isEmpty()) {
            return null;
        }
        String telefone = pessoa.getTelefone();

        if (!(telefone == null || telefone.isEmpty() || telefone.replaceAll("\\D+", "").length() < 8)) {
            return estadoProcessor.getEstado(pessoa);
        }
        return null;
    }

    private void fimDaExportacao(Exception ex, IStrategyDesktop desktop) {
        if (!terminou) {
            terminou = true;
            try {
                LogSingleton log = LogSingleton.getInstancia();
                if (ex == null) {
                    log.exportaContatos(sucesso);
                    JOptionPane.showMessageDialog(desktop.getComponent(), sucesso
                            + " contato(s) exportados com sucesso");
                } else {
                    log.exportaContatosFalha(ex);
                    JOptionPane.showMessageDialog(desktop.getComponent(),
                            "um erro impede a exportação de continuar.\n" + ex);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(desktop.getComponent(), "Não foi possivel gravar em log a exportação");
            }
        }
    }

}
