package br.com.diegjun.clockin.controller;

import br.com.diegjun.clockin.json.PontoJson;
import br.com.diegjun.clockin.json.RelatorioPontoJson;
import br.com.diegjun.clockin.mapper.PontoMapper;
import br.com.diegjun.clockin.model.Ponto;
import br.com.diegjun.clockin.model.RelatorioPonto;
import br.com.diegjun.clockin.model.Usuario;
import br.com.diegjun.clockin.service.PontoService;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static br.com.diegjun.clockin.model.Batida.ENTRADA;
import static br.com.diegjun.clockin.model.Batida.SAIDA;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class PontoControllerTest {

    @LocalServerPort
    private Long port;

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private PontoService pontoService;

    @Test
    public void testBaterPonto() {

        PontoJson entrada = new PontoJson
                .Builder(null)
                .comUsuario(1L)
                .comHoraBatida(LocalTime.now().minusHours(1L))
                .comBatida("E")
                .build();

        Usuario diego = new Usuario.Builder(1L).comNome("Diego").comCpf("123456").comDataCadastro(LocalDate.now()).comEmail("diego@teste.com.br").build();

        Ponto pontoEntrada = new PontoMapper().convertJsonToModel(entrada);
        Ponto pontoEntradaCadastrado = new Ponto.Builder(1L).comUsuario(diego).comHora(LocalTime.now()).comBatida(ENTRADA).build();

        when(pontoService.registrarPonto(any())).thenReturn(pontoEntradaCadastrado);

        HttpEntity<PontoJson> body = new HttpEntity<PontoJson>(entrada);

        ResponseEntity<PontoJson> response = restTemplate.exchange(pontosEndpoint(), HttpMethod.POST, body, new ParameterizedTypeReference<PontoJson>() {});

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getHeaders().get("Location").get(0)).contains("/clockin/v1/pontos/1");
    }

    @Test
    public void relatorioPonto() {

        Usuario diego = new Usuario.Builder(1L).comNome("diego").comEmail("diego@teste.com.br").comDataCadastro(LocalDate.now()).comCpf("123456").build();
        Ponto entrada = new Ponto.Builder(1L).comUsuario(diego).comBatida(ENTRADA).comHora(LocalTime.now()).build();
        Ponto saida = new Ponto.Builder(1L).comUsuario(diego).comBatida(SAIDA).comHora(LocalTime.now().plusHours(3L)).build();
        Ponto entrada1 = new Ponto.Builder(1L).comUsuario(diego).comBatida(ENTRADA).comHora(LocalTime.now().plusHours(5L)).build();
        Ponto saida1 = new Ponto.Builder(1L).comUsuario(diego).comBatida(SAIDA).comHora(LocalTime.now().plusHours(8L)).build();
        List<Ponto> pontos = Lists.newArrayList(entrada, saida, entrada1, saida1);
        RelatorioPonto relatorio = new RelatorioPonto(pontos, LocalTime.now());

        when(pontoService.gerarRelatorio(diego.getId())).thenReturn(relatorio);

        ResponseEntity<RelatorioPontoJson> response = restTemplate.exchange(pontosEndpoint() + "/1", HttpMethod.GET, null, new ParameterizedTypeReference<RelatorioPontoJson>() {});
        RelatorioPontoJson relatorioPonto = response.getBody();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(relatorioPonto.getPontos().size()).isEqualTo(4);

    }


    private String host() {
        return "http://localhost:" + port + "/clockin/v1/";
    }

    private String pontosEndpoint() {
        return host() + "pontos";
    }


}
