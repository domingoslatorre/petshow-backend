package hyve.petshow.service.implementation;

import hyve.petshow.controller.representation.MensagemRepresentation;
import hyve.petshow.domain.Cliente;
import hyve.petshow.domain.Login;
import hyve.petshow.exceptions.BusinessException;
import hyve.petshow.exceptions.NotFoundException;
import hyve.petshow.repository.ClienteRepository;
import hyve.petshow.service.port.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteServiceImpl implements ClienteService {
	@Autowired
	private ClienteRepository repository;

	@Override
	public Cliente buscarPorId(Long id) throws Exception {
		return repository.findById(id).orElseThrow(() -> new NotFoundException("Conta não encontrada"));
	}

	@Override
	public List<Cliente> buscarContas() {
		return repository.findAll();
	}

	@Override
	public MensagemRepresentation removerConta(Long id) {
		repository.deleteById(id);
		MensagemRepresentation mensagem = new MensagemRepresentation();
		mensagem.setId(id);
		mensagem.setSucesso(!repository.existsById(id));
		return mensagem;
	}

	@Override
	public Optional<Cliente> buscarPorCpf(String cpf) {
		return repository.findByCpf(cpf);
	}

	@Override
	public Optional<Cliente> buscarPorEmail(String email) {
		return repository.findByEmail(email);
	}

	@Override
	public Cliente atualizarConta(Long id, Cliente conta) throws NotFoundException {
		repository.findById(id).orElseThrow(()->new NotFoundException("Conta não encontrada"));
		return repository.save(conta);
	}
}
