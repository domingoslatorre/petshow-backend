package hyve.petshow.controller.converter;

import hyve.petshow.controller.representation.AvaliacaoRepresentation;
import hyve.petshow.domain.Avaliacao;
import hyve.petshow.domain.embeddables.CriteriosAvaliacao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

@Component
public class AvaliacaoConverter implements Converter<Avaliacao, AvaliacaoRepresentation> {
	@Override
	public AvaliacaoRepresentation toRepresentation(Avaliacao domain) {
		var representation = new AvaliacaoRepresentation();

		representation.setId(domain.getId());
		if (domain.getCriteriosAvaliacao() != null) {
			var info = domain.getCriteriosAvaliacao();
			representation.setAtencao(info.getAtencao());
			representation.setQualidadeProdutos(info.getQualidadeProdutos());
			representation.setCustoBeneficio(info.getCustoBeneficio());
			representation.setInfraestrutura(info.getInfraestrutura());
			representation.setQualidadeServico(info.getQualidadeServico());
			representation.setComentario(info.getComentario());
		}
		representation.setClienteId(domain.getClienteId());
		representation.setServicoAvaliadoId(domain.getServicoAvaliadoId());
		representation.setMedia(domain.getMediaAvaliacao());

		return representation;
	}

	@Override
	public Avaliacao toDomain(AvaliacaoRepresentation representation) {
		var domain = new Avaliacao();

		domain.setId(representation.getId());
		domain.setClienteId(representation.getClienteId());
		domain.setServicoAvaliadoId(representation.getServicoAvaliadoId());
		
		var info = new CriteriosAvaliacao();
		info.setAtencao(representation.getAtencao());
		info.setQualidadeProdutos(representation.getQualidadeProdutos());
		info.setCustoBeneficio(representation.getCustoBeneficio());
		info.setInfraestrutura(representation.getInfraestrutura());
		info.setQualidadeServico(representation.getQualidadeServico());
		info.setComentario(representation.getComentario());
		
		domain.setCriteriosAvaliacao(info);

		return domain;
	}

	public Page<AvaliacaoRepresentation> toRepresentationPage(Page<Avaliacao> domainPage){
		var representationPage = new PageImpl<>(toRepresentationList(domainPage.getContent()), domainPage.getPageable(),
				domainPage.getTotalElements());

		return representationPage;
	}
}
