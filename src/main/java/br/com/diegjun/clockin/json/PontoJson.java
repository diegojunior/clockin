package br.com.diegjun.clockin.json;

import br.com.diegjun.clockin.mapper.Json;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDateTime;
import java.time.LocalTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PontoJson implements Json {

    private Long id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm:ss")
    @JsonIgnore
    private LocalTime dataHoraBatida;

    private Long usuario;

    @JsonIgnore
    private String batida;


    public static class Builder {

        private Long id;

        private Long usuario;

        private String batida;

        private LocalTime dataHoraBatida;

        public Builder(Long id){
            this.id = id;
        }

        public Builder comUsuario(Long usuario) {
            this.usuario = usuario;
            return this;
        }

        public Builder comBatida(String batida) {
            this.batida = batida;
            return this;
        }

        public Builder comHoraBatida(LocalTime dataHoraBatida) {
            this.dataHoraBatida = dataHoraBatida;
            return this;
        }

        public PontoJson build() {
            return new PontoJson(this.id, this.usuario, this.batida, this.dataHoraBatida);
        }


    }

    public PontoJson(Long id, Long usuario, String batida, LocalTime dataHoraBatida) {
        this.id = id;
        this.usuario = usuario;
        this.batida = batida;
        this.dataHoraBatida = dataHoraBatida;
    }

    protected PontoJson(){}

    public Long getId() {
        return id;
    }

    public LocalTime getDataHoraBatida() {
        return dataHoraBatida;
    }

    public Long getUsuario() {
        return usuario;
    }

    public String getBatida() {
        return batida;
    }
}
