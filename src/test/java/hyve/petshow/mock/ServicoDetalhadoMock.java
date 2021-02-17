package hyve.petshow.mock;

import hyve.petshow.controller.representation.PrecoPorTipoRepresentation;
import hyve.petshow.controller.representation.PrestadorRepresentation;
import hyve.petshow.controller.representation.ServicoDetalhadoRepresentation;
import hyve.petshow.domain.ServicoDetalhado;
import hyve.petshow.domain.ServicoDetalhadoTipoAnimalEstimacao;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static hyve.petshow.mock.AdicionalMock.criaAdicional;
import static hyve.petshow.mock.AnimalEstimacaoMock.criaTipoAnimalEstimacao;
import static hyve.petshow.mock.AnimalEstimacaoMock.criaTipoAnimalEstimacaoRepresentation;
import static hyve.petshow.mock.AuditoriaMock.auditoria;
import static hyve.petshow.mock.AvaliacaoMock.criaAvaliacaoRepresentation;
import static hyve.petshow.mock.ServicoMock.servico;
import static hyve.petshow.mock.ServicoMock.servicoRepresentation;
import static hyve.petshow.util.AuditoriaUtils.ATIVO;
import static hyve.petshow.util.AuditoriaUtils.geraAuditoriaInsercao;
import static java.util.Collections.singletonList;

public class ServicoDetalhadoMock {
	public static ServicoDetalhado criaServicoDetalhado() {
		var servicoDetalhado = new ServicoDetalhado();
		var tiposAnimaisAceitos = criaServicoDetalhadoTipoAnimalEstimacao();

		servicoDetalhado.setId(1L);
		servicoDetalhado.setMediaAvaliacao(4.5F);
		servicoDetalhado.setAuditoria(auditoria(ATIVO));
		servicoDetalhado.setTipo(servico());
		servicoDetalhado.setPrestadorId(1L);
		servicoDetalhado.setAdicionais(Arrays.asList(criaAdicional(1L)));
		servicoDetalhado.setTiposAnimaisAceitos(Arrays.asList(tiposAnimaisAceitos));

		return servicoDetalhado;
	}

	public static ServicoDetalhadoRepresentation criaServicoDetalhadoRepresentation() {
		var servicoDetalhadoRepresentation = new ServicoDetalhadoRepresentation();
		var servicoDetalhado = criaServicoDetalhado();
		var prestador = new PrestadorRepresentation();

		prestador.setNome("teste");

		servicoDetalhadoRepresentation.setId(servicoDetalhado.getId());
		servicoDetalhadoRepresentation.setMediaAvaliacao(servicoDetalhado.getMediaAvaliacao());
		servicoDetalhadoRepresentation.setAvaliacoes(singletonList(criaAvaliacaoRepresentation()));
		servicoDetalhadoRepresentation.setTipo(servicoRepresentation());
		servicoDetalhadoRepresentation.setPrestador(prestador);
		servicoDetalhadoRepresentation.setPrestadorId(servicoDetalhado.getPrestadorId());

		return servicoDetalhadoRepresentation;
	}

	public static List<ServicoDetalhado> criaServicoDetalhadoList(){
		return singletonList(criaServicoDetalhado());
	}

	public static List<ServicoDetalhadoRepresentation> criaServicoDetalhadoRepresentationList(){
		return singletonList(criaServicoDetalhadoRepresentation());
	}

	public static ServicoDetalhadoTipoAnimalEstimacao criaServicoDetalhadoTipoAnimalEstimacao(){
		var servicoDetalhadoTipoAnimalEstimacao = new ServicoDetalhadoTipoAnimalEstimacao();

		servicoDetalhadoTipoAnimalEstimacao.setTipoAnimalEstimacao(criaTipoAnimalEstimacao());
		servicoDetalhadoTipoAnimalEstimacao.setServicoDetalhado(new ServicoDetalhado());
		servicoDetalhadoTipoAnimalEstimacao.setPreco(BigDecimal.valueOf(100));
		servicoDetalhadoTipoAnimalEstimacao.setAuditoria(geraAuditoriaInsercao(Optional.of(1l)));

		return servicoDetalhadoTipoAnimalEstimacao;
	}

	public static PrecoPorTipoRepresentation criaPrecoPorTipoRepresentation(){
		var precoPorTipoRepresentation = new PrecoPorTipoRepresentation();

		precoPorTipoRepresentation.setTipoAnimal(criaTipoAnimalEstimacaoRepresentation());
		precoPorTipoRepresentation.setPreco(BigDecimal.valueOf(100));
		precoPorTipoRepresentation.setAtivo(Boolean.TRUE);

		return precoPorTipoRepresentation;
	}
}
