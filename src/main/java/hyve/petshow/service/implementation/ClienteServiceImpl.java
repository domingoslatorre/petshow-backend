package hyve.petshow.service.implementation;

import hyve.petshow.controller.representation.MensagemRepresentation;
import hyve.petshow.domain.Cliente;
import hyve.petshow.domain.embeddables.Endereco;
import hyve.petshow.domain.embeddables.Geolocalizacao;
import hyve.petshow.exceptions.NotFoundException;
import hyve.petshow.repository.ClienteRepository;
import hyve.petshow.service.port.ClienteService;
import hyve.petshow.util.GeoLocUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static hyve.petshow.util.AuditoriaUtils.*;
import static hyve.petshow.util.OkHttpUtils.getRequest;

@Service
public class ClienteServiceImpl implements ClienteService {
	private static final String CONTA_NAO_ENCONTRADA = "CONTA_NAO_ENCONTRADA";//"Conta não encontrada";

	@Autowired
	private ClienteRepository repository;

	@Override
	public Cliente buscarPorId(Long id) throws NotFoundException {
		var cliente = repository.findById(id)
				.orElseThrow(() -> new NotFoundException(CONTA_NAO_ENCONTRADA));
		return cliente;
	}

	@Override
	public Cliente atualizarConta(Long id, Cliente request) throws NotFoundException {
		var conta = buscarPorId(id);

		conta.setTelefone(request.getTelefone());
		conta.setEndereco(request.getEndereco());
		conta.setGeolocalizacao(geraGeolocalizacao(conta.getEndereco()));
		conta.setAuditoria(atualizaAuditoria(conta.getAuditoria(), ATIVO));

		return repository.save(conta);
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
	public MensagemRepresentation desativarConta(Long id) throws NotFoundException {
		var cliente = buscarPorId(id);
		var mensagem = new MensagemRepresentation();

		cliente.setAuditoria(atualizaAuditoria(cliente.getAuditoria(), INATIVO));
		cliente = repository.save(cliente);

		mensagem.setId(id);
		mensagem.setSucesso(cliente.getAuditoria().getFlagAtivo().equals(INATIVO));

		return mensagem;
	}

	@Override
	public Optional<Cliente> buscarPorEmail(String email) {
		return repository.findByEmail(email);
	}
}
