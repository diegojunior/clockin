package br.com.diegjun.clockin.service;

import br.com.diegjun.clockin.model.Usuario;

import java.util.List;

public interface UsuarioService {

    public Usuario criar(Usuario usuario);

    public List<Usuario> listar();

    public Usuario atualizar(Usuario usuario, Long id);

    public Usuario getBy(Long id);

}
