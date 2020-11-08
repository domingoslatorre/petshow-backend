package hyve.petshow.mock;

import hyve.petshow.controller.converter.ServicoDetalhadoConverter;
import hyve.petshow.controller.representation.ServicoDetalhadoRepresentation;
import hyve.petshow.domain.ServicoDetalhado;

import java.math.BigDecimal;
import java.util.List;

import static hyve.petshow.mock.AuditoriaMock.auditoria;
import static hyve.petshow.mock.AvaliacaoMock.avaliacaoList;
import static hyve.petshow.mock.ServicoMock.servico;
import static hyve.petshow.util.AuditoriaUtils.ATIVO;
import static java.util.Collections.singletonList;

public class ServicoDetalhadoMock {
	public static ServicoDetalhado servicoDetalhado() {
		var servicoDetalhado = new ServicoDetalhado();

		servicoDetalhado.setId(1L);
		servicoDetalhado.setPreco(BigDecimal.valueOf(1000L));
		servicoDetalhado.setMediaAvaliacao(4.5F);
		servicoDetalhado.setAuditoria(auditoria(ATIVO));
		servicoDetalhado.setAvaliacoes(avaliacaoList());
		servicoDetalhado.setTipo(servico());
		servicoDetalhado.setPrestadorId(1L);

		return servicoDetalhado;
	}

	public static ServicoDetalhadoRepresentation servicoDetalhadoRepresentation() {
		var converter = new ServicoDetalhadoConverter();

		return converter.toRepresentation(servicoDetalhado());
	}

	public static List<ServicoDetalhado> servicoDetalhadoList(){
		return singletonList(servicoDetalhado());
	}

	public static List<ServicoDetalhadoRepresentation> servicoDetalhadoRepresentationList(){
		var converter = new ServicoDetalhadoConverter();

		return converter.toRepresentationList(servicoDetalhadoList());
	}
}
