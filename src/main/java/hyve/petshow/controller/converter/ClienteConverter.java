package hyve.petshow.controller.converter;

import hyve.petshow.controller.representation.ClienteRepresentation;
import hyve.petshow.domain.Cliente;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ClienteConverter implements Converter<Cliente, ClienteRepresentation> {
	@Autowired
	private AnimalEstimacaoConverter animalConverter = new AnimalEstimacaoConverter();
	@Autowired
	private ContaConverter contaConverter = new ContaConverter();

	@Override
	public ClienteRepresentation toRepresentation(Cliente domain) {
		var contaRepresentation = contaConverter.toRepresentation(domain);
		
		var representation = new ClienteRepresentation(contaRepresentation);
		representation.setAnimaisEstimacao(animalConverter.toRepresentationList(domain.getAnimaisEstimacao()));

		return representation;
	}

	@Override
	public Cliente toDomain(ClienteRepresentation representation) {
		var contaDomain = contaConverter.toDomain(representation);
		var domain = new Cliente(contaDomain);
		domain.setAnimaisEstimacao(animalConverter.toDomainList(representation.getAnimaisEstimacao()));

//		domain.setCpf(representation.getCpf());
//		domain.setEndereco(representation.getEndereco());
//		domain.setFoto(representation.getFoto());
//		domain.setId(representation.getId());
//		domain.setLogin(representation.getLogin());
//		domain.setNome(representation.getNome());
//		domain.setNomeSocial(representation.getNomeSocial());
//		domain.setTelefone(representation.getTelefone());
//		domain.setTipo(TipoConta.getTipoByInteger(representation.getTipo()));

		return domain;
	}

}
