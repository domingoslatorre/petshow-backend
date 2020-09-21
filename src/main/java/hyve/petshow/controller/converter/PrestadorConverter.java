package hyve.petshow.controller.converter;

import hyve.petshow.controller.representation.PrestadorRepresentation;
import hyve.petshow.domain.Prestador;
import org.springframework.stereotype.Component;

import hyve.petshow.domain.enums.TipoConta;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// TODO: IDEM ClienteCONVERTER
@Component // coloca a entidade dentro do contexto Spring
public class PrestadorConverter implements Converter<Prestador, PrestadorRepresentation> {
    //private ContaConverter contaConverter = new ContaConverter();
    //private List<ServicoDetalhado> servicoDetalhadoConverter = new ArrayList<ServicoDetalhado>();
    private String descricao;

    @Override
    public PrestadorRepresentation toRepresentation(Prestador domain) {
        if(domain == null) return new PrestadorRepresentation();
//		PrestadorRepresentation representation = (PrestadorRepresentation) contaConverter.toRepresentation(domain);
        PrestadorRepresentation representation = new PrestadorRepresentation();

        representation.setEndereco(domain.getEndereco());
        representation.setFoto(domain.getFoto());

        representation.setNomeSocial(domain.getNomeSocial());
        representation.setTelefone(domain.getTelefone());

        //representation.setServicoDetalhado(servicoDetalhadoConverter.toRepresentationList(domain.getServicoDetalhado()));
        representation.setDescricao(domain.getDescricao());
        return representation;
    }

    @Override
    public Prestador toDomain(PrestadorRepresentation representation) {
        if(representation == null) return new Prestador();
//		Prestador domain = (Prestador) contaConverter.toDomain(representation);
        Prestador domain = new Prestador();

        domain.setEndereco(representation.getEndereco());
        domain.setFoto(representation.getFoto());


        domain.setNomeSocial(representation.getNomeSocial());
        domain.setTelefone(representation.getTelefone());

        //domain.setServicoDetalhado(servicoDetalhadoConverter.toDomainList(representation.getServicoDetalhado()));
        domain.setDescricao(domain.getDescricao());
        return domain;
    }

    public List<PrestadorRepresentation> toRepresentationList(List<Prestador> domainList){
        if(domainList == null) return new ArrayList<PrestadorRepresentation>();
        List<PrestadorRepresentation> representationList = new ArrayList<>();

        domainList.forEach(domain -> representationList.add(this.toRepresentation(domain)));
        return representationList;
    }

    public List<Prestador> toDomainList(List<PrestadorRepresentation> prestador) {
        if(prestador == null) return new ArrayList<Prestador>();
        return prestador.stream().map(el -> toDomain(el)).collect(Collectors.toList());
    }


}
