package br.com.diegjun.clockin.controller;

import br.com.diegjun.clockin.json.PontoJson;
import br.com.diegjun.clockin.json.RelatorioPontoJson;
import br.com.diegjun.clockin.mapper.PontoMapper;
import br.com.diegjun.clockin.model.Ponto;
import br.com.diegjun.clockin.model.RelatorioPonto;
import br.com.diegjun.clockin.service.PontoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/v1")
public class PontoController {

    @Autowired
    private PontoService pontoService;

    @PostMapping("/pontos")
    public ResponseEntity<PontoJson> baterPonto(@RequestBody PontoJson ponto) {
        Ponto pontoParaRegistrar = new PontoMapper().convertJsonToModel(ponto);
        Ponto pontoRegistrado = pontoService.registrarPonto(pontoParaRegistrar);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(pontoRegistrado.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/pontos/{idUsuario}")
    public ResponseEntity<RelatorioPontoJson> gerarRelatorio(@PathVariable Long idUsuario) {

        RelatorioPonto relatorioPonto = pontoService.gerarRelatorio(idUsuario);
        RelatorioPontoJson relatorioPontoJson = new RelatorioPontoJson(relatorioPonto);

        return new ResponseEntity<>(relatorioPontoJson, HttpStatus.OK);
    }
}
