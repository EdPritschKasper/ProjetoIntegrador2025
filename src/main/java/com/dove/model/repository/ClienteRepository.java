package com.dove.model.repository;

import com.dove.model.entities.ClienteEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.hibernate.cfg.Configuration;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.ArrayList;

public class ClienteRepository {

    private static final SessionFactory sessionFactory;

    static {
        try {
            sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Falha na criação do SessionFactory: " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public ClienteEntity findById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(ClienteEntity.class, id);
        }
    }

    public ClienteEntity findByEmail(String email) {
        try (Session session = sessionFactory.openSession()){
            Query<ClienteEntity> query = session.createQuery(
                "SELECT c FROM ClienteEntity c LEFT JOIN FETCH c.pedidos WHERE c.email = :email", 
                ClienteEntity.class);
            query.setParameter("email", email);
            return query.uniqueResult();
        }
    }

    public void salvarCliente(ClienteEntity cliente) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();
            session.persist(cliente);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw new RuntimeException("Erro ao salvar cliente: " + e.getMessage(), e);
        }
    }

    public boolean alterarSenha(String email, String novaSenha) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();

            Query<ClienteEntity> query = session.createQuery(
                    "FROM ClienteEntity WHERE email = :email", ClienteEntity.class);
            query.setParameter("email", email);
            ClienteEntity cliente = query.uniqueResult();

            if (cliente == null) {
                return false;
            }

            cliente.alterarSenha(novaSenha);
            session.merge(cliente);

            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw new RuntimeException("Erro ao alterar senha: " + e.getMessage(), e);
        }
    }

    public boolean excluirCliente(String email) {
        Transaction tx = null;
        try (Session session = sessionFactory.openSession()) {
            tx = session.beginTransaction();

            Query<ClienteEntity> query = session.createQuery(
                    "FROM ClienteEntity WHERE email = :email", ClienteEntity.class);
            query.setParameter("email", email);
            ClienteEntity cliente = query.uniqueResult();

            if (cliente == null) {
                return false;
            }

            session.remove(cliente);
            tx.commit();
            return true;
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            throw new RuntimeException("Erro ao excluir cliente: " + e.getMessage(), e);
        }
    }

    public List <ClienteEntity> exibirClientes(){
        try (Session session = sessionFactory.openSession()){
            return session.createQuery("FROM ClienteEntity", ClienteEntity.class).list();
        }
        catch (Exception e){
            throw new RuntimeException("Erro ao exibir Clientes: "+ e.getMessage(),e);
        }

    }

    public List<ClienteEntity> getClientesComMaisPedidos() {
        try (Session session = sessionFactory.openSession()){
            Query<String> emailQuery = session.createQuery(
                "SELECT c.email FROM ClienteEntity c LEFT JOIN c.pedidos p " +
                "GROUP BY c.id, c.email, c.nome, c.senha " +
                "ORDER BY COUNT(p) DESC", String.class);
            emailQuery.setMaxResults(3);
            List<String> topEmails = emailQuery.list();
            
            if (topEmails.isEmpty()) {
                return new ArrayList<>();
            }
            
            Query<ClienteEntity> fetchQuery = session.createQuery(
                "SELECT DISTINCT c FROM ClienteEntity c LEFT JOIN FETCH c.pedidos WHERE c.email IN (:emails)", 
                ClienteEntity.class);
            fetchQuery.setParameter("emails", topEmails);
            List<ClienteEntity> clientes = fetchQuery.list();

            clientes.sort((c1, c2) -> Integer.compare(topEmails.indexOf(c1.getEmail()), topEmails.indexOf(c2.getEmail())));
            return clientes;
        }
    }

    public void fechar() {
        sessionFactory.close();
    }
}