package hyve.petshow.controller;

import hyve.petshow.controller.converter.ValidacaoConverter;
import hyve.petshow.controller.representation.ValidacaoRepresentation;
import hyve.petshow.domain.Validacao;
import hyve.petshow.service.port.ValidacaoService;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/validacao")
public class ValidacaoController {
    @Autowired
    private ValidacaoService validacaoService;

    @Autowired
    private ValidacaoConverter validacaoConverter;

    @Operation(summary = "Retorna todas as validacoes")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Retornado com sucesso",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ValidacaoRepresentation.class)))),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro durante a execução da operação")
    })
    @GetMapping
    public ResponseEntity<List<ValidacaoRepresentation>> obterValidacoes(){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(validacaoConverter.toRepresentationList((List<Validacao>) validacaoService.obterTodos()));
    }

    @Operation(summary = "Retorna uma validacao")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Retornado com sucesso",
                    content = @Content(schema =  @Schema(implementation = ValidacaoRepresentation.class))),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro durante a execução da operação")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ValidacaoRepresentation> obterValidacao(
            @Parameter(description = "Id da validacao a ser buscada", required = true, example = "1")
            @PathVariable Long id){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(validacaoConverter.toRepresentation(validacaoService.obterUmPorId(id).get()));
    }

    @Operation(summary = "Cria uma validacao")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Retornado com sucesso",
                    content = @Content(schema =  @Schema(implementation = ValidacaoRepresentation.class))),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro durante a execução da operação")
    })
    @PostMapping
    public ResponseEntity<ValidacaoRepresentation> criarValidacao(
            @RequestBody ValidacaoRepresentation validacaoRepresentation){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(validacaoConverter.toRepresentation(
                        validacaoService.criar(
                                validacaoConverter.toDomain(validacaoRepresentation))));
    }
}
