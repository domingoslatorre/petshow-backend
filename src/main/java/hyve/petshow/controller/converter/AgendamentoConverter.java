package hyve.petshow.controller.converter;

import hyve.petshow.controller.representation.AgendamentoRepresentation;
import hyve.petshow.domain.Agendamento;
import hyve.petshow.domain.AnimalEstimacaoAgendamento;
import hyve.petshow.domain.ServicoDetalhadoAgendamento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class AgendamentoConverter implements Converter<Agendamento, AgendamentoRepresentation> {
    @Autowired
    private AnimalEstimacaoConverter animalConverter;
    @Autowired
    private ServicoDetalhadoConverter servicoDetalhadoConverter;
    @Autowired
    private StatusAgendamentoConverter statusConverter;
    @Autowired
    private ClienteConverter clienteConverter;
    @Autowired
    private PrestadorConverter prestadorConverter;

    @Override
    public AgendamentoRepresentation toRepresentation(Agendamento domain) {
        var representation = new AgendamentoRepresentation();

        representation.setId(domain.getId());
        representation.setPrecoFinal(domain.getPrecoFinal());
        representation.setData(domain.getData());
        representation.setMediaAvaliacao(domain.getMediaAvaliacao());
        representation.setComentario(domain.getComentario());
        representation.setEndereco(domain.getEndereco());
        representation.setStatus(statusConverter.toRepresentation(domain.getStatus()));
        representation.setStatusId(domain.getStatus().getId());
        representation.setCliente(clienteConverter.toRepresentation(domain.getCliente()));
        representation.setClienteId(domain.getCliente().getId());
        representation.setPrestador(prestadorConverter.toRepresentation(domain.getPrestador()));
        representation.setPrestadorId(domain.getPrestador().getId());

        representation.setAnimaisAtendidos(
                animalConverter.toRepresentationList(domain.getAnimaisAtendidos().stream()
                        .map(animalEstimacaoAgendamento -> animalEstimacaoAgendamento.getAnimalEstimacao())
                        .collect(Collectors.toList())));

        representation.setServicosDetalhados(
                servicoDetalhadoConverter.toRepresentationList(domain.getServicosPrestados().stream()
                        .map(servicoDetalhadoAgendamento -> servicoDetalhadoAgendamento.getServicoDetalhado())
                        .collect(Collectors.toList())));

        return representation;
    }

    @Override
    public Agendamento toDomain(AgendamentoRepresentation representation) {
        var domain = new Agendamento();

        domain.setId(representation.getId());
        domain.setPrecoFinal(representation.getPrecoFinal());
        domain.setData(representation.getData());
        domain.setMediaAvaliacao(representation.getMediaAvaliacao());
        domain.setComentario(representation.getComentario());
        domain.setEndereco(representation.getEndereco());

        animalConverter.toDomainList(representation.getAnimaisAtendidos()).stream()
                .forEach(animalAtendido -> domain.getAnimaisAtendidos()
                        .add(new AnimalEstimacaoAgendamento(domain, animalAtendido)));

        servicoDetalhadoConverter.toDomainList(representation.getServicosDetalhados()).stream()
                .forEach(servicoDetalhado -> domain.getServicosPrestados()
                        .add(new ServicoDetalhadoAgendamento(domain, servicoDetalhado)));

        return domain;
    }
}
