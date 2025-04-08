package com.dove.entities;


import jakarta.persistence.*;


@Entity
@Table(name = "tb_funcionario")
public class Funcionario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome")
    private String nome;

    public Funcionario(){

    }

    public Funcionario(Long id, String nome){
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
