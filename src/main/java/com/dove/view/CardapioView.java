package com.dove.view;

import com.dove.controller.CardapioController;
import com.dove.model.entities.IngredienteEntity;
import com.dove.model.entities.IngredienteEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class CardapioView {
    private final Scanner scanner;
    private final CardapioController controller;

    public CardapioView(Scanner scanner) {
        this.scanner = scanner;
        this.controller = new CardapioController();
    }

    public void adicionarCardapio() {
        System.out.println("--- CADASTRAR CARDÁPIO ---");
        System.out.print("Data (YYYY-MM-DD): ");
        String dataStr = scanner.nextLine();
        try {
            LocalDate data = LocalDate.parse(dataStr);
            System.out.print("Adicionar ingredientes? (s/n): ");
            String resp = scanner.nextLine();
            List<Integer> indicesSelecionados = null;
            if (resp.equalsIgnoreCase("s")) {
                List<IngredienteEntity> ingredientes = controller.findAllIngredientes();
                if (ingredientes.isEmpty()) {
                    System.out.println("Nenhum ingrediente disponível.");
                } else {
                    System.out.println("Ingredientes disponíveis:");
                    for (int i = 0; i < ingredientes.size(); i++) {
                        System.out.println(i + " - " + ingredientes.get(i).getDescricao());
                    }
                    System.out.println("Digite índices dos ingredientes (-1 para sair):");
                    indicesSelecionados = new java.util.ArrayList<>();
                    while (true) {
                        int idx = scanner.nextInt();
                        scanner.nextLine();
                        if (idx == -1) break;
                        if (idx >= 0 && idx < ingredientes.size()) {
                            indicesSelecionados.add(idx);
                            System.out.println("Ingrediente selecionado: " + ingredientes.get(idx).getDescricao());
                        } else {
                            System.out.println("Índice inválido.");
                        }
                    }
                }
            }
            var resultado = controller.cadastrarCardapio(data, indicesSelecionados);
            System.out.println(resultado.getMensagem());
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    public void buscarCardapio() {
        System.out.println("--- BUSCAR CARDÁPIO ---");
        System.out.print("ID do cardápio: ");
        Long id = scanner.nextLong();
        scanner.nextLine();
        var resultado = controller.findCardapioById(id);
        System.out.println(resultado.getMensagem());
        if (resultado.isSucesso()) {
            System.out.println(resultado.getCardapio());
        }
    }

    public void atualizarCardapio() {
        System.out.println("--- ATUALIZAR CARDÁPIO ---");
        System.out.print("ID do cardápio: ");
        Long id = scanner.nextLong();
        scanner.nextLine();
        var resultadoBusca = controller.findCardapioById(id);
        System.out.println(resultadoBusca.getMensagem());
        if (!resultadoBusca.isSucesso()) {
            return;
        }
        System.out.print("Nova data (YYYY-MM-DD): ");
        String dataStr = scanner.nextLine();
        try {
            LocalDate novaData = LocalDate.parse(dataStr);
            System.out.print("Deseja atualizar ingredientes? (s/n): ");
            String resp = scanner.nextLine();
            List<Integer> indicesSelecionados = null;
            if (resp.equalsIgnoreCase("s")) {
                List<IngredienteEntity> ingredientes = controller.findAllIngredientes();
                if (ingredientes.isEmpty()) {
                    System.out.println("Nenhum ingrediente disponível.");
                } else {
                    System.out.println("Ingredientes disponíveis:");
                    for (int i = 0; i < ingredientes.size(); i++) {
                        System.out.println(i + " - " + ingredientes.get(i).getDescricao());
                    }
                    System.out.println("Digite índices dos ingredientes (-1 para sair):");
                    indicesSelecionados = new java.util.ArrayList<>();
                    while (true) {
                        int idx = scanner.nextInt();
                        scanner.nextLine();
                        if (idx == -1) break;
                        if (idx >= 0 && idx < ingredientes.size()) {
                            indicesSelecionados.add(idx);
                            System.out.println("Ingrediente selecionado: " + ingredientes.get(idx).getDescricao());
                        } else {
                            System.out.println("Índice inválido.");
                        }
                    }
                }
            }
            var resultado = controller.atualizarCardapio(id, novaData, indicesSelecionados);
            System.out.println(resultado.getMensagem());
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    public void deletarCardapio() {
        System.out.println("--- DELETAR CARDÁPIO ---");
        System.out.print("ID do cardápio: ");
        Long id = scanner.nextLong();
        scanner.nextLine();
        var resultadoBusca = controller.findCardapioById(id);
        if (!resultadoBusca.isSucesso()) {
            System.out.println(resultadoBusca.getMensagem());
            return;
        }
        var resultado = controller.deleteCardapio(resultadoBusca.getCardapio());
        System.out.println(resultado.getMensagem());
    }

    public void listarCardapios() {
        var resultado = controller.findAllCardapios();
        System.out.println(resultado.getMensagem());
        if (resultado.isSucesso() && resultado.getLista() != null) {
            resultado.getLista().forEach(System.out::println);
        }
    }
}