package br.com.diegjun.clockin.repository;

import br.com.diegjun.clockin.model.Ponto;
import br.com.diegjun.clockin.model.Usuario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PontoRepository extends CrudRepository<Ponto, Long> {

    public List<Ponto> findByUsuario(Usuario usuario);

    @Query("select p from Ponto p where p.usuario.id = ?1")
    public List<Ponto> findByIdUsuario(Long idUsuario);
}
