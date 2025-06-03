package com.dove.controller;

import com.dove.model.service.IngredienteService;
import com.dove.view.IngredienteView;
import java.util.Scanner;

public class IngredienteController {
    private final IngredienteService ingredienteService;
    private final IngredienteView ingredienteView;
    private final Scanner scanner;

    public IngredienteController(Scanner scanner) {
        this.scanner = scanner;
        this.ingredienteService = new IngredienteService(scanner);
        this.ingredienteView = new IngredienteView(scanner);
    }

    public void executar() {
        int controle;
        do {
            controle = ingredienteView.exibirMenu();
            scanner.nextLine(); // Limpa buffer
            switch (controle) {
                case 1 -> ingredienteService.cadastrarIngrediente();
                case 2 -> ingredienteService.lerIngrediente();
                case 3 -> ingredienteService.atualizarIngrediente();
                case 4 -> ingredienteService.deletarIngrediente();
                case 5 -> ingredienteService.listarTodosIngredientes();
                case 6 -> ingredienteService.pegarIngredienteMaisSelecionado();
                case 0 -> System.out.println("Saindo de Ingredientes...");
                default -> System.out.println("Opção inválida.");
            }
        } while (controle != 0);
    }
}
