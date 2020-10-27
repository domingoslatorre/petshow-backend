package hyve.petshow.controller.converter;

import hyve.petshow.controller.representation.AutonomoRepresentation;
import hyve.petshow.domain.Autonomo;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// TODO: IDEM ClienteCONVERTER
@Component
public class AutonomoConverter implements Converter<Autonomo, AutonomoRepresentation> {

    @Override
    public AutonomoRepresentation toRepresentation(Autonomo domain) {
        AutonomoRepresentation representation = new AutonomoRepresentation();

        representation.setEndereco(domain.getEndereco());
        representation.setFoto(domain.getFoto());

        representation.setNomeSocial(domain.getNomeSocial());
        representation.setTelefone(domain.getTelefone());

        return representation;
    }

    @Override
    public Autonomo toDomain(AutonomoRepresentation representation) {
        Autonomo domain = new Autonomo();

        domain.setEndereco(representation.getEndereco());
        domain.setFoto(representation.getFoto());


        domain.setNomeSocial(representation.getNomeSocial());
        domain.setTelefone(representation.getTelefone());

        return domain;
    }

    public List<AutonomoRepresentation> toRepresentationList(List<Autonomo> domainList){
        List<AutonomoRepresentation> representationList = new ArrayList<>();

        domainList.forEach(domain -> representationList.add(this.toRepresentation(domain)));
        return representationList;
    }

    public List<Autonomo> toDomainList(List<AutonomoRepresentation> autonomo) {
        return autonomo.stream().map(el -> toDomain(el)).collect(Collectors.toList());
    }

}
