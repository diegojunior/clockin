package br.com.diegjun.clockin.json;

import br.com.diegjun.clockin.mapper.Json;
import br.com.diegjun.clockin.mapper.PontoMapper;
import br.com.diegjun.clockin.model.RelatorioPonto;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RelatorioPontoJson implements Json {

    private final List<PontoJson> pontos;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private final LocalTime totalHoras;

    public RelatorioPontoJson(RelatorioPonto relatorioPonto) {
        this.pontos = relatorioPonto.getPontos().stream().map(ponto -> new PontoMapper().convertModelToJson(ponto)).collect(Collectors.toList());
        this.totalHoras = relatorioPonto.totalHoras();
    }

    public List<PontoJson> getPontos() {
        return pontos;
    }

    public LocalTime getTotalHoras() {
        return totalHoras;
    }
}
