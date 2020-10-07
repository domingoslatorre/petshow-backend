package hyve.petshow.service.port;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import hyve.petshow.controller.representation.MensagemRepresentation;
import hyve.petshow.domain.Conta;
import hyve.petshow.domain.Login;

@Service
public interface ContaService<T extends Conta> {
	T buscarPorId(Long id) throws Exception;

	List<T> buscarContas();

	MensagemRepresentation removerConta(Long id) throws Exception;

	Optional<T> buscarPorCpf(String cpf);

	Optional<T> buscarPorEmail(String email);

	T atualizarConta(Long id, T conta) throws Exception;
}
