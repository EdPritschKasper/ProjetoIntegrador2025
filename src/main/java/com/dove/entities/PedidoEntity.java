package com.dove.entities;

import jakarta.persistence.*;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_pedido")
public class PedidoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "marmita")
    private String marmita;
    @Column(name = "status")
    private String status;
    @Column(name = "hora_inicio")
    private LocalTime hora_inicio;
    @Column(name = "hora_fim")
    private LocalTime hora_fim;
    @ManyToOne
    @JoinColumn(name = "cardapio_id", nullable = false)
    private CardapiosEntity cardapio;
    @ManyToOne
    @JoinColumn(name = "funcionario_id")
    private Funcionario funcionario;
    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private ClienteEntity cliente;
    @ManyToMany
    @JoinTable(
            name = "tb_pedido_ingrediente",
            joinColumns = @JoinColumn(name = "pedido_id"),
            inverseJoinColumns = @JoinColumn(name = "ingrediente_id")
    )
    private List<IngredienteEntity> ingredientes = new ArrayList<>();

    public PedidoEntity() {}

    public PedidoEntity(String marmita, String status, LocalTime hora_inicio, LocalTime hora_fim, CardapiosEntity cardapio, Funcionario funcionario, ClienteEntity cliente) {
        this.marmita = marmita;
        this.status = status;
        this.hora_inicio = hora_inicio;
        this.hora_fim = hora_fim;
        this.cardapio = cardapio;
        this.funcionario = funcionario;
        this.cliente = cliente;
    }

    @Override
    public String toString() {
        return "PedidoEntity{" +
                "id=" + id +
                ", marmita='" + marmita + '\'' +
                ", status='" + status + '\'' +
                ", hora_inicio=" + hora_inicio +
                ", hora_fim=" + hora_fim +
                ", cardapio=" + cardapio +
                ", funcionario=" + funcionario +
                ", cliente=" + cliente +
                '}';
    }
}
