package com.dove.ModelEntities;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_cliente")

public class ClienteEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, unique = true)
    private String nome;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String senha;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PedidoEntity> pedidos = new ArrayList<>();

    public ClienteEntity(){}

    public ClienteEntity (String nome, String email, String senha){
    this.nome = nome;
    this.email = email;
    this.senha = senha;
    }

    public String getNome() {
        return this.nome;
    }

    public String getEmail() {
        return this.email;
    }

    public String getSenha() {
        return this.senha;
    }

    public void alterarSenha(String novaSenha) {
        if (novaSenha == null || novaSenha.length() < 4) {
            throw new IllegalArgumentException("Senha deve ter ao menos 4 caracteres.");
        }
        this.senha = novaSenha;
    }

}
