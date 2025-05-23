package com.dove.view.options;

import java.util.Scanner;

import com.dove.view.PedidoView;

public class PedidoOptions {

    private final Scanner scanner;
    private final PedidoView view;

    public PedidoOptions(Scanner scanner){
        this.scanner = scanner;
        this.view = new PedidoView(scanner);
    }

    public void  caseEntidade() {
        int controle = 0;

        do {
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

            controle = scanner.nextInt();

            switch (controle) {
                case 1 -> view.adicionaPedido();
                case 2 -> view.pesquisaPedido();
                case 3 -> view.atualizaPedido();
                case 4 -> view.deletaPedido();
                case 5 -> view.historicoPedido();
                case 6 -> view.tempoMedioPedidos();
                case 0 -> System.out.println("Saindo de Pedidos...");
                default -> System.out.println("Opção inválida.");
            }

        } while(controle != 0);
    }
}
