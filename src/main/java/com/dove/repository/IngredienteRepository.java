package com.dove.repository;

import com.dove.entities.IngredienteEntity;
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

    public void insert(IngredienteEntity ingrediente) {
        em.getTransaction().begin();
        em.persist(ingrediente);
        em.getTransaction().commit();
    }

    public void update(IngredienteEntity ingrediente) {
        em.getTransaction().begin();
        em.merge(ingrediente);
        em.getTransaction().commit();
    }

    public void delete(IngredienteEntity ingrediente) {
        em.getTransaction().begin();
        em.remove(ingrediente);
        em.getTransaction().commit();
    }

    public List<IngredienteEntity> findAll() {
        return em
                .createQuery("SELECT i FROM IngredienteEntity i", IngredienteEntity.class)
                .getResultList();
    }
}
