package hyve.petshow.controller.converter;

import hyve.petshow.controller.representation.ServicoDetalhadoRepresentation;
import hyve.petshow.domain.ServicoDetalhado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ServicoDetalhadoConverter implements Converter<ServicoDetalhado, ServicoDetalhadoRepresentation>{
	@Autowired
	private PrestadorConverter prestadorConverter;
	@Autowired
	private ServicoConverter servicoConverter;
	@Autowired
	private AvaliacaoConverter avaliacaoConverter;
	
	@Override
    public ServicoDetalhadoRepresentation toRepresentation(ServicoDetalhado domain) {
    	if(domain == null) return new ServicoDetalhadoRepresentation();
    	ServicoDetalhadoRepresentation representation = new ServicoDetalhadoRepresentation();
    	
    	
    	representation.setId(domain.getId());
        representation.setPreco(domain.getPreco());
        representation.setTipo(servicoConverter.toRepresentation(domain.getTipo()));
        representation.setPrestadorId(domain.getPrestadorId());
        representation.setAvaliacoes(avaliacaoConverter.toRepresentationList(domain.getAvaliacoes()));

        return representation;
    }

    @Override
    public ServicoDetalhado toDomain(ServicoDetalhadoRepresentation representation) {
    	if(representation == null) return new ServicoDetalhado();
    	ServicoDetalhado domain = new ServicoDetalhado();
        
    	domain.setId(representation.getId());
    	domain.setPreco(representation.getPreco());
        domain.setTipo(servicoConverter.toDomain(representation.getTipo()));
        domain.setPrestadorId(representation.getPrestadorId());
        domain.setAvaliacoes(avaliacaoConverter.toDomainList(representation.getAvaliacoes()));

        return domain;
    }

    public List<ServicoDetalhadoRepresentation> toRepresentationList(List<ServicoDetalhado> domainList){
    	if(domainList == null) return new ArrayList<ServicoDetalhadoRepresentation>();
        List<ServicoDetalhadoRepresentation> representationList = new ArrayList<>();

        domainList.forEach(domain -> representationList.add(this.toRepresentation(domain)));
        return representationList;
    }

    public List<ServicoDetalhado> toDomainList(List<ServicoDetalhadoRepresentation> representationList){
    	if(representationList == null) return new ArrayList<ServicoDetalhado>();
        List<ServicoDetalhado> domainList = new ArrayList<>();

        representationList.forEach(representation -> domainList.add(this.toDomain(representation)));
        return domainList;
    }
}
