package com.dove.options;

import com.dove.entities.IngredienteEntity;
import com.dove.entities.PedidoEntity;
import com.dove.repository.CustomizerFactory;
import com.dove.repository.IngredienteRepository;
import jakarta.persistence.EntityManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PedidoCadastraOpcao {
    public static void caseEntidade() {
        Scanner scanner = new Scanner(System.in);
        EntityManager em = CustomizerFactory.getEntityManager();
        IngredienteRepository ingredienteRepository = new IngredienteRepository(em);
        PedidoEntity pedidoEntity = new PedidoEntity();
        List<IngredienteEntity> ingredientes = ingredienteRepository.findAll();
        int controleEscolheIngrediente = 0, indexIngrediente;

        do {
            System.out.println("------------------------------");
            System.out.println("Digite um por vez os números dos ingredientes");
            for(indexIngrediente = 0; indexIngrediente < ingredientes.size(); indexIngrediente++){
                System.out.print(indexIngrediente + "-" + ingredientes.get(indexIngrediente).getDescricao() + " //");
            }
            System.out.println("(-1) - Sair das Opções de Pedido:");
            System.out.println("------------------------------");
            controleEscolheIngrediente = scanner.nextInt();

            // Verifica se usuário informou um número dentro da lista de ingredientes
            if (controleEscolheIngrediente > -1 && controleEscolheIngrediente < ingredientes.size()) {
                pedidoEntity.addIngrediente(ingredientes.get(controleEscolheIngrediente));
            }

        } while(controleEscolheIngrediente != -1);

        System.out.println("------------------------------");
//        System.out.println("");
    }
}
