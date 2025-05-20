package com.dove.view;

import java.util.Scanner;

public class CardapiosView {
    private final Scanner scanner;

    public CardapiosView(Scanner scanner) {
        this.scanner = scanner;
    }

    public int exibirMenu() {
        System.out.println("\n=== MENU CARDÁPIO ===");
        System.out.println("1 - Cadastrar novo cardápio");
        System.out.println("2 - Buscar cardápio por ID");
        System.out.println("3 - Atualizar cardápio");
        System.out.println("4 - Deletar cardápio");
        System.out.println("5 - Listar todos os cardápios");
        System.out.println("0 - Voltar ao menu principal");
        System.out.print("Escolha uma opção: ");
        return scanner.nextInt();
    }
}
