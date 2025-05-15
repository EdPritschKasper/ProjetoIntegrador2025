package com.dove.Service;

import com.dove.entities.FuncionarioEntity;
import com.dove.entities.PedidoEntity;
import com.dove.repository.FuncionarioRepository;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Scanner;

public class FuncionarioService {

    private final FuncionarioRepository funcionarioRepository;

    public FuncionarioService(EntityManager em) {
        this.funcionarioRepository = new FuncionarioRepository(em);
    }

    public void cadastrarFuncionario(String nome, String cpf) {
        if (cpf == null || cpf.trim().isEmpty()) {
            System.out.println("CPF não pode ser vazio.");
            return;
        }
        FuncionarioEntity existente = funcionarioRepository.buscarPorCpf(cpf);
        if (existente != null) {
            System.out.println("Já existe um funcionário com esse CPF cadastrado.");
        } else {
            FuncionarioEntity novoFuncionario = new FuncionarioEntity();
            novoFuncionario.setNome(nome);
            novoFuncionario.setCpf(cpf);
            funcionarioRepository.salvar(novoFuncionario);
            System.out.println("Funcionário cadastrado com sucesso!");
        }
    }

    public void listarFuncionarios() {
        List<FuncionarioEntity> funcionarios = funcionarioRepository.buscarTodos();
        System.out.println("\n--- Lista de Funcionários ---");
        for (FuncionarioEntity f : funcionarios) {
            System.out.println("ID: " + f.getId() + " | Nome: " + f.getNome() + " | CPF: " + f.getCpf());
        }
    }

    public void atualizarFuncionarioeCpf(Scanner scanner) {
        System.out.print("Digite o ID do funcionário a ser atualizado: ");
        Long id = scanner.nextLong();
        scanner.nextLine(); // limpar buffer

        FuncionarioEntity funcionario = funcionarioRepository.buscarPorId(id);
        if (funcionario == null) {
            System.out.println("Funcionário não encontrado.");
            return;
        }

        System.out.println("O que você deseja atualizar?");
        System.out.println("1 - Nome");
        System.out.println("2 - CPF");
        System.out.println("3 - Ambos");
        System.out.print("Escolha uma opção: ");
        int escolha = scanner.nextInt();
        scanner.nextLine(); // limpar buffer

        switch (escolha) {
            case 1 -> {
                System.out.print("Digite o novo nome: ");
                String novoNome = scanner.nextLine();
                funcionario.setNome(novoNome);
            }
            case 2 -> {
                System.out.print("Digite o novo CPF: ");
                String novoCpf = scanner.nextLine();

                FuncionarioEntity existente = funcionarioRepository.buscarPorCpf(novoCpf);
                if (existente != null && !existente.getId().equals(funcionario.getId())) {
                    System.out.println("Já existe um funcionário com esse CPF.");
                    return;
                }

                funcionario.setCpf(novoCpf);
            }
            case 3 -> {
                System.out.print("Digite o novo nome: ");
                String novoNome = scanner.nextLine();
                funcionario.setNome(novoNome);

                System.out.print("Digite o novo CPF: ");
                String novoCpf = scanner.nextLine();

                FuncionarioEntity existente = funcionarioRepository.buscarPorCpf(novoCpf);
                if (existente != null && !existente.getId().equals(funcionario.getId())) {
                    System.out.println("Já existe um funcionário com esse CPF.");
                    return;
                }

                funcionario.setCpf(novoCpf);
            }
            default -> {
                System.out.println("Opção inválida.");
                return;
            }
        }

        funcionarioRepository.atualizar(funcionario);
        System.out.println("Funcionário atualizado com sucesso!");
    }

    public void atualizarFuncionarioInterativo(Scanner scanner) {
    }

    public void removerFuncionario(Long id) {
        funcionarioRepository.deletar(id);
    }

    public void relatorioPedidos(Long id) {
        FuncionarioEntity funcionario = funcionarioRepository.buscarPorId(id);
        if (funcionario != null) {
            // Buscar sempre os pedidos atualizados no banco
            List<PedidoEntity> pedidos = funcionarioRepository.buscarPedidosPorFuncionario(id);

            System.out.println("\n======================================");
            System.out.println("           RELATÓRIO DE PEDIDOS        ");
            System.out.println("======================================");
            System.out.printf("Funcionário: %-20s\n", funcionario.getNome());
            System.out.printf("CPF:         %-20s\n", funcionario.getCpf());
            System.out.printf("Total de pedidos: %d\n", pedidos.size());
            System.out.println("--------------------------------------");

            if (pedidos.isEmpty()) {
                System.out.println("Nenhum pedido registrado para este funcionário.");
            } else {
                for (PedidoEntity pedido : pedidos) {
                    System.out.println("Pedido ID:      " + pedido.getId());
                    System.out.println("Status:         " + pedido.getStatus());
                    System.out.println("Hora de início: " + pedido.getHora_inicio());
                    System.out.println("Hora de fim:    " + pedido.getHora_fim());
                    System.out.println("--------------------------------------");
                }
            }
        } else {
            System.out.println("Funcionário não encontrado.");
        }
    }

}
