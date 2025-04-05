package com.dove;

import com.dove.options.PedidoOpcao;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Scanner;
import com.dove.repository.*;
import com.dove.entities.*;

import java.time.LocalTime;
import com.dove.options.*;

public class Main {
    public static void main(String[] args) {

        // Declaração de variáveis
        Scanner scanner = new Scanner(System.in);
        int controle = 0;

        // Inicialização de Opções
        PedidoOpcao pedidoOpcao = new PedidoOpcao();

        // Estrutura de repetição incial para opções de entidade
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
            scanner.nextLine(); // limpa o buffer do scanner para nao pular linha

            switch (controle) {
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
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