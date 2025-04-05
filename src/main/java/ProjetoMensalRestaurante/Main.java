package ProjetoMensalRestaurante;

import ProjetoMensalRestaurante.Service.ClienteService;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ClienteService clienteService = new ClienteService(scanner); // Scanner passado

        int opcao;

        do {
            System.out.println("\n=== MENU ===");
            System.out.println("1 - Cadastrar cliente");
            System.out.println("2 - Alterar senha");
            System.out.println("3 - Excluir cliente");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");
            opcao = scanner.nextInt();
            scanner.nextLine(); // limpar buffer

            clienteService.executarOpcao(opcao);

        } while (opcao != 0);

        scanner.close();
    }
}
