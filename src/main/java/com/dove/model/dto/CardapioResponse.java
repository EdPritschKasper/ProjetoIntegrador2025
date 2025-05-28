package com.dove.model.dto;

import com.dove.model.entities.CardapiosEntity;
import java.util.List;

public class CardapioResponse {
    private boolean sucesso;
    private String mensagem;
    private CardapiosEntity cardapio;
    private List<CardapiosEntity> lista;

    public CardapioResponse(boolean sucesso, String mensagem) {
        this.sucesso = sucesso;
        this.mensagem = mensagem;
    }

    public CardapioResponse(boolean sucesso, String mensagem, CardapiosEntity cardapio) {
        this.sucesso = sucesso;
        this.mensagem = mensagem;
        this.cardapio = cardapio;
    }

    public CardapioResponse(boolean sucesso, String mensagem, List<CardapiosEntity> lista) {
        this.sucesso = sucesso;
        this.mensagem = mensagem;
        this.lista = lista;
    }

    public boolean isSucesso() { return sucesso; }
    public String getMensagem() { return mensagem; }
    public CardapiosEntity getCardapio() { return cardapio; }
    public List<CardapiosEntity> getLista() { return lista; }
}
