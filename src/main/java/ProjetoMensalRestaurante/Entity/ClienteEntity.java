package ProjetoMensalRestaurante.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "tb_cliente")

public class ClienteEntity {

    private long id;
    private String nome;
    private String email;
    private String senha;

    protected ClienteEntity(){
        this.nome= null;
        this.email = null;
    }


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
