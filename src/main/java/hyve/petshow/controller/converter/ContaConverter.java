package hyve.petshow.controller.converter;

import java.util.Optional;

import org.springframework.stereotype.Component;

import hyve.petshow.controller.representation.ContaRepresentation;
import hyve.petshow.domain.Conta;
import hyve.petshow.domain.Login;
import hyve.petshow.domain.enums.TipoConta;

@Component
public class ContaConverter implements Converter<Conta, ContaRepresentation> {

	@Override
	public ContaRepresentation toRepresentation(Conta domain) {
		ContaRepresentation contaRep = new ContaRepresentation();
		contaRep.setId(domain.getId());
		contaRep.setCpf(domain.getCpf());
		contaRep.setEndereco(domain.getEndereco());
		contaRep.setFoto(domain.getFoto());
		var login = new Login();
		login.setEmail(Optional.ofNullable(domain.getLogin()).orElse(new Login()).getEmail());
		contaRep.setLogin(login);
		contaRep.setNome(domain.getNome());
		contaRep.setNomeSocial(domain.getNomeSocial());
		contaRep.setTelefone(domain.getTelefone());
		contaRep.setTipo(domain.getTipo().getTipo());
		return contaRep;
	}

	@Override
	public Conta toDomain(ContaRepresentation representation) {
		Conta conta = new Conta();
		conta.setCpf(representation.getCpf());
		conta.setEndereco(representation.getEndereco());
		conta.setFoto(representation.getFoto());
		conta.setId(representation.getId());
		conta.setLogin(representation.getLogin());
		conta.setNome(representation.getNome());
		conta.setNomeSocial(representation.getNomeSocial());
		conta.setTelefone(representation.getTelefone());
		conta.setTipo(TipoConta.getTipoByInteger(representation.getTipo()));
		return conta;
	}
}
