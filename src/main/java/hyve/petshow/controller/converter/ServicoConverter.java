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
    	var representation = new ServicoRepresentation();

    	representation.setId(domain.getId());
        representation.setNome(domain.getNome());

        return representation;
    }

    @Override
    public Servico toDomain(ServicoRepresentation representation) {
    	var domain = new Servico();

    	domain.setId(representation.getId());
        domain.setNome(representation.getNome());

        return domain;
    }

    public List<ServicoRepresentation> toRepresentationList(List<Servico> domainList){
        var representationList = new ArrayList<ServicoRepresentation>();

        domainList.forEach(domain -> representationList.add(this.toRepresentation(domain)));

        return representationList;
    }

	public List<Servico> toDomainList(List<ServicoRepresentation> representationList) {
		var domainList = new ArrayList<Servico>();

		representationList.forEach(representation -> domainList.add(this.toDomain(representation)));

		return domainList;
		
	}
}
