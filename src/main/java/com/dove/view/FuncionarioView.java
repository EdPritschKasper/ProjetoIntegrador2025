package com.dove.view;

import com.dove.controller.FuncionarioController;
import com.dove.model.entities.FuncionarioEntity;
import com.dove.model.entities.PedidoEntity;

import java.util.List;
import java.util.Scanner;

public class FuncionarioView {

    private final FuncionarioController funcionarioController;
    private final Scanner scanner;

    public FuncionarioView(FuncionarioController funcionarioController, Scanner scanner) {
        this.funcionarioController = funcionarioController;
        this.scanner = scanner;
    }

    public void telaDeCadastro() {
        System.out.println("\n--- Cadastro de Novo Funcionário ---");
        System.out.print("Digite o nome: ");
        String nome = scanner.nextLine();
        System.out.print("Digite o CPF: ");
        String cpf = scanner.nextLine();

        FuncionarioEntity novoFuncionario = new FuncionarioEntity();
        novoFuncionario.setNome(nome);
        novoFuncionario.setCpf(cpf);

        // Chama o controller e usa o retorno booleano para informar o usuário
        boolean sucesso = funcionarioController.cadastrarFuncionario(novoFuncionario);

        if (sucesso) {
            System.out.println(">>> Funcionário cadastrado com sucesso!");
        } else {
            System.out.println(">>> Erro: Não foi possível cadastrar. O CPF pode já existir ou ser inválido.");
        }
    }

    public void telaDeListagem() {
        System.out.println("\n--- Lista de Funcionários Cadastrados ---");
        List<FuncionarioEntity> funcionarios = funcionarioController.listarFuncionarios();

        if (funcionarios.isEmpty()) {
            System.out.println("Nenhum funcionário encontrado.");
        } else {
            for (FuncionarioEntity funcionario : funcionarios) {
                System.out.printf("ID: %d | Nome: %s | CPF: %s\n",
                        funcionario.getId(),
                        funcionario.getNome(),
                        funcionario.getCpf());
            }
        }
    }

    public void telaDeAtualizacao() {
        System.out.println("\n--- Atualização de Funcionário ---");
        System.out.print("Digite o ID do funcionário que deseja atualizar: ");
        long id = scanner.nextLong();
        scanner.nextLine();

        FuncionarioEntity funcionario = funcionarioController.buscarFuncionarioPorId(id);
        if (funcionario == null) {
            System.out.println(">>> Erro: Funcionário com o ID " + id + " não encontrado.");
            return;
        }

        System.out.println("Deixe o campo em branco e pressione Enter para não alterar a informação.");
        System.out.print("Novo nome (" + funcionario.getNome() + "): ");
        String nome = scanner.nextLine();

        System.out.print("Novo CPF (" + funcionario.getCpf() + "): ");
        String cpf = scanner.nextLine();

        if (nome != null && !nome.isBlank()) {
            funcionario.setNome(nome);
        }
        if (cpf != null && !cpf.isBlank()) {
            funcionario.setCpf(cpf);
        }

        boolean sucesso = funcionarioController.atualizarFuncionario(funcionario);

        if (sucesso) {
            System.out.println(">>> Funcionário atualizado com sucesso!");
        } else {
            System.out.println(">>> Erro: Não foi possível atualizar. O novo CPF pode já pertencer a outro funcionário.");
        }
    }

    public void telaDeRemocao() {
        System.out.println("\n--- Remoção de Funcionário ---");
        System.out.print("Digite o ID do funcionário a ser removido: ");
        long id = scanner.nextLong();
        scanner.nextLine();

        boolean sucesso = funcionarioController.removerFuncionario(id);

        if (sucesso) {
            System.out.println(">>> Funcionário removido com sucesso!");
        } else {
            System.out.println(">>> Erro: Não foi possível remover o funcionário. Verifique o ID e se ele não possui pedidos vinculados.");
        }
    }

    public void telaDeRelatorio() {
        System.out.println("\n--- Relatório de Pedidos por Funcionário ---");
        System.out.print("Digite o ID do funcionário: ");
        long id = scanner.nextLong();
        scanner.nextLine();

        FuncionarioEntity funcionario = funcionarioController.buscarFuncionarioPorId(id);
        if (funcionario == null) {
            System.out.println(">>> Erro: Funcionário não encontrado.");
            return;
        }

        List<PedidoEntity> pedidos = funcionarioController.buscarPedidosPorFuncionario(id);

        System.out.println("\n======================================");
        System.out.println("           RELATÓRIO DE PEDIDOS        ");
        System.out.println("======================================");
        System.out.printf("Funcionário: %s (ID: %d)\n", funcionario.getNome(), funcionario.getId());
        System.out.printf("Total de pedidos: %d\n", pedidos.size());
        System.out.println("--------------------------------------");

        if (pedidos.isEmpty()) {
            System.out.println("Nenhum pedido registrado para este funcionário.");
        } else {
            for (PedidoEntity pedido : pedidos) {
                System.out.println("Pedido ID: " + pedido.getId() + " | Status: " + pedido.getStatus());
            }
        }
        System.out.println("======================================");
    }
}