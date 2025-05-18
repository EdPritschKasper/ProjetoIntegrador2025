package com.dove.ModelService;

import com.dove.ModelEntities.PedidoEntity;
import jakarta.persistence.EntityManager;

import java.time.LocalTime;
import java.util.Scanner;
import com.dove.ModelRepository.PedidoRepository;
import com.dove.ModelRepository.CustomizerFactory;


public class PedidoService {

    // Switch Case 5 - CRUD do Pedido
    public void  caseEntidade() {
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
            System.out.println("3 - Definir como PRONTO (Atualiza):");
            System.out.println("4 - Deletar Pedido:");
            System.out.println("0 - Sair das Opções de Pedido:");
            System.out.println("------------------------------");

            controlePedido = scanner.nextInt();
//            scanner.nextLine(); // limpa o buffer do scanner para nao pular linha

            switch (controlePedido) {
                case 1: // Cadastra Pedido
                    PedidoCadastraService.caseEntidade();
                    break;
                case 2: // Deleta Pedido
                    System.out.println("------------------------------");
                    System.out.println("Digite o ID do pedido para PESQUISAR");
                    System.out.println("------------------------------");

                    id = scanner.nextLong();
                    pedidoEntity = pedidoRepository.findById(id);
                    System.out.println(pedidoEntity);
                    break;
                case 3: // Atualiza Pedido
                    System.out.println("------------------------------");
                    System.out.println("Digite o ID do pedido para ATUALIZAR");
                    System.out.println("------------------------------");

                    id = scanner.nextLong();
                    pedidoEntity = pedidoRepository.findById(id);

                    // Define como pronto
                    pedidoEntity.setStatus("pronto");
                    pedidoEntity.setHora_fim(LocalTime.now());

                    // Atualiza no Banco
                    pedidoRepository.update(pedidoEntity);
                    break;
                case 4: // Deleta Pedido
                    System.out.println("------------------------------");
                    System.out.println("Digite o ID do pedido para DELETAR:");
                    System.out.println("------------------------------");
                    id = scanner.nextLong();
                    pedidoEntity = pedidoRepository.findById(id);
                    pedidoRepository.delete(pedidoEntity);
                    break;
                case 0:
                    break;
                default:
                    break;
            }
        } while(controlePedido != 0);
    }
}
