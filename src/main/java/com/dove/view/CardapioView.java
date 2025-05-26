package com.dove.view;

import com.dove.controller.CardapioController;
import com.dove.model.entities.CardapiosEntity;
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
            CardapiosEntity cardapio = new CardapiosEntity(data);
            System.out.print("Adicionar ingredientes? (s/n): ");
            String resp = scanner.nextLine();
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
                    while (true) {
                        int idx = scanner.nextInt();
                        scanner.nextLine();
                        if (idx == -1) break;
                        if (idx >= 0 && idx < ingredientes.size()) {
                            cardapio.getIngredientes().add(ingredientes.get(idx));
                            System.out.println("Ingrediente adicionado: " + ingredientes.get(idx).getDescricao());
                        } else {
                            System.out.println("Índice inválido.");
                        }
                    }
                }
            }
            boolean ok = controller.insertCardapio(cardapio);
            System.out.println(ok ? "Cardápio cadastrado." : "Falha ao cadastrar cardápio.");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    public void buscarCardapio() {
        System.out.println("--- BUSCAR CARDÁPIO ---");
        System.out.print("ID do cardápio: ");
        Long id = scanner.nextLong();
        scanner.nextLine();
        CardapiosEntity cardapio = controller.findCardapioById(id);
        System.out.println(cardapio != null ? cardapio : "Cardápio não encontrado.");
    }

    public void atualizarCardapio() {
        System.out.println("--- ATUALIZAR CARDÁPIO ---");
        System.out.print("ID do cardápio: ");
        Long id = scanner.nextLong();
        scanner.nextLine();
        CardapiosEntity cardapio = controller.findCardapioById(id);
        if (cardapio == null) {
            System.out.println("Cardápio não encontrado.");
            return;
        }
        System.out.print("Nova data (YYYY-MM-DD): ");
        String dataStr = scanner.nextLine();
        try {
            LocalDate novaData = LocalDate.parse(dataStr);
            cardapio.setData(novaData);
            boolean ok = controller.updateCardapio(cardapio);
            System.out.println(ok ? "Cardápio atualizado." : "Falha ao atualizar cardápio.");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    public void deletarCardapio() {
        System.out.println("--- DELETAR CARDÁPIO ---");
        System.out.print("ID do cardápio: ");
        Long id = scanner.nextLong();
        scanner.nextLine();
        CardapiosEntity cardapio = controller.findCardapioById(id);
        if (cardapio == null) {
            System.out.println("Cardápio não encontrado.");
            return;
        }
        boolean ok = controller.deleteCardapio(cardapio);
        System.out.println(ok ? "Cardápio deletado." : "Falha ao deletar cardápio.");
    }

    public void listarCardapios() {
        System.out.println("--- LISTA DE CARDÁPIOS ---");
        List<CardapiosEntity> list = controller.findAllCardapios();
        if (list.isEmpty()) {
            System.out.println("Nenhum cardápio cadastrado.");
        } else {
            list.forEach(System.out::println);
        }
    }
}