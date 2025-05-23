package com.dove.model.repository;

import com.dove.model.entities.FuncionarioEntity;
import com.dove.model.entities.PedidoEntity;
import jakarta.persistence.*;

import java.util.List;

public class FuncionarioRepository {
    private final EntityManager entityManager;

    public FuncionarioRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void salvar(FuncionarioEntity funcionario) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(funcionario);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) transaction.rollback();
            e.printStackTrace();
        }
    }

    public FuncionarioEntity buscarPorId(Long id) {
        return entityManager.find(FuncionarioEntity.class, id);
    }

    public List<FuncionarioEntity> buscarTodos() {
        return entityManager
                .createQuery("FROM FuncionarioEntity", FuncionarioEntity.class)
                .getResultList();
    }

    public void atualizar(FuncionarioEntity funcionario) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.merge(funcionario);
            transaction.commit();
        } catch (Exception e) {
            if (transaction.isActive()) transaction.rollback();
            e.printStackTrace();
        }
    }

    public void deletar(Long id) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();

            // 1. Verifica se tem pedidos vinculados
            Long pedidosVinculados = entityManager.createQuery(
                            "SELECT COUNT(p) FROM PedidoEntity p WHERE p.funcionario.id = :id", Long.class)
                    .setParameter("id", id)
                    .getSingleResult();

            if (pedidosVinculados > 0) {
                System.out.println("Erro: Este funcionário possui pedidos e não pode ser removido.");
                transaction.rollback();
                return;
            }

            // 2. Só agora busca o Funcionario
            FuncionarioEntity funcionario = entityManager.find(FuncionarioEntity.class, id);

            if (funcionario != null) {
                entityManager.remove(funcionario);
                transaction.commit();
                System.out.println("Funcionário removido com sucesso!");
            } else {
                System.out.println("Funcionário não encontrado.");
                transaction.rollback();
            }

        } catch (Exception e) {
            if (transaction.isActive()) transaction.rollback();
            e.printStackTrace();
        }
    }

    public List<PedidoEntity> buscarPedidosPorFuncionario(Long funcionarioId) {
        return entityManager.createQuery(
                        "SELECT p FROM PedidoEntity p WHERE p.funcionario.id = :funcionarioId", PedidoEntity.class)
                .setParameter("funcionarioId", funcionarioId)
                .getResultList();
    }

    public FuncionarioEntity buscarPorCpf(String cpf) {
        try {
            return entityManager.createQuery("SELECT f FROM FuncionarioEntity f WHERE f.cpf = :cpf", FuncionarioEntity.class)
                    .setParameter("cpf", cpf)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
