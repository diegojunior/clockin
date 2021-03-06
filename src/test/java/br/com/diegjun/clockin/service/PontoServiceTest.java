package br.com.diegjun.clockin.service;

import br.com.diegjun.clockin.model.Batida;
import br.com.diegjun.clockin.model.Ponto;
import br.com.diegjun.clockin.model.RelatorioPonto;
import br.com.diegjun.clockin.model.Usuario;
import br.com.diegjun.clockin.repository.PontoRepository;
import br.com.diegjun.clockin.service.impl.PontoServiceImpl;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class PontoServiceTest {

    @TestConfiguration
    static class PontoTestContextConfiguration {

        @Bean
        public PontoService pontoService() {
            return new PontoServiceImpl();
        }
    }

    @MockBean
    private PontoRepository pontoRepository;

    @MockBean
    private UsuarioService usuarioService;

    @Autowired
    private PontoService pontoService;


    @Test
    public void registrarPontoTest() {

        Usuario usuario = criarUsuario();

        when(usuarioService.getBy(anyLong())).thenReturn(usuario);

        Ponto entrada = new Ponto
                .Builder(null)
                .comUsuario(usuario)
                .comHora(LocalTime.now())
                .comBatida(Batida.ENTRADA)
                .build();

        Ponto saida = new Ponto
                .Builder(null)
                .comUsuario(usuario)
                .comHora(LocalTime.now().plusHours(2L))
                .comBatida(Batida.SAIDA)
                .build();

        Ponto entrada2 = new Ponto
                .Builder(null)
                .comUsuario(usuario)
                .comHora(LocalTime.now().plusHours(3L))
                .comBatida(Batida.ENTRADA)
                .build();

        Ponto saida2 = new Ponto
                .Builder(null)
                .comUsuario(usuario)
                .comHora(LocalTime.now().plusHours(4L))
                .comBatida(Batida.SAIDA)
                .build();

        List<Ponto> pontos = Lists.newArrayList(entrada, saida, entrada2, saida2);



        when(pontoRepository.save(entrada)).thenReturn(entrada);
        when(pontoRepository.findByIdUsuario(usuario.getId())).thenReturn(Lists.newArrayList());
        pontoService.registrarPonto(entrada);

        when(pontoRepository.save(saida)).thenReturn(saida);
        when(pontoRepository.findByIdUsuario(usuario.getId())).thenReturn(Lists.newArrayList(entrada));
        pontoService.registrarPonto(saida);

        when(pontoRepository.save(entrada2)).thenReturn(entrada2);
        when(pontoRepository.findByIdUsuario(usuario.getId())).thenReturn(Lists.newArrayList(entrada, saida));
        pontoService.registrarPonto(entrada2);

        when(pontoRepository.save(saida2)).thenReturn(saida2);
        when(pontoRepository.findByIdUsuario(usuario.getId())).thenReturn(Lists.newArrayList(entrada, saida, entrada2));
        pontoService.registrarPonto(saida2);

        when(pontoRepository.findByIdUsuario(usuario.getId())).thenReturn(pontos);
        RelatorioPonto relatorioPonto = pontoService.gerarRelatorio(usuario.getId());

        assertThat(relatorioPonto.getPontos().size()).isEqualTo(4);
        assertThat(relatorioPonto.totalHoras()).isEqualTo("3:00:00");

    }

    @Test
    public void registrarPontoEntradaTest() {

        Usuario usuario = criarUsuario();

        when(usuarioService.getBy(anyLong())).thenReturn(usuario);

        Ponto entrada = new Ponto
                .Builder(null)
                .comUsuario(usuario)
                .comHora(LocalTime.now())
                .comBatida(Batida.ENTRADA)
                .build();


        List<Ponto> pontos = Lists.newArrayList(entrada);

        when(pontoRepository.save(entrada)).thenReturn(entrada);
        when(pontoRepository.findByIdUsuario(usuario.getId())).thenReturn(pontos);

        pontoService.registrarPonto(entrada);
        RelatorioPonto relatorioPonto = pontoService.gerarRelatorio(usuario.getId());

        assertThat(relatorioPonto.getPontos().size()).isEqualTo(1);

    }

    @Test
    public void registrarPontoEntradaSemSaidaTest() {

        Usuario usuario = criarUsuario();

        when(usuarioService.getBy(anyLong())).thenReturn(usuario);

        Ponto entrada = new Ponto
                .Builder(null)
                .comUsuario(usuario)
                .comHora(LocalTime.now())
                .comBatida(Batida.ENTRADA)
                .build();

        Ponto saida = new Ponto
                .Builder(null)
                .comUsuario(usuario)
                .comHora(LocalTime.now().plusSeconds(1))
                .comBatida(Batida.SAIDA)
                .build();

        Ponto entrada2 = new Ponto
                .Builder(null)
                .comUsuario(usuario)
                .comHora(LocalTime.now().plusSeconds(2))
                .comBatida(Batida.ENTRADA)
                .build();

        List<Ponto> pontos = Lists.newArrayList(entrada, saida, entrada2);



        when(pontoRepository.save(entrada)).thenReturn(entrada);
        when(pontoRepository.findByIdUsuario(usuario.getId())).thenReturn(Lists.newArrayList());
        pontoService.registrarPonto(entrada);

        when(pontoRepository.save(saida)).thenReturn(saida);
        when(pontoRepository.findByIdUsuario(usuario.getId())).thenReturn(Lists.newArrayList(entrada));
        pontoService.registrarPonto(saida);


        when(pontoRepository.save(entrada2)).thenReturn(entrada2);
        when(pontoRepository.findByIdUsuario(usuario.getId())).thenReturn(Lists.newArrayList(entrada,saida));
        pontoService.registrarPonto(entrada2);

        when(pontoRepository.findByIdUsuario(usuario.getId())).thenReturn(pontos);
        RelatorioPonto relatorioPonto = pontoService.gerarRelatorio(usuario.getId());

        assertThat(relatorioPonto.getPontos().size()).isEqualTo(3);

    }

    private Usuario criarUsuario() {
        return new Usuario
                .Builder(1L)
                .comNome("diego")
                .comCpf("123456789")
                .comEmail("teste@teste.com.br")
                .comDataCadastro(LocalDate.now())
                .build();
    }

}
