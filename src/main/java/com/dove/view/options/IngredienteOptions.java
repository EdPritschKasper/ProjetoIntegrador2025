package com.dove.view.options;

import com.dove.view.IngredienteView;

import java.util.Scanner;

public class IngredienteOptions {

    private final Scanner scanner;
    private final IngredienteView ingredienteView;

    public IngredienteOptions(Scanner scanner) {
        this.scanner = scanner;
        this.ingredienteView = new IngredienteView(scanner);
    }

    public void casaEntidade() {
        int controle = -1;

        do {
            System.out.println("------------------------------");
            System.out.println("OPÇÕES DE CRUD DO INGREDIENTE");
            System.out.println("Digite a opção desejada:");
            System.out.println("1 - Listar Ingredientes");
            System.out.println("2 - Inserir Ingrediente");
            System.out.println("3 - Atualizar Ingrediente");
            System.out.println("4 - Deletar Ingrediente");
            System.out.println("0 - Voltar ao menu principal");
            System.out.println("------------------------------");

            controle = scanner.nextInt();
            scanner.nextLine(); // Consome o \n após o número

            switch (controle) {
                case 1 -> ingredienteView.listarIngredientes();
                case 2 -> ingredienteView.inserirIngrediente();
                case 3 -> ingredienteView.atualizarIngrediente();
                case 4 -> ingredienteView.deletarIngrediente();
                case 0 -> System.out.println("Saindo das opções de Ingrediente...");
                default -> System.out.println("Opção inválida! Tente novamente.");
            }

        } while (controle != 0);
    }
}
