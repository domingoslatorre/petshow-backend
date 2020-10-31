package hyve.petshow.service.implementation;

import hyve.petshow.controller.representation.MensagemRepresentation;
import hyve.petshow.domain.Cliente;
import hyve.petshow.exceptions.NotFoundException;
import hyve.petshow.repository.ClienteRepository;
import hyve.petshow.service.port.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static hyve.petshow.util.AuditoriaUtils.*;

@Service
public class ClienteServiceImpl implements ClienteService {
	private final String CONTA_NAO_ENCONTRADA = "Conta não encontrada";

	@Autowired
	private ClienteRepository repository;

	@Override
	public Cliente buscarPorId(Long id) throws NotFoundException {
		return repository.findById(id)
				.orElseThrow(() -> new NotFoundException(CONTA_NAO_ENCONTRADA));
	}

	@Override
	public Cliente atualizarConta(Long id, Cliente request) throws NotFoundException {
		var conta = buscarPorId(id);

		conta.setTelefone(request.getTelefone());
		conta.setEndereco(request.getEndereco());
		conta.setAuditoria(atualizaAuditoria(conta.getAuditoria(), ATIVO));

		return repository.save(conta);
	}

	@Override
	public MensagemRepresentation removerConta(Long id) throws NotFoundException {
		var conta = buscarPorId(id);
		var mensagem = new MensagemRepresentation();

		conta.setAuditoria(atualizaAuditoria(conta.getAuditoria(), INATIVO));
		conta = repository.save(conta);

		mensagem.setId(id);
		mensagem.setSucesso(conta.getAuditoria().getFlagAtivo().equals(INATIVO));

		return mensagem;
	}

	@Override
	public List<Cliente> buscarContas() {
		return repository.findAll();
	}

	@Override
	public Optional<Cliente> buscarPorEmail(String email) {
		return repository.findByEmail(email);
	}
}
