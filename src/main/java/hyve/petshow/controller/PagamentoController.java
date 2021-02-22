package hyve.petshow.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.Preference;
import hyve.petshow.controller.converter.AgendamentoConverter;
import hyve.petshow.controller.representation.PagamentoRepresentation;
import hyve.petshow.exceptions.BusinessException;
import hyve.petshow.exceptions.NotFoundException;
import hyve.petshow.service.port.AgendamentoService;
import hyve.petshow.service.port.PagamentoService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/pagamento")
@OpenAPIDefinition(info = @Info(title = "API relativa a pagamentos via mercado pago",
                                description = "API relativa a pagamentos via mercado pago"))
public class PagamentoController {
    @Autowired
    private PagamentoService pagamentoService;
    @Autowired
    private AgendamentoService agendamentoService;
    @Autowired
    private AgendamentoConverter agendamentoConverter;

    @Operation(summary = "Retorna preference para efetuar checkout.")
    @GetMapping(value = "/cliente/{clienteId}/agendamento/{agendamentoId}")
    public ResponseEntity<PagamentoRepresentation> geraPreference(
            @Parameter(description = "Id do cliente pagador")
            @PathVariable Long clienteId,
            @Parameter(description = "Id do agendamento que será pago")
            @PathVariable Long agendamentoId) throws MPException, NotFoundException, BusinessException, JsonProcessingException {
        var agendamento = agendamentoService.buscarPorId(agendamentoId, clienteId);

        var preference = pagamentoService.efetuarPagamento(agendamento);

        return ResponseEntity.ok(PagamentoRepresentation.builder()
                .preferenceId(preference.getId())
                .build());
    }

    @Operation(summary = "Retorna preference para efetuar checkout.")
    @GetMapping(value = "/feedback")
    public void geraPreference(@RequestParam String collection_id,
                               @RequestParam String collection_status,
                               @RequestParam String payment_id,
                               @RequestParam String status,
                               @RequestParam String external_reference,
                               @RequestParam String payment_type,
                               @RequestParam String merchant_order_id,
                               @RequestParam String preference_id,
                               @RequestParam String site_id,
                               @RequestParam String processing_mode,
                               @RequestParam String merchant_account_id){
        System.out.println(collection_id);
    }
}
