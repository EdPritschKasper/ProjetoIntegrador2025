package com.dove.ModelService;

import com.dove.ModelEntities.IngredienteEntity;
import com.dove.ModelRepository.IngredienteRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class IngredienteService {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");
    private final java.util.Scanner scanner;

    public IngredienteService(java.util.Scanner scanner) {
        this.scanner = scanner;
    }

    public void cadastrarIngrediente() {
        EntityManager em = emf.createEntityManager();
        IngredienteRepository repo = new IngredienteRepository(em);

        System.out.println("Digite a descrição do ingrediente:");
        String descricao = scanner.nextLine();

        IngredienteEntity ingrediente = new IngredienteEntity(descricao);
        repo.insert(ingrediente);
        em.close();

        System.out.println("Ingrediente cadastrado com sucesso!");
    }

    public void lerIngrediente() {
        EntityManager em = emf.createEntityManager();
        IngredienteRepository repo = new IngredienteRepository(em);

        System.out.println("Digite o ID do ingrediente:");
        Long id = scanner.nextLong();
        scanner.nextLine();

        IngredienteEntity ingrediente = repo.findById(id);
        em.close();

        if (ingrediente != null) {
            System.out.println(ingrediente);
        } else {
            System.out.println("Ingrediente não encontrado.");
        }
    }

    public void atualizarIngrediente() {
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

    public void deletarIngrediente() {
        EntityManager em = emf.createEntityManager();

        try {
            System.out.println("Digite o ID do ingrediente a deletar:");
            Long id = scanner.nextLong();
            scanner.nextLine();

            em.getTransaction().begin();

            IngredienteEntity ingrediente = em.find(IngredienteEntity.class, id);
            if (ingrediente == null) {
                System.out.println("Ingrediente não encontrado.");
                em.getTransaction().rollback();
                return;
            }

            // Verifica se está no cardápio
            Long countCardapio = em.createQuery(
                            "select count(c) from CardapiosEntity c join c.ingredientes i where i.id = :id", Long.class)
                    .setParameter("id", id)
                    .getSingleResult();

            // Verifica se está em algum pedido
            Long countPedido = em.createQuery(
                            "select count(p) from PedidoEntity p join p.ingredientes i where i.id = :id", Long.class)
                    .setParameter("id", id)
                    .getSingleResult();

            if (countCardapio > 0 || countPedido > 0) {
                System.out.println("Ingrediente está cadastrado em cardápio ou pedido. Não pode ser excluído.");
                em.getTransaction().rollback();
                return;
            }

            em.remove(ingrediente);
            em.getTransaction().commit();
            System.out.println("Ingrediente excluído com sucesso.");

        } catch (Exception e) {
            System.out.println("Erro ao tentar excluir ingrediente: " + e.getMessage());
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }

    public void listarTodosIngredientes() {
        EntityManager em = emf.createEntityManager();
        IngredienteRepository repo = new IngredienteRepository(em);

        System.out.println("Lista de Ingredientes:");
        for (IngredienteEntity ingrediente : repo.findAll()) {
            System.out.println(ingrediente);
        }

        em.close();
    }

    public void pegarIngredienteMaisSelecionado() {
        EntityManager em = emf.createEntityManager();
        IngredienteRepository repo = new IngredienteRepository(em);
        try {
            IngredienteEntity ingrediente = repo.getMostSelectedIngrediente();
            if (ingrediente != null) {
                System.out.println("Ingrediente mais selecionado: " + ingrediente);
            } else {
                System.out.println("Nenhum ingrediente encontrado.");
            }
        } catch (Exception e) {
            System.out.println("Erro ao recuperar ingrediente: " + e.getMessage());
        } finally {
            em.close();
        }
    }
}
