package br.com.diegjun.clockin.model;


import java.text.SimpleDateFormat;
import java.time.LocalDate;


public class Usuario implements Model {


    private Long id;

    private String nome;

    private String cpf;

    private String email;

    private LocalDate dataCadastro;

    public static class Builder {

        private Long id;

        private String nome;

        private String cpf;

        private String email;

        private LocalDate dataCadastro;

        public Builder(Long id){
            this.id = id;
        }

        public Builder comNome(String nome) {
            this.nome = nome;
            return this;
        }

        public Builder comCpf(String cpf) {
            this.cpf = cpf;
            return this;
        }

        public Builder comEmail(String email) {
            this.email = email;
            return this;
        }

        public Builder comDataCadastro(String dataCadastro) {
            this.dataCadastro = LocalDate.parse(dataCadastro);
            return this;
        }

        public Usuario build() {
            return new Usuario(this.nome, this.cpf, this.email, this.dataCadastro);
        }


    }

    public Usuario(){}

    public Usuario(String nome, String cpf, String email, LocalDate dataCadastro) {
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

    public LocalDate getDataCadastro() {
        return dataCadastro;
    }

}