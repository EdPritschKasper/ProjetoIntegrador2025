package ProjetoMensalRestaurante.Repository;

import ProjetoMensalRestaurante.Entity.ClienteEntity;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.hibernate.cfg.Configuration;
import org.hibernate.SessionFactory;

import java.util.List;

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

    public void fechar() {
        sessionFactory.close();
    }
}