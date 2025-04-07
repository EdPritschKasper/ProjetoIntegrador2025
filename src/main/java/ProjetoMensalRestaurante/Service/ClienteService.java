package ProjetoMensalRestaurante.Service;

import ProjetoMensalRestaurante.Entity.ClienteEntity;
import ProjetoMensalRestaurante.Repository.ClienteRepository;

import java.util.Scanner;

public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final Scanner scanner;

    public ClienteService(Scanner scanner) {
        this.clienteRepository = new ClienteRepository();
        this.scanner = scanner;
    }

    public void executarOpcao(int opcao) {
        switch (opcao) {
            case 1 -> cadastrarCliente();
            case 2 -> alterarSenha();
            case 3 -> excluirCliente();
            case 4 -> exibirClientes();
            case 0 -> System.out.println("Encerrando o sistema...");
            default -> System.out.println("Opção inválida.");
        }
    }

    private void cadastrarCliente() {
        try {
            System.out.print("Nome: ");
            String nome = scanner.nextLine();

            System.out.print("Email: ");
            String email = scanner.nextLine();

            System.out.print("Senha: ");
            String senha = scanner.nextLine();

            ClienteEntity cliente = new ClienteEntity(nome, email, senha);
            clienteRepository.salvarCliente(cliente);

            System.out.println("Cliente cadastrado com sucesso!");
        }
        catch (Exception e) {
            System.out.println("Erro ao cadastrar cliente: " + e.getMessage());
        }
    }

    private void alterarSenha() {
        try {
            System.out.print("Email: ");
            String email = scanner.nextLine();

            System.out.print("Nova senha: ");
            String novaSenha = scanner.nextLine();

            boolean alterado = clienteRepository.alterarSenha(email, novaSenha);
            if (alterado) {
                System.out.println("Senha alterada com sucesso!");
            }
            else {
                System.out.println("Cliente não encontrado.");
            }
        } catch (Exception e) {
            System.out.println("Erro ao alterar senha: " + e.getMessage());
        }
    }

    private void excluirCliente() {
        try {
            System.out.print("Email do cliente a excluir: ");
            String email = scanner.nextLine();

            boolean excluido = clienteRepository.excluirCliente(email);
            if (excluido) {
                System.out.println("Cliente excluído com sucesso!");
            }
            else {
                System.out.println("Cliente não encontrado.");
            }
        } catch (Exception e) {
            System.out.println("Erro ao excluir cliente: " + e.getMessage());
        }
    }

    private void exibirClientes() {
        System.out.println("-----Lista de Clientes-----");

        var clientes = clienteRepository.exibirClientes();

        if (clientes.isEmpty()){
            System.out.println("Nenhum Cliente cadastrado.");
        }
        else {

            for (ClienteEntity cliente : clientes){
                System.out.println("Nome: "+ cliente.getNome());
                System.out.println("Email: " + cliente.getEmail());
            }
        }
    }
}
