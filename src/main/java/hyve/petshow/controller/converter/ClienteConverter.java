package hyve.petshow.controller.converter;

import hyve.petshow.controller.representation.ClienteRepresentation;
import hyve.petshow.domain.Cliente;
import hyve.petshow.domain.embeddables.Login;
import hyve.petshow.domain.enums.TipoConta;
import org.springframework.stereotype.Component;

@Component
public class ClienteConverter implements Converter<Cliente, ClienteRepresentation> {
	private AnimalEstimacaoConverter animalConverter = new AnimalEstimacaoConverter();

	@Override
	public ClienteRepresentation toRepresentation(Cliente domain) {
		var representation = new ClienteRepresentation();
		var login = new Login(domain.getLogin().getEmail());

		representation.setId(domain.getId());
		representation.setCpf(domain.getCpf());
		representation.setEndereco(domain.getEndereco());
		representation.setFoto(domain.getFoto());
		representation.setLogin(login);
		representation.setNome(domain.getNome());
		representation.setNomeSocial(domain.getNomeSocial());
		representation.setTelefone(domain.getTelefone());
		representation.setTipo(domain.getTipo() == null ? null : domain.getTipo().getTipo());
		representation.setAnimaisEstimacao(animalConverter.toRepresentationList(domain.getAnimaisEstimacao()));

		return representation;
	}

	@Override
	public Cliente toDomain(ClienteRepresentation representation) {
		var domain = new Cliente();

		domain.setCpf(representation.getCpf());
		domain.setEndereco(representation.getEndereco());
		domain.setFoto(representation.getFoto());
		domain.setId(representation.getId());
		domain.setLogin(representation.getLogin());
		domain.setNome(representation.getNome());
		domain.setNomeSocial(representation.getNomeSocial());
		domain.setTelefone(representation.getTelefone());
		domain.setTipo(TipoConta.getTipoByInteger(representation.getTipo()));

		return domain;
	}

}
