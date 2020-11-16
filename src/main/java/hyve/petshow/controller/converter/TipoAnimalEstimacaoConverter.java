package hyve.petshow.controller.converter;

import org.springframework.stereotype.Component;

import hyve.petshow.controller.representation.TipoAnimalEstimacaoRepresentation;
import hyve.petshow.domain.TipoAnimalEstimacao;

@Component
public class TipoAnimalEstimacaoConverter implements Converter<TipoAnimalEstimacao, TipoAnimalEstimacaoRepresentation> {
    @Override
    public TipoAnimalEstimacaoRepresentation toRepresentation(TipoAnimalEstimacao domain) {
        var representation = new TipoAnimalEstimacaoRepresentation();

        representation.setId(domain.getId());
        representation.setNome(domain.getNome());

        return representation;
    }

    @Override
    public TipoAnimalEstimacao toDomain(TipoAnimalEstimacaoRepresentation representation) {
        var domain = new TipoAnimalEstimacao();

        domain.setId(representation.getId());
        domain.setNome(representation.getNome());

        return domain;
    }
}
