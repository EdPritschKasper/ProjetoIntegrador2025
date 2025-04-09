package com.dove.options;

import com.dove.entities.Funcionario;
import com.dove.repository.CustomizerFactory;
import com.dove.repository.FuncionarioRepository;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Scanner;

public class FuncionarioOpcao {

    public void caseFuncionario() {
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

                    Funcionario novoFuncionario = new Funcionario();
                    novoFuncionario.setNome(nome);

                    funcionarioRepository.salvar(novoFuncionario);
                    System.out.println("Funcionário cadastrado com sucesso!");
                }

                case 2 -> {
                    List<Funcionario> funcionarios = funcionarioRepository.buscarTodos();
                    System.out.println("\n--- Lista de Funcionários ---");
                    for (Funcionario f : funcionarios) {
                        System.out.println("ID: " + f.getId() + " | Nome: " + f.getNome());
                    }
                }

                case 3 -> {
                    System.out.print("Digite o ID do funcionário a ser atualizado: ");
                    Long id = scanner.nextLong();
                    scanner.nextLine();

                    Funcionario funcionario = funcionarioRepository.buscarPorId(id);
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

                default -> System.out.println("Opção inválida. Tente novamente.");
            }

        } while (opcao != 0);

        em.close();
    }
}
