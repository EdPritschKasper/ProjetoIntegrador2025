package com.dove;

import com.dove.repository.*;
import jakarta.persistence.EntityManager;

public class Main {
    public static void main(String[] args) {

        EntityManager em = CustomizerFactory.getEntityManager();

        PedidoRepository pedidoRepository = new PedidoRepository(em);

        System.out.println(pedidoRepository.findById(1L));
    }
}