package br.com.diegjun.clockin.controller;

import br.com.diegjun.clockin.json.UsuarioJson;
import br.com.diegjun.clockin.mapper.UsuarioMapper;
import br.com.diegjun.clockin.model.Usuario;
import br.com.diegjun.clockin.service.UsuarioService;
import org.assertj.core.api.Assertions;
import org.assertj.core.util.Lists;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.mockito.Mock;
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
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class UsuarioControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private UsuarioService usuarioService;


    @Test
    public void deveRetornarListagemUsuarios() {
        List<Usuario> usuariosPersistidos = Lists.newArrayList(new Usuario.Builder(1L).comNome("diego").comCpf("123456").comEmail("teste@teste.com.br").comDataCadastro(LocalDate.now()).build());
        List<UsuarioJson> usuarioJsons = usuariosPersistidos.stream().map((usuario) -> new UsuarioMapper().convertModelToJson(usuario)).collect(Collectors.toList());

        when(usuarioService.listar()).thenReturn(usuariosPersistidos);
        ResponseEntity<List<UsuarioJson>> response = this.restTemplate.exchange(usuariosEndpoint(), HttpMethod.GET, null, new ParameterizedTypeReference<List<UsuarioJson>>() {});

        List<UsuarioJson> usuarios = response.getBody();
        assertThat(usuarios.get(0).getId() == 1L);
        assertThat(usuarios.get(0).getNome().equals("diego"));
        assertThat(response.getStatusCode().is2xxSuccessful());

    }

    @Test
    public void deveRetornarUsuario() {
        Usuario usuario = new Usuario.Builder(1L).comNome("diego").comCpf("123456").comEmail("teste@teste.com.br").comDataCadastro(LocalDate.now()).build();
        UsuarioJson usuarioJson = new UsuarioMapper().convertModelToJson(usuario);

        when(usuarioService.getBy(anyLong())).thenReturn(usuario);
        ResponseEntity<UsuarioJson> response = this.restTemplate.exchange(usuariosEndpoint()+"/1", HttpMethod.GET, null, new ParameterizedTypeReference<UsuarioJson>() {});

        UsuarioJson usuarioRetornado = response.getBody();
        assertThat(usuarioRetornado.getId() == 1L);
        assertThat(usuarioRetornado.getNome().equals("diego"));
        assertThat(response.getStatusCode().is2xxSuccessful());

    }

    @Test
    public void deveIncluirUsuario() {

        Usuario usuario = new Usuario.Builder(null).comNome("diego").comCpf("123456").comEmail("teste@teste.com.br").comDataCadastro(LocalDate.now()).build();
        Usuario usuarioPersistido = new Usuario.Builder(1L).comNome("diego").comCpf("123456").comEmail("teste@teste.com.br").comDataCadastro(LocalDate.now()).build();
        UsuarioJson usuarioJson = new UsuarioMapper().convertModelToJson(usuario);

        when(usuarioService.criar(usuario)).thenReturn(usuarioPersistido);
        HttpEntity<UsuarioJson> body = new HttpEntity<>(usuarioJson);
        ResponseEntity<UsuarioJson> response = this.restTemplate.exchange(usuariosEndpoint(), HttpMethod.POST, body, new ParameterizedTypeReference<UsuarioJson>() {});

        UsuarioJson usuarioRetornado = response.getBody();
//        assertThat(usuarioRetornado.getId() == 1L);
//        assertThat(usuarioRetornado.getNome().equals("diego"));
//        assertThat(response.getStatusCode().is2xxSuccessful());
        //assertThat(response.getHeaders().containsKey(""));

    }
    
    

    private String host() {
        return "http://localhost:" + port + "/clockin/v1/";
    }

    private String usuariosEndpoint() {
        return host() + "usuarios";
    }
}
