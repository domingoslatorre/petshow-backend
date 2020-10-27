package hyve.petshow.controller.converter;

import hyve.petshow.controller.representation.ServicoRepresentation;
import hyve.petshow.domain.Servico;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ServicoConverter implements Converter<Servico, ServicoRepresentation>{
		
	@Override
    public ServicoRepresentation toRepresentation(Servico domain) {
    	ServicoRepresentation representation = new ServicoRepresentation();
    	representation.setId(domain.getId());
        representation.setNome(domain.getNome());

        return representation;
    }

    @Override
    public Servico toDomain(ServicoRepresentation representation) {
    	Servico domain = new Servico();
    	domain.setId(representation.getId());
        domain.setNome(representation.getNome());

        return domain;
    }

    public List<ServicoRepresentation> toRepresentationList(List<Servico> domainList){
        List<ServicoRepresentation> representationList = new ArrayList<>();

        domainList.forEach(domain -> representationList.add(this.toRepresentation(domain)));
        return representationList;
    }

	public List<Servico> toDomainList(List<ServicoRepresentation> representationList) {
		List<Servico> domainList = new ArrayList<>();

		representationList.forEach(representation -> domainList.add(this.toDomain(representation)));
		return domainList;
		
	}
}
