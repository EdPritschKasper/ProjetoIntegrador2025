package com.dove.service;

import com.dove.model.ClienteEntity;
import com.dove.repository.ClienteRepository;

import java.util.Scanner;


public class ClienteService {
    private final ClienteRepository clienteRepository;
    private final Scanner scanner;

    public ClienteService(Scanner scanner) {
        this.clienteRepository = new ClienteRepository();
        this.scanner = scanner;
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
            clienteRepository.salvarCliente(cliente);

            System.out.println("Cliente cadastrado com sucesso!");
        }
        catch (Exception e) {
            System.out.println("Erro ao cadastrar cliente: " + e.getMessage());
        }
    }

    public void alterarSenha() {
        try {
            System.out.print("Email: ");
            String email = scanner.nextLine();

            System.out.print("Nova senha: ");
            String novaSenha = scanner.nextLine();

            boolean alterado = clienteRepository.alterarSenha(email, novaSenha);
            if (alterado) {
                System.out.println("Senha alterada com sucesso!");
            }
            else {
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

            boolean excluido = clienteRepository.excluirCliente(email);
            if (excluido) {
                System.out.println("Cliente excluído com sucesso!");
            }
            else {
                System.out.println("Cliente não encontrado.");
            }
        } catch (Exception e) {
            System.out.println("Erro ao excluir cliente: " + e.getMessage());
        }
    }

    public void exibirClientes() {
        System.out.println("-----Lista de Clientes-----");

        var clientes = clienteRepository.exibirClientes();

        if (clientes.isEmpty()){
            System.out.println("Nenhum Cliente cadastrado.");
        }
        else {

            for (ClienteEntity cliente : clientes){
                System.out.println("--------------------");
                System.out.println("Nome: "+ cliente.getNome());
                System.out.println("Email: " + cliente.getEmail());
            }
        }
    }

    public void exibirPedidosCliente() {
        System.out.print("Digite seu email: ");
        String email = scanner.nextLine();

        ClienteEntity cliente = clienteRepository.findByEmail(email);

        if (cliente == null) {
            System.out.println("Cliente não encontrado.");
            return;
        }

        System.out.println("Pedidos do Cliente " + cliente.getEmail() + ":");
        if (cliente.getPedidos() == null || cliente.getPedidos().isEmpty()){
            System.out.println("Nenhum pedido encontrado.");
        } else {
            cliente.getPedidos().forEach(pedido -> System.out.println(pedido));
        }
    }

    public void exibirClientesComMaisPedidos(){
        var clientes = clienteRepository.getClientesComMaisPedidos();
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
