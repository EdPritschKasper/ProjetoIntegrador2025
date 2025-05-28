package com.dove.model.service;

import com.dove.model.entities.CardapiosEntity;
import com.dove.model.entities.IngredienteEntity;
import com.dove.model.repository.CardapiosRepository;
import com.dove.model.repository.CustomizerFactory;
import com.dove.model.repository.IngredienteRepository;
import jakarta.persistence.EntityManager;
import com.dove.model.dto.CardapioResponse;

import java.util.List;

public class CardapioService {
    // Atualização de cardápio com isolamento de lógica
    public CardapioResponse atualizarCardapio(Long id, java.time.LocalDate novaData, List<Integer> indicesIngredientes) {
        try {
            CardapiosEntity cardapio = cardapioRepository.findById(id);
            if (cardapio == null) return new CardapioResponse(false, "Cardápio não encontrado.");
            cardapio.setData(novaData);
            if (indicesIngredientes != null) {
                List<IngredienteEntity> todosIngredientes = ingredienteRepository.findAll();
                List<IngredienteEntity> selecionados = new java.util.ArrayList<>();
                for (Integer idx : indicesIngredientes) {
                    if (idx >= 0 && idx < todosIngredientes.size()) {
                        selecionados.add(todosIngredientes.get(idx));
                    }
                }
                cardapio.setIngredientes(selecionados);
            }
            cardapioRepository.update(cardapio);
            return new CardapioResponse(true, "Cardápio atualizado.", cardapio);
        } catch (Exception e) {
            return new CardapioResponse(false, "Falha ao atualizar cardápio.");
        }
    }
    private final EntityManager em;
    private final CardapiosRepository cardapioRepository;
    private final IngredienteRepository ingredienteRepository;

    public CardapioService() {
        this.em = CustomizerFactory.getEntityManager();
        this.cardapioRepository = new CardapiosRepository(em);
        this.ingredienteRepository = new IngredienteRepository(em);
    }
    

    // Novo método: lógica de cadastro de cardápio com ingredientes
    public CardapioResponse cadastrarCardapio(java.time.LocalDate data, List<Integer> indicesIngredientes) {
        try {
            CardapiosEntity cardapio = new CardapiosEntity(data);
            if (indicesIngredientes != null && !indicesIngredientes.isEmpty()) {
                List<IngredienteEntity> todosIngredientes = ingredienteRepository.findAll();
                List<IngredienteEntity> selecionados = new java.util.ArrayList<>();
                for (Integer idx : indicesIngredientes) {
                    if (idx >= 0 && idx < todosIngredientes.size()) {
                        selecionados.add(todosIngredientes.get(idx));
                    }
                }
                cardapio.setIngredientes(selecionados);
            }
            cardapioRepository.insert(cardapio);
            return new CardapioResponse(true, "Cardápio cadastrado.", cardapio);
        } catch (Exception e) {
            return new CardapioResponse(false, "Falha ao cadastrar cardápio.");
        }
    }

    public CardapioResponse findCardapioById(Long id) {
        CardapiosEntity cardapio = cardapioRepository.findById(id);
        if (cardapio == null) {
            return new CardapioResponse(false, "Cardápio não encontrado.");
        }
        return new CardapioResponse(true, "Cardápio encontrado.", cardapio);
    }

    public CardapioResponse deleteCardapio(CardapiosEntity cardapio) {
        if (cardapio == null) {
            return new CardapioResponse(false, "Cardápio não encontrado.");
        }
        try {
            cardapioRepository.delete(cardapio);
            return new CardapioResponse(true, "Cardápio deletado.");
        } catch (Exception e) {
            return new CardapioResponse(false, "Falha ao deletar cardápio.");
        }
    }

    public CardapioResponse findAllCardapios() {
        List<CardapiosEntity> lista = cardapioRepository.findAll();
        if (lista == null || lista.isEmpty()) {
            return new CardapioResponse(false, "Nenhum cardápio cadastrado.");
        }
        return new CardapioResponse(true, "Lista de cardápios:", lista);
    }

    public List<IngredienteEntity> findAllIngredientes() {
        return ingredienteRepository.findAll();
    }
}

