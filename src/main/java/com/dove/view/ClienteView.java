package com.dove.view;

import java.util.Scanner;

public class ClienteView {
    private final Scanner scanner;

    public ClienteView(Scanner scanner) {
        this.scanner = scanner;
    }

    public int exibirMenu() {
        System.out.println("---------MENU---------");
        System.out.println("1- Cadastar Cliente");
        System.out.println("2- Alterar Senha");
        System.out.println("3- Excluir Conta");
        System.out.println("4- Exibir Lista de Clietes");
        System.out.println("5- Exibir Pedidos do Cliente");
        System.out.println("6- Exibir Clientes que Mais Faz Pedido");
        System.out.println("0- Sair ?");
        return scanner.nextInt();
    }
}
