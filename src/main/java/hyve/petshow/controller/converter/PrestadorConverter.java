package hyve.petshow.controller.converter;

import hyve.petshow.controller.representation.PrestadorRepresentation;
import hyve.petshow.domain.Prestador;
import hyve.petshow.domain.embeddables.Login;
import hyve.petshow.domain.enums.TipoConta;
import org.springframework.stereotype.Component;

@Component
public class PrestadorConverter implements Converter<Prestador, PrestadorRepresentation> {
	private ServicoDetalhadoConverter servicoConverter = new ServicoDetalhadoConverter();

    @Override
    public PrestadorRepresentation toRepresentation(Prestador domain) {
        var representation = new PrestadorRepresentation();
        var login = new Login(domain.getLogin().getEmail());

        representation.setNomeSocial(domain.getNomeSocial());
        representation.setCpf(domain.getCpf());
        representation.setEndereco(domain.getEndereco());
        representation.setFoto(domain.getFoto());
        representation.setId(domain.getId());
        representation.setLogin(login);
		representation.setNome(domain.getNome());
		representation.setNomeSocial(domain.getNomeSocial());
		representation.setTelefone(domain.getTelefone());
		representation.setTipo(domain.getTipo() == null ? null : domain.getTipo().getTipo());
        representation.setServicos(servicoConverter.toRepresentationList(domain.getServicosPrestados()));
        representation.setIsAtivo(domain.isAtivo());
		representation.setDescricao(domain.getDescricao());

        return representation;
    }

    @Override
    public Prestador toDomain(PrestadorRepresentation representation) {
        var domain = new Prestador();

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
}
