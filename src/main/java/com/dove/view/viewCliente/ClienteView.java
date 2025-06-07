package com.dove.view.viewCliente;

import javax.swing.*;
import java.awt.*;

import com.dove.view.viewLogin.LoginView;
import com.dove.view.viewPedido.PedidoView;

public class ClienteView extends JFrame {

    private final JButton btnAlterarSenha;
    private final JButton btnExibirPedidos;
    private final JButton btnExcluirConta;
    private final JButton btnPedido;
    private final JButton btnPedidoCliente;
    private final JPanel panelPrincipal;
    private final CardLayout cardLayout;
    private final Color laranja = Color.decode("#FFA500");
    private final Color branco = Color.WHITE;
    private final Color texto = Color.decode("#333333");
    private final Color rodape = Color.decode("#EEEEEE");

    public ClienteView() {
        setTitle("Área do Cliente - Restaurante Dove");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1024, 768);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(800, 600));
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout());

        // Cabeçalho
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(laranja);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JLabel lblTitulo = new JLabel("Restaurante Dove - Bem-vindo, Cliente!");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setForeground(Color.WHITE);
        headerPanel.add(lblTitulo, BorderLayout.WEST);

        JButton btnLogout = new JButton("Logout");
        btnLogout.setBackground(Color.WHITE);
        btnLogout.setForeground(laranja);
        headerPanel.add(btnLogout, BorderLayout.EAST);

        btnLogout.addActionListener(e -> {
            dispose();
            new LoginView();
        });

        add(headerPanel, BorderLayout.NORTH);

        // Menu lateral
        JPanel botoesPanel = new JPanel();
        botoesPanel.setBackground(branco);
        botoesPanel.setLayout(new BoxLayout(botoesPanel, BoxLayout.Y_AXIS));
        botoesPanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));

        btnPedido = criarBotao("Fazer Pedido", laranja, Color.WHITE);
        btnAlterarSenha = criarBotao("Alterar Senha", laranja, Color.WHITE);
        btnExcluirConta = criarBotao("Excluir Conta", laranja, Color.WHITE);
        btnExibirPedidos = criarBotao("Exibir Pedidos", laranja, Color.WHITE);
        btnPedidoCliente = criarBotao("Pedido", laranja, Color.WHITE);

//        botoesPanel.add(btnPedido);
//        botoesPanel.add(Box.createVerticalStrut(20));
        botoesPanel.add(btnAlterarSenha);
        botoesPanel.add(Box.createVerticalStrut(20));
        botoesPanel.add(btnExcluirConta);
        botoesPanel.add(Box.createVerticalStrut(20));
//        botoesPanel.add(btnExibirPedidos);
        botoesPanel.add(btnPedidoCliente);

        add(botoesPanel, BorderLayout.WEST);

        // Área principal
        cardLayout = new CardLayout();
        panelPrincipal = new JPanel(cardLayout);
        panelPrincipal.setBackground(Color.decode("#FFF5E5"));

        panelPrincipal.add(criarPainelAlterarSenha(), "alterarSenha");
        panelPrincipal.add(criarPainelExcluirConta(), "excluirConta");
        panelPrincipal.add(criarPainelPedido(), "fazerPedido");
        panelPrincipal.add(criarPainelExibirPedido(), "exibirPedidos");
        panelPrincipal.add(new PedidoView().view(), "pedidoCliente");

        add(panelPrincipal, BorderLayout.CENTER);

        // Rodapé
        JPanel rodapePanel = new JPanel();
        rodapePanel.setBackground(rodape);
        JLabel rodapeTexto = new JLabel("© 2025 Restaurante Dove. Todos os direitos reservados.");
        rodapeTexto.setForeground(texto);
        rodapePanel.add(rodapeTexto);

        add(rodapePanel, BorderLayout.SOUTH);

        // Ações dos botões
        btnAlterarSenha.addActionListener(e -> cardLayout.show(panelPrincipal, "alterarSenha"));
        btnExcluirConta.addActionListener(e -> cardLayout.show(panelPrincipal, "excluirConta"));
        btnPedido.addActionListener(e -> cardLayout.show(panelPrincipal, "fazerPedido"));
        btnExibirPedidos.addActionListener(e -> cardLayout.show(panelPrincipal, "exibirPedidos"));
        btnPedidoCliente.addActionListener(e -> cardLayout.show(panelPrincipal, "pedidoCliente"));

        setVisible(true);
    }

    private JButton criarBotao(String texto, Color bg, Color fg) {
        JButton btn = new JButton(texto);
        btn.setBackground(bg);
        btn.setForeground(fg);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Arial", Font.BOLD, 14));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setMaximumSize(new Dimension(160, 40));
        return btn;
    }

    private JPanel criarPainelAlterarSenha() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.decode("#FFF5E5"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel lblTitulo = new JLabel("Alterar Senha");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));

        JLabel lblEmail = new JLabel("Email:");
        JTextField txtEmail = new JTextField(20);

        JLabel lblSenhaAntiga = new JLabel("Senha Antiga:");
        JTextField txtSenhaAntiga = new JTextField(20);

        JLabel lblSenhaNova = new JLabel("Senha Nova:");
        JTextField txtSenhaNova = new JTextField(20);

        JButton btnSalvar = new JButton("Salvar Nova Senha");
        btnSalvar.setBackground(laranja);
        btnSalvar.setForeground(Color.WHITE);

        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2; panel.add(lblTitulo, gbc);
        gbc.gridwidth = 1;
        gbc.gridy = 1; panel.add(lblEmail, gbc);
        gbc.gridx = 1; panel.add(txtEmail, gbc);
        gbc.gridx = 0; gbc.gridy = 2; panel.add(lblSenhaAntiga, gbc);
        gbc.gridx = 1; panel.add(txtSenhaAntiga, gbc);
        gbc.gridx = 0; gbc.gridy = 3; panel.add(lblSenhaNova, gbc);
        gbc.gridx = 1; panel.add(txtSenhaNova, gbc);
        gbc.gridx = 1; gbc.gridy = 4; panel.add(btnSalvar, gbc);

        return panel;
    }

    private JPanel criarPainelExcluirConta() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.decode("#FFF5E5"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel lblTitulo = new JLabel("Excluir Conta");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));

        JLabel lblEmail = new JLabel("Email:");
        JTextField txtEmail = new JTextField(20);

        JLabel lblSenha = new JLabel("Senha:");
        JTextField txtSenha = new JTextField(20);

        JButton btnExcluir = new JButton("Excluir Conta");
        btnExcluir.setBackground(laranja);
        btnExcluir.setForeground(Color.WHITE);

        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2; panel.add(lblTitulo, gbc);
        gbc.gridwidth = 1;
        gbc.gridy = 1; panel.add(lblEmail, gbc);
        gbc.gridx = 1; panel.add(txtEmail, gbc);
        gbc.gridx = 0; gbc.gridy = 2; panel.add(lblSenha, gbc);
        gbc.gridx = 1; panel.add(txtSenha, gbc);
        gbc.gridx = 1; gbc.gridy = 3; panel.add(btnExcluir, gbc);

        return panel;
    }

    private JPanel criarPainelPedido() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.decode("#FFF5E5"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel lblTitulo = new JLabel("Fazer Pedido");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));

        JLabel lblIngredientes = new JLabel("Ingredientes:");
        JTextField txtIngredientes = new JTextField(20);

        JButton btnFazerPedido = new JButton("Fazer Pedido");
        btnFazerPedido.setBackground(laranja);
        btnFazerPedido.setForeground(Color.WHITE);

        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2; panel.add(lblTitulo, gbc);
        gbc.gridwidth = 1;
        gbc.gridy = 1; panel.add(lblIngredientes, gbc);
        gbc.gridx = 1; panel.add(txtIngredientes, gbc);
        gbc.gridx = 1; gbc.gridy = 2; panel.add(btnFazerPedido, gbc);

        return panel;
    }

    private JPanel criarPainelExibirPedido() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.decode("#FFF5E5"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel lblTitulo = new JLabel("Seus Pedidos");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));

        JTextArea txtPedidos = new JTextArea(10, 30);
        txtPedidos.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(txtPedidos);

        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2; panel.add(lblTitulo, gbc);
        gbc.gridy = 1; panel.add(scrollPane, gbc);

        return panel;
    }

}
