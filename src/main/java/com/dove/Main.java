package com.dove;

import java.util.Scanner;
import com.dove.view.options.PedidoOptions;
import com.dove.view.options.CardapioOptions;
import com.dove.view.IngredienteView;
import com.dove.view.options.ClienteOptions;
import com.dove.view.LoginView;

public class Main {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(LoginView::new);

        // Declaração de variáveis
        Scanner scanner = new Scanner(System.in);
        int controle = 0;

        // Inicialização de Opções
        CardapioOptions cardapioOptions = new CardapioOptions(scanner);
        com.dove.controller.FuncionarioController funcionarioController = new com.dove.controller.FuncionarioController(scanner);
        IngredienteView ingredienteView = new IngredienteView(scanner);
        FuncionarioOptions funcionarioOptions = new FuncionarioOptions(scanner);
        com.dove.controller.IngredienteController ingredienteController = new com.dove.controller.IngredienteController(scanner);
        com.dove.controller.ClienteController clienteController = new com.dove.controller.ClienteController(scanner);
        PedidoOptions pedidoOptions = new PedidoOptions(scanner);
        ClienteOptions clienteOptions = new ClienteOptions(scanner);

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
                case 1 -> funcionarioController.executar();
                case 2 -> clienteOptions.caseEntidades();
                case 3 -> cardapioOptions.caseEntidade();
                case 4 -> ingredienteView.executar();
                case 5 -> pedidoOptions.caseEntidade();
                case 0 -> System.out.println("Encerrando Sistema...");
                default -> System.out.println("Opção Inválida");
            }

        } while(controle != 0);

        scanner.close();
    }
}