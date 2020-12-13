package hyve.petshow.controller;

import hyve.petshow.controller.converter.AgendamentoConverter;
import hyve.petshow.controller.representation.AgendamentoRepresentation;
import hyve.petshow.facade.AgendamentoFacade;
import hyve.petshow.service.port.AgendamentoService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static hyve.petshow.util.PagingAndSortingUtils.geraPageable;

@RestController
@RequestMapping("/agendamento")
@OpenAPIDefinition(info = @Info(title = "API agendamento", description = "API para CRUD de agendamento"))
public class AgendamentoController {
    @Autowired
    private AgendamentoService agendamentoService;
    @Autowired
    private AgendamentoFacade agendamentoFacade;
    @Autowired
    private AgendamentoConverter agendamentoConverter;

    @Operation(summary = "Adiciona um novo agendamento.")
    @PostMapping
    public ResponseEntity<AgendamentoRepresentation> adicionarAgendamento(
            @Parameter(description = "Agendamento que será inserido")
            @RequestBody AgendamentoRepresentation request) throws Exception {
        var agendamento = agendamentoFacade.adicionarAgendamento(request);

        return ResponseEntity.ok(agendamento);
    }

    @Operation(summary = "Recupera todos os agendamentos por cliente.")
    @GetMapping("/cliente/{id}")
    public ResponseEntity<Page<AgendamentoRepresentation>> buscarAgendamentosPorCliente(
            @Parameter(description = "Id do cliente cujos agendamentos serão buscados")
            @PathVariable Long id,
            @Parameter(description = "Número da página")
            @RequestParam("pagina") Integer pagina,
            @Parameter(description = "Número de itens")
            @RequestParam("quantidadeItens") Integer quantidadeItens) throws Exception {
        var agendamentos = agendamentoService.buscarAgendamentosPorCliente(id, geraPageable(pagina, quantidadeItens));
        var representation = agendamentoConverter.toRepresentationPage(agendamentos);

        return ResponseEntity.ok(representation);
    }

    @Operation(summary = "Recupera todos os agendamentos por prestador.")
    @GetMapping("/prestador/{id}")
    public ResponseEntity<Page<AgendamentoRepresentation>> buscarAgendamentosPorPrestador(
            @Parameter(description = "Id do prestador cujos agendamentos serão buscados")
            @PathVariable Long id,
            @Parameter(description = "Número da página")
            @RequestParam("pagina") Integer pagina,
            @Parameter(description = "Número de itens")
            @RequestParam("quantidadeItens") Integer quantidadeItens) throws Exception {
        var agendamentos = agendamentoService.buscarAgendamentosPorPrestador(id, geraPageable(pagina, quantidadeItens));
        var representation = agendamentoConverter.toRepresentationPage(agendamentos);

        return ResponseEntity.ok(representation);
    }
}
