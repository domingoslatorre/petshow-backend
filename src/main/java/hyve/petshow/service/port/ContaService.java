package hyve.petshow.service.port;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import hyve.petshow.controller.representation.MensagemRepresentation;
import hyve.petshow.domain.Cliente;
import hyve.petshow.domain.Conta;
import hyve.petshow.domain.Login;

@Service
public interface ContaService {
	Conta salvaConta(Conta conta) throws Exception;

	Conta obterContaPorId(Long id) throws Exception;

	List<Conta> obterContas();

	Conta obterPorLogin(Login login) throws Exception;

	MensagemRepresentation removerConta(Long id);
	
	Optional<Conta> buscaPorCpf(String cpf);
	
	Optional<Conta> buscaPorEmail(String email);
	
	Conta atualizaConta(Conta conta);
	
	Cliente atualizaCliente(Cliente cliente);
}
