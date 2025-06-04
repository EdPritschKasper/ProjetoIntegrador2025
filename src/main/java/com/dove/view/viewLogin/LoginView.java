package com.dove.view.viewLogin;

import com.dove.view.viewCliente.ClienteView;
import com.dove.view.viewFuncionario.TelaPrincipalFuncionarioView;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.Timer;

public class LoginView extends JFrame {

    private final JPanel panelPrincipal;
    private final JPanel painelCliente;
    private final JPanel painelCadastro;
    private final JPanel painelFuncionario;
    private final JButton btnCadastro;
    private final JButton btnCliente;
    private final JButton btnFuncionario;
    private final Color laranja = Color.decode("#FFA500");
    private final Color branco = Color.WHITE;
    private final Color texto = Color.decode("#333333");

    public LoginView() {
        setTitle("Restaurante Dove - Login");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(700, 450);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout());

        JLabel titulo = new JLabel("Restaurante Dove", SwingConstants.CENTER);
        titulo.setFont(new Font("Serif", Font.BOLD, 28));
        titulo.setForeground(laranja);
        titulo.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        add(titulo, BorderLayout.NORTH);

        JPanel botoesPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        botoesPanel.setBackground(Color.decode("#FFF5E5"));
        btnCadastro = criarBotao("Cadastro", texto, branco);
        btnCliente = criarBotao("Cliente", texto, branco);
        btnFuncionario = criarBotao("Funcionário", texto, branco);
        botoesPanel.add(btnFuncionario);
        botoesPanel.add(btnCliente);
        botoesPanel.add(btnCadastro);
        add(botoesPanel, BorderLayout.SOUTH);

        panelPrincipal = new JPanel(null);
        panelPrincipal.setBackground(branco);
        add(panelPrincipal, BorderLayout.CENTER);

        painelCadastro = criarPainelCadastro();
        painelCliente = criarPainelCliente();
        painelFuncionario = criarPainelFuncionario();

        painelFuncionario.setBounds(-700, 0, 700, 350);
        painelCliente.setBounds(0, 0, 700, 350);
        painelCadastro.setBounds(700, 0, 700, 350);

        panelPrincipal.add(painelCadastro);
        panelPrincipal.add(painelCliente);
        panelPrincipal.add(painelFuncionario);

        btnCadastro.addActionListener(e -> animarParaCadastro());
        btnCliente.addActionListener(e -> animarParaCliente());
        btnFuncionario.addActionListener(e -> animarParaFuncionario());

        setVisible(true);
    }

    private JButton criarBotao(String texto, Color bg, Color fg) {
        JButton btn = new JButton(texto);
        btn.setBackground(bg);
        btn.setForeground(fg);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btn.setPreferredSize(new Dimension(140, 35));
        btn.setBorder(new LineBorder(fg));
        return btn;
    }

    private JPanel criarPainelCliente() {
        JPanel card = criarCardBase();

        JLabel lblEmail = new JLabel("Email:");
        JTextField txtEmail = new JTextField(20);

        JLabel lblSenha = new JLabel("Senha:");
        JPasswordField txtSenha = new JPasswordField(20);

        JButton btnLogin = new JButton("Entrar");
        estilizarBotaoPrimario(btnLogin);
        btnLogin.addActionListener(e -> {
            new ClienteView();
            dispose();
        });

        adicionarCamposCard(card, new JLabel[]{lblEmail, lblSenha}, new JComponent[]{txtEmail, txtSenha, btnLogin});

        return card;
    }

    private JPanel criarPainelFuncionario() {
        JPanel card = criarCardBase();

        JLabel lblCPF = new JLabel("CPF:");
        JTextField txtCPF = new JTextField(20);

        JButton btnLogin = new JButton("Entrar");
        estilizarBotaoPrimario(btnLogin);

        // --- ADICIONANDO A AÇÃO AO BOTÃO DE LOGIN ---
        btnLogin.addActionListener(e -> {
            // Validação simples (pode ser melhorada com a lógica do seu controller)
            if (!txtCPF.getText().isBlank()) {
                // Cria a nova tela de dashboard
                new TelaPrincipalFuncionarioView();
                // Fecha a tela de login
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Por favor, insira o CPF.", "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        });

        adicionarCamposCard(card, new JLabel[]{lblCPF}, new JComponent[]{txtCPF, btnLogin});

        return card;
    }

    private JPanel criarPainelCadastro() {
        JPanel card = criarCardBase();

        JLabel lblNome = new JLabel("Nome:");
        JTextField txtNome = new JTextField(20);

        JLabel lblEmail = new JLabel("Email:");
        JTextField txtEmail = new JTextField(20);

        JLabel lblSenha = new JLabel("Senha:");
        JPasswordField txtSenha = new JPasswordField(20);

        JButton btnCadastrar = new JButton("Cadastrar");
        estilizarBotaoPrimario(btnCadastrar);

        adicionarCamposCard(card, new JLabel[]{lblNome, lblEmail, lblSenha}, new JComponent[]{txtNome, txtEmail, txtSenha, btnCadastrar});

        return card;
    }

    private JPanel criarCardBase() {
        JPanel card = new JPanel(new GridBagLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(new CompoundBorder(
                new LineBorder(new Color(220, 220, 220), 1, true),
                new EmptyBorder(20, 40, 20, 40)));
        return card;
    }

    private void adicionarCamposCard(JPanel card, JLabel[] labels, JComponent[] campos) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.WEST;

        for (int i = 0; i < labels.length; i++) {
            card.add(labels[i], gbc);
            gbc.gridx = 1;
            card.add(campos[i], gbc);
            gbc.gridx = 0;
            gbc.gridy++;
        }
        if (campos.length > labels.length) {
            gbc.gridwidth = 2;
            card.add(campos[campos.length - 1], gbc);
        }
    }

    private void estilizarBotaoPrimario(JButton button) {
        button.setBackground(laranja);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(120, 35));
    }

    private void animarParaCliente() {
        animarTransicao(painelCliente, 0);
    }

    private void animarParaFuncionario() {
        animarTransicao(painelFuncionario, 0);
    }

    private void animarParaCadastro() {
        animarTransicao(painelCadastro, 0);
    }

    private void animarTransicao(JPanel target, int finalX) {
        Timer timer = new Timer(5, null);
        timer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int x = target.getX();
                if (x != finalX) {
                    int step = (x > finalX) ? -20 : 20;
                    painelCliente.setLocation(painelCliente.getX() + step, 0);
                    painelFuncionario.setLocation(painelFuncionario.getX() + step, 0);
                    painelCadastro.setLocation(painelCadastro.getX() + step, 0);
                    panelPrincipal.repaint();
                } else {
                    timer.stop();
                }
            }
        });
        timer.start();
    }


}
