package hyve.petshow.service.implementation;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hyve.petshow.controller.representation.MensagemRepresentation;
import hyve.petshow.domain.Cliente;
import hyve.petshow.domain.Login;
import hyve.petshow.exceptions.BusinessException;
import hyve.petshow.exceptions.NotFoundException;
import hyve.petshow.repository.ClienteRepository;
import hyve.petshow.service.port.ClienteService;

@Service
public class ClienteServiceImpl implements ClienteService {
	@Autowired
	private ClienteRepository repository;

	@Override
	public Cliente adicionarCliente(Cliente conta) throws Exception {
		validaNovaConta(conta);
		return repository.save(conta);
	}

	private void validaNovaConta(Cliente conta) throws BusinessException {
		if (repository.findByEmail(conta.getLogin().getEmail()).isPresent()) {
			throw new BusinessException("Email já cadastrado no sistema");
		}

		if (repository.findByCpf(conta.getCpf()).isPresent()) {
			throw new BusinessException("CPF já cadastrado no sistema");
		}
	}

	@Override
	public Cliente buscarPorId(Long id) throws Exception {
		return repository.findById(id).orElseThrow(() -> new NotFoundException("Conta não encontrada"));
	}

	@Override
	public List<Cliente> obterContas() {
		return repository.findAll();
	}

	@Override
	public Cliente realizarLogin(Login login) throws Exception {
		return repository.findByLogin(login)
				.orElseThrow(() -> new NotFoundException("Login informado não encontrado no sistema"));
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
	public Optional<Cliente> buscaPorCpf(String cpf) {
		return repository.findByCpf(cpf);
	}

	@Override
	public Optional<Cliente> buscaPorEmail(String email) {
		return repository.findByEmail(email);
	}

	@Override
	public Cliente atualizarConta(Cliente conta) {
		return repository.save(conta);
	}

	@Override
	public Cliente atualizaConta(Long id, Cliente conta) {
		return repository.save(conta);
	}

}
