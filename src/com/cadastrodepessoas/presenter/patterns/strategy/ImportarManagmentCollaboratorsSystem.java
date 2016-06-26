/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cadastrodepessoas.presenter.patterns.strategy;

import com.cadastrodepessoas.model.Importa;
import com.cadastrodepessoas.model.Pessoa;
import com.cadastrodepessoas.presenter.patterns.iterator.IColecao;
import com.cadastrodepessoas.presenter.patterns.iterator.IIterator;
import com.cadastrodepessoas.presenter.patterns.iterator.IteratorConcreto;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.TreeSet;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ImportarManagmentCollaboratorsSystem implements IStrategyImportar, IColecao<Pessoa> {

    private TreeSet<Pessoa> pessoas;

    public ImportarManagmentCollaboratorsSystem() {
        pessoas = new TreeSet<>();
    }

    @Override
    public boolean preparaParaImportar(IStrategyDesktop dektop) throws Exception {
        JFileChooser chooser = new JFileChooser();
        dektop.addComponent(chooser);

        chooser.setApproveButtonText("importar");

        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "arquivos JAR", "jar");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(chooser.getParent());

        if (returnVal == JFileChooser.APPROVE_OPTION) {

            ClassLoader classLoader
                    = new URLClassLoader(
                            new URL[]{new URL("file:" + chooser.getSelectedFile().getAbsolutePath())},
                            ClassLoader.getSystemClassLoader());
            Class classe
                    = Class.forName("managementcollaborators.ManagementCollaboratorsSystem", true, classLoader);

            Method method = classe.getMethod(
                    "getCollaborators",
                    new Class[]{});

            Object obj = method.invoke(null, null);
            Object[] colecao = (Object[]) obj;
            Class collaborador = colecao[0].getClass();

            Method getFirstName = collaborador.getMethod("getFirstName", new Class[]{});
            Method getMiddleName = collaborador.getMethod("getMiddleName", new Class[]{});
            Method getLastName = collaborador.getMethod("getLastName", new Class[]{});
            Method getPhone = collaborador.getMethod("getPhone", new Class[]{});
            Method[] metodos = {getFirstName, getMiddleName, getLastName, getPhone};

            for (Object object : colecao) {
                pessoas.add(importaCollaborator(object, metodos));
            }
            return true;
        }

        return false;
    }

    Pessoa importaCollaborator(Object collaborator, Method[] metodos)
            throws NoSuchMethodException, IllegalAccessException,
            IllegalArgumentException, InvocationTargetException {
        String firName = (String) metodos[0].invoke(collaborator, null);
        String midName = (String) metodos[1].invoke(collaborator, null);
        String lasName = (String) metodos[2].invoke(collaborator, null);
        String tel = (String) metodos[3].invoke(collaborator, null);
        Pessoa pessoa = new Pessoa(firName + " " + midName + " " + lasName, tel);
        //  System.out.println(pessoa);
        return pessoa;
    }

    @Override
    public void popularCollection(Importa<Pessoa> importa) {
        int i = 0;
        IIterator<Pessoa> iterator = getIterator();
        importa.statusTotal(0);
        while (iterator.hasNext()) {
            try {
                importa.statusUnitario(0);
                Pessoa pessoa = iterator.getNext();
                importa.statusUnitario(60);
                importa.getCollection().add(pessoa);
                importa.statusUnitario(80);
                importa.statusTotal(i++);
                importa.statusUnitario(100);
            } catch (Exception ex) {
                importa.addErro(ex);
            }
        }
    }

    @Override
    public String toString() {
        return "Managment Collaborators System";
    }

    @Override
    public int getQuantidade() {
        return pessoas.size();
    }

    @Override
    public IIterator<Pessoa> getIterator() {
        return new IteratorConcreto(this);
    }

    @Override
    public TreeSet<Pessoa> getColecaoReal() {
        return this.pessoas;
    }

}
