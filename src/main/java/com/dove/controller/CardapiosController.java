package com.dove.controller;

import com.dove.model.service.CardapioService;
import com.dove.view.CardapiosView;
import java.util.Scanner;

public class CardapiosController {
    private final CardapioService cardapioService;
    private final CardapiosView cardapiosView;
    private final Scanner scanner;

    public CardapiosController(Scanner scanner) {
        this.cardapioService = new CardapioService();
        this.cardapiosView = new CardapiosView(scanner);
        this.scanner = scanner;
    }

    public void executar() {
        int opcao;
        do {
            opcao = cardapiosView.exibirMenu();
            scanner.nextLine(); // Limpa buffer
            switch (opcao) {
                case 1 -> cardapioService.cadastrarCardapio();
                case 2 -> cardapioService.buscarCardapio();
                case 3 -> cardapioService.atualizarCardapio();
                case 4 -> cardapioService.deletarCardapio();
                case 5 -> cardapioService.listarCardapios();
                case 0 -> System.out.println("Voltando ao menu principal...");
                default -> System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
    }
}
