package hyve.petshow.mock;

import static hyve.petshow.mock.AuditoriaMock.auditoria;
import static hyve.petshow.mock.AvaliacaoMock.avaliacaoRepresentation;
import static hyve.petshow.mock.ServicoMock.servico;
import static hyve.petshow.mock.ServicoMock.servicoRepresentation;
import static hyve.petshow.util.AuditoriaUtils.ATIVO;
import static java.util.Collections.singletonList;

import java.util.List;

import hyve.petshow.controller.representation.ServicoDetalhadoRepresentation;
import hyve.petshow.domain.ServicoDetalhado;

public class ServicoDetalhadoMock {
	public static ServicoDetalhado servicoDetalhado() {
		var servicoDetalhado = new ServicoDetalhado();

		servicoDetalhado.setId(1L);
		servicoDetalhado.setMediaAvaliacao(4.5F);
		servicoDetalhado.setAuditoria(auditoria(ATIVO));
		servicoDetalhado.setTipo(servico());
		servicoDetalhado.setPrestadorId(1L);

		return servicoDetalhado;
	}

	public static ServicoDetalhadoRepresentation servicoDetalhadoRepresentation() {
		var servicoDetalhadoRepresentation = new ServicoDetalhadoRepresentation();
		var servicoDetalhado = servicoDetalhado();

		servicoDetalhadoRepresentation.setId(servicoDetalhado.getId());
		servicoDetalhadoRepresentation.setMediaAvaliacao(servicoDetalhado.getMediaAvaliacao());
		servicoDetalhadoRepresentation.setAvaliacoes(singletonList(avaliacaoRepresentation()));
		servicoDetalhadoRepresentation.setTipo(servicoRepresentation());
		servicoDetalhadoRepresentation.setPrestadorId(servicoDetalhado.getPrestadorId());

		return servicoDetalhadoRepresentation;
	}

	public static List<ServicoDetalhado> servicoDetalhadoList(){
		return singletonList(servicoDetalhado());
	}

	public static List<ServicoDetalhadoRepresentation> servicoDetalhadoRepresentationList(){
		return singletonList(servicoDetalhadoRepresentation());
	}
}
