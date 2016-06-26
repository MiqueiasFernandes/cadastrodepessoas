/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cadastrodepessoas.presenter.patterns.strategy;

import com.cadastrodepessoas.model.Importa;
import com.cadastrodepessoas.model.Pessoa;
import java.io.File;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ImportarXML implements IStrategyImportar {

    protected Element root;
    protected Document document;
    protected File file;

    @Override
    public boolean preparaParaImportar(IStrategyDesktop dektop) throws Exception {
        JFileChooser chooser = new JFileChooser();
        dektop.addComponent(chooser);

        chooser.setApproveButtonText("importar");

        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "arquivos XML", "xml");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(chooser.getParent());

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            file = chooser.getSelectedFile();
            return carregarArquivo();
        } else {
            return false;
        }
    }

    boolean carregarArquivo() throws ParserConfigurationException, SAXException, IOException {
        if (file.exists()) {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = null;
            dBuilder = dbFactory.newDocumentBuilder();
            document = dBuilder.parse(file);
            document.getDocumentElement().normalize();
            root = document.getDocumentElement();
            return true;
        }
        return false;
    }

    @Override
    public void popularCollection(Importa<Pessoa> importa) {
        NodeList list = root.getElementsByTagName("contato");
        importa.getCollection().clear();
        importa.statusTotal(0);
        for (int i = 0; i < list.getLength(); i++) {
            try {
                importa.statusUnitario(0);
                Node item = list.item(i);
                importa.statusUnitario(40);
                Pessoa pessoa = getPessoaByNode(item);
                importa.statusUnitario(80);
                if (pessoa != null) {
                    importa.getCollection().add(pessoa);
                }
                importa.statusTotal(i + 1);
                importa.statusUnitario(100);
            } catch (Exception ex) {
                importa.addErro(ex);
            }
        }
    }

    Pessoa getPessoaByNode(Node node) {
        if (node.getNodeType() == Node.ELEMENT_NODE) {
            Element contato = (Element) node;
            NodeList nome = contato.getElementsByTagName("Nome");
            NodeList telefone = contato.getElementsByTagName("Telefone");
            if (nome.getLength() > 0 && telefone.getLength() > 0) {
                return new Pessoa(nome.item(0).getTextContent(), telefone.item(0).getTextContent());
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "Arquivo XML";
    }

    @Override
    public int getQuantidade() {
        return root.getElementsByTagName("contato").getLength();
    }
}
