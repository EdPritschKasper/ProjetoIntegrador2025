package com.dove.view;

import java.util.Scanner;

public class IngredienteView {
    private final Scanner scanner;

    public IngredienteView(Scanner scanner) {
        this.scanner = scanner;
    }

    public int exibirMenu() {
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
        return scanner.nextInt();
    }
}
