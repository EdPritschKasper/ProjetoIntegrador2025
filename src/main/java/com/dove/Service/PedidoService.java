package com.dove.Service;

import com.dove.entities.*;
import com.dove.repository.*;
import jakarta.persistence.EntityManager;

import java.time.Duration;
import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;


public class PedidoService {
    private final Scanner scanner;
    private final EntityManager em;
    private final ClienteRepository clienteRepository;
    private final FuncionarioRepository funcionarioRepository;
    private final CardapiosRepository cardapiosRepository;
    private final PedidoRepository pedidoRepository;
    private final String[] marmita = {"prato", "pequena", "média", "grande"};
    private Long id;


    public PedidoService(Scanner scanner) {
        this.scanner = scanner;
        this.em = CustomizerFactory.getEntityManager();
        this.clienteRepository = new ClienteRepository();
        this.funcionarioRepository = new FuncionarioRepository(em);
        this.cardapiosRepository = new CardapiosRepository(em);
        this.pedidoRepository = new PedidoRepository(em);
    }

    // Switch Case 5 - CRUD do Pedido


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
    public void adicionaPedido() {

        int controleIngrediente = 0, controleMarmita = 0;
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
            for(int i = 1; i <= ingredientes.size(); i++){
                System.out.println(i + " - " + ingredientes.get(i-1).getDescricao());
            }
            System.out.println("0 - Sair das Opções de Pedido:");
            System.out.println("------------------------------");
            controleIngrediente = scanner.nextInt();

            // Verifica se usuário informou um número dentro da lista de ingredientes
            if (controleIngrediente > 0 && controleIngrediente <= ingredientes.size()) {
                pedidoEntity.addIngrediente(ingredientes.get(controleIngrediente-1));
            }
            else if (controleIngrediente == 0) System.out.println("Ingredientes confirmados");
            else System.out.println("Opção Inválida");

        } while(controleIngrediente != 0);

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
        if(clienteEntity == null) {
            System.out.println("Cliente não encontrado");
            return;
        }
        pedidoEntity.setCliente(clienteEntity);

        // Adiciona Funcionário
        System.out.println("------------------------------");
        System.out.println("Digite o Id do Funcionário");
        FuncionarioEntity funcionario = funcionarioRepository.buscarPorId(scanner.nextLong());
        if(funcionario == null) {
            System.out.println("Funcionario não encontrado");
            return;
        }
        pedidoEntity.setFuncionario(funcionario);

        // Insere no Banco
        pedidoRepository.insert(pedidoEntity);
        System.out.println("Pedido criado com sucesso");
    }

    // pesquisa um pedido por ID
    public void pesquisaPedido() {
        System.out.println("------------------------------");
        System.out.println("Digite o ID do pedido para PESQUISAR");
        System.out.println("------------------------------");

        PedidoEntity pedidoEntity = pesquisaPedidoId();

        if(pedidoEntity != null) System.out.println(pedidoEntity);
    }

    // atualiza pedido, definindo como pronto e a hora final
    public void atualizaPedido(){
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
        System.out.println("Pedido atualizado com sucesso");
    }

    // deleta pedido por id
    public void deletaPedido(){
        System.out.println("------------------------------");
        System.out.println("Digite o ID do pedido para DELETAR:");
        System.out.println("------------------------------");
        PedidoEntity pedidoEntity = pesquisaPedidoId();

        if(pedidoEntity != null) pedidoRepository.delete(pedidoEntity);
        System.out.println("Pedido deletado com sucesso");
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

    public void tempoMedioPedidos() {
        Duration duracao = pedidoRepository.buscaTempoMedio();
        long segundosTotais = duracao.getSeconds();

        long horas = segundosTotais / 3600;
        long minutos = (segundosTotais % 3600) / 60;
        long segundos = segundosTotais % 60;

        String tempoFormatado = String.format("%02d:%02d:%02d", horas, minutos, segundos);
        System.out.println("Tempo médio: " + tempoFormatado);
    }
}
