package com.dove.view.options;

import java.util.Scanner;

import com.dove.view.FuncionarioView;
import com.dove.controller.FuncionarioController;
import com.dove.model.service.FuncionarioService;
import com.dove.model.repository.CustomizerFactory;
import jakarta.persistence.EntityManager;

public class FuncionarioOptions {

    private final Scanner scanner;
    private final FuncionarioView view;

    public FuncionarioOptions(Scanner scanner) {
        this.scanner = scanner;

        EntityManager em = CustomizerFactory.getEntityManager();

        FuncionarioService funcionarioService = new FuncionarioService(em);

        FuncionarioController funcionarioController = new FuncionarioController(funcionarioService);

        this.view = new FuncionarioView(funcionarioController, scanner);
    }

    public void caseEntidade() {
        int controle = 0;

        do {
            System.out.println("---------------------------------");
            System.out.println("  OPÇÕES DE CRUD DO FUNCIONÁRIO  ");
            System.out.println("---------------------------------");
            System.out.println("Digite a opção desejada:");
            System.out.println("1 - Cadastrar Funcionário");
            System.out.println("2 - Listar todos os Funcionários");
            System.out.println("3 - Atualizar um Funcionário");
            System.out.println("4 - Deletar um Funcionário");
            System.out.println("5 - Gerar Relatório de Pedidos");
            System.out.println("0 - Voltar ao Menu Principal");
            System.out.println("---------------------------------");

            if (scanner.hasNextInt()) {
                controle = scanner.nextInt();
                scanner.nextLine();
            } else {
                System.out.println("Entrada inválida. Por favor, digite um número.");
                scanner.next();
                controle = -1;
                continue;
            }

            switch (controle) {
                case 1 -> view.telaDeCadastro();
                case 2 -> view.telaDeListagem();
                case 3 -> view.telaDeAtualizacao();
                case 4 -> view.telaDeRemocao();
                case 5 -> view.telaDeRelatorio();
                case 0 -> System.out.println("Voltando ao menu principal...");
                default -> System.out.println("Opção inválida. Tente novamente.");
            }

        } while (controle != 0);
    }
}