package com.dove.view.options;

import java.util.Scanner;
import com.dove.view.CardapioView;

public class CardapioOptions {
    private final Scanner scanner;
    private final CardapioView view;

    public CardapioOptions(Scanner scanner) {
        this.scanner = scanner;
        this.view = new CardapioView(scanner);
    }

    public void caseEntidade() {
        int opcao;
        do {
            System.out.println("------------------------------");
            System.out.println("OPÇÕES DE CRUD DO CARDÁPIO");
            System.out.println("1 - Cadastrar Cardápio");
            System.out.println("2 - Ler Cardápio por ID");
            System.out.println("3 - Atualizar Cardápio");
            System.out.println("4 - Deletar Cardápio");
            System.out.println("5 - Listar Cardápios");
            System.out.println("0 - Sair");
            System.out.println("------------------------------");
            opcao = scanner.nextInt();
            scanner.nextLine();
            switch (opcao) {
                case 1 -> view.adicionarCardapio();
                case 2 -> view.buscarCardapio();
                case 3 -> view.atualizarCardapio();
                case 4 -> view.deletarCardapio();
                case 5 -> view.listarCardapios();
                case 0 -> System.out.println("Saindo de Cardápio...");
                default -> System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }
}