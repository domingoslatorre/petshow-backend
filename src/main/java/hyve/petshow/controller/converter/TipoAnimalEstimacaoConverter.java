package hyve.petshow.controller.converter;

import java.util.Optional;

import org.springframework.stereotype.Component;

import hyve.petshow.controller.representation.TipoAnimalEstimacaoRepresentation;
import hyve.petshow.domain.TipoAnimalEstimacao;

@Component
public class TipoAnimalEstimacaoConverter implements Converter<TipoAnimalEstimacao, TipoAnimalEstimacaoRepresentation> {
    @Override
    public TipoAnimalEstimacaoRepresentation toRepresentation(TipoAnimalEstimacao domain) {
    	return Optional.ofNullable(domain).map(dominio -> {
    		var representation = new TipoAnimalEstimacaoRepresentation();
    		representation.setId(domain.getId());
            representation.setNome(domain.getNome());
            representation.setPelagem(domain.getPelagem());
            representation.setPorte(domain.getPorte());
    		return representation;
    	}).orElse(new TipoAnimalEstimacaoRepresentation()); 
    }

    @Override
    public TipoAnimalEstimacao toDomain(TipoAnimalEstimacaoRepresentation representation) {
    	return Optional.ofNullable(representation).map(representacao -> {
    		var domain = new TipoAnimalEstimacao();

            domain.setId(representacao.getId());
            domain.setNome(representacao.getNome());
            domain.setPelagem(representacao.getPelagem());
            domain.setPorte(representacao.getPorte());
            return domain;
    	}).orElse(new TipoAnimalEstimacao());
    }
}
