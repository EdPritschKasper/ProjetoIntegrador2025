package com.dove.view.viewIngrediente;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class IngredientePanel extends JPanel {

    private static final Color COR_FUNDO = Color.WHITE;
    private static final Color COR_ACENTO = new Color(0xFFA500);
    private static final Color COR_TEXTO = new Color(0x333333);
    private static final Color COR_SLA = new Color(0x9C999A);

    private JTable tabela;
    private DefaultTableModel modeloTabela;

    private final List<Ingrediente> ingredientes = new ArrayList<>();

    public IngredientePanel() {
        setBorder(new EmptyBorder(15, 15, 15, 15));
        setLayout(new BorderLayout(10, 15));
        setBackground(COR_FUNDO);

        JLabel lblTitulo = new JLabel("Gerenciamento de Ingredientes");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 22));
        lblTitulo.setForeground(COR_ACENTO);
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        add(lblTitulo, BorderLayout.NORTH);

        // Colunas: ID e Nome (descritivo)
        String[] colunas = {"ID", "Nome"};
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

        painelBotoes.add(btnAdicionar);
        painelBotoes.add(btnAtualizar);
        painelBotoes.add(btnRemover);

        add(painelBotoes, BorderLayout.SOUTH);

        // Eventos dos botões
        btnAdicionar.addActionListener(e -> {
            Ingrediente novo = exibirDialogo(null);
            if (novo != null) {
                novo.setId(gerarNovoId());
                ingredientes.add(novo);
                atualizarTabela();
            }
        });

        btnAtualizar.addActionListener(e -> {
            int linhaSelecionada = tabela.getSelectedRow();
            if (linhaSelecionada == -1) {
                JOptionPane.showMessageDialog(this, "Selecione um ingrediente para atualizar.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int indiceReal = tabela.convertRowIndexToModel(linhaSelecionada);
            Ingrediente existente = ingredientes.get(indiceReal);

            Ingrediente atualizado = exibirDialogo(existente);
            if (atualizado != null) {
                ingredientes.set(indiceReal, atualizado);
                atualizarTabela();
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
                ingredientes.remove(indiceReal);
                atualizarTabela();
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
        for (Ingrediente i : ingredientes) {
            modeloTabela.addRow(new Object[]{
                    i.getId(),
                    i.getNome()
            });
        }
    }

    private Ingrediente exibirDialogo(Ingrediente original) {
        JTextField campoNome = new JTextField();

        if (original != null) {
            campoNome.setText(original.getNome());
        }

        JPanel painel = new JPanel(new GridLayout(0, 1));
        painel.add(new JLabel("Nome:"));
        painel.add(campoNome);

        int resultado = JOptionPane.showConfirmDialog(this, painel,
                original == null ? "Novo Ingrediente" : "Editar Ingrediente",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (resultado == JOptionPane.OK_OPTION) {
            String nome = campoNome.getText().trim();

            if (nome.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Preencha o campo Nome!", "Erro", JOptionPane.ERROR_MESSAGE);
                return null;
            }

            if (original == null) {
                return new Ingrediente(null, nome);
            } else {
                original.setNome(nome);
                return original;
            }
        }
        return null;
    }

    private long gerarNovoId() {
        return ingredientes.stream()
                .mapToLong(Ingrediente::getId)
                .max()
                .orElse(0) + 1;
    }

    // Classe interna para simular entidade sem banco
    private static class Ingrediente {
        private Long id;
        private String nome;

        public Ingrediente(Long id, String nome) {
            this.id = id;
            this.nome = nome;
        }

        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public String getNome() { return nome; }
        public void setNome(String nome) { this.nome = nome; }
    }
}
