package com.dove.view.viewCardapio;

import com.dove.controller.CardapioController;
import com.dove.model.entities.CardapiosEntity;
import com.dove.model.entities.IngredienteEntity;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class CardapioPanel extends JPanel {
    private static final Color COR_FUNDO = Color.WHITE;
    private static final Color COR_ACENTO = new Color(0xFFA500);
    private static final Color COR_TEXTO = new Color(0x333333);
    private static final Color COR_SLA = new Color(0x9C999A);

    private JTable tabela;
    private DefaultTableModel modeloTabela;
    private CardapioController controller;

    public CardapioPanel(CardapioController controller) {
        this.controller = controller;
        setBorder(new EmptyBorder(15, 15, 15, 15));
        setLayout(new BorderLayout(10, 15));
        setBackground(COR_FUNDO);

        JLabel lblTitulo = new JLabel("Gerenciamento de Cardápios");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 22));
        lblTitulo.setForeground(COR_ACENTO);
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        add(lblTitulo, BorderLayout.NORTH);

        String[] colunas = {"ID", "Data", "Ingredientes"};
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

        JScrollPane scrollPane = new JScrollPane(tabela);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));
        add(scrollPane, BorderLayout.CENTER);

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        JButton btnAdicionar = new JButton("Adicionar");
        JButton btnAtualizar = new JButton("Atualizar");
        JButton btnDeletar = new JButton("Deletar");
        JButton btnRefresh = new JButton("Atualizar Lista");
        painelBotoes.add(btnAdicionar);
        painelBotoes.add(btnAtualizar);
        painelBotoes.add(btnDeletar);
        painelBotoes.add(btnRefresh);
        add(painelBotoes, BorderLayout.SOUTH);

        btnAdicionar.addActionListener(e -> adicionarCardapio());
        btnAtualizar.addActionListener(e -> atualizarCardapio());
        btnDeletar.addActionListener(e -> deletarCardapio());
        btnRefresh.addActionListener(e -> carregarCardapios());

        carregarCardapios();
    }

    private void carregarCardapios() {
        modeloTabela.setRowCount(0);
        List<CardapiosEntity> lista = controller.findAllCardapios();
        for (CardapiosEntity c : lista) {
            StringBuilder ingredientes = new StringBuilder();
            for (IngredienteEntity i : c.getIngredientes()) {
                ingredientes.append(i.getDescricao()).append(", ");
            }
            if (ingredientes.length() > 0) ingredientes.setLength(ingredientes.length() - 2);
            modeloTabela.addRow(new Object[]{c.getId(), c.getData(), ingredientes.toString()});
        }
    }

    private void adicionarCardapio() {
        JTextField dataField = new JTextField();
        List<IngredienteEntity> ingredientes = controller.findAllIngredientes();
        JCheckBox[] checkBoxes = new JCheckBox[ingredientes.size()];
        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Data (YYYY-MM-DD):"));
        panel.add(dataField);
        panel.add(new JLabel("Ingredientes:"));
        for (int i = 0; i < ingredientes.size(); i++) {
            checkBoxes[i] = new JCheckBox(ingredientes.get(i).getDescricao());
            panel.add(checkBoxes[i]);
        }
        int result = JOptionPane.showConfirmDialog(this, panel, "Adicionar Cardápio", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                LocalDate data = LocalDate.parse(dataField.getText());
                CardapiosEntity cardapio = new CardapiosEntity(data);
                for (int i = 0; i < checkBoxes.length; i++) {
                    if (checkBoxes[i].isSelected()) {
                        cardapio.getIngredientes().add(ingredientes.get(i));
                    }
                }
                boolean ok = controller.insertCardapio(cardapio);
                if (ok) {
                    JOptionPane.showMessageDialog(this, "Cardápio cadastrado.");
                    carregarCardapios();
                } else {
                    JOptionPane.showMessageDialog(this, "Falha ao cadastrar cardápio.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void atualizarCardapio() {
        int row = tabela.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um cardápio para atualizar.");
            return;
        }
        Long id = (Long) modeloTabela.getValueAt(row, 0);
        CardapiosEntity cardapio = controller.findCardapioById(id);
        if (cardapio == null) {
            JOptionPane.showMessageDialog(this, "Cardápio não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        JTextField dataField = new JTextField(cardapio.getData().toString());
        List<IngredienteEntity> ingredientes = controller.findAllIngredientes();
        JCheckBox[] checkBoxes = new JCheckBox[ingredientes.size()];
        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Data (YYYY-MM-DD):"));
        panel.add(dataField);
        panel.add(new JLabel("Ingredientes:"));
        for (int i = 0; i < ingredientes.size(); i++) {
            checkBoxes[i] = new JCheckBox(ingredientes.get(i).getDescricao());
            if (cardapio.getIngredientes().contains(ingredientes.get(i))) {
                checkBoxes[i].setSelected(true);
            }
            panel.add(checkBoxes[i]);
        }
        int result = JOptionPane.showConfirmDialog(this, panel, "Atualizar Cardápio", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                cardapio.setData(LocalDate.parse(dataField.getText()));
                cardapio.getIngredientes().clear();
                for (int i = 0; i < checkBoxes.length; i++) {
                    if (checkBoxes[i].isSelected()) {
                        cardapio.getIngredientes().add(ingredientes.get(i));
                    }
                }
                boolean ok = controller.updateCardapio(cardapio);
                if (ok) {
                    JOptionPane.showMessageDialog(this, "Cardápio atualizado.");
                    carregarCardapios();
                } else {
                    JOptionPane.showMessageDialog(this, "Falha ao atualizar cardápio.", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void deletarCardapio() {
        int row = tabela.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um cardápio para deletar.");
            return;
        }
        Long id = (Long) modeloTabela.getValueAt(row, 0);
        CardapiosEntity cardapio = controller.findCardapioById(id);
        if (cardapio == null) {
            JOptionPane.showMessageDialog(this, "Cardápio não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja deletar o cardápio?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            boolean ok = controller.deleteCardapio(cardapio);
            if (ok) {
                JOptionPane.showMessageDialog(this, "Cardápio deletado.");
                carregarCardapios();
            } else {
                JOptionPane.showMessageDialog(this, "Falha ao deletar cardápio.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
