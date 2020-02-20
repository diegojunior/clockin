package br.com.diegjun.clockin.repository;

import br.com.diegjun.clockin.model.Usuario;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UsuarioRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;


    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    public void saveTest() {

        Usuario usuarioASerPersistido = new Usuario.Builder(null).comNome("chaves").comCpf("11111").comEmail("teste@teste.com.br").comDataCadastro(LocalDate.now()).build();

        Usuario usuario = usuarioRepository.save(usuarioASerPersistido);
        assertThat(usuario.getId()).isEqualTo(1L);
        assertThat(usuario.getNome()).isEqualTo("chaves");
    }

    @Test
    public void listTest() {

        Usuario usuario1 = new Usuario.Builder(null).comNome("diego").comCpf("123456").comEmail("teste@teste.com.br").comDataCadastro(LocalDate.now()).build();


        Usuario usuario = usuarioRepository.save(usuario1);

        List<Usuario> usuarios = Lists.newArrayList(usuarioRepository.findAll());

        assertThat(usuarios.get(0).getNome()).isEqualTo("diego");
        assertThat(usuarios.get(0).getCpf()).isEqualTo("123456");
    }

    @Test
    public void findByIdTest() {

        Usuario usuario1 = new Usuario.Builder(null).comNome("core").comCpf("123456").comEmail("teste@teste.com.br").comDataCadastro(LocalDate.now()).build();

        Usuario usuarioPersistido = entityManager.persistAndFlush(usuario1);
        Usuario usuario = usuarioRepository.findById(usuarioPersistido.getId()).get();

        assertThat(usuario.getNome()).isEqualTo("core");
        assertThat(usuario.getCpf()).isEqualTo("123456");
    }

    @Test
    public void updateUsuarioTest() {

        Usuario usuarioASerPersistido = new Usuario.Builder(null).comNome("chaves").comCpf("11111").comEmail("teste@teste.com.br").comDataCadastro(LocalDate.now()).build();

        Usuario usuario = usuarioRepository.save(usuarioASerPersistido);

        Usuario usuarioAlterado = new Usuario.Builder(usuario.getId()).comNome("novo valor").comCpf("2222").comEmail("teste@teste.com.br").comDataCadastro(LocalDate.now()).build();

        Usuario found = usuarioRepository.findById(usuario.getId()).get();

        Usuario usuarioComAlteracao = usuarioRepository.save(usuarioAlterado);

        assertThat(usuarioComAlteracao.getId()).isEqualTo(usuario.getId());
        assertThat(usuarioComAlteracao.getNome()).isEqualTo("novo valor");
    }
}
