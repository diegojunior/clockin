package br.com.diegjun.clockin.model;

import javax.persistence.*;
import java.time.LocalTime;

import static br.com.diegjun.clockin.model.Batida.ENTRADA;

@Entity
public class Ponto implements Model {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private LocalTime dataHoraBatida;

    @ManyToOne
    private Usuario usuario;

    @Enumerated(EnumType.STRING)
    private Batida batida;

    protected Ponto(){}

    public Ponto(Long id, LocalTime dataHoraBatida, Usuario usuario, Batida batida) {
        this.id = id;
        this.dataHoraBatida = dataHoraBatida;
        this.usuario = usuario;
        this.batida = batida;
    }

    public static class Builder {

        private Long id;

        private LocalTime dataHoraBatida;

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

        public Builder comHora(LocalTime dataHoraBatida) {
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

    public LocalTime getDataHoraBatida() {
        return dataHoraBatida;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Batida getBatida() {
        return batida;
    }

    public void atualizaDataHoraBatida(LocalTime dataHoraBatida) {
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

    public boolean ehEntrada() {
        return this.batida.equals(ENTRADA);
    }

    public boolean ehSaida() {
        return this.batida.equals(Batida.SAIDA);
    }
}
