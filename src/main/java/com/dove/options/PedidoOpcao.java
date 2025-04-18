package com.dove.options;

import com.dove.entities.*;
import com.dove.repository.*;
import jakarta.persistence.EntityManager;

import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;


public class PedidoOpcao {
    private final Scanner scanner;
    private final EntityManager em;
    private final ClienteRepository clienteRepository;
    private final FuncionarioRepository funcionarioRepository;
    private final CardapiosRepository cardapiosRepository;
    private final IngredienteRepository ingredienteRepository;
    private final PedidoRepository pedidoRepository;
    private final String[] marmita = {"prato", "pequena", "média", "grande"};
    int controlePedido, controleEscolheIngrediente, indexIngrediente, controleMarmita;
    private Long id;


    public PedidoOpcao(Scanner scanner) {
        this.scanner = scanner;
        this.em = CustomizerFactory.getEntityManager();
        this.controlePedido = 0;
        this.ingredienteRepository = new IngredienteRepository(em);
        this.clienteRepository = new ClienteRepository();
        this.funcionarioRepository = new FuncionarioRepository(em);
        this.cardapiosRepository = new CardapiosRepository(em);
        this.pedidoRepository = new PedidoRepository(em);
    }

    // Switch Case 5 - CRUD do Pedido
    public void  caseEntidade() {

        do {
            System.out.println("------------------------------");
            System.out.println("OPÇÕES DE CRUD DO PEDIDO");
            System.out.println("Digite a opção desejada:");
            System.out.println("1 - Cadastrar Pedido:");
            System.out.println("2 - Ler Pedido Por ID:");
            System.out.println("3 - Definir como PRONTO (Atualiza):");
            System.out.println("4 - Deletar Pedido:");
            System.out.println("5 - Exibir Histórico de Pedidos");
            System.out.println("0 - Sair das Opções de Pedido:");
            System.out.println("------------------------------");

            controlePedido = scanner.nextInt();

            switch (controlePedido) {
                case 1 -> adicionaPedido();
                case 2 -> pesquisaPedido();
                case 3 -> atualizaPedido();
                case 4 -> deletaPedido();
                case 5 -> historicoPedido();
                case 0 -> System.out.println("Saindo de Pedidos...");
                default -> System.out.println("Opção inválida.");
            }
        } while(controlePedido != 0);
    }

    // cria pedido no dia
    public void adicionaPedido() {
        PedidoEntity pedidoEntity = new PedidoEntity();
        // Seta hora_inicio do pedido para HOJE
        pedidoEntity.setHora_inicio(LocalTime.now());
        // Seta o status do pedido como "Iniciado"
        pedidoEntity.setStatus("Iniciado");
        // Seta o Cardápio do pedido para o cardápio de HOJE
        CardapiosEntity cardapiosEntity = cardapiosRepository.getCardapioHoje();
        pedidoEntity.setCardapio(cardapiosEntity);
        List<IngredienteEntity> ingredientes = cardapiosEntity.getIngredientes();


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

            if (controleMarmita > 0 && controleMarmita <= marmita.length) {
                pedidoEntity.setMarmita(marmita[--controleMarmita]);
                controleMarmita = 0;
            } else {
                System.out.println("Opção Inválida");
            }

        } while(controleMarmita != 0);

        // Adiciona Cliente
        System.out.println("------------------------------");
        System.out.println("Digite o Id do Cliente");
        ClienteEntity clienteEntity = clienteRepository.findById(scanner.nextLong());
        pedidoEntity.setCliente(clienteEntity);

        // Adiciona Funcionário
        System.out.println("------------------------------");
        System.out.println("Digite o Id do Funcionário");
        FuncionarioEntity funcionario = funcionarioRepository.buscarPorId(scanner.nextLong());
        pedidoEntity.setFuncionario(funcionario);

        // Insere no Banco
        pedidoRepository.insert(pedidoEntity);
    }

    // pesquisa um pedido por ID
    public void pesquisaPedido() {
        System.out.println("------------------------------");
        System.out.println("Digite o ID do pedido para PESQUISAR");
        System.out.println("------------------------------");
        id = scanner.nextLong();
        PedidoEntity pedidoEntity = pedidoRepository.findById(id);
        System.out.println(pedidoEntity);
    }

    // atualiza pedido, definindo como pronto e a hora final
    public void atualizaPedido(){
        System.out.println("------------------------------");
        System.out.println("Digite o ID do pedido para ATUALIZAR");
        System.out.println("------------------------------");
        id = scanner.nextLong();
        PedidoEntity pedidoEntity = pedidoRepository.findById(id);
        // Define como pronto
        pedidoEntity.setStatus("pronto");
        pedidoEntity.setHora_fim(LocalTime.now());
        // Atualiza no Banco
        pedidoRepository.update(pedidoEntity);
    }

    // deleta pedido por id
    public void deletaPedido(){
        System.out.println("------------------------------");
        System.out.println("Digite o ID do pedido para DELETAR:");
        System.out.println("------------------------------");
        id = scanner.nextLong();
        PedidoEntity pedidoEntity = pedidoRepository.findById(id);
        pedidoRepository.delete(pedidoEntity);
    }

    // histórico de todos os pedidos
    public void historicoPedido(){
        System.out.println("------------------------------");
        System.out.println("Histórico de Pedidos, seus Status e Tempo:");
        var pedidos = pedidoRepository.findAll();
        if (pedidos.isEmpty()) {
            System.out.println("Nenhum pedido realizado.");
        } else {
            pedidos.sort(java.util.Comparator.comparing(PedidoEntity::getHora_fim,
                    java.util.Comparator.nullsLast(java.util.Comparator.naturalOrder())).reversed());
            for (PedidoEntity pedido : pedidos) {
                String tempo;
                if (pedido.getHora_fim() != null) {
                    java.time.Duration duracao = java.time.Duration.between(pedido.getHora_inicio(), pedido.getHora_fim());
                    long minutos = duracao.toMinutes();
                    long segundos = duracao.minusMinutes(minutos).getSeconds();
                    tempo = minutos + " minutos " + segundos + " segundos";
                } else {
                    tempo = "Em andamento";
                }
                System.out.println("Pedido ID: " + pedido.getId() +
                        " | Status: " + pedido.getStatus() +
                        " | Tempo: " + tempo);
            }
        }
    }
}
