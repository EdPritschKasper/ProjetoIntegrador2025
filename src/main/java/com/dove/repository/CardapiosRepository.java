package com.dove.repository;

import com.dove.entities.CardapiosEntity;
import jakarta.persistence.EntityManager;

import java.util.List;

public class CardapiosRepository {
    private EntityManager em;

    public CardapiosRepository(EntityManager em) {
        this.em = em;
    }

    public CardapiosEntity findById(Long id) {
        return em.find(CardapiosEntity.class, id);
    }

    public void insert(CardapiosEntity cardapios) {
        em.getTransaction().begin();
        em.persist(cardapios);
        em.getTransaction().commit();
    }

    public void update(CardapiosEntity cardapios) {
        em.getTransaction().begin();
        em.merge(cardapios);
        em.getTransaction().commit();
    }

    public void delete(CardapiosEntity cardapios) {
        em.getTransaction().begin();
        em.remove(cardapios);
        em.getTransaction().commit();
    }

    public List<CardapiosEntity> findAll() {
        return em
                .createQuery("SELECT i FROM CardapiosEntity i", CardapiosEntity.class)
                .getResultList();
    }

//    public List<CardapiosEntity> getCardapioIngredientePorId(Long id) {
//        return em.createQuery(
//                        "SELECT c FROM CardapiosEntity c " +
//                                "INNER JOIN ingredientes i " +
//                                "WHERE i.id = :id", CardapiosEntity.class)
//                .setParameter("id", id)
//                .getResultList();
//    }
}
