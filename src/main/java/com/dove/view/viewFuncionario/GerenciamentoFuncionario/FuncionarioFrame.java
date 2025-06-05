package com.dove.view.viewFuncionario.GerenciamentoFuncionario;

import com.dove.controller.FuncionarioController;
import com.dove.model.service.FuncionarioService;
import com.dove.model.repository.CustomizerFactory;
import jakarta.persistence.EntityManager;

import javax.swing.*;

public class FuncionarioFrame extends JFrame {

    private FuncionarioController funcionarioController;

    public FuncionarioFrame() {

        EntityManager em = CustomizerFactory.getEntityManager();
        FuncionarioService funcionarioService = new FuncionarioService(em);
        this.funcionarioController = new FuncionarioController(funcionarioService);

        setTitle("Gerenciamento de Funcionários");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        FuncionarioPanel panel = new FuncionarioPanel(this.funcionarioController);
        add(panel);
    }
}