package hyve.petshow.service.port;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import hyve.petshow.controller.representation.MensagemRepresentation;
import hyve.petshow.domain.Conta;
import hyve.petshow.domain.Login;

@Service
public interface ContaService<T extends Conta> {
	T adicionarCliente(T conta) throws Exception;

	T buscarPorId(Long id) throws Exception;

	List<T> obterContas();

	T realizarLogin(Login login) throws Exception;

	MensagemRepresentation removerConta(Long id) throws Exception;

	Optional<T> buscaPorCpf(String cpf);

	Optional<T> buscaPorEmail(String email);

	T atualizarConta(T conta);
}
