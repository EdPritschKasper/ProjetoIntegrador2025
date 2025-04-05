package com.dove;

import jakarta.persistence.EntityManager;
import com.dove.repository.*;
import com.dove.entities.*;

import java.time.LocalTime;

public class Main {
    public static void main(String[] args) {

        EntityManager em = CustomizerFactory.getEntityManager();

        PedidoRepository pedidoRepository = new PedidoRepository(em);

        // EXEMPLO INSERT
//        PedidoEntity pedidoEntity = new PedidoEntity("grande", "pronto", LocalTime.parse("12:20:05"), LocalTime.parse("12:40:05"), 1L, 1L, 1L);
//        pedidoRepository.insert(pedidoEntity);

        // EXEMPLO READ
//        System.out.println(pedidoRepository.findById(1L));

        // EXEMPLO UPDATE
//        PedidoEntity pedidoEntity2 = new PedidoEntity(5L, "pequeno", "pronto", LocalTime.parse("12:20:05"), LocalTime.parse("12:40:05"), 1L, 1L, 1L);
//        pedidoRepository.update(pedidoEntity2);

        // EXEMPLO DELETE
//        pedidoRepository.delete(pedidoEntity2);
    }
}