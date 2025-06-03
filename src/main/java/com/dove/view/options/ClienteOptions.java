package com.dove.view.options;

import com.dove.view.ClienteView;
import java.util.Scanner;

public class ClienteOptions {

    private final Scanner scanner;
    private final ClienteView view;

    public ClienteOptions(Scanner scanner) {
        this.scanner = scanner;
        this.view = new ClienteView(scanner);
    }

    public void caseEntidades() {
        int opcao;

        do {
            System.out.println("---------MENU---------");
            System.out.println("1- Cadastar Cliente");
            System.out.println("2- Alterar Senha");
            System.out.println("3- Excluir Conta");
            System.out.println("4- Exibir Lista de Clietes");
            System.out.println("5- Exibir Pedidos do Cliente");
            System.out.println("6- Exibir Clientes que Mais Faz Pedido");
            System.out.println("0- Sair ?");
            opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1 -> view.cadastrarCliente();
                case 2 -> view.alterarSenha();
                case 3 -> view.excluirCliente();
                case 4 -> view.exibirClientes();
                case 5 -> view.exibirPedidosCliente();
                case 6 -> view.exibirClientesComMaisPedidos();
                case 0 -> System.out.println("Encerrando o sistema...");
                default -> System.out.println("Opção inválida.");
            }
        } while (opcao != 0);
    }
}