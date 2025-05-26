package com.dove.model.service;

import com.dove.model.entities.CardapiosEntity;
import com.dove.model.entities.IngredienteEntity;
import com.dove.model.repository.CardapiosRepository;
import com.dove.model.repository.CustomizerFactory;
import com.dove.model.repository.IngredienteRepository;
import jakarta.persistence.EntityManager;

import java.util.List;

public class CardapioService {
    private final EntityManager em;
    private final CardapiosRepository cardapioRepository;
    private final IngredienteRepository ingredienteRepository;

    public CardapioService() {
        this.em = CustomizerFactory.getEntityManager();
        this.cardapioRepository = new CardapiosRepository(em);
        this.ingredienteRepository = new IngredienteRepository(em);
    }
    
    // CRUD operations without direct user interaction
    public boolean insertCardapio(CardapiosEntity cardapio) {
        cardapioRepository.insert(cardapio);
        return true;
    }

    public CardapiosEntity findCardapioById(Long id) {
        return cardapioRepository.findById(id);
    }

    public boolean updateCardapio(CardapiosEntity cardapio) {
        cardapioRepository.update(cardapio);
        return true;
    }

    public boolean deleteCardapio(CardapiosEntity cardapio) {
        cardapioRepository.delete(cardapio);
        return true;
    }

    public List<CardapiosEntity> findAllCardapios() {
        return cardapioRepository.findAll();
    }

    public List<IngredienteEntity> findAllIngredientes() {
        return ingredienteRepository.findAll();
    }
}

