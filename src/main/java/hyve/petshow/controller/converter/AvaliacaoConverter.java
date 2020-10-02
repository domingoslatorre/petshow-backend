package hyve.petshow.controller.converter;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import hyve.petshow.controller.representation.AvaliacaoRepresentation;
import hyve.petshow.domain.Avaliacao;
import hyve.petshow.domain.AvaliacaoInfo;

@Component
public class AvaliacaoConverter implements Converter<Avaliacao, AvaliacaoRepresentation> {
	private ClienteConverter clienteConverter = new ClienteConverter();
	private ServicoDetalhadoConverter servicoConverter = new ServicoDetalhadoConverter();

	@Override
	public AvaliacaoRepresentation toRepresentation(Avaliacao domain) {
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
		representation.setServicoAvaliado(servicoConverter.toRepresentation(domain.getServicoAvaliado()));
		representation.setMedia(domain.getMediaAvaliacao());
		return representation;
	}

	@Override
	public Avaliacao toDomain(AvaliacaoRepresentation representation) {
		var domain = new Avaliacao();
		domain.setId(representation.getId());
		domain.setCliente(clienteConverter.toDomain(representation.getCliente()));
		domain.setServicoAvaliado(servicoConverter.toDomain(representation.getServicoAvaliado()));
		
		var info = new AvaliacaoInfo();
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
		return domainList.stream()
				.map(el -> toRepresentation(el))
				.collect(Collectors.toList());
	}
}
