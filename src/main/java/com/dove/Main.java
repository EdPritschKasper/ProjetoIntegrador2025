package com.dove;

import com.dove.options.IngredienteOpcao;
import com.dove.options.PedidoOpcao;
import com.dove.options.FuncionarioOpcao;
import com.dove.options.ClienteOpcao;

import java.util.List;
import java.util.Scanner;
import com.dove.repository.*;
import com.dove.entities.*;

import java.time.LocalTime;
import com.dove.options.*;
import jakarta.persistence.EntityManager;

public class Main {
    public static void main(String[] args) {

        // Declaração de variáveis
        Scanner scanner = new Scanner(System.in);
        int controle = 0;

        // Inicialização de Opções
        // Classes com a implementação das switch cases para não poluir a Main
        FuncionarioOpcao funcionarioOpcao = new FuncionarioOpcao();
        PedidoOpcao pedidoOpcao = new PedidoOpcao();
        IngredienteOpcao ingredienteOpcao = new IngredienteOpcao();
        ClienteOpcao clienteOpcao = new ClienteOpcao(scanner);
        CardapioOpcao cardapioOpcao = new CardapioOpcao();

//        EntityManager emf = CustomizerFactory.getEntityManager();
//
//        CardapiosRepository card = new CardapiosRepository(emf);
//        System.out.println(card.getCardapioIngredientePorId(1L));

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
                    funcionarioOpcao.caseEntidades();
                    break;
                case 2: // Opcao Cliente
                    clienteOpcao.executarOpcao();
                    break;
                case 3:
                    cardapioOpcao.caseEntidade();
                    break;
                case 4://  Opcao Ingrediente
                    ingredienteOpcao.caseEntidade();
                    break;
                case 5: // Opção Pedido
                    pedidoOpcao.caseEntidade();
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