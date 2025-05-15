package com.dove.Service;

import com.dove.entities.CardapiosEntity;
import com.dove.repository.CardapiosRepository;
import jakarta.persistence.EntityManager;
import com.dove.repository.CustomizerFactory;
import com.dove.repository.IngredienteRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class CardapioService {
    private EntityManager em;
    private CardapiosRepository cardapiosRepository;
    private Scanner scanner;

    public CardapioService() {
        this.em = CustomizerFactory.getEntityManager();
        this.cardapiosRepository = new CardapiosRepository(em);
        this.scanner = new Scanner(System.in);
    }

    public void caseEntidade() {
        int opcao;

        do {
            System.out.println("\n=== MENU CARDÁPIO ===");
            System.out.println("1 - Cadastrar novo cardápio");
            System.out.println("2 - Buscar cardápio por ID");
            System.out.println("3 - Atualizar cardápio");
            System.out.println("4 - Deletar cardápio");
            System.out.println("5 - Listar todos os cardápios");
            System.out.println("0 - Voltar ao menu principal");
            System.out.print("Escolha uma opção: ");

            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1 -> cadastrarCardapio();
                case 2 -> buscarCardapio();
                case 3 -> atualizarCardapio();
                case 4 -> deletarCardapio();
                case 5 -> listarCardapios();
                case 0 -> System.out.println("Voltando ao menu principal...");
                default -> System.out.println("Opção inválida!");
            }

        } while (opcao != 0);
    }

    private void cadastrarCardapio() {
        System.out.println("\n=== CADASTRAR CARDÁPIO ===");
        System.out.println("Digite a data (YYYY-MM-DD): ");
        String dataStr = scanner.nextLine();
        
        try {
            LocalDate data = LocalDate.parse(dataStr);
            CardapiosEntity cardapio = new CardapiosEntity(data);
            
            System.out.println("Deseja adicionar ingredientes ao cardápio? (s/n): ");
            String resposta = scanner.nextLine();
            if (resposta.equalsIgnoreCase("s")) {
                IngredienteRepository ingredienteRepository = new IngredienteRepository(em);
                var ingredientesDisponiveis = ingredienteRepository.findAll();
                
                if (ingredientesDisponiveis.isEmpty()) {
                    System.out.println("Nenhum ingrediente cadastrado disponível.");
                } else {
                    System.out.println("Lista de Ingredientes:");
                    for (int i = 0; i < ingredientesDisponiveis.size(); i++) {
                        System.out.println(i + " - " + ingredientesDisponiveis.get(i).getDescricao());
                    }
                    System.out.println("Digite os índices dos ingredientes para adicionar (digite -1 para finalizar): ");
                    while (true) {
                        int indice = scanner.nextInt();
                        scanner.nextLine();
                        if (indice == -1) {
                            break;
                        }
                        if (indice >= 0 && indice < ingredientesDisponiveis.size()) {
                            cardapio.getIngredientes().add(ingredientesDisponiveis.get(indice));
                            System.out.println("Ingrediente adicionado: " + ingredientesDisponiveis.get(indice).getDescricao());
                        } else {
                            System.out.println("Opção inválida. Tente novamente.");
                        }
                    }
                }
            }
            
            cardapiosRepository.insert(cardapio);
            System.out.println("Cardápio cadastrado com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar cardápio: " + e.getMessage());
        }
    }

    private void buscarCardapio() {
        System.out.println("\n=== BUSCAR CARDÁPIO ===");
        System.out.println("Digite o ID do cardápio: ");
        Long id = scanner.nextLong();

        try {
            CardapiosEntity cardapio = cardapiosRepository.findById(id);
            if (cardapio != null) {
                System.out.println(cardapio);
            } else {
                System.out.println("Cardápio não encontrado!");
            }
        } catch (Exception e) {
            System.out.println("Erro ao buscar cardápio: " + e.getMessage());
        }
    }

    private void atualizarCardapio() {
        System.out.println("\n=== ATUALIZAR CARDÁPIO ===");
        System.out.println("Digite o ID do cardápio: ");
        Long id = scanner.nextLong();
        scanner.nextLine();

        try {
            CardapiosEntity cardapio = cardapiosRepository.findById(id);
            if (cardapio != null) {
                System.out.println("Digite a nova data (YYYY-MM-DD): ");
                String dataStr = scanner.nextLine();
                LocalDate novaData = LocalDate.parse(dataStr);
                
                cardapio.setData(novaData);
                cardapiosRepository.update(cardapio);
                System.out.println("Cardápio atualizado com sucesso!");
            } else {
                System.out.println("Cardápio não encontrado!");
            }
        } catch (Exception e) {
            System.out.println("Erro ao atualizar cardápio: " + e.getMessage());
        }
    }

    private void deletarCardapio() {
        System.out.println("\n=== DELETAR CARDÁPIO ===");
        System.out.println("Digite o ID do cardápio: ");
        Long id = scanner.nextLong();

        try {
            CardapiosEntity cardapio = cardapiosRepository.findById(id);
            if (cardapio != null) {
                cardapiosRepository.delete(cardapio);
                System.out.println("Cardápio deletado com sucesso!");
            } else {
                System.out.println("Cardápio não encontrado!");
            }
        } catch (Exception e) {
            System.out.println("Erro ao deletar cardápio: " + e.getMessage());
        }
    }

    private void listarCardapios() {
        System.out.println("\n=== LISTA DE CARDÁPIOS ===");
        try {
            List<CardapiosEntity> cardapios = cardapiosRepository.findAll();
            if (cardapios.isEmpty()) {
                System.out.println("Nenhum cardápio cadastrado!");
            } else {
                cardapios.forEach(System.out::println);
            }
        } catch (Exception e) {
            System.out.println("Erro ao listar cardápios: " + e.getMessage());
        }
    }
}