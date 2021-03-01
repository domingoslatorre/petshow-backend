package hyve.petshow.controller;

import hyve.petshow.controller.representation.AgendamentoRepresentation;
import hyve.petshow.controller.representation.PagamentoRepresentation;
import hyve.petshow.facade.PagamentoFacade;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/pagamento")
@OpenAPIDefinition(info = @Info(title = "API relativa a pagamentos via mercado pago",
                                description = "API relativa a pagamentos via mercado pago"))
public class PagamentoController {
    @Autowired
    private PagamentoFacade pagamentoFacade;

    @Operation(summary = "Retorna preference para efetuar checkout.")
    @PostMapping(value = "/preference")
    public ResponseEntity<PagamentoRepresentation> geraPreference(
            @RequestBody AgendamentoRepresentation request) throws Exception {
        var preference = pagamentoFacade.efetuarPagamento(request);

        return ResponseEntity.ok(PagamentoRepresentation.builder()
                .preferenceId(preference.getId())
                .build());
    }
}
