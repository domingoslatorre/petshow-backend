package hyve.petshow.controller.converter;

import org.springframework.stereotype.Component;

import hyve.petshow.controller.representation.ServicoDetalhadoRepresentation;
import hyve.petshow.domain.ServicoDetalhado;

@Component
public class ServicoDetalhadoConverter implements Converter<ServicoDetalhado, ServicoDetalhadoRepresentation>{
	private ServicoConverter servicoConverter = new ServicoConverter();
	private AvaliacaoConverter avaliacaoConverter = new AvaliacaoConverter();
	
	@Override
    public ServicoDetalhadoRepresentation toRepresentation(ServicoDetalhado domain) {
    	var representation = new ServicoDetalhadoRepresentation();
    	
    	representation.setId(domain.getId());
        representation.setPreco(domain.getPreco());
        representation.setTipo(servicoConverter.toRepresentation(domain.getTipo()));
        representation.setPrestadorId(domain.getPrestadorId());
        representation.setAvaliacoes(avaliacaoConverter.toRepresentationList(domain.getAvaliacoes()));
        representation.setMediaAvaliacao(domain.getMediaAvaliacao());

        return representation;
    }

    @Override
    public ServicoDetalhado toDomain(ServicoDetalhadoRepresentation representation) {
    	var domain = new ServicoDetalhado();
        
    	domain.setId(representation.getId());
    	domain.setPreco(representation.getPreco());
        domain.setTipo(servicoConverter.toDomain(representation.getTipo()));
        domain.setPrestadorId(representation.getPrestadorId());
        domain.setAvaliacoes(avaliacaoConverter.toDomainList(representation.getAvaliacoes()));
        domain.setMediaAvaliacao(representation.getMediaAvaliacao());
        return domain;
    }
}
