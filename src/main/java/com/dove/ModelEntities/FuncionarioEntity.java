package com.dove.ModelEntities;

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

    @OneToMany(mappedBy = "funcionario", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PedidoEntity> pedidos = new ArrayList<>();

    public FuncionarioEntity(){

    }

    public FuncionarioEntity(Long id, String nome){
        this.id = id;
    }

    //Metodo Getters e Setters
    public Long getId(){
        return id;
    }

    public String getNome(){
        return nome;
    }

    public void setId(Long id){
        this.id = id;
    }

    public void setNome(String nome){
        this.nome = nome;
    }


    @Override
    public String toString() {
        return "Funcionario{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                '}';
    }

}
