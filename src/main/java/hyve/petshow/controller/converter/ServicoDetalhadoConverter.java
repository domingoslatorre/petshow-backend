package hyve.petshow.controller.converter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import hyve.petshow.controller.representation.ServicoDetalhadoRepresentation;
import hyve.petshow.domain.ServicoDetalhado;

@Component
public class ServicoDetalhadoConverter implements Converter<ServicoDetalhado, ServicoDetalhadoRepresentation>{
		
	@Override
    public ServicoDetalhadoRepresentation toRepresentation(ServicoDetalhado domain) {
    	if(domain == null) return new ServicoDetalhadoRepresentation();
    	ServicoDetalhadoRepresentation representation = new ServicoDetalhadoRepresentation();
    	ServicoConverter servicoConverter = new ServicoConverter();
    	
    	representation.setId(domain.getId());
        representation.setPreco(domain.getPreco());
        representation.setTipo(servicoConverter.toRepresentation(domain.getTipo()));
//      representation.setAnimaisAceitos(animalConverter.toRepresentationList(domain.getAnimaisAceitos()));
//      representation.setPrestador(prestadorConverter.toRepresentationList(domain.getPrestador()));

        return representation;
    }

    @Override
    public ServicoDetalhado toDomain(ServicoDetalhadoRepresentation representation) {
    	if(representation == null) return new ServicoDetalhado();
    	ServicoDetalhado domain = new ServicoDetalhado();
    	ServicoConverter servicoConverter = new ServicoConverter();
        
    	domain.setId(representation.getId());
    	domain.setPreco(representation.getPreco());
        domain.setTipo(servicoConverter.toDomain(representation.getTipo()));
//      domain.setPrestador(servicoPrestador.toDomainList(representation.getPrestador()));
//    	domain.setAnimaisAceitos(representation.getAnimaisAceitos());
		
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
