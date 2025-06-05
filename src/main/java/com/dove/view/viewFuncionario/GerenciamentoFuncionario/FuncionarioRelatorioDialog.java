package com.dove.view.viewFuncionario.GerenciamentoFuncionario;

import com.dove.model.entities.FuncionarioEntity;
import com.dove.model.entities.PedidoEntity;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class FuncionarioRelatorioDialog extends JDialog {

    private static final Color COR_FUNDO = new Color(245, 245, 245);
    private static final Color COR_BORDA = new Color(200, 200, 200);
    private static final Color COR_TEXTO_AREA = new Color(255, 255, 255);
    private static final Font FONTE_TITULO = new Font("Segoe UI", Font.BOLD, 18);
    private static final Font FONTE_LABEL = new Font("Segoe UI", Font.BOLD, 14);
    private static final Font FONTE_DADOS = new Font("Segoe UI", Font.PLAIN, 14);
    private static final Font FONTE_RELATORIO = new Font("Monospaced", Font.PLAIN, 12);

    public FuncionarioRelatorioDialog(Frame owner, FuncionarioEntity funcionario, List<PedidoEntity> pedidos) {
        super(owner, "Relatório de Pedidos", true);
        setSize(550, 500);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout());
        getContentPane().setBackground(COR_FUNDO);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));
        panel.setBackground(COR_FUNDO);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JLabel lblTitulo = new JLabel("Relatório de Pedidos do Funcionário");
        lblTitulo.setFont(FONTE_TITULO);
        lblTitulo.setForeground(new Color(60, 60, 60));
        panel.add(lblTitulo, gbc);

        gbc.gridy = 1;
        gbc.insets = new Insets(20, 0, 10, 0);
        JPanel infoPanel = new JPanel(new GridLayout(3, 2, 10, 5));
        infoPanel.setBackground(COR_FUNDO);
        infoPanel.setBorder(new CompoundBorder(
                new LineBorder(COR_BORDA),
                new EmptyBorder(10, 10, 10, 10)
        ));

        infoPanel.add(createLabel("Nome:"));
        infoPanel.add(createDataLabel(funcionario.getNome()));
        infoPanel.add(createLabel("CPF:"));
        infoPanel.add(createDataLabel(funcionario.getCpf()));
        infoPanel.add(createLabel("Total de Pedidos:"));
        infoPanel.add(createDataLabel(String.valueOf(pedidos.size())));
        panel.add(infoPanel, gbc);

        gbc.gridy = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 0, 5, 0);
        JTextArea textAreaPedidos = new JTextArea();
        textAreaPedidos.setEditable(false);
        textAreaPedidos.setFont(FONTE_RELATORIO);
        textAreaPedidos.setBackground(COR_TEXTO_AREA);
        textAreaPedidos.setBorder(new EmptyBorder(5, 5, 5, 5));

        preencherRelatorio(textAreaPedidos, pedidos);

        JScrollPane scrollPane = new JScrollPane(textAreaPedidos);
        scrollPane.setBorder(new LineBorder(COR_BORDA));
        panel.add(scrollPane, gbc);


        gbc.gridy = 3;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.EAST;
        JButton btnFechar = new JButton("Fechar");
        btnFechar.setFont(FONTE_LABEL);
        btnFechar.addActionListener(e -> dispose());
        panel.add(btnFechar, gbc);

        add(panel, BorderLayout.CENTER);
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(FONTE_LABEL);
        return label;
    }

    private JLabel createDataLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(FONTE_DADOS);
        return label;
    }

    private void preencherRelatorio(JTextArea textArea, List<PedidoEntity> pedidos) {
        StringBuilder relatorio = new StringBuilder();

        if (pedidos.isEmpty()) {
            relatorio.append("Nenhum pedido encontrado para este funcionário.");
        } else {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            for (PedidoEntity pedido : pedidos) {
                relatorio.append("ID do Pedido: ").append(pedido.getId()).append("\n");
                relatorio.append("   Status: ").append(pedido.getStatus()).append("\n");
                if (pedido.getHora_inicio() != null) {
                    relatorio.append("   Início: ").append(pedido.getHora_inicio().format(formatter)).append("\n");
                }
                if (pedido.getHora_fim() != null) {
                    relatorio.append("   Fim:    ").append(pedido.getHora_fim().format(formatter)).append("\n");
                }
                relatorio.append("--------------------------------------------------\n");
            }
        }

        textArea.setText(relatorio.toString());
        textArea.setCaretPosition(0);
    }
}