package com.dove.options;

import com.dove.entities.ClienteEntity;
import com.dove.entities.Funcionario;
import com.dove.entities.IngredienteEntity;
import com.dove.entities.PedidoEntity;
import com.dove.repository.ClienteRepository;
import com.dove.repository.CustomizerFactory;
import com.dove.repository.FuncionarioRepository;
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
        ClienteRepository clienteRepository = new ClienteRepository();
        FuncionarioRepository funcionarioRepository = new FuncionarioRepository(em);
        PedidoEntity pedidoEntity = new PedidoEntity();
        ClienteEntity clienteEntity = new ClienteEntity();
        Funcionario funcionario = new Funcionario();
        List<IngredienteEntity> ingredientes = ingredienteRepository.findAll();
        String[] marmita = {"Prato no Local", "Marmita Pequena", "Marmita Média", "Marmita Grande"};
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

        // adiciona cliente
        System.out.println("------------------------------");
        System.out.println("Digite o Id do Cliente");
        clienteEntity = clienteRepository.findById(scanner.nextLong());
        pedidoEntity.setCliente(clienteEntity);

        // adiciona funcionário
        System.out.println("------------------------------");
        System.out.println("Digite o Id do Funcionário");
        funcionario = funcionarioRepository.buscarPorId(scanner.nextLong());
        pedidoEntity.setFuncionario(funcionario);

        System.out.println("------------------------------");
//        System.out.println("");
    }
}
