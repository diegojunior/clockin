package br.com.diegjun.clockin.mapper;

import br.com.diegjun.clockin.json.UsuarioJson;
import br.com.diegjun.clockin.model.Usuario;

public class UsuarioMapper implements JsonMapper<UsuarioJson, Usuario> {

    @Override
    public Usuario convertJsonToModel(UsuarioJson json) {
        return new Usuario
                    .Builder(json.getId())
                    .comNome(json.getNome())
                    .comCpf(json.getCpf())
                    .comEmail(json.getEmail())
                    .comDataCadastro(json.getDataCadastro())
                    .build();
    }

    @Override
    public UsuarioJson convertModelToJson(Usuario model) {
        return new UsuarioJson
                    .Builder(model.getId())
                    .comNome(model.getNome())
                    .comCpf(model.getCpf())
                    .comEmail(model.getEmail())
                    .comDataCadastro(model.getDataCadastro())
                    .build();
    }
}
