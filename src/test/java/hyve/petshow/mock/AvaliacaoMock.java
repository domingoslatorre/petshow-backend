package hyve.petshow.mock;

import static hyve.petshow.mock.ClienteMock.clienteRepresentation;

import java.util.ArrayList;
import java.util.List;

import hyve.petshow.controller.representation.AvaliacaoRepresentation;
import hyve.petshow.domain.Avaliacao;
import hyve.petshow.domain.Cliente;
import hyve.petshow.domain.Prestador;
import hyve.petshow.domain.Servico;
import hyve.petshow.domain.ServicoDetalhado;
import hyve.petshow.domain.embeddables.CriteriosAvaliacao;

public class AvaliacaoMock {
	public static Avaliacao avaliacao() {
		var tipo = new Servico();
		tipo.setId(1);
		tipo.setNome("Banho");

		var servicoAvaliado = new ServicoDetalhado();
		servicoAvaliado.setId(1l);
		servicoAvaliado.setTipo(tipo);

		var prestador = new Prestador();
		prestador.setId(1l);
		prestador.setNome("TestePrestador");

		servicoAvaliado.setPrestadorId(prestador.getId());
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
		avaliacao.setCliente(cliente);

		return avaliacao;
	}

	public static AvaliacaoRepresentation avaliacaoRepresentation() {
		var avaliacao = avaliacao();
		var avaliacaoRepresentation = new AvaliacaoRepresentation();

		avaliacaoRepresentation.setId(avaliacao.getId());
		avaliacaoRepresentation.setAtencao(avaliacao.getCriteriosAvaliacao().getAtencao());
		avaliacaoRepresentation.setQualidadeProdutos(avaliacao.getCriteriosAvaliacao().getQualidadeProdutos());
		avaliacaoRepresentation.setCustoBeneficio(avaliacao.getCriteriosAvaliacao().getCustoBeneficio());
		avaliacaoRepresentation.setInfraestrutura(avaliacao.getCriteriosAvaliacao().getInfraestrutura());
		avaliacaoRepresentation.setQualidadeServico(avaliacao.getCriteriosAvaliacao().getQualidadeServico());
		avaliacaoRepresentation.setComentario(avaliacao.getCriteriosAvaliacao().getComentario());
		avaliacaoRepresentation.setMedia(avaliacao.getMediaAvaliacao());
		avaliacaoRepresentation.setCliente(clienteRepresentation());
		avaliacaoRepresentation.setServicoAvaliadoId(avaliacao.getServicoAvaliadoId());

		return avaliacaoRepresentation;
	}

	public static List<Avaliacao> avaliacaoList() {
		var avaliacaoList = new ArrayList<Avaliacao>();

		avaliacaoList.add(avaliacao());

		return avaliacaoList;
	}
}
