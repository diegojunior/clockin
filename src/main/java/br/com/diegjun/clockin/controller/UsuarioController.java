package br.com.diegjun.clockin.controller;

import br.com.diegjun.clockin.json.UsuarioJson;
import br.com.diegjun.clockin.mapper.UsuarioMapper;
import br.com.diegjun.clockin.model.Usuario;
import br.com.diegjun.clockin.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController()
@RequestMapping("/v1")
public class UsuarioController {

    @Autowired
    private UsuarioService service;


    @GetMapping("/usuarios")
    public ResponseEntity<List<UsuarioJson>> listagemUsuario() {

        List<UsuarioJson> usuarios = service.listar().stream().map(convert()).collect(Collectors.toList());

        return new ResponseEntity<>(usuarios, HttpStatus.OK);
    }



    @GetMapping("/usuarios/{id}")
    public ResponseEntity<UsuarioJson> consulta(@PathVariable Long id) {

        UsuarioJson usuario = new UsuarioMapper().convertModelToJson(service.getBy(id));

        return new ResponseEntity<>(usuario, HttpStatus.OK);

    }

    @PutMapping("/usuarios/{id}")
    public ResponseEntity<UsuarioJson> atualizar(@RequestBody Usuario usuario, @PathVariable Long id) {

        UsuarioJson usuarioAtualizado = new UsuarioMapper().convertModelToJson(service.atualizar(usuario, id));

        return new ResponseEntity<>(usuarioAtualizado, HttpStatus.OK);
    }

    @PostMapping("/usuarios")
    public ResponseEntity<UsuarioJson> salvar(@RequestBody Usuario usuario) {

        UsuarioJson usuarioJson = new UsuarioMapper().convertModelToJson(service.criar(usuario));

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(usuarioJson.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    private Function<Usuario, UsuarioJson> convert() {
        return (item) -> new UsuarioMapper().convertModelToJson(item);
    }

}
