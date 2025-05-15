package com.dove.options;

import com.dove.entities.*;
import com.dove.repository.*;
import jakarta.persistence.EntityManager;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PedidoCadastraOpcao {
    public static void caseEntidade() {
        Scanner scanner = new Scanner(System.in);
        EntityManager em = CustomizerFactory.getEntityManager();
        IngredienteRepository ingredienteRepository = new IngredienteRepository(em);
        ClienteRepository clienteRepository = new ClienteRepository();
        FuncionarioRepository funcionarioRepository = new FuncionarioRepository(em);
        CardapiosRepository cardapiosRepository = new CardapiosRepository(em);
        PedidoRepository pedidoRepository = new PedidoRepository(em);
        PedidoEntity pedidoEntity = new PedidoEntity();
        ClienteEntity clienteEntity = new ClienteEntity();
        FuncionarioEntity funcionario = new FuncionarioEntity();
        CardapiosEntity cardapiosEntity = new CardapiosEntity();
        List<IngredienteEntity> ingredientes = ingredienteRepository.findAll();
        String[] marmita = {"prato", "pequena", "média", "grande"};
        int controleEscolheIngrediente = 0, indexIngrediente, controleMarmita = 0;

        // Adição de ingredientes
        do {
            System.out.println("------------------------------");
            System.out.println("Digite um por vez os números dos ingredientes");
            for(indexIngrediente = 0; indexIngrediente < ingredientes.size(); indexIngrediente++){
                System.out.println(indexIngrediente + " - " + ingredientes.get(indexIngrediente).getDescricao());
            }
            System.out.println("(-1) - Sair das Opções de Pedido:");
            System.out.println("------------------------------");
            controleEscolheIngrediente = scanner.nextInt();

            // Verifica se usuário informou um número dentro da lista de ingredientes
            if (controleEscolheIngrediente > -1 && controleEscolheIngrediente < ingredientes.size()) {
                pedidoEntity.addIngrediente(ingredientes.get(controleEscolheIngrediente));
            } else {
                System.out.println("Opção Inválida");
            }

        } while(controleEscolheIngrediente != -1);

        // Seleção tamanho marmita ou prato
        do {
            System.out.println("------------------------------");
            System.out.println("Digite a opção de Marmita");
            System.out.println("1 - Prato no local");
            System.out.println("2 - Marmita Pequena");
            System.out.println("3 - Marmita Média");
            System.out.println("4 - Marmita Grande");
            System.out.println("------------------------------");
            controleMarmita = scanner.nextInt();

            if (controleMarmita > 0 && controleMarmita < marmita.length) {
                pedidoEntity.setMarmita(marmita[controleMarmita]);
                controleMarmita = 0;
            } else {
                System.out.println("Opção Inválida");
            }

        } while(controleMarmita != 0);

        // Adiciona Cliente
        System.out.println("------------------------------");
        System.out.println("Digite o Id do Cliente");
        clienteEntity = clienteRepository.findById(scanner.nextLong());
        pedidoEntity.setCliente(clienteEntity);

        // Adiciona Funcionário
        System.out.println("------------------------------");
        System.out.println("Digite o Id do Funcionário");
        funcionario = funcionarioRepository.buscarPorId(scanner.nextLong());
        pedidoEntity.setFuncionario(funcionario);

        // Adições Automáticas + cardápio
        pedidoEntity.setHora_inicio(LocalTime.now());
        pedidoEntity.setStatus("Iniciado");
        cardapiosEntity = cardapiosRepository.findById(1L);
        pedidoEntity.setCardapio(cardapiosEntity);

        // Insere no Banco
        pedidoRepository.insert(pedidoEntity);
    }
}
