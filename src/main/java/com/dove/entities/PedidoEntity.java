package com.dove.entities;

import jakarta.persistence.*;

import java.time.LocalTime;

@Entity
@Table(name = "tb_pedido")
public class PedidoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "marmita")
    private String marmita;
    @Column(name = "status")
    private String status;
    @Column(name = "hora_inicio")
    private LocalTime hora_inicio;
    @Column(name = "hora_fim")
    private LocalTime hora_fim;
    @Column(name = "cardapio_id")
    private Long cardapio_id;
    @Column(name = "funcionario_id")
    private Long funcionario_id;
    @Column(name = "cliente_id")
    private Long cliente_id;

    public PedidoEntity() {}

    // construtor para insert
    public PedidoEntity(String marmita, String status, LocalTime hora_inicio, LocalTime hora_fim, Long cardapio_id, Long funcionario_id, Long cliente_id) {
        this.marmita = marmita;
        this.status = status;
        this.hora_inicio = hora_inicio;
        this.hora_fim = hora_fim;
        this.cardapio_id = cardapio_id;
        this.funcionario_id = funcionario_id;
        this.cliente_id = cliente_id;
    }

    // construtor para update
    public PedidoEntity(Integer id, String marmita, String status, LocalTime hora_inicio, LocalTime hora_fim, Long cardapio_id, Long funcionario_id, Long cliente_id) {
        this.id = id;
        this.marmita = marmita;
        this.status = status;
        this.hora_inicio = hora_inicio;
        this.hora_fim = hora_fim;
        this.cardapio_id = cardapio_id;
        this.funcionario_id = funcionario_id;
        this.cliente_id = cliente_id;
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "pedido_id=" + id +
                ", marmita='" + marmita + '\'' +
                ", status='" + status + '\'' +
                ", hora_inicio=" + hora_inicio +
                ", hora_fim=" + hora_fim +
                ", cardapio_id=" + cardapio_id +
                ", funcionario_id=" + funcionario_id +
                ", cliente_id=" + cliente_id +
                '}';
    }
}
