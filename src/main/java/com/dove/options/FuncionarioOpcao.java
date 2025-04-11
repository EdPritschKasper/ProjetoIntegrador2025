package com.dove.options;

import com.dove.entities.FuncionarioEntity;
import com.dove.entities.PedidoEntity;
import com.dove.repository.CustomizerFactory;
import com.dove.repository.FuncionarioRepository;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Scanner;

public class FuncionarioOpcao {

    public void caseEntidades() {
        EntityManager em = CustomizerFactory.getEntityManager();
        FuncionarioRepository funcionarioRepository = new FuncionarioRepository(em);
        Scanner scanner = new Scanner(System.in);

        int opcao;

        do {
            System.out.println("\n--- MENU FUNCIONÁRIO ---");
            System.out.println("1 - Cadastrar funcionário");
            System.out.println("2 - Listar funcionários");
            System.out.println("3 - Atualizar funcionário");
            System.out.println("4 - Remover funcionário");
            System.out.println("5 - Relatório de pedidos realizados");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");

            opcao = scanner.nextInt();
            scanner.nextLine(); // Limpa o buffer

            if (opcao == 0) {
                break; // Sai do loop sem cair no switch
            }

            switch (opcao) {
                case 1 -> {
                    System.out.print("Digite o nome do funcionário: ");
                    String nome = scanner.nextLine();

                    FuncionarioEntity novoFuncionario = new FuncionarioEntity();
                    novoFuncionario.setNome(nome);

                    funcionarioRepository.salvar(novoFuncionario);
                    System.out.println("Funcionário cadastrado com sucesso!");
                }

                case 2 -> {
                    List<FuncionarioEntity> funcionarios = funcionarioRepository.buscarTodos();
                    System.out.println("\n--- Lista de Funcionários ---");
                    for (FuncionarioEntity f : funcionarios) {
                        System.out.println("ID: " + f.getId() + " | Nome: " + f.getNome());
                    }
                }

                case 3 -> {
                    System.out.print("Digite o ID do funcionário a ser atualizado: ");
                    Long id = scanner.nextLong();
                    scanner.nextLine();

                    FuncionarioEntity funcionario = funcionarioRepository.buscarPorId(id);
                    if (funcionario != null) {
                        System.out.print("Digite o novo nome: ");
                        String novoNome = scanner.nextLine();
                        funcionario.setNome(novoNome);

                        funcionarioRepository.atualizar(funcionario);
                        System.out.println("Funcionário atualizado com sucesso!");
                    } else {
                        System.out.println("Funcionário não encontrado.");
                    }
                }

                case 4 -> {
                    System.out.print("Digite o ID do funcionário a ser removido: ");
                    Long id = scanner.nextLong();
                    scanner.nextLine();

                    funcionarioRepository.deletar(id);
                }

                case 5 -> {
                    System.out.print("Digite o ID do funcionário para gerar o relatório: ");
                    Long id = scanner.nextLong();
                    scanner.nextLine();

                    FuncionarioEntity funcionario = funcionarioRepository.buscarPorId(id);
                    if (funcionario != null) {
                        List<PedidoEntity> pedidos = funcionario.getPedidos();

                        System.out.println("\n--- Relatório de Pedidos ---");
                        System.out.println("Funcionário: " + funcionario.getNome());
                        System.out.println("Total de pedidos: " + pedidos.size());

                        for (PedidoEntity pedido : pedidos) {
                            System.out.println("Pedido ID: " + pedido.getId());
                            System.out.println("Status: " + pedido.getStatus());
                            System.out.println("Hora de início: " + pedido.getHora_inicio());
                            System.out.println("Hora de fim: " + pedido.getHora_fim());
                            System.out.println("-----------------------------");
                        }

                    } else {
                        System.out.println("Funcionário não encontrado.");
                    }
                }
                default -> System.out.println("Opção inválida. Tente novamente.");
            }

        } while (opcao != 0);

        em.close();
    }
}
