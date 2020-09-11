package hyve.petshow.controller.converter;

import org.springframework.stereotype.Component;

import hyve.petshow.controller.representation.ClienteRepresentation;
import hyve.petshow.domain.Cliente;

// TODO: Pensar em forma de não repetir código. São 02:40 da manhã, não me julgue pelo que fiz. Pelo q parece quando se é criada uma instância de algo ela não pode ser casteada então não sei como fazer rsrs
@Component
public class ClienteConverter implements Converter<Cliente, ClienteRepresentation> {
//	private ContaConverter contaConverter = new ContaConverter();
	private AnimalEstimacaoConverter animalConverter = new AnimalEstimacaoConverter();

	@Override
	public ClienteRepresentation toRepresentation(Cliente domain) {
//		ClienteRepresentation representation = (ClienteRepresentation) contaConverter.toRepresentation(domain);
		ClienteRepresentation representation = new ClienteRepresentation();
		representation.setId(domain.getId());
		representation.setCpf(domain.getCpf());
		representation.setEndereco(domain.getEndereco());
		representation.setFoto(domain.getFoto());
		representation.setLogin(domain.getLogin());
		representation.setNome(domain.getNome());
		representation.setNomeSocial(domain.getNomeSocial());
		representation.setTelefone(domain.getTelefone());
		representation.setTipo(domain.getTipo());
		representation.setAnimaisEstimacao(animalConverter.toRepresentationList(domain.getAnimaisEstimacao()));
		return representation;
	}

	@Override
	public Cliente toDomain(ClienteRepresentation representation) {
//		Cliente domain = (Cliente) contaConverter.toDomain(representation);
		Cliente domain = new Cliente();
		domain.setCpf(representation.getCpf());
		domain.setEndereco(representation.getEndereco());
		domain.setFoto(representation.getFoto());
		domain.setId(representation.getId());
		domain.setLogin(representation.getLogin());
		domain.setNome(representation.getNome());
		domain.setNomeSocial(representation.getNomeSocial());
		domain.setTelefone(representation.getTelefone());
		domain.setTipo(representation.getTipo());
		domain.setAnimaisEstimacao(animalConverter.toDomainList(representation.getAnimaisEstimacao()));
		return domain;
	}

}
