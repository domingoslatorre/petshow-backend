package hyve.petshow.service.implementation;

import static hyve.petshow.util.AuditoriaUtils.ATIVO;
import static hyve.petshow.util.AuditoriaUtils.INATIVO;
import static hyve.petshow.util.AuditoriaUtils.atualizaAuditoria;
import static hyve.petshow.util.AuditoriaUtils.geraAuditoriaInsercao;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hyve.petshow.domain.Empresa;
import hyve.petshow.exceptions.NotFoundException;
import hyve.petshow.repository.EmpresaRepository;
import hyve.petshow.service.port.EmpresaService;

@Service
public class EmpresaServiceImpl implements EmpresaService {
	private static final String EMPRESA_NAO_ENCONTRADA = "EMPRESA_NAO_ENCONTRADA";
	@Autowired
	private EmpresaRepository repository;
	@Override
	public Empresa salvarEmpresa(Empresa empresa) {
		empresa.setAuditoria(geraAuditoriaInsercao(Optional.ofNullable(empresa.getDono().getId())));
		return repository.save(empresa);
	}

	@Override
	public Empresa buscarPorId(Long id) throws Exception {
		return repository.findById(id).orElseThrow(() -> new NotFoundException(EMPRESA_NAO_ENCONTRADA));
	}	

	@Override
	public Empresa atualizaEmpresa(Long idEmpresa, Empresa empresa) throws Exception {
		var domain = buscarPorId(idEmpresa);
		domain.setEndereco(empresa.getEndereco());
		domain.setGeolocalizacao(empresa.getGeolocalizacao());
		domain.setAuditoria(atualizaAuditoria(domain.getAuditoria(), ATIVO));
		return repository.save(domain);
	}

	@Override
	public Empresa desativaEmpresa(Long idEmpresa, Boolean flagAtivo) throws Exception {
		var domain = buscarPorId(idEmpresa);
		domain.setAuditoria(atualizaAuditoria(domain.getAuditoria(), flagAtivo ? ATIVO : INATIVO));
		return repository.save(domain);
	}

}
