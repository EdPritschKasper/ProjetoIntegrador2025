package com.dove.model.repository;

import com.dove.model.entities.ClienteEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

public class ClienteRepository {

    private final EntityManager em;

    public ClienteRepository(EntityManager em) {
        this.em = em;
    }

    public void salvarCliente(ClienteEntity cliente) {
        try {
            em.getTransaction().begin();
            em.persist(cliente);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new RuntimeException("Erro ao salvar cliente", e);
        }
    }

    public ClienteEntity buscarPorEmailESenha(String email, String senha) {
        try {
            TypedQuery<ClienteEntity> query = em.createQuery(
                    "FROM ClienteEntity WHERE email = :email AND senha = :senha",
                    ClienteEntity.class);
            query.setParameter("email", email);
            query.setParameter("senha", senha);
            return query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public ClienteEntity buscarPorEmail(String email) {
        try {
            TypedQuery<ClienteEntity> query = em.createQuery(
                    "FROM ClienteEntity WHERE email = :email", ClienteEntity.class);
            query.setParameter("email", email);
            return query.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public ClienteEntity findById(Long id) {
        return em.find(ClienteEntity.class, id);
    }
}
