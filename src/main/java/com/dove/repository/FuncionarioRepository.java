package com.dove.repository;

import com.dove.entities.FuncionarioEntity;
import jakarta.persistence.*;

import java.util.List;

public class FuncionarioRepository {
    private final EntityManager entityManager;

    public FuncionarioRepository(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    public void salvar(FuncionarioEntity funcionario){
        EntityTransaction transaction = entityManager.getTransaction();
        try{
            transaction.begin();
            entityManager.persist(funcionario);
            transaction.commit();
        }catch (Exception e){
            if (transaction.isActive()) transaction.rollback();
            e.printStackTrace();
        }
    }

    public FuncionarioEntity buscarPorId(Long id){
        return entityManager.find(FuncionarioEntity.class, id);
    }

    public List<FuncionarioEntity> buscarTodos() {
        return entityManager
                .createQuery("FROM FuncionarioEntity", FuncionarioEntity.class)
                .getResultList();
    }

    public void atualizar(FuncionarioEntity funcionario){
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.merge(funcionario);
            transaction.commit();
        }catch (Exception e) {
            if (transaction.isActive()) transaction.rollback();
            e.printStackTrace();
        }
    }

    public void deletar(Long id) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            FuncionarioEntity funcionario = entityManager.find(FuncionarioEntity.class, id);

            if (funcionario != null) {
                // Verifica se há pedidos relacionados ao funcionário
                String jpql = "SELECT COUNT(p) FROM PedidoEntity p WHERE p.funcionario.id = :id";
                Long pedidosVinculados = entityManager.createQuery(jpql, Long.class)
                        .setParameter("id", id)
                        .getSingleResult();

                if (pedidosVinculados > 0) {
                    transaction.rollback();
                    System.out.println("Erro: Este funcionário está vinculado a um ou mais pedidos e não pode ser removido.");
                } else {
                    entityManager.remove(funcionario);
                    transaction.commit();
                    System.out.println("Funcionário removido com sucesso!");
                }
            } else {
                transaction.rollback();
                System.out.println("Funcionário não encontrado.");
            }
        } catch (Exception e) {
            if (transaction.isActive()) transaction.rollback();
            e.printStackTrace();
        }
    }

}
