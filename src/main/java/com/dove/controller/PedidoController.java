package com.dove.controller;

import com.dove.service.PedidoService;
import com.dove.view.PedidoView;
import java.util.Scanner;

public class PedidoController {
    private final PedidoService pedidoService;
    private final PedidoView pedidoView;
    private final Scanner scanner;

    public PedidoController(Scanner scanner) {
        this.scanner = scanner;
        this.pedidoService = new PedidoService(scanner);
        this.pedidoView = new PedidoView(scanner);
    }

    public void executar() {
        int controle;
        do {
            controle = pedidoView.exibirMenu();
            switch (controle) {
                case 1 -> pedidoService.adicionaPedido();
                case 2 -> pedidoService.pesquisaPedido();
                case 3 -> pedidoService.atualizaPedido();
                case 4 -> pedidoService.deletaPedido();
                case 5 -> pedidoService.historicoPedido();
                case 6 -> pedidoService.tempoMedioPedidos();
                case 0 -> System.out.println("Saindo de Pedidos...");
                default -> System.out.println("Opção inválida.");
            }
        } while (controle != 0);
    }
}
