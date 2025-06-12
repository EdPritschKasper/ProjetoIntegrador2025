// imports
package com.dove.view.viewFuncionario;

import com.dove.controller.PedidoController;
import com.dove.model.entities.FuncionarioEntity;
import com.dove.view.viewFuncionario.GerenciamentoFuncionario.FuncionarioFrame;
import com.dove.view.viewIngrediente.IngredienteFrame;
import com.dove.view.viewLogin.LoginView;
import com.dove.view.viewPedido.PedidoView;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TelaPrincipalFuncionarioView extends JFrame {

    private final Color corFundoPrincipal = new Color(0xFFF5E5);
    private final Color corLaranja = new Color(0xFFA500);
    private final Color corTextoCabecalho = new Color(0x333333);

    public TelaPrincipalFuncionarioView(FuncionarioEntity funcionario) {
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

        // cabeçalho
        JPanel painelCabecalho = new JPanel(new BorderLayout(10, 10));
        painelCabecalho.setBackground(corLaranja);
        painelCabecalho.setBorder(new EmptyBorder(10, 20, 10, 20));

        JLabel lblBemVindo = new JLabel("Restaurante Dove - Bem-vindo, Funcionário!");
        lblBemVindo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblBemVindo.setForeground(corFundoPrincipal);

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
        CardLayout cardLayout = new CardLayout();
        JPanel painelCentral = new JPanel(cardLayout);

        // botão Gerenciamento de Funcionário
        JButton btnGerenciamento = criarBotaoMenu("Gerenciamento de Funcionário");
        btnGerenciamento.addActionListener(e -> {
            FuncionarioFrame frameGerenciamento = new FuncionarioFrame();
            frameGerenciamento.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frameGerenciamento.setVisible(true);
            cardLayout.show(painelCentral, "funcionario");
        });

        // botão Gerenciar Ingredientes
        JButton btnIngredientes = criarBotaoMenu("Gerenciar Ingredientes");
        btnIngredientes.addActionListener(e -> {
            IngredienteFrame frameIngredientes = new IngredienteFrame();
            frameIngredientes.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frameIngredientes.setVisible(true);
            cardLayout.show(painelCentral, "ingredientes");
        });

        // botão Cardápio
        JButton btnCardapio = criarBotaoMenu("Cardápio");
        btnCardapio.addActionListener(e -> {
            com.dove.view.viewCardapio.CardapioFrame frameCardapio = new com.dove.view.viewCardapio.CardapioFrame();
            frameCardapio.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frameCardapio.setVisible(true);
        });

        // botão Pedido
        JButton btnPedido = criarBotaoMenu("Pedido");
        btnPedido.addActionListener(e -> cardLayout.show(painelCentral, "pedido"));

        // adicionar botões ao menu
        painelMenu.add(Box.createVerticalStrut(10));
        painelMenu.add(btnGerenciamento);
        painelMenu.add(Box.createVerticalStrut(20));
        painelMenu.add(btnIngredientes);
        painelMenu.add(Box.createVerticalStrut(20));
        painelMenu.add(btnCardapio);
        painelMenu.add(Box.createVerticalStrut(20));
        painelMenu.add(btnPedido);
        painelMenu.add(Box.createVerticalGlue());

        painelFundo.add(painelMenu, BorderLayout.WEST);

        // painel central
        painelCentral.add(new JLabel(), "funcionario");
        painelCentral.add(new PedidoView().view(new PedidoController()), "pedido");
        painelCentral.setBackground(corFundoPrincipal);
        painelFundo.add(painelCentral, BorderLayout.CENTER);

        // rodapé
        JPanel painelRodape = new JPanel(new FlowLayout(FlowLayout.CENTER));
        painelRodape.setBackground(new Color(51, 51, 51));
        JLabel lblRodape = new JLabel("© 2025 Restaurante Dove. Todos os direitos reservados.");
        lblRodape.setForeground(Color.WHITE);
        lblRodape.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        painelRodape.add(lblRodape);
        painelFundo.add(painelRodape, BorderLayout.SOUTH);

        setVisible(true);
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
