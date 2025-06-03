package com.dove.model.service;

import com.dove.model.entities.ClienteEntity;
import com.dove.model.repository.ClienteRepository;

import java.util.List;

public class ClienteService {
    private final ClienteRepository clienteRepository;

    public ClienteService() {
        this.clienteRepository = new ClienteRepository();
    }

    public ClienteEntity buscarClientePorEmail(String email) {
        return clienteRepository.findByEmail(email);
    }

    public void cadastrarCliente(ClienteEntity cliente) {
        clienteRepository.salvarCliente(cliente);
    }

    public boolean alterarSenha(String email, String senha) {
        return clienteRepository.alterarSenha(email, senha);
    }

    public boolean excluirCliente(String email) {
        return clienteRepository.excluirCliente(email);
    }

    public List<ClienteEntity> exibirClientes() {
        return clienteRepository.exibirClientes();
    }

    public List<ClienteEntity> exibirClientesComMaisPedidos() {
        return clienteRepository.getClientesComMaisPedidos();
    }
}