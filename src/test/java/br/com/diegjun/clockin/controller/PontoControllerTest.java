package br.com.diegjun.clockin.controller;

import br.com.diegjun.clockin.json.PontoJson;
import br.com.diegjun.clockin.model.Batida;
import br.com.diegjun.clockin.model.Ponto;
import br.com.diegjun.clockin.model.Usuario;
import br.com.diegjun.clockin.service.PontoService;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static br.com.diegjun.clockin.model.Batida.ENTRADA;
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
                .comDataHoraBatida(LocalDateTime.now().minusHours(1L))
                .comBatida("E")
                .build();

        Usuario diego = new Usuario.Builder(1L).comNome("Diego").comCpf("123456").comDataCadastro(LocalDate.now()).comEmail("diego@teste.com.br").build();

        Ponto pontoEntrada = new Ponto.Builder(null).comUsuario(diego).comDataHoraBatida(LocalDateTime.now()).comBatida(ENTRADA).build();
        Ponto pontoEntradaCadastrado = new Ponto.Builder(1L).comUsuario(diego).comDataHoraBatida(LocalDateTime.now()).comBatida(ENTRADA).build();

        Mockito.when(pontoService.registrarPonto(pontoEntrada)).thenReturn(pontoEntradaCadastrado);

        HttpEntity<PontoJson> body = new HttpEntity<>(entrada);

        ResponseEntity<PontoJson> response = restTemplate.exchange(pontosEndpoint(), HttpMethod.POST, body, new ParameterizedTypeReference<PontoJson>() {});

        PontoJson ponto = response.getBody();

        Assertions.assertThat(ponto.getId()).isEqualTo(pontoEntradaCadastrado.getId());
        Assertions.assertThat(ponto.getUsuario()).isEqualTo(pontoEntradaCadastrado.getUsuario().getId());
        Assertions.assertThat(ponto.getBatida()).isEqualTo(pontoEntradaCadastrado.getBatida().getTipo());
    }


    private String host() {
        return "http://localhost:" + port + "/clockin/v1/";
    }

    private String pontosEndpoint() {
        return host() + "pontos";
    }


}
