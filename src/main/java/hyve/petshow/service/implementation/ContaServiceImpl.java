package hyve.petshow.service.implementation;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hyve.petshow.controller.representation.MensagemRepresentation;
import hyve.petshow.domain.Conta;
import hyve.petshow.domain.Login;
import hyve.petshow.exceptions.NotFoundException;
import hyve.petshow.repository.ContaGenericaRepository;
import hyve.petshow.service.port.ContaGenericaService;
import hyve.petshow.service.port.ContaService;

@Service
public class ContaServiceImpl implements ContaGenericaService {
	private static final String CONTA_NAO_ENCONTRADA = "Conta não encontrada";
	@Autowired
	private ContaGenericaRepository repository;
	
	@Override
	public Conta adicionarConta(Conta conta) throws Exception {
		return repository.save(conta);
	}

	@Override
	public Conta buscarPorId(Long id) throws Exception {
		return repository.findById(id).orElseThrow(() -> new NotFoundException(CONTA_NAO_ENCONTRADA));
	}

	@Override
	public List<Conta> buscarContas() {
		return repository.findAll();
	}

	@Override
	public Conta realizarLogin(Login login) throws Exception {
		return repository.findByLogin(login).orElseThrow(() -> new NotFoundException(CONTA_NAO_ENCONTRADA));
	}

	@Override
	public MensagemRepresentation removerConta(Long id) throws Exception {
		repository.deleteById(id);
		MensagemRepresentation mensagem = new MensagemRepresentation();
		mensagem.setId(id);
		mensagem.setSucesso(!repository.existsById(id));
		return mensagem;
	}

	@Override
	public Optional<Conta> buscarPorCpf(String cpf) {
		return repository.findByCpf(cpf);
	}

	@Override
	public Optional<Conta> buscarPorEmail(String email) {
		return repository.findByEmail(email);
	}

	@Override
	public Conta atualizarConta(Long id, Conta conta) throws Exception {
		var contaDb = buscarPorId(id);
		conta.setLogin(contaDb.getLogin());
		return repository.save(conta);
	}

}
