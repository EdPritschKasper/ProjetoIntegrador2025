package com.dove.view.viewPedido;

import com.dove.model.entities.CardapiosEntity;
import com.dove.model.entities.ClienteEntity;
import com.dove.model.entities.FuncionarioEntity;
import com.dove.model.entities.PedidoEntity;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class PedidoFillerData {
    private List<PedidoEntity> pedidos;
    public PedidoFillerData(){
         this.pedidos = new ArrayList<PedidoEntity>();
        pedidos.add(new PedidoEntity(1L, "pequena", "pronto", LocalTime.now(), LocalTime.now(), new CardapiosEntity(), new FuncionarioEntity(), new ClienteEntity()));
        pedidos.add(new PedidoEntity(1L, "pequena", "pronto", LocalTime.now(), LocalTime.now(), new CardapiosEntity(), new FuncionarioEntity(), new ClienteEntity()));
        pedidos.add(new PedidoEntity(1L, "pequena", "pronto", LocalTime.now(), LocalTime.now(), new CardapiosEntity(), new FuncionarioEntity(), new ClienteEntity()));
    }

    public List<PedidoEntity> getPedidos() {
        return pedidos;
    }

    public void addPedido(PedidoEntity pedido){
        this.pedidos.add(pedido);
    }
}
