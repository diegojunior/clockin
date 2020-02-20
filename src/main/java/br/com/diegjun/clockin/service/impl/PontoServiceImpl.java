package br.com.diegjun.clockin.service.impl;

import br.com.diegjun.clockin.model.Ponto;
import br.com.diegjun.clockin.model.RelatorioPonto;
import br.com.diegjun.clockin.model.Usuario;
import br.com.diegjun.clockin.repository.PontoRepository;
import br.com.diegjun.clockin.service.PontoService;
import br.com.diegjun.clockin.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

import static br.com.diegjun.clockin.model.Batida.ENTRADA;
import static br.com.diegjun.clockin.model.Batida.SAIDA;

@Service
public class PontoServiceImpl implements PontoService {

    @Autowired
    private PontoRepository pontoRepository;

    @Autowired
    private UsuarioService usuarioService;

    public Ponto registrarPonto(Ponto ponto) {
        Usuario usuario = usuarioService.getBy(ponto.getUsuario().getId());

        if (deveSerEntrada(usuario)) {
            ponto.atualizaBatida(ENTRADA);
        } else {
            ponto.atualizaBatida(SAIDA);
        }
        ponto.atualizaUsuario(usuario);

        return pontoRepository.save(ponto);
    }

    private boolean deveSerEntrada(Usuario usuario) {
        List<Ponto> pontos = pontoRepository.findByIdUsuario(usuario.getId());
        if (pontos.size() % 2 == 0){
            return true;
        }
        return false;
    }

    public RelatorioPonto gerarRelatorio(Long idUsuario) {
        List<Ponto> pontos = pontoRepository.findByIdUsuario(idUsuario);
        return new RelatorioPonto(pontos, LocalTime.now());
    }
}

