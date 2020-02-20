package br.com.diegjun.clockin.mapper;

import br.com.diegjun.clockin.json.PontoJson;
import br.com.diegjun.clockin.model.Ponto;
import br.com.diegjun.clockin.model.Usuario;

import java.time.LocalDateTime;
import java.time.LocalTime;

import static br.com.diegjun.clockin.model.Batida.fromTipo;

public class PontoMapper implements JsonMapper<PontoJson, Ponto> {

    @Override
    public Ponto convertJsonToModel(PontoJson json) {
        return new Ponto
                    .Builder(json.getId())
                    .comUsuario(new Usuario.Builder(json.getUsuario()).build())
                    .comHora(LocalTime.now())
                    .build();
    }

    @Override
    public PontoJson convertModelToJson(Ponto model) {
        return new PontoJson
                    .Builder(model.getId())
                    .comUsuario(model.getUsuario().getId())
                    .comBatida(model.getBatida().getTipo())
                    .comHoraBatida(model.getDataHoraBatida())
                    .build();
    }
}
