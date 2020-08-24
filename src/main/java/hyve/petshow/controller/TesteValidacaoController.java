package hyve.petshow.controller;

import hyve.petshow.controller.converter.TesteValidacaoConverter;
import hyve.petshow.controller.representation.TesteRepresentation;
import hyve.petshow.controller.representation.TesteValidacaoRepresentation;
import hyve.petshow.facade.port.TesteValidacaoFacade;
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

@RestController
@RequestMapping("/teste-validacao")
public class TesteValidacaoController {
    @Autowired
    private TesteValidacaoFacade testeValidacaoFacade;

    @Autowired
    private TesteValidacaoConverter testeValidacaoConverter;

    @Operation(summary = "Retorna um teste e validacao")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Retornado com sucesso",
                    content = @Content(schema = @Schema(implementation = TesteValidacaoRepresentation.class))),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro durante a execução da operação")
    })
    @GetMapping("/teste/{idTeste}/validacao/{idValidacao}")
    public ResponseEntity<TesteValidacaoRepresentation> obterTesteValidacao(
            @Parameter(description = "Id do teste a ser buscado", required = true, example = "1")
            @PathVariable("idTeste") Long idTeste,
            @Parameter(description = "Id da validacao a ser buscada", required = true, example = "1")
            @PathVariable("idValidacao") Long idValidacao){

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(testeValidacaoConverter.toRepresentation(testeValidacaoFacade.obterTesteValidacao(idTeste, idValidacao)));
    }
}
