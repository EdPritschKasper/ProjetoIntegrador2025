package com.dove.repository;

import com.dove.model.CardapiosEntity;
import jakarta.persistence.EntityManager;

import java.time.LocalDate;
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

    public CardapiosEntity getCardapioHoje() {
        List<CardapiosEntity> cardapiosEntity = em.createQuery("SELECT c " +
                    "FROM CardapiosEntity c " +
                    "WHERE c.data = :hoje", CardapiosEntity.class)
            .setParameter("hoje", LocalDate.now())
            .getResultList();

        if(cardapiosEntity.isEmpty()) {
            System.out.println("Não há cardápio registrado para hoje");
            return null;
        }

        if(cardapiosEntity.size() > 1) {
            System.out.println("Há mais do que um cardápio registrado hoje");
            return null;
        }

        return cardapiosEntity.get(0);
    }
}
