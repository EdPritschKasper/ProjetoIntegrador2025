package com.dove.controller;

import com.dove.model.entities.ClienteEntity;
import com.dove.model.service.ClienteService;

public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController() {
        this.clienteService = new ClienteService();
    }

    public void salvarCliente(String nome, String email, String senha) {
        ClienteEntity cliente = new ClienteEntity(nome, email, senha);
        clienteService.cadastrarCliente(cliente);
    }

    public ClienteEntity autenticar(String email, String senha) {
        return clienteService.buscarPorEmailESenha(email, senha);
    }

    public ClienteEntity findByEmail(String email) {
        return clienteService.buscarClientePorEmail(email);
    }
}
