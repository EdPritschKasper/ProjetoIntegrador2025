package com.dove.view.viewFuncionario.GerenciamentoFuncionario;

import com.dove.controller.FuncionarioController;
import com.dove.model.entities.FuncionarioEntity;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class FuncionarioDialog extends JDialog {



    private FuncionarioController funcionarioController;
    private FuncionarioEntity funcionario;
    private JTextField txtNome;
    private JTextField txtCpf;

    public FuncionarioDialog(Frame owner, FuncionarioController controller, FuncionarioEntity funcionario) {
        super(owner, true);
        this.funcionarioController = controller;
        this.funcionario = funcionario;

        setTitle(funcionario == null ? "Adicionar Novo Funcionário" : "Atualizar Funcionário");
        setSize(450, 220);
        setLocationRelativeTo(owner);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;


        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        JLabel lblNome = new JLabel("Nome:");
        lblNome.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        panel.add(lblNome, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        txtNome = new JTextField(20);
        txtNome.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        panel.add(txtNome, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        JLabel lblCpf = new JLabel("CPF:");
        lblCpf.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        panel.add(lblCpf, gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        txtCpf = new JTextField(20);
        txtCpf.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        panel.add(txtCpf, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.EAST;
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));

        JButton btnSalvar = new JButton("Salvar");
        JButton btnCancelar = new JButton("Cancelar");
        btnSalvar.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btnCancelar.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        painelBotoes.add(btnSalvar);
        painelBotoes.add(btnCancelar);
        panel.add(painelBotoes, gbc);

        add(panel);

        if (funcionario != null) {
            txtNome.setText(funcionario.getNome());
            txtCpf.setText(funcionario.getCpf());
        }

        btnSalvar.addActionListener(e -> salvar());
        btnCancelar.addActionListener(e -> dispose());
    }

    private void salvar() {

        String nome = txtNome.getText();
        String cpf = txtCpf.getText();

        if (nome.isBlank() || cpf.isBlank()) {
            JOptionPane.showMessageDialog(this, "Nome e CPF são obrigatórios.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean sucesso;
        if (funcionario == null) {
            FuncionarioEntity novoFuncionario = new FuncionarioEntity();
            novoFuncionario.setNome(nome);
            novoFuncionario.setCpf(cpf);
            sucesso = funcionarioController.cadastrarFuncionario(novoFuncionario);
        } else {
            funcionario.setNome(nome);
            funcionario.setCpf(cpf);
            sucesso = funcionarioController.atualizarFuncionario(funcionario);
        }

        if (sucesso) {
            JOptionPane.showMessageDialog(this, "Operação realizada com sucesso!");
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Erro ao realizar a operação. Verifique os dados (o CPF pode ser duplicado).", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}