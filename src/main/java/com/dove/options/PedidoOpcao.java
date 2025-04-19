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
    private int controleEscolheIngrediente, indexIngrediente, controleMarmita;
    private Long id;


    public PedidoOpcao(Scanner scanner) {
        this.scanner = scanner;
        this.em = CustomizerFactory.getEntityManager();
        this.ingredienteRepository = new IngredienteRepository(em);
        this.clienteRepository = new ClienteRepository();
        this.funcionarioRepository = new FuncionarioRepository(em);
        this.cardapiosRepository = new CardapiosRepository(em);
        this.pedidoRepository = new PedidoRepository(em);
    }

    // Switch Case 5 - CRUD do Pedido
    public void  caseEntidade() {
        int controle = 0;

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

            controle = scanner.nextInt();

            switch (controle) {
                case 1 -> adicionaPedido();
                case 2 -> pesquisaPedido();
                case 3 -> atualizaPedido();
                case 4 -> deletaPedido();
                case 5 -> historicoPedido();
                case 0 -> System.out.println("Saindo de Pedidos...");
                default -> System.out.println("Opção inválida.");
            }
        } while(controle != 0);
    }

    // função auxiliar para validar e pesquisar o ID
    public PedidoEntity pesquisaPedidoId(){
        if(!scanner.hasNextLong()) {
            scanner.next();
            System.out.println("Tipo Inválido");
            return null;
        }

        PedidoEntity pedidoEntity = pedidoRepository.findById(scanner.nextLong());

        if(pedidoEntity == null){
            System.out.println("Pedido não encontrado");
            return null;
        }

        return pedidoEntity;
    }

    // cria pedido no dia
    private void adicionaPedido() {
        // verifica se tem apenas um cardápio do dia
        CardapiosEntity cardapiosEntity = cardapiosRepository.getCardapioHoje();
        if(cardapiosEntity == null) return;

        // inicialização do pedido
        List<IngredienteEntity> ingredientes = cardapiosEntity.getIngredientes();
        PedidoEntity pedidoEntity = new PedidoEntity();
        pedidoEntity.setHora_inicio(LocalTime.now());
        pedidoEntity.setStatus("Iniciado");
        pedidoEntity.setCardapio(cardapiosEntity);


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
            }
            else if (controleEscolheIngrediente == -1) System.out.println("Ingredientes confirmados");
            else System.out.println("Opção Inválida");

        } while(controleEscolheIngrediente != -1);

        // seleção do tamanho da marmita ou apenas prato
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
            } else System.out.println("Opção Inválida");

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
    private void pesquisaPedido() {
        System.out.println("------------------------------");
        System.out.println("Digite o ID do pedido para PESQUISAR");
        System.out.println("------------------------------");

        PedidoEntity pedidoEntity = pesquisaPedidoId();

        if(pedidoEntity != null) System.out.println(pedidoEntity);
    }

    // atualiza pedido, definindo como pronto e a hora final
    private void atualizaPedido(){
        System.out.println("------------------------------");
        System.out.println("Digite o ID do pedido para ATUALIZAR");
        System.out.println("------------------------------");

        PedidoEntity pedidoEntity = pesquisaPedidoId();

        if(pedidoEntity == null) return;

        // Define como pronto
        pedidoEntity.setStatus("pronto");
        pedidoEntity.setHora_fim(LocalTime.now());
        // Atualiza no Banco
        pedidoRepository.update(pedidoEntity);
    }

    // deleta pedido por id
    private void deletaPedido(){
        System.out.println("------------------------------");
        System.out.println("Digite o ID do pedido para DELETAR:");
        System.out.println("------------------------------");
        PedidoEntity pedidoEntity = pesquisaPedidoId();

        if(pedidoEntity != null) pedidoRepository.delete(pedidoEntity);
    }

    // histórico de todos os pedidos
    private void historicoPedido(){
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
