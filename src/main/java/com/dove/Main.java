package com.dove;

import java.util.List;
import java.util.Scanner;

import com.dove.controller.PedidoController;
import com.dove.model.entities.PedidoEntity;
import com.dove.view.IngredienteView;
import com.dove.view.options.PedidoOptions;
import com.dove.view.options.CardapioOptions;
import com.dove.view.options.IngredienteOptions;
import com.dove.view.options.FuncionarioOptions;
import com.dove.view.options.ClienteOptions;
import com.dove.view.viewLogin.LoginView;
import com.dove.view.viewFuncionario.*;
import com.dove.view.viewPedido.*;
import com.dove.model.service.*;
import jakarta.persistence.EntityManager;
import com.dove.view.viewCliente.ClienteView;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {


//        javax.swing.SwingUtilities.invokeLater(LoginView::new);
        javax.swing.SwingUtilities.invokeLater(TelaPrincipalFuncionarioView::new);
//        javax.swing.SwingUtilities.invokeLater(ClienteView::new);

//        PedidoController controller = new PedidoController();
//        List<PedidoEntity> pedidos = controller.findAll();
//        System.out.println(pedidos);

        // Declaração de variáveis
        Scanner scanner = new Scanner(System.in);
        int controle = 0;



        // Inicialização de Opções
        CardapioOptions cardapioOptions = new CardapioOptions(scanner);
        FuncionarioOptions funcionarioOptions = new FuncionarioOptions(scanner);
        PedidoOptions pedidoOptions = new PedidoOptions(scanner);
        ClienteOptions clienteOptions = new ClienteOptions(scanner);
        IngredienteOptions ingredienteOptions = new IngredienteOptions(scanner);
        IngredienteView ingredienteView = new IngredienteView(scanner);

        // Estrutura de repetição inicial para opções de entidade
        do {
            System.out.println("------------------------------");
            System.out.println("OPÇÕES DE ENTIDADES");
            System.out.println("Digite a opção desejada:");
            System.out.println("1 - Funcionário:");
            System.out.println("2 - Cliente:");
            System.out.println("3 - Cardápio:");
            System.out.println("4 - Ingrediente:");
            System.out.println("5 - Pedido:");
            System.out.println("0 - Sair do programa:");
            System.out.println("------------------------------");

            controle = scanner.nextInt();
            switch (controle) {
                case 1 -> funcionarioOptions.caseEntidade();
                case 2 -> clienteOptions.caseEntidades();
                case 3 -> cardapioOptions.caseEntidade();
                case 4 -> ingredienteOptions.casaEntidade();
                case 5 -> pedidoOptions.caseEntidade();
                case 0 -> System.out.println("Encerrando Sistema...");
                default -> System.out.println("Opção Inválida");
            }

        } while(controle != 0);

        scanner.close();
    }
}