package com.dove.config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JPAUtil {

    private static final EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("default"); // nome do persistence.xml

    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
}
