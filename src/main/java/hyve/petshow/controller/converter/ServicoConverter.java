package hyve.petshow.controller.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import hyve.petshow.domain.enums.TipoAnimalEstimacao;
import org.springframework.stereotype.Component;

import hyve.petshow.controller.representation.ServicoDetalhadoRepresentation;
import hyve.petshow.controller.representation.ServicoRepresentation;
import hyve.petshow.domain.Servico;
import hyve.petshow.domain.ServicoDetalhado;

@Component
public class ServicoConverter implements Converter<Servico, ServicoRepresentation>{
		
	@Override
    public ServicoRepresentation toRepresentation(Servico domain) {
    	if(domain == null) return new ServicoRepresentation();
    	ServicoRepresentation representation = new ServicoRepresentation();

        representation.setNome(domain.getNome());
        representation.setDescricao(domain.getDescricao());
   
        
        return representation;
    }

    @Override
    public Servico toDomain(ServicoRepresentation representation) {
    	if(representation == null) return new Servico();
    	Servico domain = new Servico();

        domain.setNome(representation.getNome());
        domain.setDescricao(representation.getDescricao());
        
        return domain;
    }

    public List<ServicoRepresentation> toRepresentationList(List<Servico> domainList){
    	if(domainList == null) return new ArrayList<ServicoRepresentation>();
        List<ServicoRepresentation> representationList = new ArrayList<>();

        domainList.forEach(domain -> representationList.add(this.toRepresentation(domain)));
        return representationList;
    }

	public List<Servico> toDomainList(List<ServicoRepresentation> servico) {
		if(servico == null) return new ArrayList<Servico>();
		return servico.stream().map(el -> toDomain(el)).collect(Collectors.toList());
	}
}
