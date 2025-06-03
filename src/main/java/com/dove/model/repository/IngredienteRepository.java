package com.dove.model.repository;

import com.dove.model.entities.IngredienteEntity;
import jakarta.persistence.EntityManager;

import java.util.List;

public class IngredienteRepository {
    private EntityManager em;

    public IngredienteRepository(EntityManager em) {
        this.em = em;
    }

    public IngredienteEntity findById(Long id) {
        return em.find(IngredienteEntity.class, id);
    }

    public boolean insert(IngredienteEntity ingrediente) {
        try {
            em.getTransaction().begin();
            em.persist(ingrediente);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            return false;
        }
    }

    public boolean update(IngredienteEntity ingrediente) {
        try {
            em.getTransaction().begin();
            em.merge(ingrediente);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            return false;
        }
    }

    public boolean delete(IngredienteEntity ingrediente) {
        try {
            em.getTransaction().begin();

            // Busca o ingrediente no contexto do EntityManager para ficar gerenciado
            IngredienteEntity ingredienteGerenciado = em.find(IngredienteEntity.class, ingrediente.getId());

            if (ingredienteGerenciado != null) {
                em.remove(ingredienteGerenciado);
                em.getTransaction().commit();
                return true;
            } else {
                em.getTransaction().rollback();
                return false;
            }
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            return false;
        }
    }

    public List<IngredienteEntity> findAll() {
        return em.createQuery("SELECT i FROM IngredienteEntity i", IngredienteEntity.class)
                .getResultList();
    }

    public IngredienteEntity getMostSelectedIngrediente() {
        String jpql = "SELECT i FROM IngredienteEntity i LEFT JOIN i.pedidos p GROUP BY i ORDER BY COUNT(p) DESC";
        return em.createQuery(jpql, IngredienteEntity.class)
                .setMaxResults(1)
                .getSingleResult();
    }
}
