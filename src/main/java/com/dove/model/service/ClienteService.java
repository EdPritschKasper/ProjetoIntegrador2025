package com.dove.model.service;

import com.dove.config.JPAUtil;
import com.dove.model.entities.ClienteEntity;
import com.dove.model.repository.ClienteRepository;
import jakarta.persistence.EntityManager;

import java.util.List;

public class ClienteService {

    public void cadastrarCliente(ClienteEntity cliente) {
        EntityManager em = JPAUtil.getEntityManager();
        ClienteRepository repo = new ClienteRepository(em);

        try {
            repo.salvarCliente(cliente);
        } finally {
            if (em.isOpen()) em.close();
        }
    }

    public ClienteEntity buscarPorEmailESenha(String email, String senha) {
        EntityManager em = JPAUtil.getEntityManager();
        ClienteRepository repo = new ClienteRepository(em);

        try {
            return repo.buscarPorEmailESenha(email, senha);
        } finally {
            if (em.isOpen()) em.close();
        }
    }

    public ClienteEntity buscarClientePorEmail(String email) {
        EntityManager em = JPAUtil.getEntityManager();
        ClienteRepository repo = new ClienteRepository(em);

        try {
            return repo.buscarPorEmail(email);
        } finally {
            if (em.isOpen()) em.close();
        }
    }
}
