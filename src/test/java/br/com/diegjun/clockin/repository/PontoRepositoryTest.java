package br.com.diegjun.clockin.repository;

import br.com.diegjun.clockin.model.Batida;
import br.com.diegjun.clockin.model.Ponto;
import br.com.diegjun.clockin.model.Usuario;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PontoRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PontoRepository pontoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;


    @Test
    public void registrarPontoTest() {

        Usuario usuario = criarUsuario();

        Ponto entrada = new Ponto
                .Builder(null)
                .comUsuario(usuario)
                .comHora(LocalTime.now().minusHours(1L))
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
                .comHora(LocalTime.now().plusHours(4L))
                .comBatida(Batida.ENTRADA)
                .build();

        Ponto saida2 = new Ponto
                .Builder(null)
                .comUsuario(usuario)
                .comHora(LocalTime.now().plusHours(8L))
                .comBatida(Batida.SAIDA)
                .build();

        pontoRepository.save(entrada);
        pontoRepository.save(saida);
        pontoRepository.save(entrada2);
        pontoRepository.save(saida2);

        List<Ponto> pontos = pontoRepository.findByUsuario(usuario);
        Assertions.assertThat(pontos.size()).isEqualTo(4);


    }

    private Usuario criarUsuario() {
        Usuario usuario = new Usuario
                .Builder(null)
                .comNome("diego")
                .comCpf("123456789")
                .comEmail("teste@teste.com.br")
                .comDataCadastro(LocalDate.now())
                .build();
        return entityManager.persistAndFlush(usuario);
    }
}
