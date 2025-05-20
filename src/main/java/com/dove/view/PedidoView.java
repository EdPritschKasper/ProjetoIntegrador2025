package com.dove.view;

import java.util.Scanner;

public class PedidoView {
    private final Scanner scanner;

    public PedidoView(Scanner scanner) {
        this.scanner = scanner;
    }

    public int exibirMenu() {
        System.out.println("------------------------------");
        System.out.println("OPÇÕES DE CRUD DO PEDIDO");
        System.out.println("Digite a opção desejada:");
        System.out.println("1 - Cadastrar Pedido:");
        System.out.println("2 - Ler Pedido Por ID:");
        System.out.println("3 - Definir como PRONTO (Atualiza):");
        System.out.println("4 - Deletar Pedido:");
        System.out.println("5 - Exibir Histórico de Pedidos");
        System.out.println("6 - Tempo Médio dos Pedidos");
        System.out.println("0 - Sair das Opções de Pedido:");
        System.out.println("------------------------------");
        return scanner.nextInt();
    }
}
