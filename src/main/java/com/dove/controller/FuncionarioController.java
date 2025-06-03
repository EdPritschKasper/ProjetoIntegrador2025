package com.dove.controller;

import com.dove.model.entities.FuncionarioEntity;
import com.dove.model.entities.PedidoEntity; // Import necessário
import com.dove.model.service.FuncionarioService;
import java.util.List;

public class FuncionarioController {

    private FuncionarioService funcionarioService;

    public FuncionarioController(FuncionarioService funcionarioService) {
        this.funcionarioService = funcionarioService;
    }

    public boolean cadastrarFuncionario(FuncionarioEntity funcionario) {
        return funcionarioService.cadastrarFuncionario(funcionario);
    }

    public List<FuncionarioEntity> listarFuncionarios() {
        return funcionarioService.listarFuncionarios();
    }

    public boolean atualizarFuncionario(FuncionarioEntity funcionario) {
        return funcionarioService.atualizarFuncionario(funcionario);
    }

    public boolean removerFuncionario(Long id) {
        return funcionarioService.removerFuncionario(id);
    }

    public FuncionarioEntity buscarFuncionarioPorId(Long id) {
        return funcionarioService.buscarFuncionarioPorId(id);
    }

    public List<PedidoEntity> buscarPedidosPorFuncionario(Long id) {
        return funcionarioService.buscarPedidosPorFuncionario(id);
    }
}