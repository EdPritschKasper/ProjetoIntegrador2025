package com.dove.view.viewPedido;

import com.dove.model.entities.CardapiosEntity;
import com.dove.model.entities.ClienteEntity;
import com.dove.model.entities.FuncionarioEntity;
import com.dove.model.entities.PedidoEntity;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PedidoFillerData {
    private List<PedidoEntity> pedidos;
    public PedidoFillerData(){
         this.pedidos = new ArrayList<PedidoEntity>();
//        pedidos.add(new PedidoEntity(Math.abs(new Random().nextLong()), "Pequena", "Pronto", LocalTime.parse("11:30"), LocalTime.parse("15:30"), new CardapiosEntity(), new FuncionarioEntity(), new ClienteEntity()));
//        pedidos.add(new PedidoEntity(Math.abs(new Random().nextLong()), "Média", "Pronto", LocalTime.parse("12:00"), LocalTime.parse("12:30"), new CardapiosEntity(), new FuncionarioEntity(), new ClienteEntity()));
//        pedidos.add(new PedidoEntity(Math.abs(new Random().nextLong()), "Grande", "Iniciado", LocalTime.parse("12:30"), null, new CardapiosEntity(), new FuncionarioEntity(), new ClienteEntity()));
    }

    public List<PedidoEntity> getPedidos() {
        return pedidos;
    }

    public void addPedido(PedidoEntity pedido){
        this.pedidos.add(pedido);
    }
}
