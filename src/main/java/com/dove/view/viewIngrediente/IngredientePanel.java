package com.dove.view.viewIngrediente;

import com.dove.controller.IngredienteController;
import com.dove.model.entities.IngredienteEntity;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class IngredientePanel extends JPanel {

    private static final Color COR_FUNDO = Color.WHITE;
    private static final Color COR_ACENTO = new Color(0xFFA500);
    private static final Color COR_TEXTO = new Color(0x333333);
    private static final Color COR_SLA = new Color(0x9C999A);

    private JTable tabela;
    private DefaultTableModel modeloTabela;

    private IngredienteController controller;

    public IngredientePanel() {
        controller = new IngredienteController();

        setBorder(new EmptyBorder(15, 15, 15, 15));
        setLayout(new BorderLayout(10, 15));
        setBackground(COR_FUNDO);

        JLabel lblTitulo = new JLabel("Gerenciamento de Ingredientes");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 22));
        lblTitulo.setForeground(COR_ACENTO);
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        add(lblTitulo, BorderLayout.NORTH);

        String[] colunas = {"ID", "Descrição"};
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

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        painelBotoes.setBackground(COR_FUNDO);

        JButton btnAdicionar = createStyledButton("Adicionar");
        JButton btnAtualizar = createStyledButton("Atualizar");
        JButton btnRemover = createStyledButton("Remover");
        JButton btnMaisSelecionado = createStyledButton("Ingrediente Mais Selecionado");

        painelBotoes.add(btnAdicionar);
        painelBotoes.add(btnAtualizar);
        painelBotoes.add(btnRemover);
        painelBotoes.add(btnMaisSelecionado);

        add(painelBotoes, BorderLayout.SOUTH);

        // Eventos dos botões
        btnAdicionar.addActionListener(e -> {
            IngredienteEntity novo = exibirDialogo(null);
            if (novo != null) {
                boolean sucesso = controller.insert(novo);
                if (sucesso) {
                    atualizarTabela();
                } else {
                    JOptionPane.showMessageDialog(this, "Erro ao adicionar ingrediente no banco.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnAtualizar.addActionListener(e -> {
            int linhaSelecionada = tabela.getSelectedRow();
            if (linhaSelecionada == -1) {
                JOptionPane.showMessageDialog(this, "Selecione um ingrediente para atualizar.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int indiceReal = tabela.convertRowIndexToModel(linhaSelecionada);
            IngredienteEntity existente = getIngredientes().get(indiceReal);

            IngredienteEntity atualizado = exibirDialogo(existente);
            if (atualizado != null) {
                atualizado = new IngredienteEntity(existente.getId(), atualizado.getDescricao()); // manter o id
                boolean sucesso = controller.update(atualizado);
                if (sucesso) {
                    atualizarTabela();
                } else {
                    JOptionPane.showMessageDialog(this, "Erro ao atualizar ingrediente no banco.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnRemover.addActionListener(e -> {
            int linhaSelecionada = tabela.getSelectedRow();
            if (linhaSelecionada == -1) {
                JOptionPane.showMessageDialog(this, "Selecione um ingrediente para remover.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int confirmacao = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja remover este ingrediente?", "Confirmação", JOptionPane.YES_NO_OPTION);
            if (confirmacao == JOptionPane.YES_OPTION) {
                int indiceReal = tabela.convertRowIndexToModel(linhaSelecionada);
                IngredienteEntity aRemover = getIngredientes().get(indiceReal);
                boolean sucesso = controller.delete(aRemover);
                if (sucesso) {
                    atualizarTabela();
                } else {
                    JOptionPane.showMessageDialog(this, "Erro ao remover ingrediente no banco.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnMaisSelecionado.addActionListener(e -> {
            IngredienteEntity maisSelecionado = controller.getMostSelectedIngrediente();
            if (maisSelecionado != null) {
                JOptionPane.showMessageDialog(this,
                        "Ingrediente mais selecionado: " + maisSelecionado.getDescricao(),
                        "Ingrediente Mais Selecionado",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this,
                        "Nenhum ingrediente encontrado.",
                        "Ingrediente Mais Selecionado",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });

        atualizarTabela();
    }

    private JButton createStyledButton(String texto) {
        JButton botao = new JButton(texto);
        botao.setFont(new Font("SansSerif", Font.BOLD, 12));
        botao.setBackground(COR_ACENTO);
        botao.setForeground(Color.WHITE);
        botao.setFocusPainted(false);
        botao.setOpaque(true);
        botao.setBorderPainted(false);
        botao.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        botao.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                botao.setBackground(COR_ACENTO.brighter());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                botao.setBackground(COR_ACENTO);
            }
        });

        return botao;
    }

    private void atualizarTabela() {
        modeloTabela.setRowCount(0);
        List<IngredienteEntity> ingredientes = getIngredientes();
        for (IngredienteEntity i : ingredientes) {
            modeloTabela.addRow(new Object[]{
                    i.getId(),
                    i.getDescricao()
            });
        }
    }

    private List<IngredienteEntity> getIngredientes() {
        return controller.findAll();
    }

    private IngredienteEntity exibirDialogo(IngredienteEntity original) {
        JTextField campoDescricao = new JTextField();

        if (original != null) {
            campoDescricao.setText(original.getDescricao());
        }

        JPanel painel = new JPanel(new GridLayout(0, 1));
        painel.add(new JLabel("Descrição:"));
        painel.add(campoDescricao);

        int resultado = JOptionPane.showConfirmDialog(this, painel,
                original == null ? "Novo Ingrediente" : "Editar Ingrediente",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (resultado == JOptionPane.OK_OPTION) {
            String descricao = campoDescricao.getText().trim();

            if (descricao.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Preencha o campo descrição!", "Erro", JOptionPane.ERROR_MESSAGE);
                return null;
            }

            if (original == null) {
                return new IngredienteEntity(descricao);
            } else {
                original.setDescricao(descricao);
                return original;
            }
        }
        return null;
    }
}
