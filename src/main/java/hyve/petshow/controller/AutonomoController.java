package hyve.petshow.controller;


import hyve.petshow.controller.converter.AutonomoConverter;
import hyve.petshow.controller.representation.AutonomoRepresentation;
import hyve.petshow.controller.representation.MensagemRepresentation;
import hyve.petshow.domain.Autonomo;
import hyve.petshow.service.port.AutonomoService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/autonomo")
@OpenAPIDefinition(info = @Info(title = "API autonomo", description = "API para CRUD de autonomo"))
public class AutonomoController {
    @Autowired
    private AutonomoService service;

    @Autowired
    private AutonomoConverter converter; //converte para uma entidade de dominio para não utilizar o mesmo objeto

    @Operation(summary = "Busca autonomo por id.")
    @GetMapping("{id}")
    public ResponseEntity<AutonomoRepresentation> buscarAutonomo(
            @Parameter(description = "Id do autonomo.")
            @PathVariable Long id) throws Exception {
        ResponseEntity<AutonomoRepresentation> response = ResponseEntity.status(HttpStatus.NO_CONTENT).build();

        Autonomo autonomo = service.buscarPorId(id);

        return  ResponseEntity.status(HttpStatus.OK).body(converter.toRepresentation(autonomo));
    }

    @Operation(summary = "Atualiza autonomo.")
    @PutMapping("{id}")
    public ResponseEntity<AutonomoRepresentation> atualizarAutonomo(
            @Parameter(description = "Id do autonomo.")
            @PathVariable Long id,
            @Parameter(description = "Autonomo que será atualizado.")
            @RequestBody AutonomoRepresentation autonomo) throws Exception {
        Autonomo domain = converter.toDomain(autonomo);
        Autonomo autonomoAtualizado = service.atualizarConta(id, domain);
        AutonomoRepresentation representation = converter.toRepresentation(autonomoAtualizado);
        return ResponseEntity.status(HttpStatus.OK).body(representation);
    }

    @Operation(summary = "Deleta autonomo.")
    @DeleteMapping("{id}")
    public ResponseEntity<MensagemRepresentation> removerServicoDetalhado(
            @Parameter(description = "Id do autonomo.")
            @PathVariable Long id) throws Exception {
        ResponseEntity<MensagemRepresentation> response = ResponseEntity.status(HttpStatus.NO_CONTENT).build();

        MensagemRepresentation mensagem = service.removerConta(id);

        if(mensagem.getSucesso()){
            response = ResponseEntity.status(HttpStatus.OK).body(mensagem);
        }

        return response;
    }
}
