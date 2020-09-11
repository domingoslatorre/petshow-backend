package hyve.petshow.service.implementation;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hyve.petshow.controller.representation.MensagemRepresentation;
import hyve.petshow.domain.Cliente;
import hyve.petshow.domain.Conta;
import hyve.petshow.domain.Login;
import hyve.petshow.exceptions.BusinessException;
import hyve.petshow.exceptions.NotFoundException;
import hyve.petshow.repository.ClienteRepository;
import hyve.petshow.repository.ContaRepository;
import hyve.petshow.service.port.ContaService;

@Service
public class ContaServiceImpl implements ContaService {

	@Autowired
	private ContaRepository<Conta> repository;
	
	@Autowired
	private ClienteRepository clienteRepository;

	@Override
	public Conta salvaConta(Conta conta) throws Exception {
		validaNovaConta(conta);
		Conta save = repository.save(conta);
		return save;
	}

	private void validaNovaConta(Conta conta) throws BusinessException {
		String email = conta.getLogin().getEmail();
		Optional<Conta> buscaPorEmail = buscaPorEmail(email);
		if(buscaPorEmail.isPresent()) {
			throw new BusinessException("E-mail já cadastrado no sistema");
		}
		
		Optional<Conta> buscaPorCpf = buscaPorCpf(conta.getCpf());
		if(buscaPorCpf.isPresent()) {
			throw new BusinessException("CPF já cadastrado no sistema");
		}
	}

	@Override
	public Conta obterContaPorId(Long id) throws Exception {
		return repository.findById(id).orElseThrow(() -> new NotFoundException("Conta não encontrada"));
	}

	@Override
	public List<Conta> obterContas() {
		return repository.findAll();
	}

	@Override
	public Conta obterPorLogin(Login login) throws Exception {
		return repository.findByLogin(login).orElseThrow(() -> new NotFoundException("Conta não encontrada"));
	}

	@Override
	public MensagemRepresentation removerConta(Long id) {
		Conta conta;
		MensagemRepresentation mensagemRepresentation = new MensagemRepresentation();
		try {
			conta = obterContaPorId(id);
			repository.delete(conta);
			mensagemRepresentation.setId(id);
			mensagemRepresentation.setSucesso(!repository.existsById(id));
			return mensagemRepresentation;
		} catch (Exception e) {
			mensagemRepresentation.setId(id);
			mensagemRepresentation.setSucesso(false);
			return mensagemRepresentation;
		}

	}

	@Override
	public Optional<Conta> buscaPorCpf(String cpf) {
		return repository.findByCpf(cpf);
	}

	@Override
	public Optional<Conta> buscaPorEmail(String email) {
		// TODO Auto-generated method stub
		return repository.findByEmail(email);
	}

	@Override
	public Conta atualizaConta(Conta conta) {
		return repository.save(conta);
	}
	
	@Override
	public Cliente atualizaCliente(Cliente cliente) {
		return clienteRepository.save(cliente);
	}
	
	

}
