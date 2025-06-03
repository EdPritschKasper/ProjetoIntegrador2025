package com.dove.controller;

import com.dove.model.entities.ClienteEntity;
import com.dove.model.service.ClienteService;

import java.util.List;
import java.util.Scanner;

public class ClienteController {
    private final ClienteService clienteService;
    private final Scanner scanner;

    public ClienteController(Scanner scanner) {
        this.clienteService = new ClienteService();
        this.scanner = scanner;
    }

    public ClienteEntity findByEmail(String email) {
        return clienteService.buscarClientePorEmail(email);
    }

    public void salvarCliente(ClienteEntity cliente) {
        clienteService.cadastrarCliente(cliente);
    }

    public boolean alterarSenha(String email, String senha) {
        return clienteService.alterarSenha(email, senha);
    }

    public boolean excluirCliente(String email) {
        return clienteService.excluirCliente(email);
    }

    public List<ClienteEntity> exibirClientes() {
        return clienteService.exibirClientes();
    }

    public List<ClienteEntity> getClientesComMaisPedidos() {
        return clienteService.exibirClientesComMaisPedidos();
    }
}