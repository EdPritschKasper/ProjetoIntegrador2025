package com.dove.view;

import com.dove.model.entities.ClienteEntity;
import com.dove.controller.ClienteController;

import java.util.Scanner;

public class ClienteView {
    private final Scanner scanner;
    private final ClienteController clienteController;

    public ClienteView(Scanner scanner) {
        this.scanner = scanner;
        this.clienteController = new ClienteController(scanner);
    }

    public void cadastrarCliente() {
        try {
            System.out.print("Nome: ");
            String nome = scanner.nextLine();

            System.out.print("Email: ");
            String email = scanner.nextLine();

            System.out.print("Senha: ");
            String senha = scanner.nextLine();

            ClienteEntity cliente = new ClienteEntity(nome, email, senha);
            clienteController.salvarCliente(cliente);

            System.out.println("Cliente cadastrado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar cliente: " + e.getMessage());
        }
    }

    public void alterarSenha() {
        try {
            System.out.print("Email: ");
            String email = scanner.nextLine();

            System.out.print("Nova senha: ");
            String novaSenha = scanner.nextLine();

            boolean alterado = clienteController.alterarSenha(email, novaSenha);
            if (alterado) {
                System.out.println("Senha alterada com sucesso!");
            } else {
                System.out.println("Cliente não encontrado.");
            }
        } catch (Exception e) {
            System.out.println("Erro ao alterar senha: " + e.getMessage());
        }
    }

    public void excluirCliente() {
        try {
            System.out.print("Email do cliente a excluir: ");
            String email = scanner.nextLine();

            boolean excluido = clienteController.excluirCliente(email);
            if (excluido) {
                System.out.println("Cliente excluído com sucesso!");
            } else {
                System.out.println("Cliente não encontrado.");
            }
        } catch (Exception e) {
            System.out.println("Erro ao excluir cliente: " + e.getMessage());
        }
    }

    public void exibirClientes() {
        System.out.println("-----Lista de Clientes-----");
        var clientes = clienteController.exibirClientes();

        if (clientes.isEmpty()) {
            System.out.println("Nenhum Cliente cadastrado.");
        } else {
            for (ClienteEntity cliente : clientes) {
                System.out.println("--------------------");
                System.out.println("Nome: " + cliente.getNome());
                System.out.println("Email: " + cliente.getEmail());
            }
        }
    }

    public void exibirPedidosCliente() {
        System.out.print("Digite seu email: ");
        String email = scanner.nextLine();

        ClienteEntity cliente = clienteController.findByEmail(email);

        if (cliente == null) {
            System.out.println("Cliente não encontrado.");
            return;
        }

        System.out.println("Pedidos do Cliente " + cliente.getEmail() + ":");
        if (cliente.getPedidos() == null || cliente.getPedidos().isEmpty()) {
            System.out.println("Nenhum pedido encontrado.");
        } else {
            cliente.getPedidos().forEach(System.out::println);
        }
    }

    public void exibirClientesComMaisPedidos() {
        var clientes = clienteController.getClientesComMaisPedidos();
        if (clientes == null || clientes.isEmpty()) {
            System.out.println("Nenhum cliente encontrado.");
            return;
        }
        System.out.println("Clientes com mais pedidos:");
        for (ClienteEntity cliente : clientes) {
            System.out.println("--------------------");
            System.out.println("Nome: " + cliente.getNome());
            System.out.println("Email: " + cliente.getEmail());
            System.out.println("Quantidade de pedidos: " + cliente.getPedidos().size());
        }
    }
}