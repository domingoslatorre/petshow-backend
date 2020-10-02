package hyve.petshow.controller.converter;

import hyve.petshow.controller.representation.AnimalEstimacaoRepresentation;
import hyve.petshow.domain.AnimalEstimacao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class AnimalEstimacaoConverter implements Converter<AnimalEstimacao, AnimalEstimacaoRepresentation>{
    @Autowired
    private ClienteConverter clienteConverter;

    @Autowired
    private TipoAnimalEstimacaoConverter tipoAnimalEstimacaoConverter;

    @Override
    public AnimalEstimacaoRepresentation toRepresentation(AnimalEstimacao domain) {
    	if(domain == null) return new AnimalEstimacaoRepresentation();
        AnimalEstimacaoRepresentation representation = new AnimalEstimacaoRepresentation();

        representation.setId(domain.getId());
        representation.setNome(domain.getNome());
        representation.setFoto(domain.getFoto());
        representation.setTipo(tipoAnimalEstimacaoConverter.toRepresentation(domain.getTipo()));

        return representation;
    }

    @Override
    public AnimalEstimacao toDomain(AnimalEstimacaoRepresentation representation) {
    	if(representation == null) return new AnimalEstimacao();
        AnimalEstimacao domain = new AnimalEstimacao();

        domain.setId(representation.getId());
        domain.setNome(representation.getNome());
        domain.setFoto(representation.getFoto());
        domain.setTipo(tipoAnimalEstimacaoConverter.toDomain(representation.getTipo()));
        domain.setDono(clienteConverter.toDomain(representation.getDono()));

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
