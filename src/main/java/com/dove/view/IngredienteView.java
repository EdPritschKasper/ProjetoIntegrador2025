package com.dove.view;

import com.dove.controller.IngredienteController;
import com.dove.model.entities.IngredienteEntity;

import java.util.List;
import java.util.Scanner;

public class IngredienteView {

    private final IngredienteController controller;
    private final Scanner scanner;

    public IngredienteView(Scanner scanner) {
        this.scanner = scanner;
        this.controller = new IngredienteController();
    }

    public void listarIngredientes() {
        List<IngredienteEntity> lista = controller.findAll();
        if (lista.isEmpty()) {
            System.out.println("Nenhum ingrediente cadastrado.");
        } else {
            lista.forEach(i -> System.out.println(i.getId() + " - " + i.getDescricao()));
        }
    }

    public void inserirIngrediente() {
        System.out.print("Descrição do ingrediente: ");
        String descricao = scanner.nextLine();
        IngredienteEntity ingrediente = new IngredienteEntity();
        ingrediente.setDescricao(descricao);
        boolean sucesso = controller.insert(ingrediente);
        System.out.println(sucesso ? "Ingrediente inserido com sucesso." : "Erro ao inserir ingrediente.");
    }

    public void atualizarIngrediente() {
        System.out.print("ID do ingrediente para atualizar: ");
        Long id = scanner.nextLong();
        scanner.nextLine(); // Consome o \n

        IngredienteEntity ingrediente = controller.findById(id);
        if (ingrediente == null) {
            System.out.println("Ingrediente não encontrado.");
            return;
        }

        System.out.print("Nova descrição: ");
        String descricao = scanner.nextLine();
        ingrediente.setDescricao(descricao);

        boolean sucesso = controller.update(ingrediente);
        System.out.println(sucesso ? "Ingrediente atualizado." : "Erro ao atualizar ingrediente.");
    }

    public void deletarIngrediente() {
        System.out.print("ID do ingrediente para deletar: ");
        Long id = scanner.nextLong();
        scanner.nextLine(); // Consome o \n

        IngredienteEntity ingrediente = controller.findById(id);
        if (ingrediente == null) {
            System.out.println("Ingrediente não encontrado.");
            return;
        }

        boolean sucesso = controller.delete(ingrediente);
        System.out.println(sucesso ? "Ingrediente deletado." : "Erro ao deletar ingrediente.");
    }
}
