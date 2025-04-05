package com.dove.options;

import java.util.Scanner;


public class PedidoOpcao {

    // Switch Case 5 - CRUD do Pedido
    public void caseEntidade() {
        Scanner scanner = new Scanner(System.in);
        int controlePedido = 0;

        do {
            System.out.println("------------------------------");
            System.out.println("OPÇÕES DE CRUD DO PEDIDO");
            System.out.println("Digite a opção desejada:");
            System.out.println("1 - Cadastrar Pedido:");
            System.out.println("2 - Ler Pedido Por ID:");
            System.out.println("3 - Atualizar Pedido:");
            System.out.println("4 - Deletar Pedido:");
            System.out.println("0 - Sair das Opções de Pedido:");
            System.out.println("------------------------------");

            controlePedido = scanner.nextInt();
            scanner.nextLine(); // limpa o buffer do scanner para nao pular linha

            switch (controlePedido) {
                case 1:
                    System.out.println("case 1");
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 0:
                    break;
                default:
                    break;
            }
        } while(controlePedido != 0);
    }
}
