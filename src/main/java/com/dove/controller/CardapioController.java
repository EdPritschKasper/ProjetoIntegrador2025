package com.dove.controller;

import com.dove.model.entities.CardapiosEntity;
import com.dove.model.entities.IngredienteEntity;
import com.dove.model.service.CardapioService;

import java.util.List;
import com.dove.model.dto.CardapioResponse;

public class CardapioController {
    // Atualização de cardápio isolando lógica da View
    public CardapioResponse atualizarCardapio(Long id, java.time.LocalDate novaData, List<Integer> indicesIngredientes) {
        return service.atualizarCardapio(id, novaData, indicesIngredientes);
    }
    private final CardapioService service;

    public CardapioController() {
        this.service = new CardapioService();
    }


    // Novo método para cadastro isolando lógica da View
    public CardapioResponse cadastrarCardapio(java.time.LocalDate data, List<Integer> indicesIngredientes) {
        return service.cadastrarCardapio(data, indicesIngredientes);
    }

    public CardapioResponse findCardapioById(Long id) {
        return service.findCardapioById(id);
    }

    // Método removido: updateCardapio(CardapiosEntity cardapio)

    public CardapioResponse deleteCardapio(CardapiosEntity cardapio) {
        return service.deleteCardapio(cardapio);
    }

    public CardapioResponse findAllCardapios() {
        return service.findAllCardapios();
    }

    public List<IngredienteEntity> findAllIngredientes() {
        return service.findAllIngredientes();
    }
}