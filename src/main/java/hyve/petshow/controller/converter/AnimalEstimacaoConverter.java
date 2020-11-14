package hyve.petshow.controller.converter;

import hyve.petshow.controller.representation.AnimalEstimacaoRepresentation;
import hyve.petshow.controller.representation.AvaliacaoRepresentation;
import hyve.petshow.domain.AnimalEstimacao;
import hyve.petshow.domain.Avaliacao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

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

    public Page<AnimalEstimacaoRepresentation> toRepresentationPage(Page<AnimalEstimacao> domainPage){
        var representationPage = new PageImpl<>(toRepresentationList(domainPage.getContent()), domainPage.getPageable(),
                domainPage.getTotalElements());

        return representationPage;
    }

    public List<AnimalEstimacaoRepresentation> toRepresentationList(List<AnimalEstimacao> domainList){
        var representationList = new ArrayList<AnimalEstimacaoRepresentation>();

        domainList.forEach(domain -> representationList.add(this.toRepresentation(domain)));

        return representationList;
    }
}
