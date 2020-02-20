package br.com.diegjun.clockin.service;

import br.com.diegjun.clockin.model.Ponto;
import br.com.diegjun.clockin.model.RelatorioPonto;

public interface PontoService {

    public Ponto registrarPonto(Ponto ponto);

    public RelatorioPonto gerarRelatorio(Long idUsuario);
}
