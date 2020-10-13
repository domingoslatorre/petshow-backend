package hyve.petshow.controller.converter;

import hyve.petshow.controller.representation.ClienteRepresentation;
import hyve.petshow.domain.Cliente;
import hyve.petshow.domain.Login;
import hyve.petshow.domain.enums.TipoConta;
import org.springframework.stereotype.Component;

import java.util.Optional;

// TODO: Pensar em forma de n�o repetir c�digo. S�o 02:40 da manh�, n�o me julgue pelo que fiz. Pelo q parece quando se � criada uma inst�ncia de algo ela n�o pode ser casteada ent�o n�o sei como fazer rsrs
@Component
public class ClienteConverter implements Converter<Cliente, ClienteRepresentation> {
	private AnimalEstimacaoConverter animalConverter = new AnimalEstimacaoConverter();

	@Override
	public ClienteRepresentation toRepresentation(Cliente domain) {
		if(domain == null) return new ClienteRepresentation();
//		ClienteRepresentation representation = (ClienteRepresentation) contaConverter.toRepresentation(domain);
		ClienteRepresentation representation = new ClienteRepresentation();
		representation.setId(domain.getId());
		representation.setCpf(domain.getCpf());
		representation.setEndereco(domain.getEndereco());
		representation.setFoto(domain.getFoto());
		var login = new Login();
		login.setEmail(Optional.ofNullable(domain.getLogin()).orElse(new Login()).getEmail());
		representation.setLoginEmail(login);
		representation.setNome(domain.getNome());
		representation.setNomeSocial(domain.getNomeSocial());
		representation.setTelefone(domain.getTelefone());
		representation.setTipo(domain.getTipo() == null ? null : domain.getTipo().getTipo());
		representation.setAnimaisEstimacao(animalConverter.toRepresentationList(domain.getAnimaisEstimacao()));
		return representation;
	}

	@Override
	public Cliente toDomain(ClienteRepresentation representation) {
		if(representation == null) return new Cliente();
		Cliente domain = new Cliente();

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
