package com.dove.model.entities;

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
    private FuncionarioEntity funcionario;
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

    // Construtor para insert pedido
    public PedidoEntity(String marmita, CardapiosEntity cardapio, FuncionarioEntity funcionario, ClienteEntity cliente, List<IngredienteEntity> ingredientes) {
        this.marmita = marmita;
        this.status = "Iniciado";
        this.hora_inicio =  LocalTime.now();
        this.hora_fim = null;
        this.cardapio = cardapio;
        this.funcionario = funcionario;
        this.cliente = cliente;
        this.ingredientes = ingredientes;
    }

    public PedidoEntity(){}

    @Override
    public String toString() {
        return "id = " + id + "\n" +
                "marmita = " + marmita + "\n" +
                "status = " + status + "\n" +
                "hora_inicio = " + hora_inicio + "\n" +
                "hora_fim = " + hora_fim + "\n";
//                + "cardapio = " + cardapio + "\n" +
//                "funcionario = " + funcionario + "\n" +
//                "cliente = " + cliente + "\n";
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMarmita() {
        return marmita;
    }

    public void setMarmita(String marmita) {
        this.marmita = marmita;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalTime getHora_inicio() {
        return hora_inicio;
    }

    public void setHora_inicio(LocalTime hora_inicio) {
        this.hora_inicio = hora_inicio;
    }

    public LocalTime getHora_fim() {
        return hora_fim;
    }

    public void setHora_fim(LocalTime hora_fim) {
        this.hora_fim = hora_fim;
    }

    public CardapiosEntity getCardapio() {
        return cardapio;
    }

    public void setCardapio(CardapiosEntity cardapio) {
        this.cardapio = cardapio;
    }

    public FuncionarioEntity getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(FuncionarioEntity funcionario) {
        this.funcionario = funcionario;
    }

    public ClienteEntity getCliente() {
        return cliente;
    }

    public void setCliente(ClienteEntity cliente) {
        this.cliente = cliente;
    }

    public List<IngredienteEntity> getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(List<IngredienteEntity> ingredientes) {
        this.ingredientes = ingredientes;
    }

    public void addIngrediente(IngredienteEntity ingrediente) {
        this.ingredientes.add(ingrediente);
    }
}
