package com.dove.options;

import com.dove.entities.PedidoEntity;
import jakarta.persistence.EntityManager;
import java.util.Scanner;
import com.dove.repository.PedidoRepository;
import com.dove.repository.CustomizerFactory;


public class PedidoOpcao {

    // Switch Case 5 - CRUD do Pedido
    public static void  caseEntidade() {
        Scanner scanner = new Scanner(System.in);
        EntityManager em = CustomizerFactory.getEntityManager();
        PedidoRepository pedidoRepository = new PedidoRepository(em);
        PedidoEntity pedidoEntity = new PedidoEntity();
        int controlePedido = 0;
        Long id;

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
//            scanner.nextLine(); // limpa o buffer do scanner para nao pular linha

            switch (controlePedido) {
                case 1:
                    PedidoCadastraOpcao.caseEntidade();
                    break;
                case 2:
                    System.out.println("------------------------------");
                    System.out.println("Digite o ID do pedido:");
                    System.out.println("------------------------------");

                    id = scanner.nextLong();
                    pedidoEntity = pedidoRepository.findById(id);
                    System.out.println(pedidoEntity);
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
