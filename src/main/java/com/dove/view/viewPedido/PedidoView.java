package com.dove.view.viewPedido;

import com.dove.controller.PedidoController;
import com.dove.model.entities.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalTime;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import java.util.stream.Collectors;

import com.dove.view.viewPedido.PedidoFillerData;

public class PedidoView {
    // --- Paleta de cores definida ---
    private static final Color COR_FUNDO = Color.WHITE;
    private static final Color COR_ACENTO = new Color(0xFFA500); // Laranja
    private static final Color COR_TEXTO = new Color(0x333333);   // Cinza Escuro
    private static final Color COR_SLA = new Color(0x9C999A);
    private static final Color corFundoPrincipal = new Color(0xFFF5E5);

    private PedidoFillerData pedidos;
    private DefaultTableModel modeloTabela;

    public JPanel view(PedidoController pedidoController){
        JPanel panelGeral = new JPanel(new BorderLayout());
        JPanel panelBotoes = new JPanel();
        CardLayout cardLayout = new CardLayout();
        JPanel panelConteudo = new JPanel(cardLayout);

        // btn
        JButton btnLista = criarBotaoMenu("LISTA");
        JButton btnCadastra = criarBotaoMenu("CADASTRA");
        btnLista.addActionListener(e -> cardLayout.show(panelConteudo, "lista"));
        btnCadastra.addActionListener(e -> cardLayout.show(panelConteudo, "cadastro"));

        // Add panelConteudo
        this.pedidos = new PedidoFillerData();
        panelConteudo.add(listar(pedidoController), "lista");
        panelConteudo.add(cadastrar(pedidoController), "cadastro");

        // Add panelBotoes
        panelBotoes.add(btnLista);
        panelBotoes.add(btnCadastra);

        // Add panelGeral
        panelGeral.add(panelConteudo, BorderLayout.NORTH);
        panelGeral.add(panelBotoes, BorderLayout.SOUTH);

        // return
        return panelGeral;
    }

    public JPanel listar(PedidoController pedidoController) {
        JPanel panel = new JPanel(new BorderLayout());

        // --- Tabela ---
        String[] colunas = {"Id", "Marmita", "Status", "Hora Inicio", "Hora Fim", "Funcionario", "Cliente"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
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

        // --- Painel de Botões ---
        JPanel botoesPanel = new JPanel();
        botoesPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        botoesPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 0)); // espaço entre botões

        JButton btnAtualizar = new JButton("ATUALIZAR");
        JButton btnDeletar = new JButton("DELETAR");
        btnAtualizar.setBackground(COR_TEXTO);
        btnAtualizar.setForeground(Color.WHITE);
        btnDeletar.setBackground(COR_TEXTO);
        btnDeletar.setForeground(Color.WHITE);

        // Forçar mesma largura (maior entre os dois botões)
        Dimension tamanhoAtualizar = btnAtualizar.getPreferredSize();
        Dimension tamanhoDeletar = btnDeletar.getPreferredSize();

        int larguraMax = Math.max(tamanhoAtualizar.width, tamanhoDeletar.width) + 20; // um pouco de folga
        int alturaMax = Math.max(tamanhoAtualizar.height, tamanhoDeletar.height);

        Dimension tamanhoPadrao = new Dimension(larguraMax, alturaMax);
        btnAtualizar.setPreferredSize(tamanhoPadrao);
        btnDeletar.setPreferredSize(tamanhoPadrao);

        // ActionListeners com verificação de seleção única
        btnAtualizar.addActionListener(e -> {
            int linhaVisual = tabela.getSelectedRow();
            if (linhaVisual != -1 && tabela.getSelectedRowCount() == 1) {
                int linhaModelo = tabela.convertRowIndexToModel(linhaVisual);
                Object idObj = modeloTabela.getValueAt(linhaModelo, 0);
                long id = Long.parseLong(idObj.toString());

                // Procura o pedido correspondente
                for (PedidoEntity pedido : pedidoController.findAll()) {
                    if (pedido.getId() == id) {
                        // Verifica se o status já está "Pronto"
                        if ("Pronto".equalsIgnoreCase(pedido.getStatus().trim())) {
                            JOptionPane.showMessageDialog(panel, "O pedido já está pronto.");
                        } else {
                            // Atualiza o status e a hora fim
                            pedido.setStatus("Pronto");
                            LocalTime horaFim = LocalTime.now();
                            pedido.setHora_fim(horaFim);
                            boolean atualizado = pedidoController.updatePedido(pedido);

                            if(atualizado){
                                // Atualiza a tabela visualmente
                                modeloTabela.setValueAt("Pronto", linhaModelo, 2); // coluna "Status"
                                modeloTabela.setValueAt(horaFim, linhaModelo, 4);  // coluna "Hora Fim"

                                JOptionPane.showMessageDialog(panel, "Pedido atualizado para pronto.");
                            } else {
                                JOptionPane.showMessageDialog(panel, "Pedido não atualizado");
                            }
                        }
                        return;
                    }
                }
                JOptionPane.showMessageDialog(panel, "Pedido não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(panel, "Selecione exatamente uma linha para atualizar.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });


        btnDeletar.addActionListener(e -> {
            int linhaVisual = tabela.getSelectedRow();
            if (linhaVisual != -1 && tabela.getSelectedRowCount() == 1) {
                int linhaModelo = tabela.convertRowIndexToModel(linhaVisual);
                Object idObj = modeloTabela.getValueAt(linhaModelo, 0);
                long id = Long.parseLong(idObj.toString());

                // Remove da lista de pedidos
                boolean removido = pedidoController.deletePedido(pedidoController.pesquisaPedido(id));

                if (removido) {
                    // Remove da tabela visual
                    modeloTabela.removeRow(linhaModelo);
                    JOptionPane.showMessageDialog(panel, "Pedido deletado com sucesso.");
                } else {
                    JOptionPane.showMessageDialog(panel, "Pedido não encontrado na entidade.", "Erro", JOptionPane.ERROR_MESSAGE);
                }

            } else {
                JOptionPane.showMessageDialog(panel, "Selecione exatamente uma linha para deletar.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });


        // ao clicar no scrollpane ele tira a selecao da linha
        scrollPane.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Deseleciona todas as linhas da tabela
                tabela.clearSelection();
            }
        });

        botoesPanel.add(btnAtualizar);
        botoesPanel.add(btnDeletar);

        panel.add(botoesPanel, BorderLayout.SOUTH);

        atualizarTabela(pedidoController);

        return panel;
    }

    public JPanel cadastrar(PedidoController pedidoController) {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(10, 10, 10, 10); // Espaçamento

        // Panel de listas
        JPanel listaPanel = new JPanel();
        listaPanel.setLayout(new BoxLayout(listaPanel, BoxLayout.Y_AXIS));

        JPanel marmitaPanel = new JPanel();
        marmitaPanel.setLayout(new BoxLayout(marmitaPanel, BoxLayout.Y_AXIS));

        // Ingredientes - JCheckBox
        List<JCheckBox> checkBoxes = new ArrayList<>();
        String[] opcoes = {"Arroz", "Feijão", "Carne", "Frango", "Alface", "Tomate"};
        for (String nome : opcoes) {
            JCheckBox box = new JCheckBox(nome);
            box.setFocusPainted(false);
            box.setContentAreaFilled(true);
            box.setOpaque(true);
            box.setBackground(corFundoPrincipal);
            box.setFont(new Font("Arial", Font.BOLD, 14));
            box.setAlignmentX(Component.LEFT_ALIGNMENT);
            box.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
            box.setHorizontalAlignment(SwingConstants.LEFT);

            box.addItemListener(e -> {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    box.setBackground(COR_ACENTO);
                    box.setForeground(Color.WHITE);
                } else {
                    box.setBackground(corFundoPrincipal);
                    box.setForeground(COR_TEXTO);
                }
            });

            checkBoxes.add(box);
            listaPanel.add(box);
        }

        // Marmita - JRadioButton
        ButtonGroup grupo = new ButtonGroup();
        List<JRadioButton> radioButtons = new ArrayList<>();
        String[] opcoes2 = {"Pequena", "Média", "Grande", "Prato no Local"};

        for (String nome : opcoes2) {
            JRadioButton radio = new JRadioButton(nome);
            radio.setFocusPainted(false);
            radio.setContentAreaFilled(true);
            radio.setOpaque(true);
            radio.setBackground(corFundoPrincipal);
            radio.setFont(new Font("Arial", Font.BOLD, 14));
            radio.setAlignmentX(Component.LEFT_ALIGNMENT);
            radio.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
            radio.setHorizontalAlignment(SwingConstants.LEFT);

            grupo.add(radio);
            radioButtons.add(radio);

            radio.addItemListener(e -> {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    radio.setBackground(COR_ACENTO);
                    radio.setForeground(Color.WHITE);
                } else {
                    radio.setBackground(corFundoPrincipal);
                    radio.setForeground(COR_TEXTO);
                }
            });

            marmitaPanel.add(radio);
        }

        // Labels estilizados
        JLabel labelIngredientes = new JLabel("Ingredientes");
        labelIngredientes.setFont(new Font("Arial", Font.BOLD, 18));

        JLabel labelMarmita = new JLabel("Marmita");
        labelMarmita.setFont(new Font("Arial", Font.BOLD, 18));

        // Scroll panes
        JScrollPane scrollIngredientes = new JScrollPane(listaPanel);
        JScrollPane scrollMarmita = new JScrollPane(marmitaPanel);

        // Super panels
        JPanel panelSuperIngredientes = new JPanel(new BorderLayout());
        panelSuperIngredientes.add(labelIngredientes, BorderLayout.NORTH);
        panelSuperIngredientes.add(scrollIngredientes, BorderLayout.CENTER);

        JPanel panelSuperMarmita = new JPanel(new BorderLayout());
        panelSuperMarmita.add(labelMarmita, BorderLayout.NORTH);
        panelSuperMarmita.add(scrollMarmita, BorderLayout.CENTER);

        // Botão de Concluir
        JButton btnConcluir = new JButton("Concluir Pedido");
        btnConcluir.setFont(new Font("Arial", Font.BOLD, 16));
        btnConcluir.setBackground(COR_TEXTO);
        btnConcluir.setForeground(Color.WHITE);
        btnConcluir.addActionListener(e -> {
            boolean radioSelecionado = radioButtons.stream().anyMatch(AbstractButton::isSelected);
            boolean checkboxMarcado = checkBoxes.stream().anyMatch(AbstractButton::isSelected);

            if (radioSelecionado && checkboxMarcado) {

                // ingredientes selecionados
                List<String> ingredientesSelecionados = checkBoxes.stream()
                        .filter(JCheckBox::isSelected)
                        .map(AbstractButton::getText)
                        .collect(Collectors.toList());

                // marmita selecionada
                String marmitaSelecionada = radioButtons.stream()
                        .filter(JRadioButton::isSelected)
                        .map(AbstractButton::getText)
                        .findFirst()
                        .orElse(null);

                pedidos.addPedido(new PedidoEntity(Math.abs(new Random().nextLong()), marmitaSelecionada, "Iniciado", LocalTime.now(), null, new CardapiosEntity(), new FuncionarioEntity(), new ClienteEntity()));

                atualizarTabela(pedidoController);

                JOptionPane.showMessageDialog(panel, "Pedido concluído!");

                // Limpa checkboxes
                checkBoxes.forEach(box -> {
                    box.setSelected(false);
                    box.setBackground(corFundoPrincipal);
                    box.setForeground(COR_TEXTO);
                });

                // Limpa radio buttons
                grupo.clearSelection();
                radioButtons.forEach(radio -> {
                    radio.setBackground(corFundoPrincipal);
                    radio.setForeground(COR_TEXTO);
                });
            } else {
                JOptionPane.showMessageDialog(panel, "Selecione pelo menos uma opção de marmita e um ingrediente.");
            }
        });

        // Adiciona ao GridBagLayout
        // Panel ingredientes
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.55;
        gbc.weighty = 1.0;
        gbc.gridwidth = 1;
        panel.add(panelSuperIngredientes, gbc);

        // Panel marmita
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.45;
        gbc.weighty = 1.0;
        gbc.gridwidth = 1;
        panel.add(panelSuperMarmita, gbc);

        // Botão
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 0.1;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(btnConcluir, gbc);

        return panel;
    }


    public void atualizarTabela(PedidoController pedidoController) {
        modeloTabela.setRowCount(0);

        for (PedidoEntity pedido : pedidoController.findAll()) {
            modeloTabela.addRow(new Object[]{
                    pedido.getId(),
                    pedido.getMarmita(),
                    pedido.getStatus(),
                    pedido.getHora_inicio(),
                    pedido.getHora_fim(),
                    pedido.getFuncionario().getId(),
                    pedido.getCliente().getEmail(),
            });
        }
    }

    private JButton criarBotaoMenu(String texto) {
        JButton btn = new JButton(texto);

        final Color corOriginal = COR_ACENTO;
        final Color corHover = COR_ACENTO.brighter();

        btn.setBackground(corOriginal);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Arial", Font.BOLD, 14));

        btn.setOpaque(true);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);

        btn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        btn.setAlignmentX(Component.LEFT_ALIGNMENT);
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(corHover);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btn.setBackground(corOriginal);
            }
        });

        return btn;
    }
}


