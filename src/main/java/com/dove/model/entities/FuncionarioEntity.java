package com.dove.model.entities;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_funcionario")
public class FuncionarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "cpf", unique = true)
    private String cpf;

    @OneToMany(mappedBy = "funcionario", cascade = CascadeType.ALL)
    private List<PedidoEntity> pedidos = new ArrayList<>();

    public FuncionarioEntity(){}

    // Getters e Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public List<PedidoEntity> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<PedidoEntity> pedidos) {
        this.pedidos = pedidos;
    }

    public void addPedido(PedidoEntity pedido) {
        pedidos.add(pedido);
        pedido.setFuncionario(this);
    }

    @Override
    public String toString() {
        return "FuncionarioEntity{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", cpf='" + cpf + '\'' +
                ", pedidos=" + pedidos +
                '}';
    }
}
