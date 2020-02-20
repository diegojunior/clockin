package br.com.diegjun.clockin.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Ponto implements Model {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private LocalDateTime dataHoraBatida;

    @ManyToOne
    private Usuario usuario;

    @Enumerated(EnumType.STRING)
    private Batida batida;

    protected Ponto(){}

    public Ponto(Long id, LocalDateTime dataHoraBatida, Usuario usuario, Batida batida) {
        this.id = id;
        this.dataHoraBatida = dataHoraBatida;
        this.usuario = usuario;
        this.batida = batida;
    }

    public static class Builder {

        private Long id;

        private LocalDateTime dataHoraBatida;

        private Usuario usuario;

        private Batida batida;

        public Builder(Long id) {
            this.id = id;
        }

        public Builder comUsuario(Usuario usuario) {
            this.usuario = usuario;
            return this;
        }

        public Builder comBatida(Batida batida) {
            this.batida = batida;
            return this;
        }

        public Builder comDataHoraBatida(LocalDateTime dataHoraBatida) {
            this.dataHoraBatida = dataHoraBatida;
            return this;
        }

        public Ponto build() {
            return new Ponto(this.id, this.dataHoraBatida, this.usuario, this.batida);
        }
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getDataHoraBatida() {
        return dataHoraBatida;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Batida getBatida() {
        return batida;
    }

    public void atualizaDataHoraBatida(LocalDateTime dataHoraBatida) {
        this.dataHoraBatida = dataHoraBatida;
    }

    public void atualizaBatida(Batida batida) {
        this.batida = batida;
    }

    public void atualizaUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void atualizarDados(Ponto ponto) {
        atualizaUsuario(ponto.getUsuario());
        atualizaBatida(ponto.getBatida());
        atualizaDataHoraBatida(ponto.getDataHoraBatida());
    }
}
