package com.dove.entities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_ingrediente")
public class IngredienteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "descricao", nullable = false)
    private String descricao;
    @ManyToMany(mappedBy = "ingredientes")
    private List<PedidoEntity> pedidos = new ArrayList<>();

    public IngredienteEntity() {}

    // Construtor para INSERT
    public IngredienteEntity(String descricao) {
        this.descricao = descricao;
    }

    // Construtor para UPDATE (com ID)
    public IngredienteEntity(Long id, String descricao) {
        this.id = id;
        this.descricao = descricao;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public String toString() {
        return "IngredienteEntity{" +
                "id=" + id +
                ", descricao='" + descricao + '\'' +
                '}';
    }
}
