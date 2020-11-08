package hyve.petshow.mock;

import hyve.petshow.controller.converter.AvaliacaoConverter;
import hyve.petshow.controller.representation.AvaliacaoRepresentation;
import hyve.petshow.domain.*;
import hyve.petshow.domain.embeddables.CriteriosAvaliacao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class AvaliacaoMock {
	public static Avaliacao avaliacao() {
		var tipo = new Servico();
		tipo.setId(1);
		tipo.setNome("Banho");

		var servicoAvaliado = new ServicoDetalhado();
		servicoAvaliado.setId(1l);
		servicoAvaliado.setPreco(BigDecimal.valueOf(30.5));
		servicoAvaliado.setTipo(tipo);

		var prestador = new Prestador();
		prestador.setId(1l);
		prestador.setNome("TestePrestador");

		servicoAvaliado.setPrestadorId(prestador.getId());
		servicoAvaliado.setAvaliacoes(new ArrayList<>());
		var cliente = new Cliente();
		cliente.setId(1l);
		cliente.setNome("Teste");

		var avaliacao = new Avaliacao();
		avaliacao.setServicoAvaliadoId(servicoAvaliado.getId());
		var info = new CriteriosAvaliacao();
		info.setAtencao(5);
		info.setQualidadeProdutos(5);
		info.setCustoBeneficio(5);
		info.setInfraestrutura(5);
		info.setQualidadeServico(5);
		info.setComentario("Muito bom");
		avaliacao.setCriteriosAvaliacao(info);
		avaliacao.setClienteId(cliente.getId());

		return avaliacao;
	}

	public static AvaliacaoRepresentation avaliacaoRepresentation() {
		var converter = new AvaliacaoConverter();

		return converter.toRepresentation(avaliacao());
	}

	public static List<Avaliacao> avaliacaoList() {
		var avaliacaoList = new ArrayList<Avaliacao>();

		avaliacaoList.add(avaliacao());

		return avaliacaoList;
	}
}
