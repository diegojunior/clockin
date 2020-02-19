package br.com.diegjun.clockin.controller;

import br.com.diegjun.clockin.model.Usuario;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UsuarioControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void deveRetornarListagemUsuarios() {
        Assertions.assertThat(this.restTemplate.exchange(usuariosEndpoint(), HttpMethod.GET, null, new ParameterizedTypeReference<List<Usuario>>(){}).getBody().isEmpty());
    }

    private String host() {
        return "http://localhost:" + port + "/";
    }

    private String usuariosEndpoint() {
        return host() + "usuarios";
    }
}
