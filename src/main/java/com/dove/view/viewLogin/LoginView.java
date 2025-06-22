package com.dove.view.viewLogin;

import com.dove.controller.ClienteController;
import com.dove.controller.FuncionarioController;
import com.dove.model.entities.ClienteEntity;
import com.dove.model.entities.FuncionarioEntity;
import com.dove.model.repository.CustomizerFactory;
import com.dove.model.service.FuncionarioService;
import com.dove.view.viewCliente.ClienteView;
import com.dove.view.viewFuncionario.TelaPrincipalFuncionarioView;

import jakarta.persistence.EntityManager;

import java.util.List;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.Timer;
import javax.swing.text.MaskFormatter;

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
            String email = txtEmail.getText();
            String senha = new String(txtSenha.getPassword());

            if (email.isBlank() || senha.isBlank()) {
                JOptionPane.showMessageDialog(this, "Preencha todos os campos!",
                        "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            ClienteController controller = new ClienteController();
            ClienteEntity cliente = controller.autenticar(email, senha);

            if (cliente != null) {
                new ClienteView(cliente);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Email ou senha inválidos.",
                        "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        adicionarCamposCard(card, new JLabel[]{lblEmail, lblSenha}, new JComponent[]{txtEmail, txtSenha, btnLogin});
        return card;
    }

    private JPanel criarPainelFuncionario() {
        JPanel card = criarCardBase();

        JLabel lblCPF = new JLabel("CPF:");
        JFormattedTextField txtCPF = criarCampoCpf();

        JButton btnLogin = new JButton("Entrar");
        estilizarBotaoPrimario(btnLogin);

        btnLogin.addActionListener(e -> {
            String cpf = txtCPF.getText().replaceAll("[^0-9]", "");

            if (cpf.isBlank()) {
                JOptionPane.showMessageDialog(this, "Por favor, insira o CPF.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            EntityManager em = CustomizerFactory.getEntityManager();
            FuncionarioService funcionarioService = new FuncionarioService(em);
            FuncionarioController funcionarioController = new FuncionarioController(funcionarioService);

            List<FuncionarioEntity> funcionarios = funcionarioController.listarFuncionarios();
            boolean encontrado = false;

            for (FuncionarioEntity funcionario : funcionarios) {
                if (funcionario.getCpf().equals(cpf)) {
                    new TelaPrincipalFuncionarioView(funcionario);
                    dispose();
                    encontrado = true;
                    break;
                }
            }

            if (!encontrado) {
                JOptionPane.showMessageDialog(this, "CPF não encontrado.");
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

        btnCadastrar.addActionListener(e -> {
            String nome = txtNome.getText();
            String email = txtEmail.getText();
            String senha = new String(txtSenha.getPassword());

            if (nome.isBlank() || email.isBlank() || senha.isBlank()) {
                JOptionPane.showMessageDialog(this, "Preencha todos os campos!",
                        "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            }

            ClienteController controller = new ClienteController();
            List<ClienteEntity> clientes = controller.exibirClientes();

            for (ClienteEntity cliente : clientes) {
                if (cliente.getEmail().equalsIgnoreCase(email)) {
                    JOptionPane.showMessageDialog(this, "Email já cadastrado!", "Erro", JOptionPane.WARNING_MESSAGE);
                    return;
                }
            }

            controller.salvarCliente(nome, email, senha);
            JOptionPane.showMessageDialog(this, "Cliente cadastrado com sucesso!");
            new ClienteView(controller.findByEmail(email));
            dispose();
        });

        adicionarCamposCard(card, new JLabel[]{lblNome, lblEmail, lblSenha}, new JComponent[]{txtNome, txtEmail, txtSenha, btnCadastrar});
        return card;
    }

    private JFormattedTextField criarCampoCpf() {
        try {
            MaskFormatter mf = new MaskFormatter("###.###.###-##");
            mf.setPlaceholderCharacter('_');
            return new JFormattedTextField(mf);
        } catch (Exception e) {
            return new JFormattedTextField();
        }
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
        timer.addActionListener(e -> {
            int step = 20;
            boolean done = true;

            for (JPanel panel : new JPanel[]{painelCadastro, painelCliente, painelFuncionario}) {
                int x = panel.getX();
                if (x != finalX) {
                    int delta = (x < finalX) ? step : -step;
                    panel.setLocation(x + delta, 0);
                    done = false;
                }
            }

            if (done) ((Timer) e.getSource()).stop();
            panelPrincipal.repaint();
        });
        timer.start();
    }
}
