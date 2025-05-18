package com.dove.ModelService;

import com.dove.ModelEntities.IngredienteEntity;
import com.dove.ModelRepository.IngredienteRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.Scanner;

public class IngredienteService {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");

    public void caseEntidade() {
        Scanner scanner = new Scanner(System.in);
        int opcao;

        do {
            System.out.println("------------------------------");
            System.out.println("OPÇÕES DE CRUD DO INGREDIENTE");
            System.out.println("1 - Cadastrar Ingrediente:");
            System.out.println("2 - Ler Ingrediente por ID:");
            System.out.println("3 - Atualizar Ingrediente:");
            System.out.println("4 - Deletar Ingrediente:");
            System.out.println("5 - Listar Todos Ingredientes");
            System.out.println("0 - Sair:");
            System.out.println("------------------------------");

            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1 -> cadastrarIngrediente(scanner);
                case 2 -> lerIngrediente(scanner);
                case 3 -> atualizarIngrediente(scanner);
                case 4 -> deletarIngrediente(scanner);
                case 5 -> listarTodosIngredientes();
                case 0 -> System.out.println("Saindo do CRUD de Ingrediente.");
                default -> System.out.println("Opção inválida.");
            }

        } while (opcao != 0);
    }

    private static void cadastrarIngrediente(Scanner scanner) {
        EntityManager em = emf.createEntityManager();
        IngredienteRepository repo = new IngredienteRepository(em);

        System.out.println("Digite a descrição do ingrediente:");
        String descricao = scanner.nextLine();

        IngredienteEntity ingrediente = new IngredienteEntity(descricao);

        repo.insert(ingrediente);
        em.close();

        System.out.println("Ingrediente cadastrado com sucesso!");
    }

    private static void lerIngrediente(Scanner scanner) {
        EntityManager em = emf.createEntityManager();
        IngredienteRepository repo = new IngredienteRepository(em);

        System.out.println("Digite o ID do ingrediente:");
        Long id = scanner.nextLong();

        IngredienteEntity ingrediente = repo.findById(id);
        em.close();

        if (ingrediente != null) {
            System.out.println(ingrediente);
        } else {
            System.out.println("Ingrediente não encontrado.");
        }
    }

    private static void atualizarIngrediente(Scanner scanner) {
        EntityManager em = emf.createEntityManager();
        IngredienteRepository repo = new IngredienteRepository(em);

        System.out.println("Digite o ID do ingrediente a atualizar:");
        Long id = scanner.nextLong();
        scanner.nextLine();

        IngredienteEntity ingrediente = repo.findById(id);

        if (ingrediente != null) {
            System.out.println("Digite a nova descrição do ingrediente:");
            String novaDescricao = scanner.nextLine();

            ingrediente.setDescricao(novaDescricao);
            repo.update(ingrediente);

            System.out.println("Ingrediente atualizado com sucesso!");
        } else {
            System.out.println("Ingrediente não encontrado.");
        }

        em.close();
    }

    private static void deletarIngrediente(Scanner scanner) {
        EntityManager em = emf.createEntityManager();
        IngredienteRepository repo = new IngredienteRepository(em);

        System.out.println("Digite o ID do ingrediente a deletar:");
        Long id = scanner.nextLong();

        IngredienteEntity ingrediente = repo.findById(id);

        if (ingrediente != null) {
            repo.delete(ingrediente);
            System.out.println("Ingrediente deletado com sucesso!");
        } else {
            System.out.println("Ingrediente não encontrado.");
        }

        em.close();
    }

    private static void listarTodosIngredientes() {
        EntityManager em = emf.createEntityManager();
        IngredienteRepository repo = new IngredienteRepository(em);

        System.out.println("Lista de Ingredientes:");
        for (IngredienteEntity ingrediente : repo.findAll()) {
            System.out.println(ingrediente);
        }

        em.close();
    }
}
