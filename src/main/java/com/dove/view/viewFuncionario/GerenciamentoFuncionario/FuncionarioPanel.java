package com.dove.view.viewFuncionario.GerenciamentoFuncionario;

import com.dove.controller.FuncionarioController;
import com.dove.model.entities.FuncionarioEntity;
import com.dove.model.entities.PedidoEntity;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class FuncionarioPanel extends JPanel {

    // --- Paleta de cores definida ---
    private static final Color COR_FUNDO = Color.WHITE;
    private static final Color COR_ACENTO = new Color(0xFFA500); // Laranja
    private static final Color COR_TEXTO = new Color(0x333333);   // Cinza Escuro
    private static final Color COR_SLA = new Color(0x9C999A);

    private FuncionarioController funcionarioController;
    private JTable tabela;
    private DefaultTableModel modeloTabela;

    public FuncionarioPanel(FuncionarioController controller) {
        this.funcionarioController = controller;
        setBorder(new EmptyBorder(15, 15, 15, 15));
        setLayout(new BorderLayout(10, 15));
        setBackground(COR_FUNDO);

        // --- Título ---
        JLabel lblTitulo = new JLabel("Gerenciamento de Funcionários");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 22));
        lblTitulo.setForeground(COR_ACENTO);
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        add(lblTitulo, BorderLayout.NORTH);

        // --- Tabela ---
        String[] colunas = {"ID", "Nome", "CPF"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabela = new JTable(modeloTabela);

        tabela.setFont(new Font("SansSerif", Font.PLAIN, 14));
        tabela.setForeground(COR_TEXTO);
        tabela.setRowHeight(28);
        tabela.setBackground(COR_FUNDO);
        tabela.setSelectionBackground(COR_SLA);
        tabela.setSelectionForeground(Color.WHITE);
        tabela.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));
        tabela.getTableHeader().setForeground(COR_ACENTO);
        tabela.getTableHeader().setReorderingAllowed(false);
        tabela.setRowSorter(new TableRowSorter<>(modeloTabela));

        JScrollPane scrollPane = new JScrollPane(tabela);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));
        add(scrollPane, BorderLayout.CENTER);

        // --- Painel de Botões ---
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        painelBotoes.setBackground(COR_FUNDO);

        JButton btnAdicionar = createStyledButton("Adicionar");
        JButton btnAtualizar = createStyledButton("Atualizar");
        JButton btnRemover = createStyledButton("Remover");
        JButton btnRelatorio = createStyledButton("Ver Relatório");

        painelBotoes.add(btnAdicionar);
        painelBotoes.add(btnAtualizar);
        painelBotoes.add(btnRemover);
        painelBotoes.add(btnRelatorio);
        add(painelBotoes, BorderLayout.SOUTH);

        // --- LÓGICA COMPLETA DOS ACTION LISTENERS ---
        btnAdicionar.addActionListener(e -> {
            FuncionarioDialog dialog = new FuncionarioDialog((Frame) SwingUtilities.getWindowAncestor(this), funcionarioController, null);
            dialog.setVisible(true);
            atualizarTabela();
        });

        btnAtualizar.addActionListener(e -> {
            int linhaSelecionada = tabela.getSelectedRow();
            if (linhaSelecionada == -1) {
                JOptionPane.showMessageDialog(this, "Selecione um funcionário para atualizar.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int indiceReal = tabela.convertRowIndexToModel(linhaSelecionada);
            Long id = (Long) modeloTabela.getValueAt(indiceReal, 0);
            FuncionarioEntity funcionarioParaAtualizar = funcionarioController.buscarFuncionarioPorId(id);
            FuncionarioDialog dialog = new FuncionarioDialog((Frame) SwingUtilities.getWindowAncestor(this), funcionarioController, funcionarioParaAtualizar);
            dialog.setVisible(true);
            atualizarTabela();
        });

        btnRemover.addActionListener(e -> {
            int linhaSelecionada = tabela.getSelectedRow();
            if (linhaSelecionada == -1) {
                JOptionPane.showMessageDialog(this, "Selecione um funcionário para remover.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int confirmacao = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja remover este funcionário?", "Confirmação", JOptionPane.YES_NO_OPTION);
            if (confirmacao == JOptionPane.YES_OPTION) {
                int indiceReal = tabela.convertRowIndexToModel(linhaSelecionada);
                Long id = (Long) modeloTabela.getValueAt(indiceReal, 0);
                boolean sucesso = funcionarioController.removerFuncionario(id);
                if (sucesso) {
                    JOptionPane.showMessageDialog(this, "Funcionário removido com sucesso!");
                    atualizarTabela();
                } else {
                    JOptionPane.showMessageDialog(this, "Erro ao remover o funcionário.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnRelatorio.addActionListener(e -> {
            int linhaSelecionada = tabela.getSelectedRow();
            if (linhaSelecionada == -1) {
                JOptionPane.showMessageDialog(this, "Selecione um funcionário para ver o relatório.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int indiceReal = tabela.convertRowIndexToModel(linhaSelecionada);
            Long id = (Long) modeloTabela.getValueAt(indiceReal, 0);

            FuncionarioEntity funcionario = funcionarioController.buscarFuncionarioPorId(id);
            List<PedidoEntity> pedidos = funcionarioController.buscarPedidosPorFuncionario(id);

            FuncionarioRelatorioDialog dialog = new FuncionarioRelatorioDialog((Frame) SwingUtilities.getWindowAncestor(this), funcionario, pedidos);
            dialog.setVisible(true);
        });

        atualizarTabela();
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("SansSerif", Font.BOLD, 12));

        final Color originalColor = COR_ACENTO;
        button.setBackground(originalColor);
        button.setForeground(Color.WHITE);

        button.setFocusPainted(false);
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(originalColor.brighter());
            }
            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(originalColor);
            }
        });

        return button;
    }

    private void atualizarTabela() {
        modeloTabela.setRowCount(0);
        List<FuncionarioEntity> funcionarios = funcionarioController.listarFuncionarios();
        for (FuncionarioEntity funcionario : funcionarios) {
            modeloTabela.addRow(new Object[]{
                    funcionario.getId(),
                    funcionario.getNome(),
                    funcionario.getCpf()
            });
        }
    }
}