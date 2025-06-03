package com.dove.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
        setTitle("Login - Restaurante Dove");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setSize(600, 400);
        setLocationRelativeTo(null);
        //setResizable(false);

        setLayout(new BorderLayout());

        JPanel botoesPanel = new JPanel();
        botoesPanel.setBackground(laranja);
        btnCadastro = criarBotao("Cadastro", branco, texto);
        btnCliente = criarBotao("Cliente", branco, texto);
        btnFuncionario = criarBotao("Funcionário", branco, texto);
        botoesPanel.add(btnFuncionario);
        botoesPanel.add(btnCliente);
        botoesPanel.add(btnCadastro);
        add(botoesPanel, BorderLayout.NORTH);

        panelPrincipal = new JPanel(null);
        panelPrincipal.setBackground(branco);
        add(panelPrincipal, BorderLayout.CENTER);

        painelCadastro = criarPainelCadastro();
        painelCliente = criarPainelCliente();
        painelFuncionario = criarPainelFuncionario();

        painelFuncionario.setBounds(-600, 0, 600, 300);
        painelCliente.setBounds(0, 0, 600, 300);
        painelCadastro.setBounds(600, 0, 600, 300);

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
        btn.setFont(new Font("Arial", Font.BOLD, 16));
        return btn;
    }

    private JPanel criarPainelCadastro() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(branco);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel lblNome = new JLabel("Nome: ");
        lblNome.setForeground(texto);
        JTextField txtNome = new JTextField(20);

        JLabel lblEmail = new JLabel("Email: ");
        lblEmail.setForeground(texto);
        JTextField txtEmail = new JTextField(20);

        JLabel lblSenha = new JLabel("Senha: ");
        lblSenha.setForeground(texto);
        JPasswordField txtSenha = new JPasswordField(20);

        JButton btnCadastrar = new JButton("Cadastrar");
        btnCadastrar.setBackground(laranja);
        btnCadastrar.setForeground(Color.WHITE);
        btnCadastrar.setFont(new Font("Arial", Font.BOLD, 14));

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(lblNome, gbc);
        gbc.gridx = 1;
        panel.add(txtNome, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(lblEmail, gbc);
        gbc.gridx = 1;
        panel.add(txtEmail, gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(lblSenha, gbc);
        gbc.gridx = 1;
        panel.add(txtSenha, gbc);
        gbc.gridx = 1;
        gbc.gridy = 3;
        panel.add(btnCadastrar, gbc);

        return panel;
    }

    private JPanel criarPainelCliente() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(branco);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setForeground(texto);
        JTextField txtEmail = new JTextField(20);

        JLabel lblSenha = new JLabel("Senha:");
        lblSenha.setForeground(texto);
        JPasswordField txtSenha = new JPasswordField(20);

        JButton btnLogin = new JButton("Entrar");
        btnLogin.setBackground(laranja);
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFont(new Font("Arial", Font.BOLD, 14));

        btnLogin.addActionListener(e -> {
            String email = txtEmail.getText();
            String senha = new String(txtSenha.getPassword());
            JOptionPane.showMessageDialog(this, "Cliente logado com: " + email);
        });

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(lblEmail, gbc);
        gbc.gridx = 1;
        panel.add(txtEmail, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(lblSenha, gbc);
        gbc.gridx = 1;
        panel.add(txtSenha, gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(btnLogin, gbc);

        return panel;
    }

    private JPanel criarPainelFuncionario() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(branco);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel lblCPF = new JLabel("CPF:");
        lblCPF.setForeground(texto);
        JTextField txtCPF = new JTextField(20);

        JButton btnLogin = new JButton("Entrar");
        btnLogin.setBackground(laranja);
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFont(new Font("Arial", Font.BOLD, 14));

        btnLogin.addActionListener(e -> {
            String cpf = txtCPF.getText();
            JOptionPane.showMessageDialog(this, "Funcionário logado com CPF: " + cpf);
        });

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(lblCPF, gbc);
        gbc.gridx = 1;
        panel.add(txtCPF, gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(btnLogin, gbc);

        return panel;
    }

    private void animarParaCliente() {
        Timer timer = new Timer(5, null);
        timer.addActionListener(new ActionListener() {
            int target = 0;
            public void actionPerformed(ActionEvent e) {
                int xCliente = painelCliente.getX();
                if (xCliente != target) {
                    int step = (xCliente > target) ? -20 : 20;
                    painelCliente.setLocation(xCliente + step, 0);
                    painelFuncionario.setLocation(xCliente + step - 600, 0);
                    painelCadastro.setLocation(xCliente + step + 600, 0);
                    panelPrincipal.repaint();
                } else {
                    timer.stop();
                }
            }
        });
        timer.start();
    }

    private void animarParaFuncionario() {
        Timer timer = new Timer(5, null);
        timer.addActionListener(new ActionListener() {
            int target = 0;
            public void actionPerformed(ActionEvent e) {
                int xFuncionario = painelFuncionario.getX();
                if (xFuncionario != target) {
                    int step = (xFuncionario > target) ? -20 : 20;
                    painelFuncionario.setLocation(xFuncionario + step, 0);
                    painelCliente.setLocation(xFuncionario + step + 600, 0);
                    painelCadastro.setLocation(xFuncionario + step + 1200, 0);
                    panelPrincipal.repaint();
                } else {
                    timer.stop();
                }
            }
        });
        timer.start();
    }

    private void animarParaCadastro() {
        Timer timer = new Timer(5, null);
        timer.addActionListener(new ActionListener() {
            int target = 0;
            public void actionPerformed(ActionEvent e) {
                int xCadastro = painelCadastro.getX();
                if (xCadastro != target) {
                    int step = (xCadastro > target) ? -20 : 20;
                    painelCadastro.setLocation(xCadastro + step, 0);
                    painelCliente.setLocation(xCadastro + step - 600, 0);
                    painelFuncionario.setLocation(xCadastro + step - 1200, 0);
                    panelPrincipal.repaint();
                } else {
                    timer.stop();
                }
            }
        });
        timer.start();
    }
}