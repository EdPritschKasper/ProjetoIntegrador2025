package com.dove.controller;

import com.dove.model.entities.*;
import com.dove.model.service.*;

import java.time.Duration;
import java.util.List;

public class PedidoController {

    private PedidoService pedidoService;

    public PedidoController(){
        this.pedidoService = new PedidoService();
    }

    // funcoes do adiciona pedido
    public CardapiosEntity getCardapioHoje(){
        return pedidoService.getCardapioHoje();
    }

    public ClienteEntity getClienteById(Long id){
        return pedidoService.getClienteById(id);
    }

    public FuncionarioEntity getFuncionarioById(Long id){
        return pedidoService.getFuncionarioById(id);
    }

    public boolean insertPedido(PedidoEntity pedido){
        return pedidoService.insertPedido(pedido);
    }

    // outras funcoes
    public PedidoEntity pesquisaPedido(Long id) {
        return pedidoService.pesquisaPedidoId(id);
    }

    public boolean updatePedido(PedidoEntity pedido){
        return pedidoService.updatePedido(pedido);
    }

    public boolean deletePedido(PedidoEntity pedido){
        return pedidoService.deletePedido(pedido);
    }

    public List<PedidoEntity> findAll(){
        return pedidoService.findAll();
    }

    public Duration buscaTempoMedio(){
        return pedidoService.buscaTempoMedio();
    }
}
