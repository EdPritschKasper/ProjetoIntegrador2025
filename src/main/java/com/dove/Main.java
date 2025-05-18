package com.dove;

import com.dove.ModelService.IngredienteService;
import com.dove.ModelService.PedidoService;
import com.dove.ModelService.FuncionarioService;
import com.dove.ModelService.ClienteService;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        // Declaração de variáveis
        Scanner scanner = new Scanner(System.in);
        int controle = 0;

        // Inicialização de Opções
        // Classes com a implementação das switch cases para não poluir a Main
        FuncionarioService funcionarioService = new FuncionarioService();
        PedidoService pedidoService = new PedidoService();
        IngredienteService ingredienteService = new IngredienteService();
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
                case 1:// Opcao Funcionario
                    funcionarioService.caseEntidades();
                    break;
                case 2:
                    clienteService.executarOpcao();
                    break;
                case 3:
                    break;
                case 4://  Opcao Ingrediente
                    ingredienteService.caseEntidade();
                    break;
                case 5: // Opção Pedido
                    pedidoService.caseEntidade();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Opção inválida");
                    break;
            }

        } while(controle != 0);

        scanner.close();
    }
}