package com.dove.Options;

import com.dove.repository.CustomizerFactory;
import jakarta.persistence.EntityManager;
import com.dove.Service.FuncionarioService;

import java.util.Scanner;

public class FuncionarioOptions {
    private final FuncionarioService funcionarioService;
    private final Scanner scanner;

    public FuncionarioOptions() {
        EntityManager em = CustomizerFactory.getEntityManager();
        this.funcionarioService = new FuncionarioService(em);
        this.scanner = new Scanner(System.in);
    }


    public void caseEntidades() {
        int opcao;

        do {
            System.out.println("------------------------------");
            System.out.println("OPÇÕES DE CRUD DO FUNCIONÁRIO");
            System.out.println("1 - Cadastrar Funcionário");
            System.out.println("2 - Listar Funcionários");
            System.out.println("3 - Atualizar Funcionário");
            System.out.println("4 - Remover Funcionário");
            System.out.println("5 - Relatório de Pedidos");
            System.out.println("0 - Sair");
            System.out.println("------------------------------");

            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1 -> cadastrarFuncionario();
                case 2 -> listarFuncionarios();
                case 3 -> atualizarFuncionarios(scanner);
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

    private void listarFuncionarios() {
        funcionarioService.listarFuncionarios();
    }

    private void atualizarFuncionarios(Scanner scanner) {
        funcionarioService.atualizarFuncionarioeCpf(this.scanner);
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

