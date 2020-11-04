package hyve.petshow.controller.converter;

import hyve.petshow.controller.representation.TipoAnimalEstimacaoRepresentation;
import hyve.petshow.domain.TipoAnimalEstimacao;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

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

    public List<TipoAnimalEstimacaoRepresentation> toRepresentationList(List<TipoAnimalEstimacao> domainList){
        var representationList = new ArrayList<TipoAnimalEstimacaoRepresentation>();

        domainList.forEach(domain -> representationList.add(this.toRepresentation(domain)));

        return representationList;
    }
}
