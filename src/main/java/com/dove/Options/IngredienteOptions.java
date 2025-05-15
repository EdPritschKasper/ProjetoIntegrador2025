package com.dove.Options;

import com.dove.Service.IngredienteService;

import java.util.Scanner;

public class IngredienteOptions {

    private final Scanner scanner;
    private final IngredienteService service;

    public IngredienteOptions(Scanner scanner) {
        this.scanner = scanner;
        this.service = new IngredienteService(scanner);
    }

    public void caseEntidade() {
        int controle = 0;

        do {
            System.out.println("------------------------------");
            System.out.println("OPÇÕES DE CRUD DO INGREDIENTE");
            System.out.println("Digite a opção desejada:");
            System.out.println("1 - Cadastrar Ingrediente:");
            System.out.println("2 - Ler Ingrediente por ID:");
            System.out.println("3 - Atualizar Ingrediente:");
            System.out.println("4 - Deletar Ingrediente:");
            System.out.println("5 - Listar Todos Ingredientes");
            System.out.println("6 - Ingrediente Mais Selecionado");
            System.out.println("0 - Sair das Opções de Ingrediente:");
            System.out.println("------------------------------");

            controle = scanner.nextInt();
            scanner.nextLine();

            switch (controle) {
                case 1 -> service.cadastrarIngrediente();
                case 2 -> service.lerIngrediente();
                case 3 -> service.atualizarIngrediente();
                case 4 -> service.deletarIngrediente();
                case 5 -> service.listarTodosIngredientes();
                case 6 -> service.pegarIngredienteMaisSelecionado();
                case 0 -> System.out.println("Saindo de Ingredientes...");
                default -> System.out.println("Opção inválida.");
            }
        } while (controle != 0);
    }
}
