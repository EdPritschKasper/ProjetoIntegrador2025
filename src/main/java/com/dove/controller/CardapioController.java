package com.dove.controller;

import com.dove.model.entities.CardapiosEntity;
import com.dove.model.entities.IngredienteEntity;
import com.dove.model.service.CardapioService;

import java.util.List;

public class CardapioController {
    private final CardapioService service;

    public CardapioController() {
        this.service = new CardapioService();
    }

    public boolean insertCardapio(CardapiosEntity cardapio) {
        return service.insertCardapio(cardapio);
    }

    public CardapiosEntity findCardapioById(Long id) {
        return service.findCardapioById(id);
    }

    public boolean updateCardapio(CardapiosEntity cardapio) {
        return service.updateCardapio(cardapio);
    }

    public boolean deleteCardapio(CardapiosEntity cardapio) {
        return service.deleteCardapio(cardapio);
    }

    public List<CardapiosEntity> findAllCardapios() {
        return service.findAllCardapios();
    }

    public List<IngredienteEntity> findAllIngredientes() {
        return service.findAllIngredientes();
    }
}