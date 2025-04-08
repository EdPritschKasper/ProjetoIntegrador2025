package com.dove.entities;

import jakarta.persistence.*;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;

@Entity
@Table(name = "tb_cardapio")
public class CardapiosEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "data", nullable = false)
    private LocalDate data;

    @OneToMany(mappedBy = "cardapio", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PedidoEntity> pedidos = new ArrayList<>();

    public CardapiosEntity() {}

    // Construtor para INSERT
    public CardapiosEntity(LocalDate data) {
        this.data = data;
    }

    // Construtor para UPDATE (com ID)
    public CardapiosEntity(Long id, LocalDate data) {
        this.id = id;
        this.data = data;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public LocalDate getData() {
        return data;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "CardapiosEntity{" +
                "id=" + id +
                ", data=" + data + '\'' +
                '}';
    }
}
