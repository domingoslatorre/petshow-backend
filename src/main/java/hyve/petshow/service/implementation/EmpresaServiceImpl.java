package hyve.petshow.service.implementation;

import static hyve.petshow.util.AuditoriaUtils.ATIVO;
import static hyve.petshow.util.AuditoriaUtils.INATIVO;
import static hyve.petshow.util.AuditoriaUtils.atualizaAuditoria;
import static hyve.petshow.util.AuditoriaUtils.geraAuditoriaInsercao;
import static hyve.petshow.util.OkHttpUtils.getRequest;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hyve.petshow.domain.Empresa;
import hyve.petshow.domain.embeddables.Endereco;
import hyve.petshow.domain.embeddables.Geolocalizacao;
import hyve.petshow.exceptions.NotFoundException;
import hyve.petshow.repository.EmpresaRepository;
import hyve.petshow.service.port.EmpresaService;
import hyve.petshow.util.GeoLocUtils;

@Service
public class EmpresaServiceImpl implements EmpresaService {
	private static final String EMPRESA_NAO_ENCONTRADA = "EMPRESA_NAO_ENCONTRADA";
	@Autowired
	private EmpresaRepository repository;
	@Override
	public Empresa salvarEmpresa(Empresa empresa) {
		empresa.setAuditoria(geraAuditoriaInsercao(Optional.ofNullable(empresa.getDono().getId())));
		empresa.setGeolocalizacao(geraGeolocalizacao(empresa.getEndereco()));
		return repository.save(empresa);
	}

    private Geolocalizacao geraGeolocalizacao(Endereco endereco) {
		var geolocalizacao = new Geolocalizacao();
    	try {
    		var url = GeoLocUtils.geraUrl(endereco);
        	var response = getRequest(url);
        	var geoloc = GeoLocUtils.mapeiaJson(response);
        	geolocalizacao.setGeolocLatitude(geoloc.getLat());
        	geolocalizacao.setGeolocLongitude(geoloc.getLon());
        	return geolocalizacao;
    	} catch (Exception e) {
    		return geolocalizacao;
    	}    	
	}
	@Override
	public Empresa buscarPorId(Long id) throws Exception {
		return repository.findById(id).orElseThrow(() -> new NotFoundException(EMPRESA_NAO_ENCONTRADA));
	}	

	@Override
	public Empresa atualizaEmpresa(Long idEmpresa, Empresa empresa) throws Exception {
		var domain = buscarPorId(idEmpresa);
		domain.setEndereco(empresa.getEndereco());
		domain.setGeolocalizacao(geraGeolocalizacao(domain.getEndereco()));
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
