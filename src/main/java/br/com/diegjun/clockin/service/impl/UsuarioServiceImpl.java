package br.com.diegjun.clockin.service.impl;

import br.com.diegjun.clockin.model.Usuario;
import br.com.diegjun.clockin.repository.UsuarioRepository;
import br.com.diegjun.clockin.service.UsuarioService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository repository;


    @Override
    public Usuario criar(Usuario usuario) {
        return repository.save(usuario);
    }

    @Override
    public List<Usuario> listar() {
        return Lists.newArrayList(repository.findAll());
    }

    @Override
    public Usuario atualizar(Usuario newUsuario, Long id) {
        return repository.findById(id).map((usuarioListado) -> {
            usuarioListado.atualizarDados(newUsuario);
            return repository.save(usuarioListado);
        }).orElseGet(() -> {
            newUsuario.setId(id);
            return repository.save(newUsuario);
        });
    }

    @Override
    public Usuario getBy(Long id) {
        return repository.findById(id).orElseThrow(() -> new IllegalArgumentException(MessageFormat.format("Usuário {0} não encontrado", id)));
    }
}
