package br.com.diegjun.clockin.controller;

import br.com.diegjun.clockin.json.UsuarioJson;
import br.com.diegjun.clockin.mapper.UsuarioMapper;
import br.com.diegjun.clockin.model.Usuario;
import br.com.diegjun.clockin.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@RestController()
@RequestMapping("/v1")
@Tag(name = "usuarios", description = "API de usuário")
public class UsuarioController {

    @Autowired
    private UsuarioService service;


    @Operation(summary = "Listagem de usuários", description = "Lista todos os usuários cadastrados.", tags = { "usuario" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = UsuarioJson.class))))})
    @GetMapping("/usuarios")
    public ResponseEntity<List<UsuarioJson>> listagemUsuario() {

        List<UsuarioJson> usuarios = service.listar().stream().map(convert()).collect(Collectors.toList());

        return new ResponseEntity<>(usuarios, HttpStatus.OK);
    }


    @Operation(summary = "Busca por um usuário", description = "Exibe as informações do usuário.", tags = { "usuario" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioJson.class)))})
    @GetMapping("/usuarios/{id}")
    public ResponseEntity<UsuarioJson> consulta(@Parameter(description = "Id do usuário", required = true) @PathVariable Long id) {

        UsuarioJson usuario = new UsuarioMapper().convertModelToJson(service.getBy(id));

        return new ResponseEntity<>(usuario, HttpStatus.OK);

    }

    @Operation(summary = "Atualiza informações do usuário", description = "Atualiza informações do usuário.", tags = { "usuario" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "successful operation",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioJson.class)))})
    @PutMapping("/usuarios/{id}")
    public ResponseEntity<UsuarioJson> atualizar(@Parameter(description = "Dados do usuário a ser atualizado", required = true) @RequestBody UsuarioJson usuario,
                                                 @Parameter(description = "Id do usuário para atualizar", required = true) @PathVariable Long id) {

        UsuarioJson usuarioAtualizado = new UsuarioMapper().convertModelToJson(service.atualizar(new UsuarioMapper().convertJsonToModel(usuario), id));

        return new ResponseEntity<>(usuarioAtualizado, HttpStatus.OK);
    }

    @Operation(summary = "Inclusão do usuário", description = "Inclusão dos usuários.", tags = { "usuario" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "successful operation",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioJson.class)))})
    @PostMapping("/usuarios")
    public ResponseEntity<UsuarioJson> salvar(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Dados do usuário",
                                                    content = @Content(schema = @Schema(implementation = UsuarioJson.class)), required = true)
                                                  @RequestBody UsuarioJson usuario) {

        UsuarioJson usuarioJson = new UsuarioMapper().convertModelToJson(service.criar(new UsuarioMapper().convertJsonToModel(usuario)));

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(usuarioJson.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    private Function<Usuario, UsuarioJson> convert() {
        return (item) -> new UsuarioMapper().convertModelToJson(item);
    }

}
