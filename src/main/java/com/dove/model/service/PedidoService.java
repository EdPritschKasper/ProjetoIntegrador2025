package com.dove.model.service;

import com.dove.model.entities.*;
import com.dove.model.repository.*;
import jakarta.persistence.EntityManager;

import java.time.Duration;
import java.util.List;

public class PedidoService {
    private final EntityManager em;
    private final ClienteRepository clienteRepository;
    private final FuncionarioRepository funcionarioRepository;
    private final CardapiosRepository cardapiosRepository;
    private final PedidoRepository pedidoRepository;


    public PedidoService() {
        this.em = CustomizerFactory.getEntityManager();
        this.clienteRepository = new ClienteRepository();
        this.funcionarioRepository = new FuncionarioRepository(em);
        this.cardapiosRepository = new CardapiosRepository(em);
        this.pedidoRepository = new PedidoRepository(em);
    }

    // função auxiliar para validar e pesquisar o ID
    public PedidoEntity pesquisaPedidoId(Long id){
        return pedidoRepository.findById(id);
    }

    // funcoes do create pedido
    public CardapiosEntity getCardapioHoje(){
        return cardapiosRepository.getCardapioHoje();
    }

    public ClienteEntity getClienteById(Long id){
        return clienteRepository.findById(id);
    }

    public FuncionarioEntity getFuncionarioById(Long id){
        return funcionarioRepository.buscarPorId(id);
    }

    public boolean insertPedido(PedidoEntity pedido){
        return pedidoRepository.insert(pedido);
    }

    // outras funcoes
    public boolean updatePedido(PedidoEntity pedido){
        return pedidoRepository.update(pedido);
    }

    public boolean deletePedido(PedidoEntity pedido){
        return pedidoRepository.delete(pedido);
    }

    public List<PedidoEntity> findAll(){
        return pedidoRepository.findAll();
    }

    public Duration buscaTempoMedio(){
        return pedidoRepository.buscaTempoMedio();
    }
}
