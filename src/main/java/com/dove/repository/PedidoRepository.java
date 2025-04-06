package com.dove.repository;

import java.util.List;
import java.util.ArrayList;
import jakarta.persistence.EntityManager;
import com.dove.entities.PedidoEntity;

public class PedidoRepository {
    private EntityManager em;

    public PedidoRepository(EntityManager em) {
        this.em = em;
    }

    public PedidoEntity findById(Long id) {
        return em.find(PedidoEntity.class, id);
    }

    public void insert(PedidoEntity pedido) {
        em.getTransaction().begin();
        em.persist(pedido);
        em.getTransaction().commit();
    }

    public void update(PedidoEntity pedido) {
        em.getTransaction().begin();
        em.merge(pedido);
        em.getTransaction().commit();
    }

    public void delete(PedidoEntity pedido) {
        em.getTransaction().begin();
        em.remove(pedido);
        em.getTransaction().commit();
    }

    public List<PedidoEntity> findAll() {
        return em
                .createQuery("SELECT e FROM PedidoEntity e", PedidoEntity.class)
                .getResultList();
    }
}
