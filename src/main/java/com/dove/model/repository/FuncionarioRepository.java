package com.dove.model.repository;

import com.dove.model.entities.FuncionarioEntity;
import com.dove.model.entities.PedidoEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

import java.util.Collections;
import java.util.List;

public class FuncionarioRepository {

    private final EntityManager em;

    public FuncionarioRepository(EntityManager em) {
        this.em = em;
    }

    public boolean salvar(FuncionarioEntity funcionario) {
        try {
            em.getTransaction().begin();
            em.persist(funcionario);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
            return false;
        }
    }

    public boolean atualizar(FuncionarioEntity funcionario) {
        try {
            em.getTransaction().begin();
            em.merge(funcionario); // Merge é o método correto para atualizar.
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
            return false;
        }
    }

    public boolean deletar(Long id) {

        FuncionarioEntity funcionario = buscarPorId(id);
        if (funcionario == null) {
            return false;
        }
        try {
            em.getTransaction().begin();
            em.remove(funcionario);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
            return false;
        }
    }

    public FuncionarioEntity buscarPorId(Long id) {
        return em.find(FuncionarioEntity.class, id);
    }

    public List<FuncionarioEntity> buscarTodos() {
        String jpql = "SELECT f FROM FuncionarioEntity f";
        TypedQuery<FuncionarioEntity> query = em.createQuery(jpql, FuncionarioEntity.class);
        return query.getResultList();
    }

    public FuncionarioEntity buscarPorCpf(String cpf) {
        try {
            String jpql = "SELECT f FROM FuncionarioEntity f WHERE f.cpf = :cpf";
            TypedQuery<FuncionarioEntity> query = em.createQuery(jpql, FuncionarioEntity.class);
            query.setParameter("cpf", cpf);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public List<PedidoEntity> buscarPedidosPorFuncionario(Long id) {
        try {
            String jpql = "SELECT p FROM PedidoEntity p WHERE p.funcionario.id = :funcionarioId";
            TypedQuery<PedidoEntity> query = em.createQuery(jpql, PedidoEntity.class);
            query.setParameter("funcionarioId", id);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}