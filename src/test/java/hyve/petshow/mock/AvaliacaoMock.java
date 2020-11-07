package hyve.petshow.mock;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

		var tipo = new ServicoRepresentation();
		tipo.setId(1);
		tipo.setNome("Banho");
		tipo.setDescricao("Banho");

		var servicoAvaliado = new ServicoDetalhadoRepresentation();
		servicoAvaliado.setId(1l);
		servicoAvaliado.setPreco(BigDecimal.valueOf(30.5));
		servicoAvaliado.setTipo(tipo);
		servicoAvaliado.setAvaliacoes(new ArrayList<>());
		
		var prestador = new PrestadorRepresentation();
		prestador.setId(1l);
		prestador.setNome("TestePrestador");
		
		servicoAvaliado.setPrestadorId(prestador.getId());

		var cliente = new ClienteRepresentation();
		cliente.setId(1l);
		cliente.setNome("Teste");

		var avaliacao = new AvaliacaoRepresentation();
		avaliacao.setServicoAvaliadoId(servicoAvaliado.getId());
		avaliacao.setAtencao(5);
		avaliacao.setQualidadeProdutos(5);
		avaliacao.setCustoBeneficio(5);
		avaliacao.setInfraestrutura(5);
		avaliacao.setQualidadeServico(5);
		avaliacao.setComentario("Muito bom");
		avaliacao.setMedia(5d);
		avaliacao.setClienteId(cliente.getId());
		return avaliacao;
	}

	public static List<Avaliacao> avaliacaoList() {
		var tipo = new Servico();
		tipo.setId(1);
		tipo.setNome("Banho");

		var servicoAvaliado = new ServicoDetalhado();
		servicoAvaliado.setId(1l);
		servicoAvaliado.setPreco(BigDecimal.valueOf(30.5));
		servicoAvaliado.setTipo(tipo);
		var cliente = new Cliente();
		cliente.setId(1l);
		cliente.setNome("Teste");
		return Stream.of(new Avaliacao(), new Avaliacao(), new Avaliacao()).map(avaliacao -> {
			avaliacao.setServicoAvaliadoId(servicoAvaliado.getId());
			var info = new CriteriosAvaliacao();
			info.setAtencao(geraNota());
			info.setQualidadeProdutos(geraNota());
			info.setCustoBeneficio(geraNota());
			info.setInfraestrutura(geraNota());
			info.setQualidadeServico(geraNota());
			info.setComentario("Muito bom");
			avaliacao.setCriteriosAvaliacao(info);
			avaliacao.setClienteId(cliente.getId());
			return avaliacao;
		}).collect(Collectors.toList());
	}

	private static Integer geraNota() {
		return new Random().ints(1, 0, 5).boxed().findFirst().get();
	}

}
