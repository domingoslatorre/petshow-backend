package hyve.petshow.controller;

import hyve.petshow.controller.converter.TesteConverter;
import hyve.petshow.controller.representation.TesteRepresentation;
import hyve.petshow.domain.Teste;
import hyve.petshow.service.port.TesteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/teste")
public class TesteController {
    @Autowired
    private TesteService testeService;

    @Autowired
    private TesteConverter testeConverter;

    @Operation(summary = "Retorna todos os testes")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Retornado com sucesso",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = TesteRepresentation.class)))),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro durante a execução da operação")
    })
    @GetMapping
    public ResponseEntity<List<TesteRepresentation>> obterTestes(){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(testeConverter.toRepresentationList(testeService.obterTestes()));
    }

    @Operation(summary = "Retorna um teste")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Retornado com sucesso",
                    content = @Content(schema =  @Schema(implementation = TesteRepresentation.class))),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro durante a execução da operação")
    })
    @GetMapping("/{id}")
    public ResponseEntity<TesteRepresentation> obterTeste(
            @Parameter(description = "Id do teste a ser buscado", required = true, example = "1")
            @PathVariable Long id){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(testeConverter.toRepresentation(testeService.obterTeste(id)));
    }
}