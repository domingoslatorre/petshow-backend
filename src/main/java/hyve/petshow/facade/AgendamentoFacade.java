package hyve.petshow.facade;

import hyve.petshow.controller.converter.AgendamentoConverter;
import hyve.petshow.controller.representation.AgendamentoRepresentation;
import hyve.petshow.domain.StatusAgendamento;
import hyve.petshow.service.port.AgendamentoService;
import hyve.petshow.service.port.ClienteService;
import hyve.petshow.service.port.PrestadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static hyve.petshow.util.AuditoriaUtils.geraAuditoriaInsercao;

@Component
public class AgendamentoFacade {
    @Autowired
    private ClienteService clienteService;
    @Autowired
    private PrestadorService prestadorService;
    @Autowired
    private AgendamentoService agendamentoService;
    @Autowired
    private AgendamentoConverter agendamentoConverter;

    public AgendamentoRepresentation adicionarAgendamento(AgendamentoRepresentation request) throws Exception {
        var agendamento = agendamentoConverter.toDomain(request);
        var cliente = clienteService.buscarPorId(request.getClienteId());
        var prestador = prestadorService.buscarPorId(request.getPrestadorId());
        var status = new StatusAgendamento(1, "AGENDADO");

        agendamento.setStatus(status);
        agendamento.setCliente(cliente);
        agendamento.setPrestador(prestador);
        agendamento.setAuditoria(geraAuditoriaInsercao(Optional.of(request.getClienteId())));
        agendamento = agendamentoService.adicionarAgendamento(agendamento);

        var representation = agendamentoConverter.toRepresentation(agendamento);

        return representation;
    }
}
