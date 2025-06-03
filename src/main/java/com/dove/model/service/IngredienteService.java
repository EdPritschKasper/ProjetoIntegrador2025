package com.dove.model.service;

import com.dove.model.entities.IngredienteEntity;
import com.dove.model.repository.IngredienteRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.List;

public class IngredienteService {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("default");

    public IngredienteEntity findById(Long id) {
        EntityManager em = emf.createEntityManager();
        IngredienteRepository repo = new IngredienteRepository(em);
        IngredienteEntity ingrediente = repo.findById(id);
        em.close();
        return ingrediente;
    }

    public boolean insert(IngredienteEntity ingrediente) {
        EntityManager em = emf.createEntityManager();
        IngredienteRepository repo = new IngredienteRepository(em);
        boolean result = repo.insert(ingrediente);
        em.close();
        return result;
    }

    public boolean update(IngredienteEntity ingrediente) {
        EntityManager em = emf.createEntityManager();
        IngredienteRepository repo = new IngredienteRepository(em);
        boolean result = repo.update(ingrediente);
        em.close();
        return result;
    }

    public boolean delete(IngredienteEntity ingrediente) {
        EntityManager em = emf.createEntityManager();
        IngredienteRepository repo = new IngredienteRepository(em);
        boolean result = repo.delete(ingrediente);
        em.close();
        return result;
    }

    public List<IngredienteEntity> findAll() {
        EntityManager em = emf.createEntityManager();
        IngredienteRepository repo = new IngredienteRepository(em);
        List<IngredienteEntity> lista = repo.findAll();
        em.close();
        return lista;
    }

    public IngredienteEntity getMostSelectedIngrediente() {
        EntityManager em = emf.createEntityManager();
        IngredienteRepository repo = new IngredienteRepository(em);
        IngredienteEntity ingrediente = repo.getMostSelectedIngrediente();
        em.close();
        return ingrediente;
    }
}
