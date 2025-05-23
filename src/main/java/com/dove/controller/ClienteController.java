package com.dove.controller;

import com.dove.model.service.ClienteService;
import com.dove.view.ClienteView;
import java.util.Scanner;

public class ClienteController {
    private final ClienteService clienteService;
    private final ClienteView clienteView;
    private final Scanner scanner;

    public ClienteController(Scanner scanner) {
        this.clienteService = new ClienteService(scanner);
        this.clienteView = new ClienteView(scanner);
        this.scanner = scanner;
    }

    public void executar() {
        int opcao;
        do {
            opcao = clienteView.exibirMenu();
            scanner.nextLine(); // Limpa buffer
            switch (opcao) {
                case 1 -> cadastrarCliente();
                case 2 -> alterarSenha();
                case 3 -> excluirCliente();
                case 4 -> exibirClientes();
                case 5 -> exibirPedidosCliente();
                case 6 -> exibirClientesComMaisPedidos();
                case 0 -> System.out.println("Encerrando o sistema...");
                default -> System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }

    private void cadastrarCliente() {
        clienteService.cadastrarCliente();
    }

    private void alterarSenha() {
        clienteService.alterarSenha();
    }

    private void excluirCliente() {
        clienteService.excluirCliente();
    }

    private void exibirClientes() {
        clienteService.exibirClientes();
    }

    private void exibirPedidosCliente() {
        clienteService.exibirPedidosCliente();
    }

    private void exibirClientesComMaisPedidos() {
        clienteService.exibirClientesComMaisPedidos();
    }
}
