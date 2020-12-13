package hyve.petshow.controller.converter;

import hyve.petshow.controller.representation.AgendamentoRepresentation;
import hyve.petshow.domain.Agendamento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
        representation.setAnimaisAtendidos(animalConverter.toRepresentationList(domain.getAnimaisAtendidos()));
        representation.setEndereco(domain.getEndereco());
        representation.setServicosDetalhados(servicoDetalhadoConverter.toRepresentationList(domain.getServicosDetalhados()));
        representation.setStatus(statusConverter.toRepresentation(domain.getStatus()));
        representation.setStatusId(domain.getStatus().getId());
        representation.setCliente(clienteConverter.toRepresentation(domain.getCliente()));
        representation.setClienteId(domain.getCliente().getId());
        representation.setPrestador(prestadorConverter.toRepresentation(domain.getPrestador()));
        representation.setPrestadorId(domain.getPrestador().getId());

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
        domain.setAnimaisAtendidos(animalConverter.toDomainList(representation.getAnimaisAtendidos()));
        domain.setEndereco(representation.getEndereco());
        domain.setServicosDetalhados(servicoDetalhadoConverter.toDomainList(representation.getServicosDetalhados()));

        return domain;
    }
}
