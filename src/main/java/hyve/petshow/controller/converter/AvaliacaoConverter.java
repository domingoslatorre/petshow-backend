package hyve.petshow.controller.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import hyve.petshow.controller.representation.AvaliacaoRepresentation;
import hyve.petshow.domain.Avaliacao;
import hyve.petshow.domain.CriteriosAvaliacao;

@Component
public class AvaliacaoConverter implements Converter<Avaliacao, AvaliacaoRepresentation> {
	@Autowired
	private ClienteConverter clienteConverter;
	@Autowired
	private ServicoDetalhadoConverter servicoConverter;

	@Override
	public AvaliacaoRepresentation toRepresentation(Avaliacao domain) {
		if(domain == null) return new AvaliacaoRepresentation();
		
		var representation = new AvaliacaoRepresentation();
		representation.setId(domain.getId());
		if (domain.getAvaliacaoInfo() != null) {
			var info = domain.getAvaliacaoInfo();
			representation.setAtencao(info.getAtencao());
			representation.setQualidadeProdutos(info.getQualidadeProdutos());
			representation.setCustoBeneficio(info.getCustoBeneficio());
			representation.setInfraestrutura(info.getInfraestrutura());
			representation.setQualidadeServico(info.getQualidadeServico());
			representation.setComentario(info.getComentario());
		}
		representation.setCliente(clienteConverter.toRepresentation(domain.getCliente()));
		representation.setMedia(domain.getMediaAvaliacao());
		return representation;
	}

	@Override
	public Avaliacao toDomain(AvaliacaoRepresentation representation) {
		if(representation == null) return new Avaliacao();
		var domain = new Avaliacao();
		domain.setId(representation.getId());
		domain.setCliente(clienteConverter.toDomain(representation.getCliente()));
		domain.setServicoAvaliado(servicoConverter.toDomain(representation.getServicoAvaliado()));
		
		var info = new CriteriosAvaliacao();
		info.setAtencao(representation.getAtencao());
		info.setQualidadeProdutos(representation.getQualidadeProdutos());
		info.setCustoBeneficio(representation.getCustoBeneficio());
		info.setInfraestrutura(representation.getInfraestrutura());
		info.setQualidadeServico(representation.getQualidadeServico());
		info.setComentario(representation.getComentario());
		
		domain.setAvaliacaoInfo(info);
		return domain;
	}

	
	
	public List<AvaliacaoRepresentation> toRepresentationList(List<Avaliacao> domainList) {
		if(domainList == null) return new ArrayList<AvaliacaoRepresentation>();
		return domainList.stream()
				.map(el -> toRepresentation(el))
				.collect(Collectors.toList());
	}
	
	public List<Avaliacao> toDomainList(List<AvaliacaoRepresentation> representationList) {
		if(representationList == null) return new ArrayList<Avaliacao>();
		return representationList.stream()
				.map(el -> toDomain(el))
				.collect(Collectors.toList());
	}
}
