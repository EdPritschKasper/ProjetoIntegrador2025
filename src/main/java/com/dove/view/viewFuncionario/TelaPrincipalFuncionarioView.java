package com.dove.view.viewFuncionario;

// Imports necessários para as novas classes e funcionalidades
import com.dove.controller.FuncionarioController;
import com.dove.model.entities.FuncionarioEntity;
import com.dove.model.repository.CustomizerFactory;
import com.dove.model.service.FuncionarioService;
import com.dove.view.viewFuncionario.GerenciamentoFuncionario.FuncionarioPanel;
import com.dove.view.viewIngrediente.IngredienteFrame;
import com.dove.view.viewLogin.LoginView;
import com.dove.view.viewPedido.PedidoView;
import jakarta.persistence.EntityManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class TelaPrincipalFuncionarioView extends JFrame {

    private final CardLayout cardLayout = new CardLayout();
    private final JPanel painelCentral;
    private JButton botaoMenuActive; // Para destacar o botão ativo

    private final Color corFundoPrincipal = new Color(0xFFF5E5);
    private final Color corLaranja = new Color(0xFFA500);
    private final Color corLaranjaEscuro = corLaranja.darker();

    public TelaPrincipalFuncionarioView() {
        // setup inicial
        setTitle("Área do Funcionário - Restaurante Dove");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1024, 768);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(800, 600));

        // painel fundo
        JPanel painelFundo = new JPanel(new BorderLayout());
        painelFundo.setBackground(corFundoPrincipal);
        add(painelFundo);

        // cabeçalho (sem alterações)
        JPanel painelCabecalho = new JPanel(new BorderLayout(10, 10));
        painelCabecalho.setBackground(corLaranja);
        painelCabecalho.setBorder(new EmptyBorder(10, 20, 10, 20));
        JLabel lblBemVindo = new JLabel("Restaurante Dove - Bem-vindo, Funcionário!");
        lblBemVindo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblBemVindo.setForeground(Color.WHITE);
        JButton btnLogout = new JButton("Logout");
        btnLogout.setBackground(Color.WHITE);
        btnLogout.setForeground(corLaranja);
        btnLogout.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnLogout.setFocusPainted(false);
        btnLogout.addActionListener(e -> {
            dispose();
            new LoginView();
        });
        painelCabecalho.add(lblBemVindo, BorderLayout.WEST);
        painelCabecalho.add(btnLogout, BorderLayout.EAST);
        painelFundo.add(painelCabecalho, BorderLayout.NORTH);

        // menu lateral
        JPanel painelMenu = new JPanel();
        painelMenu.setLayout(new BoxLayout(painelMenu, BoxLayout.Y_AXIS));
        painelMenu.setBackground(Color.WHITE);
        painelMenu.setBorder(new EmptyBorder(20, 10, 20, 10));
        painelMenu.setPreferredSize(new Dimension(280, 0));

        // painel central
        painelCentral = new JPanel(cardLayout);

        // --- PREPARANDO O PAINEL DE FUNCIONÁRIO ---
        EntityManager em = CustomizerFactory.getEntityManager();
        FuncionarioService funcionarioService = new FuncionarioService(em);
        FuncionarioController funcionarioController = new FuncionarioController(funcionarioService);
        List<FuncionarioEntity> funcionariosIniciais = funcionarioController.listarFuncionarios();
        FuncionarioPanel painelFuncionario = new FuncionarioPanel(funcionarioController, funcionariosIniciais);

        // Adicionando os painéis ao CardLayout
        painelCentral.add(painelFuncionario, "funcionario"); // Tela de funcionário agora é um painel
        painelCentral.add(new PedidoView().view(), "pedido"); // Mantendo o de pedido como estava

        // AÇÃO DO BOTÃO DE FUNCIONÁRIO CORRIGIDA
        JButton btnGerenciamento = criarBotaoMenu("Gerenciamento de Funcionário");
        btnGerenciamento.addActionListener(e -> {
            cardLayout.show(painelCentral, "funcionario");
            setBotaoMenuActive(btnGerenciamento);
        });

        // Mantendo os outros botões com o comportamento antigo
        JButton btnIngredientes = criarBotaoMenu("Gerenciar Ingredientes");
        btnIngredientes.addActionListener(e -> {
            IngredienteFrame frameIngredientes = new IngredienteFrame();
            frameIngredientes.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frameIngredientes.setVisible(true);
        });
        JButton btnCardapio = criarBotaoMenu("Cardápio");
        btnCardapio.addActionListener(e -> {
            com.dove.view.viewCardapio.CardapioFrame frameCardapio = new com.dove.view.viewCardapio.CardapioFrame();
            frameCardapio.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frameCardapio.setVisible(true);
        });
        JButton btnPedido = criarBotaoMenu("Pedido");
        btnPedido.addActionListener(e -> {
            cardLayout.show(painelCentral, "pedido");
            setBotaoMenuActive(btnPedido);
        });

        // Adicionando botões ao menu
        painelMenu.add(btnGerenciamento);
        painelMenu.add(Box.createVerticalStrut(20));
        painelMenu.add(btnIngredientes);
        painelMenu.add(Box.createVerticalStrut(20));
        painelMenu.add(btnCardapio);
        painelMenu.add(Box.createVerticalStrut(20));
        painelMenu.add(btnPedido);
        painelMenu.add(Box.createVerticalGlue());

        painelFundo.add(painelMenu, BorderLayout.WEST);
        painelFundo.add(painelCentral, BorderLayout.CENTER);

        // rodapé (sem alterações)
        JPanel painelRodape = new JPanel(new FlowLayout(FlowLayout.CENTER));
        // ... (código do rodapé) ...
        painelFundo.add(painelRodape, BorderLayout.SOUTH);

        // Inicia mostrando o dashboard
        setBotaoMenuActive(btnGerenciamento);
        setVisible(true);
    }

    // --- MÉTODOS AUXILIARES ---

    private void setBotaoMenuActive(JButton botao) {
        if (botaoMenuActive != null) {
            botaoMenuActive.setBackground(corLaranja);
        }
        botao.setBackground(corLaranjaEscuro);
        botaoMenuActive = botao;
    }

    private JButton criarBotaoMenu(String texto) {
        JButton btn = new JButton(texto);
        final Color corOriginal = corLaranja;
        final Color corHover = corLaranja.brighter();
        btn.setBackground(corOriginal);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Arial", Font.BOLD, 14));
        btn.setOpaque(true);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        btn.setAlignmentX(Component.LEFT_ALIGNMENT);
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (btn != botaoMenuActive) {
                    btn.setBackground(corHover);
                }
            }
            @Override
            public void mouseExited(MouseEvent e) {
                if (btn != botaoMenuActive) {
                    btn.setBackground(corOriginal);
                }
            }
        });
        return btn;
    }

    private JPanel createPlaceholderPanel(String nomeDaTela) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(corFundoPrincipal);
        JLabel label = new JLabel("Tela de '" + nomeDaTela + "' em construção.");
        label.setFont(new Font("Segoe UI", Font.ITALIC, 20));
        label.setForeground(Color.GRAY);
        panel.add(label);
        return panel;
    }
}