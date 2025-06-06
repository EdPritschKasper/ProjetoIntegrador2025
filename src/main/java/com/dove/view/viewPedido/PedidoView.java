package com.dove.view.viewPedido;

import com.dove.model.entities.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import com.dove.view.viewPedido.PedidoFillerData;

public class PedidoView {
    // --- Paleta de cores definida ---
    private static final Color COR_FUNDO = Color.WHITE;
    private static final Color COR_ACENTO = new Color(0xFFA500); // Laranja
    private static final Color COR_TEXTO = new Color(0x333333);   // Cinza Escuro
    private static final Color COR_SLA = new Color(0x9C999A);

    private PedidoFillerData pedidos;

    public JPanel view(){
        JPanel panelGeral = new JPanel(new BorderLayout());
        JPanel panelBotoes = new JPanel();
        CardLayout cardLayout = new CardLayout();
        JPanel panelConteudo = new JPanel(cardLayout);

        // btn
        JButton btnLista = new JButton("lista");
        JButton btnCadastra = new JButton("cadastra");
        btnLista.addActionListener(e -> cardLayout.show(panelConteudo, "lista"));
        btnCadastra.addActionListener(e -> cardLayout.show(panelConteudo, "cadastro"));

        // Add panelConteudo
        this.pedidos = new PedidoFillerData();
        panelConteudo.add(listar(), "lista");
        panelConteudo.add(cadastrar(), "cadastro");

        // Add panelBotoes
        panelBotoes.add(btnLista);
        panelBotoes.add(btnCadastra);

        // Add panelGeral
        panelGeral.add(panelConteudo, BorderLayout.NORTH);
        panelGeral.add(panelBotoes, BorderLayout.SOUTH);

        // return
        return panelGeral;
    }

    public JPanel listar(){
        JPanel panel = new JPanel(new BorderLayout());

        // --- Tabela ---
        String[] colunas = {"ID", "Marmita", "Status", "Hora Inicio", "Hora Fim", "Funcionario", "Cliente"};
        DefaultTableModel modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable tabela = new JTable(modeloTabela);
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
        tabela.getColumnModel().getColumn(0).setPreferredWidth(10);

        JScrollPane scrollPane = new JScrollPane(tabela);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));
        panel.add(scrollPane, BorderLayout.CENTER);

        atualizarTabela(modeloTabela);

        scrollPane.setBackground(Color.red);
        scrollPane.setSize(1000, 1000);
        return panel;
    }

    public JPanel cadastrar(){
        JPanel panel = new JPanel(new GridLayout());
        JLabel label = new JLabel("cadastrar");
        panel.add(label);
        return panel;
    }

    public void atualizarTabela(DefaultTableModel modeloTabela) {
        modeloTabela.setRowCount(0);

        for (PedidoEntity pedido : pedidos.getPedidos()) {
            modeloTabela.addRow(new Object[]{
                    pedido.getId(),
                    pedido.getMarmita(),
                    pedido.getStatus(),
                    pedido.getHora_inicio(),
                    pedido.getHora_fim(),
                    pedido.getFuncionario(),
                    pedido.getCliente(),
            });
        }
    }
}
