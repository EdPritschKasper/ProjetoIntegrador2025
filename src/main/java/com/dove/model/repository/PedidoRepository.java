package com.dove.model.repository;

import com.dove.model.entities.PedidoEntity;
import jakarta.persistence.EntityManager;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.List;

public class PedidoRepository {
    private EntityManager em;

    public PedidoRepository(EntityManager em) {
        this.em = em;
    }

    public PedidoEntity findById(Long id) {
        return em.find(PedidoEntity.class, id);
    }

    public boolean insert(PedidoEntity pedido) {
        em.getTransaction().begin();
        em.persist(pedido);
        em.getTransaction().commit();
        return true;
    }

    public boolean update(PedidoEntity pedido) {
        em.getTransaction().begin();
        em.merge(pedido);
        em.getTransaction().commit();
        return true;
    }

    public boolean delete(PedidoEntity pedido) {
        em.getTransaction().begin();
        em.remove(pedido);
        em.getTransaction().commit();
        return true;
    }

    public List<PedidoEntity> findAll() {
        return em
                .createQuery("SELECT e FROM PedidoEntity e", PedidoEntity.class)
                .getResultList();
    }

    public Duration buscaTempoMedio() {
        BigDecimal segundos = (BigDecimal) em.createNativeQuery(
                "SELECT AVG(EXTRACT(EPOCH FROM hora_fim - hora_inicio)) FROM tb_pedido where hora_fim is not null and hora_inicio is not null"
        ).getSingleResult();

        return Duration.ofSeconds(segundos.longValue());
    }
}
