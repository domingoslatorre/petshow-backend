package hyve.petshow.controller.converter;

import org.springframework.stereotype.Component;

import hyve.petshow.controller.representation.AnimalEstimacaoRepresentation;
import hyve.petshow.domain.AnimalEstimacao;

@Component
public class AnimalEstimacaoConverter implements Converter<AnimalEstimacao, AnimalEstimacaoRepresentation>{
    private TipoAnimalEstimacaoConverter tipoAnimalEstimacaoConverter = new TipoAnimalEstimacaoConverter();

    @Override
    public AnimalEstimacaoRepresentation toRepresentation(AnimalEstimacao domain) {
        var representation = new AnimalEstimacaoRepresentation();

        representation.setId(domain.getId());
        representation.setNome(domain.getNome());
        representation.setFoto(domain.getFoto());
        representation.setTipo(tipoAnimalEstimacaoConverter.toRepresentation(domain.getTipo()));
        representation.setPelagem(domain.getPelagem());
        representation.setPorte(domain.getPorte());
        representation.setDonoId(domain.getDonoId());

        return representation;
    }

    @Override
    public AnimalEstimacao toDomain(AnimalEstimacaoRepresentation representation) {
        var domain = new AnimalEstimacao();

        domain.setId(representation.getId());
        domain.setNome(representation.getNome());
        domain.setFoto(representation.getFoto());
        domain.setTipo(tipoAnimalEstimacaoConverter.toDomain(representation.getTipo()));
        domain.setPelagem(representation.getPelagem());
        domain.setPorte(representation.getPorte());
        domain.setDonoId(representation.getDonoId());

        return domain;
    }
}
