package com.dove;

import com.dove.Service.*;
import com.dove.Options.*;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        // Declaração de variáveis
        Scanner scanner = new Scanner(System.in);
        int controle = 0;

        // Inicialização de Opções
        CardapioService cardapioService = new CardapioService();
        FuncionarioService funcionarioService = new FuncionarioService();
        PedidoOptions pedidoOptions = new PedidoOptions(scanner);
        IngredienteOptions ingredienteOptions = new IngredienteOptions(scanner);
        ClienteService clienteService = new ClienteService(scanner);

        // Estrutura de repetição inicial para opções de entidade
        do {
            System.out.println("------------------------------");
            System.out.println("OPÇÕES DE ENTIDADES");
            System.out.println("Digite a opção desejada:");
            System.out.println("1 - Funcionário:");
            System.out.println("2 - Cliente:");
            System.out.println("3 - Cardápio:");
            System.out.println("4 - Ingrediente:");
            System.out.println("5 - Pedido:");
            System.out.println("0 - Sair do programa:");
            System.out.println("------------------------------");

            controle = scanner.nextInt();
            switch (controle) {
                case 1 -> funcionarioService.caseEntidades();
                case 2 -> clienteService.executarOpcao();
                case 3 -> cardapioService.caseEntidade();
                case 4 -> ingredienteOptions.caseEntidade();
                case 5 -> pedidoOptions.caseEntidade();
                case 0 -> System.out.println("Encerrando Sistema...");
                default -> System.out.println("Opção Inválida");
            }

        } while(controle != 0);

        scanner.close();
    }
}