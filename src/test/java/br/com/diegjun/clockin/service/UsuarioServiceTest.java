package br.com.diegjun.clockin.service;

import br.com.diegjun.clockin.model.Usuario;
import br.com.diegjun.clockin.repository.UsuarioRepository;
import br.com.diegjun.clockin.service.impl.UsuarioServiceImpl;
import org.assertj.core.api.Assertions;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class UsuarioServiceTest {

    @TestConfiguration
    static class UsuarioTestContextConfiguration {

        @Bean
        public UsuarioService usuarioService() {
            return new UsuarioServiceImpl();
        }
    }

    @Autowired
    private UsuarioService usuarioService;

    @MockBean
    private UsuarioRepository usuarioRepository;


    @Test
    public void listUsuariosTest() {
        List<Usuario> usuariosPersistidos = Lists.newArrayList(new Usuario.Builder(1L).comNome("diego").comCpf("123456").comEmail("teste@teste.com.br").comDataCadastro(LocalDate.now()).build());
        when(usuarioRepository.findAll()).thenReturn(usuariosPersistidos);

        List<Usuario> usuariosListados = usuarioService.listar();
        Assertions.assertThat(usuariosListados.get(0).getNome()).isEqualTo(usuariosPersistidos.get(0).getNome());
    }

    @Test
    public void getUsuarioTest() {
        Usuario usuarioPersistido = new Usuario.Builder(1L).comNome("diego").comCpf("123456").comEmail("teste@teste.com.br").comDataCadastro(LocalDate.now()).build();
        when(usuarioRepository.findById(anyLong())).thenReturn(Optional.of(usuarioPersistido));

        Usuario usuario = usuarioService.getBy(1L);
        Assertions.assertThat(usuario.getNome()).isEqualTo(usuarioPersistido.getNome());
    }

    @Test
    public void updateUsuarioTest() {
        Usuario usuarioPersistido = new Usuario.Builder(1L).comNome("diego").comCpf("123456").comEmail("teste@teste.com.br").comDataCadastro(LocalDate.now()).build();
        Usuario usuarioASerAtualizado = new Usuario.Builder(1L).comNome("chave").comCpf("11111").comEmail("teste@teste.com.br").comDataCadastro(LocalDate.now()).build();
        when(usuarioRepository.findById(anyLong())).thenReturn(Optional.of(usuarioPersistido));
        when(usuarioRepository.save(usuarioASerAtualizado)).thenReturn(usuarioASerAtualizado);

        Usuario usuario = usuarioService.atualizar(usuarioASerAtualizado, 1L);
        Assertions.assertThat(usuario.getNome()).isEqualTo(usuarioASerAtualizado.getNome());
    }

    @Test
    public void criarUsuarioTest() {

        Usuario usuarioParaSalvar = new Usuario.Builder(null).comNome("diego").comCpf("123456").comEmail("teste@teste.com.br").comDataCadastro(LocalDate.now()).build();
        Usuario usuarioPersistido = new Usuario.Builder(1L).comNome("diego").comCpf("123456").comEmail("teste@teste.com.br").comDataCadastro(LocalDate.now()).build();


        when(usuarioRepository.save(usuarioParaSalvar)).thenReturn(usuarioPersistido);

        Usuario usuario = usuarioService.criar(usuarioParaSalvar);
        Assertions.assertThat(usuario.getNome()).isEqualTo(usuarioPersistido.getNome());
        Assertions.assertThat(usuario.getId()).isEqualTo(1L);
    }
}
