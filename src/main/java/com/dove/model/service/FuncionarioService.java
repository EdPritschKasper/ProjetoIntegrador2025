package com.dove.model.service;

import com.dove.model.entities.FuncionarioEntity;
import com.dove.model.entities.PedidoEntity;
import com.dove.model.repository.FuncionarioRepository;
import jakarta.persistence.EntityManager;
import java.util.List;

public class FuncionarioService {

    private final FuncionarioRepository funcionarioRepository;

    public FuncionarioService(EntityManager em) {
        this.funcionarioRepository = new FuncionarioRepository(em);
    }

    public boolean cadastrarFuncionario(FuncionarioEntity funcionario) {
        if (funcionario.getCpf() == null || funcionario.getCpf().trim().isEmpty()) {
            return false;
        }

        FuncionarioEntity existente = funcionarioRepository.buscarPorCpf(funcionario.getCpf());
        if (existente != null) {
            return false;
        }
        return funcionarioRepository.salvar(funcionario);
    }

    public List<FuncionarioEntity> listarFuncionarios() {
        return funcionarioRepository.buscarTodos();
    }

    public boolean atualizarFuncionario(FuncionarioEntity funcionario) {
        FuncionarioEntity existenteComCpf = funcionarioRepository.buscarPorCpf(funcionario.getCpf());
        if (existenteComCpf != null && !existenteComCpf.getId().equals(funcionario.getId())) {
            return false;
        }
        return funcionarioRepository.atualizar(funcionario);
    }

    public boolean removerFuncionario(Long id) {
        return funcionarioRepository.deletar(id);
    }

    public FuncionarioEntity buscarFuncionarioPorId(Long id) {
        return funcionarioRepository.buscarPorId(id);
    }

    public List<PedidoEntity> buscarPedidosPorFuncionario(Long id) {
        return funcionarioRepository.buscarPedidosPorFuncionario(id);
    }
}