package br.com.diegjun.clockin.json;

import br.com.diegjun.clockin.mapper.Json;
import br.com.diegjun.clockin.model.Batida;
import br.com.diegjun.clockin.model.Usuario;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import java.time.LocalDate;
import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PontoJson implements Json {

    private Long id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime dataHoraBatida;

    private Long usuario;

    private String batida;


    public static class Builder {

        private Long id;

        private Long usuario;

        private String batida;

        private LocalDateTime dataHoraBatida;

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

        public Builder comDataHoraBatida(LocalDateTime dataHoraBatida) {
            this.dataHoraBatida = dataHoraBatida;
            return this;
        }

        public PontoJson build() {
            return new PontoJson(this.id, this.usuario, this.batida, this.dataHoraBatida);
        }


    }

    public PontoJson(Long id, Long usuario, String batida, LocalDateTime dataHoraBatida) {
        this.id = id;
        this.usuario = usuario;
        this.batida = batida;
        this.dataHoraBatida = dataHoraBatida;
    }

    protected PontoJson(){}

    public Long getId() {
        return id;
    }

    public LocalDateTime getDataHoraBatida() {
        return dataHoraBatida;
    }

    public Long getUsuario() {
        return usuario;
    }

    public String getBatida() {
        return batida;
    }
}
