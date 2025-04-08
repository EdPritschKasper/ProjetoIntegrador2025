package com.dove.repository;

import com.dove.entities.Funcionario;
import jakarta.persistence.*;

import java.util.List;

public class FuncionarioRepository {

    private final EntityManager entityManager;

    public FuncionarioRepository(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    public void salvar (Funcionario funcionario){
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

    public Funcionario buscarPorId(Long id){
        return entityManager.find(Funcionario.class, id);
    }

    public List<Funcionario> buscarTodos(){
        TypedQuery<Funcionario> query = entityManager.createQuery("SELECT f FROM Funcionario f", Funcionario.class);
        return query.getResultList();
    }

    public void atualizar (Funcionario funcionario){
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

    public void deletar (Long id){
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Funcionario funcionario = entityManager.find(Funcionario.class, id);
            if (funcionario != null){
                entityManager.remove(funcionario);
            }
            transaction.commit();
        }catch (Exception e){
            if (transaction.isActive()) transaction.rollback();
            e.printStackTrace();
            }
    }
}


