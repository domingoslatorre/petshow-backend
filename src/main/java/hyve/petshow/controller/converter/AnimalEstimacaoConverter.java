package hyve.petshow.controller.converter;

import hyve.petshow.controller.representation.AnimalEstimacaoRepresentation;
import hyve.petshow.domain.AnimalEstimacao;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AnimalEstimacaoConverter implements Converter<AnimalEstimacao, AnimalEstimacaoRepresentation>{
    private TipoAnimalEstimacaoConverter tipoAnimalEstimacaoConverter = new TipoAnimalEstimacaoConverter();

    @Override
    public AnimalEstimacaoRepresentation toRepresentation(AnimalEstimacao domain) {
        AnimalEstimacaoRepresentation representation = new AnimalEstimacaoRepresentation();

        representation.setId(domain.getId());
        representation.setNome(domain.getNome());
        representation.setFoto(domain.getFoto());
        representation.setTipo(tipoAnimalEstimacaoConverter.toRepresentation(domain.getTipo()));
        representation.setDonoId(domain.getDonoId());

        return representation;
    }

    @Override
    public AnimalEstimacao toDomain(AnimalEstimacaoRepresentation representation) {
        AnimalEstimacao domain = new AnimalEstimacao();

        domain.setId(representation.getId());
        domain.setNome(representation.getNome());
        domain.setFoto(representation.getFoto());
        domain.setTipo(tipoAnimalEstimacaoConverter.toDomain(representation.getTipo()));
        domain.setDonoId(representation.getDonoId());

        return domain;
    }

    public List<AnimalEstimacaoRepresentation> toRepresentationList(List<AnimalEstimacao> domainList){
        List<AnimalEstimacaoRepresentation> representationList = new ArrayList<>();

        domainList.forEach(domain -> representationList.add(this.toRepresentation(domain)));
        return representationList;
    }

	public List<AnimalEstimacao> toDomainList(List<AnimalEstimacaoRepresentation> animaisEstimacao) {
		return animaisEstimacao.stream().map(el -> toDomain(el)).collect(Collectors.toList());
	}
}
