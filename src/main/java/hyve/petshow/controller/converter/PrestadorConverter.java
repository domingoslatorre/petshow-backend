package hyve.petshow.controller.converter;

import hyve.petshow.controller.representation.PrestadorRepresentation;
import hyve.petshow.domain.Prestador;
import hyve.petshow.domain.enums.TipoConta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// TODO: IDEM ClienteCONVERTER
@Component
public class PrestadorConverter implements Converter<Prestador, PrestadorRepresentation> {
	@Autowired
	private ServicoDetalhadoConverter servicoConverter;

    @Override
    public PrestadorRepresentation toRepresentation(Prestador domain) {
        PrestadorRepresentation representation = new PrestadorRepresentation();
        representation.setNomeSocial(domain.getNomeSocial());

        representation.setCpf(domain.getCpf());
        representation.setEndereco(domain.getEndereco());
        representation.setFoto(domain.getFoto());
        representation.setId(domain.getId());
        representation.setLoginEmail(domain.getLogin());
		representation.setNome(domain.getNome());
		representation.setNomeSocial(domain.getNomeSocial());
		representation.setTelefone(domain.getTelefone());
		representation.setTipo(domain.getTipo() == null ? null : domain.getTipo().getTipo());
        representation.setServicos(servicoConverter.toRepresentationList(domain.getServicosPrestados()));
		representation.setDescricao(domain.getDescricao());
        return representation;
    }

    @Override
    public Prestador toDomain(PrestadorRepresentation representation) {
        Prestador domain = new Prestador();
        domain.setNomeSocial(representation.getNomeSocial());

        domain.setCpf(representation.getCpf());
		domain.setEndereco(representation.getEndereco());
		domain.setFoto(representation.getFoto());
		domain.setId(representation.getId());
		domain.setLogin(representation.getLogin());
		domain.setNome(representation.getNome());
		domain.setNomeSocial(representation.getNomeSocial());
		domain.setTelefone(representation.getTelefone());
		domain.setTipo(TipoConta.getTipoByInteger(representation.getTipo()));
        domain.setServicosPrestados(servicoConverter.toDomainList(representation.getServicos()));
        domain.setDescricao(representation.getDescricao());
        return domain;
    }

    public List<PrestadorRepresentation> toRepresentationList(List<Prestador> domainList){
        List<PrestadorRepresentation> representationList = new ArrayList<>();

        domainList.forEach(domain -> representationList.add(this.toRepresentation(domain)));
        return representationList;
    }

    public List<Prestador> toDomainList(List<PrestadorRepresentation> prestador) {
        return prestador.stream().map(el -> toDomain(el)).collect(Collectors.toList());
    }


}
