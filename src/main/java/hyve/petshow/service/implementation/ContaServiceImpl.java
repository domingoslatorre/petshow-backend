package hyve.petshow.service.implementation;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hyve.petshow.domain.Conta;
import hyve.petshow.domain.Login;
import hyve.petshow.repository.ContaRepository;
import hyve.petshow.service.port.ContaService;

@Service
public class ContaServiceImpl implements ContaService {
	
	@Autowired
	private ContaRepository repository;
	
	@Override
	public Conta salvaConta(Conta conta) {
		Conta save = repository.save(conta);
		return save;
	}

	@Override
	public Optional<Conta> obterContaPorId(Long id) {
		return repository.findById(id);
	}

	@Override
	public List<Conta> obterContas() {
		return repository.findAll();
	}

	@Override
	public Optional<Conta> obterPorLogin(Login login) {
		return repository.findByLogin(login);
	}
	
}
