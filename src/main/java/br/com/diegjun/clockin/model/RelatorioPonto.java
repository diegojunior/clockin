package br.com.diegjun.clockin.model;

import java.time.LocalTime;
import java.util.List;

public class RelatorioPonto {

    private final List<Ponto> pontos;

    public RelatorioPonto(List<Ponto> pontos) {
        this.pontos = pontos;
    }

    public List<Ponto> getPontos() {
        return pontos;
    }

    public LocalTime totalHoras() {
        return LocalTime.now();
    }
}
