package com.dove.repository;

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
}
