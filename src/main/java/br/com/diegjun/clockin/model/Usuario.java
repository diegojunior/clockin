package br.com.diegjun.clockin.model;


import java.io.Serializable;
import java.time.LocalDateTime;


public class Usuario implements Serializable {


    private Long id;

    private String nome;

    private String cpf;

    private String email;

    private LocalDateTime dataCadastro;

    public Usuario(){}

    public Usuario(String nome, String cpf, String email, LocalDateTime dataCadastro) {
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.dataCadastro = dataCadastro;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public String getEmail() {
        return email;
    }

    public LocalDateTime getDataCadastro() {
        return dataCadastro;
    }

}
