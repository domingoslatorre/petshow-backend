package hyve.petshow.controller.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import hyve.petshow.domain.enums.TipoAnimalEstimacao;
import org.springframework.stereotype.Component;

import hyve.petshow.controller.representation.AnimalEstimacaoRepresentation;
import hyve.petshow.domain.AnimalEstimacao;

@Component
public class AnimalEstimacaoConverter implements Converter<AnimalEstimacao, AnimalEstimacaoRepresentation>{
    @Override
    public AnimalEstimacaoRepresentation toRepresentation(AnimalEstimacao domain) {
    	if(domain == null) return new AnimalEstimacaoRepresentation();
        AnimalEstimacaoRepresentation representation = new AnimalEstimacaoRepresentation();

        representation.setId(domain.getId());
        representation.setNome(domain.getNome());
        representation.setFoto(domain.getFoto());
        representation.setTipo(TipoAnimalEstimacao.valueOf(domain.getTipo()));

        return representation;
    }

    @Override
    public AnimalEstimacao toDomain(AnimalEstimacaoRepresentation representation) {
    	if(representation == null) return new AnimalEstimacao();
        AnimalEstimacao domain = new AnimalEstimacao();

        domain.setId(representation.getId());
        domain.setNome(representation.getNome());
        domain.setFoto(representation.getFoto());
        domain.setTipo(representation.getTipo().id());

        return domain;
    }

    public List<AnimalEstimacaoRepresentation> toRepresentationList(List<AnimalEstimacao> domainList){
    	if(domainList == null) return new ArrayList<AnimalEstimacaoRepresentation>();
        List<AnimalEstimacaoRepresentation> representationList = new ArrayList<>();

        domainList.forEach(domain -> representationList.add(this.toRepresentation(domain)));
        return representationList;
    }

	public List<AnimalEstimacao> toDomainList(List<AnimalEstimacaoRepresentation> animaisEstimacao) {
		if(animaisEstimacao == null) return new ArrayList<AnimalEstimacao>();
		return animaisEstimacao.stream().map(el -> toDomain(el)).collect(Collectors.toList());
	}
}
