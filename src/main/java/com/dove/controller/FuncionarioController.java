package com.dove.controller;

import com.dove.repository.CustomizerFactory;
import com.dove.service.FuncionarioService;
import com.dove.view.FuncionarioView;
import jakarta.persistence.EntityManager;
import java.util.Scanner;

public class FuncionarioController {
    private final FuncionarioService funcionarioService;
    private final FuncionarioView funcionarioView;
    private final Scanner scanner;

    public FuncionarioController(Scanner scanner) {
        EntityManager em = CustomizerFactory.getEntityManager();
        this.funcionarioService = new FuncionarioService(em);
        this.funcionarioView = new FuncionarioView(scanner);
        this.scanner = scanner;
    }

    public void executar() {
        int opcao;
        do {
            opcao = funcionarioView.exibirMenu();
            scanner.nextLine(); // Limpa buffer
            switch (opcao) {
                case 1 -> cadastrarFuncionario();
                case 2 -> funcionarioService.listarFuncionarios();
                case 3 -> funcionarioService.atualizarFuncionarioeCpf(scanner);
                case 4 -> removerFuncionario();
                case 5 -> relatorioPedidos();
                case 0 -> System.out.println("Saindo do CRUD de Funcionários.");
                default -> System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }

    private void cadastrarFuncionario() {
        System.out.print("Digite o nome do funcionário: ");
        String nome = scanner.nextLine();
        System.out.print("Digite o CPF do funcionário: ");
        String cpf = scanner.nextLine();
        funcionarioService.cadastrarFuncionario(nome, cpf);
    }

    private void removerFuncionario() {
        System.out.print("Digite o ID do funcionário a ser removido: ");
        Long id = scanner.nextLong();
        scanner.nextLine();
        funcionarioService.removerFuncionario(id);
    }

    private void relatorioPedidos() {
        System.out.print("Digite o ID do funcionário para gerar o relatório: ");
        Long id = scanner.nextLong();
        scanner.nextLine();
        funcionarioService.relatorioPedidos(id);
    }
}
