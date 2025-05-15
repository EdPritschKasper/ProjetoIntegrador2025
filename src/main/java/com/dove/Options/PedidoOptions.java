package com.dove.Options;

import com.dove.Service.PedidoService;

import java.util.Scanner;

public class PedidoOptions {

    private final Scanner scanner;
    private final PedidoService service;

    public PedidoOptions(Scanner scanner){
        this.scanner = scanner;
        this.service = new PedidoService(scanner);
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
                case 1 -> service.adicionaPedido();
                case 2 -> service.pesquisaPedido();
                case 3 -> service.atualizaPedido();
                case 4 -> service.deletaPedido();
                case 5 -> service.historicoPedido();
                case 6 -> service.tempoMedioPedidos();
                case 0 -> System.out.println("Saindo de Pedidos...");
                default -> System.out.println("Opção inválida.");
            }

        } while(controle != 0);
    }
}
