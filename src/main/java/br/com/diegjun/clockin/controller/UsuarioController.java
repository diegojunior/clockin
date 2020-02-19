package br.com.diegjun.clockin.controller;

import br.com.diegjun.clockin.json.UsuarioJson;
import br.com.diegjun.clockin.mapper.UsuarioMapper;
import br.com.diegjun.clockin.model.Usuario;
import br.com.diegjun.clockin.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController()
@RequestMapping("/v1")
public class UsuarioController {

    @Autowired
    private UsuarioService service;


    @GetMapping("/usuarios")
    public ResponseEntity<List<UsuarioJson>> listagemUsuario() {

        List<UsuarioJson> usuarios = service.listar().stream().map((item) -> new UsuarioMapper().convertModelToJson(item)).collect(Collectors.toList());

        return new ResponseEntity<List<UsuarioJson>>(usuarios, HttpStatus.OK);
    }

    @GetMapping("/usuarios/{id}")
    public ResponseEntity<UsuarioJson> consulta(@PathVariable Long id) {

        UsuarioJson usuario = new UsuarioMapper().convertModelToJson(service.getBy(id));

        return new ResponseEntity<UsuarioJson>(usuario, HttpStatus.OK);

    }

    @PutMapping("/usuarios")
    public ResponseEntity<UsuarioJson> atualizar(@RequestBody Usuario usuario) {

        UsuarioJson usuarioAtualizado = new UsuarioMapper().convertModelToJson(service.atualizar(usuario));

        return new ResponseEntity<UsuarioJson>(usuarioAtualizado, HttpStatus.OK);
    }

}
