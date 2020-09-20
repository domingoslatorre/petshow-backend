package hyve.petshow.controller.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import hyve.petshow.domain.enums.TipoAnimalEstimacao;
import org.springframework.stereotype.Component;

import hyve.petshow.controller.representation.ServicoDetalhadoRepresentation;
import hyve.petshow.domain.ServicoDetalhado;

@Component
public class ServicoDetalhadoConverter implements Converter<ServicoDetalhado, ServicoDetalhadoRepresentation>{
    @Override
    public ServicoDetalhadoRepresentation toRepresentation(ServicoDetalhado domain) {
    	if(domain == null) return new ServicoDetalhadoRepresentation();
    	ServicoDetalhadoRepresentation representation = new ServicoDetalhadoRepresentation();

        representation.setPreco(domain.getPreco());
        representation.setAnimaisAceitos(TipoAnimalEstimacao.valueOf(domain.getAnimaisAceitos()));
        representation.setTipo(servicoConverter.toRepresentationList(domain.getTipo()));
        representation.setPrestador(prestadorConverter.toRepresentationList(domain.getPrestador()));
        
        return representation;
    }

    @Override
    public ServicoDetalhado toDomain(ServicoDetalhadoRepresentation representation) {
    	if(representation == null) return new ServicoDetalhado();
    	ServicoDetalhado domain = new ServicoDetalhado();

        domain.setPreco(representation.getPreco());
        domain.setAnimaisAceitos(domain.getAnimaisAceitos().id());
        domain.setTipo(servicoConverter.toDomainList(representation.getTipo()));
        domain.setPrestador(servicoPrestador.toDomainList(representation.getPrestador()));
		
        return domain;
    }

    public List<ServicoDetalhadoRepresentation> toRepresentationList(List<ServicoDetalhado> domainList){
    	if(domainList == null) return new ArrayList<ServicoDetalhadoRepresentation>();
        List<ServicoDetalhadoRepresentation> representationList = new ArrayList<>();

        domainList.forEach(domain -> representationList.add(this.toRepresentation(domain)));
        return representationList;
    }

	public List<ServicoDetalhado> toDomainList(List<ServicoDetalhadoRepresentation> servicoDetalhado) {
		if(servicoDetalhado == null) return new ArrayList<ServicoDetalhado>();
		return servicoDetalhado.stream().map(el -> toDomain(el)).collect(Collectors.toList());
	}
}
