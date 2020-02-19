package br.com.diegjun.clockin.repository;

import br.com.diegjun.clockin.model.Usuario;
import org.springframework.data.repository.CrudRepository;

public interface UsuarioRepository extends CrudRepository<Usuario, Long> {

}
