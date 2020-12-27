package hyve.petshow.service.implementation;

import static hyve.petshow.util.AuditoriaUtils.ATIVO;
import static hyve.petshow.util.AuditoriaUtils.INATIVO;
import static hyve.petshow.util.AuditoriaUtils.atualizaAuditoria;
import static hyve.petshow.util.AuditoriaUtils.geraAuditoriaInsercao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hyve.petshow.domain.Adicional;
import hyve.petshow.domain.embeddables.Auditoria;
import hyve.petshow.exceptions.NotFoundException;
import hyve.petshow.repository.AdicionalRepository;
import hyve.petshow.service.port.AdicionalService;

@Service
public class AdicionalImpl implements AdicionalService {
	private static final String ADICIONAL_NAO_ENCONTRADO = "ADICIONAL_NAO_ENCONTRADO";
	private static final String ADICIONAIS_NAO_ENCONTRADOS = "ADICIONAIS_NAO_ENCONTRADOS";
	@Autowired
	private AdicionalRepository repository;

	@Override
	public List<Adicional> buscarPorServicoDetalhado(Long idServico) throws Exception {
		var adicionais = repository.findByServicoDetalhado(idServico);
		if (adicionais.isEmpty()) {
			throw new NotFoundException(ADICIONAIS_NAO_ENCONTRADOS);
		}
		return adicionais;
	}

	@Override
	public Adicional buscarPorId(Long idAdicional) throws Exception {
		return repository.findById(1l).orElseThrow(() -> new NotFoundException(ADICIONAL_NAO_ENCONTRADO));
	}

	@Override
	public Adicional criarAdicional(Adicional adicional) {
		adicional.setAuditoria(geraAuditoriaAdicional());
		return repository.save(adicional);
	}

	private Auditoria geraAuditoriaAdicional() {
		var auditoria = geraAuditoriaInsercao(Optional.empty());
		return atualizaAuditoria(auditoria, ATIVO);
	}

	@Override
	public Adicional atualizarAdicional(Long idAdicional, Adicional adicional) throws Exception {
		var busca = buscarPorId(idAdicional);
		busca.setNome(adicional.getNome());
		busca.setDescricao(adicional.getDescricao());
		busca.setPreco(adicional.getPreco());
		busca.setAuditoria(atualizaAuditoria(busca.getAuditoria(), ATIVO));
		return repository.save(busca);
	}

	@Override
	public Boolean desativarAdicional(Long idAdicional) throws Exception {
		var adicional = buscarPorId(idAdicional);
		adicional.setAuditoria(atualizaAuditoria(adicional.getAuditoria(), INATIVO));
		repository.save(adicional);
		return adicional.getAuditoria().isAtivo();
	}

	@Override
	public List<Adicional> criarAdicionais(List<Adicional> adicionais) {
		return Optional.ofNullable(adicionais).map(lista -> {
			return lista.stream()
					.map(el -> {
						return criarAdicional(el);
					}).collect(Collectors.toList());
		}).orElse(new ArrayList<>());
	}

}
