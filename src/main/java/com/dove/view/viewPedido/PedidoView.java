package com.dove.view.viewPedido;

import com.dove.controller.FuncionarioController;
import com.dove.model.entities.CardapiosEntity;
import com.dove.model.entities.ClienteEntity;
import com.dove.model.entities.FuncionarioEntity;
import com.dove.model.entities.PedidoEntity;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class PedidoView {
    // --- Paleta de cores definida ---
    private static final Color COR_FUNDO = Color.WHITE;
    private static final Color COR_ACENTO = new Color(0xFFA500); // Laranja
    private static final Color COR_TEXTO = new Color(0x333333);   // Cinza Escuro
    private static final Color COR_SLA = new Color(0x9C999A);

    private JTable tabela;
    private DefaultTableModel modeloTabela;

    public PedidoView(){
        String[] colunas = {"ID", "Marmita", "Status", "Hora Inicio", "Hora Fim", "Funcionario", "Cliente"};
        this.modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        this.tabela = new JTable(modeloTabela);
        atualizarTabela();
    }

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
        JPanel panel = new JPanel();

        // --- Tabela ---
//        String[] colunas = {"ID", "Marmita", "Status", "Hora Inicio", "Hora Fim", "Funcionario", "Cliente"};
//        this.modeloTabela = new DefaultTableModel(colunas, 0) {
//            @Override
//            public boolean isCellEditable(int row, int column) {
//                return false;
//            }
//        };
//        this.tabela = new JTable(modeloTabela);

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
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    public JPanel cadastrar(){
        JPanel panel = new JPanel();
        JLabel label = new JLabel("cadastrar");
        panel.add(label);
        return panel;
    }

    private void atualizarTabela() {
        modeloTabela.setRowCount(0);

        List<PedidoEntity> pedidos = new ArrayList<PedidoEntity>();
        pedidos.add(new PedidoEntity(1L, "pequena", "pronto", LocalTime.now(), LocalTime.now(), new CardapiosEntity(), new FuncionarioEntity(), new ClienteEntity()));
        pedidos.add(new PedidoEntity(1L, "pequena", "pronto", LocalTime.now(), LocalTime.now(), new CardapiosEntity(), new FuncionarioEntity(), new ClienteEntity()));
        pedidos.add(new PedidoEntity(1L, "pequena", "pronto", LocalTime.now(), LocalTime.now(), new CardapiosEntity(), new FuncionarioEntity(), new ClienteEntity()));

        for (PedidoEntity pedido : pedidos) {
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
