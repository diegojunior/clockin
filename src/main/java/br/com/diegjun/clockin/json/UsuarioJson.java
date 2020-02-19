package br.com.diegjun.clockin.json;

import br.com.diegjun.clockin.mapper.Json;
import br.com.diegjun.clockin.model.Usuario;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDate;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UsuarioJson implements Json {

    private Long id;

    private String nome;

    private String cpf;

    private String email;

    private String dataCadastro;

    public static class Builder {

        private Long id;

        private String nome;

        private String cpf;

        private String email;

        private String dataCadastro;

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

        public Builder comDataCadastro(LocalDate dataCadastro) {
            this.dataCadastro = dataCadastro.toString();
            return this;
        }

        public UsuarioJson build() {
            return new UsuarioJson(this.id, this.nome, this.cpf, this.email, this.dataCadastro);
        }


    }

    public UsuarioJson(Long id, String nome, String cpf, String email, String dataCadastro) {
        this.id = id;
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

    public String getDataCadastro() {
        return dataCadastro;
    }
}