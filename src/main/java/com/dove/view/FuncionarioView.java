package com.dove.view;

import java.util.Scanner;

public class FuncionarioView {
    private final Scanner scanner;

    public FuncionarioView(Scanner scanner) {
        this.scanner = scanner;
    }

    public int exibirMenu() {
        System.out.println("------------------------------");
        System.out.println("OPÇÕES DE CRUD DO FUNCIONÁRIO");
        System.out.println("1 - Cadastrar Funcionário");
        System.out.println("2 - Listar Funcionários");
        System.out.println("3 - Atualizar Funcionário");
        System.out.println("4 - Remover Funcionário");
        System.out.println("5 - Relatório de Pedidos");
        System.out.println("0 - Sair");
        System.out.println("------------------------------");
        return scanner.nextInt();
    }
}

