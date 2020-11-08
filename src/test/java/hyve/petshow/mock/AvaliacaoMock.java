package hyve.petshow.mock;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import hyve.petshow.controller.converter.AvaliacaoConverter;
import hyve.petshow.controller.representation.AvaliacaoRepresentation;
import hyve.petshow.controller.representation.ClienteRepresentation;
import hyve.petshow.controller.representation.PrestadorRepresentation;
import hyve.petshow.controller.representation.ServicoDetalhadoRepresentation;
import hyve.petshow.controller.representation.ServicoRepresentation;
import hyve.petshow.domain.Avaliacao;
import hyve.petshow.domain.embeddables.CriteriosAvaliacao;
import hyve.petshow.domain.Cliente;
import hyve.petshow.domain.Prestador;
import hyve.petshow.domain.Servico;
import hyve.petshow.domain.ServicoDetalhado;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;

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
