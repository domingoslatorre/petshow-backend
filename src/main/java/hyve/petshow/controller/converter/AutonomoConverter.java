package hyve.petshow.controller.converter;

import hyve.petshow.controller.representation.AutonomoRepresentation;
import hyve.petshow.domain.Autonomo;
import org.springframework.stereotype.Component;

import hyve.petshow.domain.enums.TipoConta;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// TODO: IDEM ClienteCONVERTER
public class AutonomoConverter implements Converter<Autonomo, AutonomoRepresentation> {
    //private ContaConverter contaConverter = new ContaConverter();
    //private List<Pagamento> pagamentosConverter = new ArrayList<Pagamento>();

    @Override
    public AutonomoRepresentation toRepresentation(Autonomo domain) {
        if(domain == null) return new AutonomoRepresentation();
//      AutonomoRepresentation representation = (PrestadorRepresentation) contaConverter.toRepresentation(domain);
        AutonomoRepresentation representation = new AutonomoRepresentation();

        representation.setEndereco(domain.getEndereco());
        representation.setFoto(domain.getFoto());

        representation.setNomeSocial(domain.getNomeSocial());
        representation.setTelefone(domain.getTelefone());

        //representation.setPagamento(pagamentosConverter.toRepresentationList(domain.getPagamento()));
        return representation;
    }

    @Override
    public Autonomo toDomain(AutonomoRepresentation representation) {
        if(representation == null) return new Autonomo();
//      Prestador domain = (Prestador) contaConverter.toDomain(representation);
        Autonomo domain = new Autonomo();

        domain.setEndereco(representation.getEndereco());
        domain.setFoto(representation.getFoto());


        domain.setNomeSocial(representation.getNomeSocial());
        domain.setTelefone(representation.getTelefone());

        //domain.setPagamento(pagamentosConverter.toDomainList(representation.getPagamento()));
        return domain;
    }

    public List<AutonomoRepresentation> toRepresentationList(List<Autonomo> domainList){
        if(domainList == null) return new ArrayList<AutonomoRepresentation>();
        List<AutonomoRepresentation> representationList = new ArrayList<>();

        domainList.forEach(domain -> representationList.add(this.toRepresentation(domain)));
        return representationList;
    }

    public List<Autonomo> toDomainList(List<AutonomoRepresentation> autonomo) {
        if(autonomo == null) return new ArrayList<Autonomo>();
        return autonomo.stream().map(el -> toDomain(el)).collect(Collectors.toList());
    }

}
