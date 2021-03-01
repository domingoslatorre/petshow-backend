package hyve.petshow.facade;

import hyve.petshow.controller.converter.AvaliacaoConverter;
import hyve.petshow.controller.representation.AvaliacaoRepresentation;
import hyve.petshow.domain.Avaliacao;
import hyve.petshow.exceptions.NotFoundException;
import hyve.petshow.service.port.AgendamentoService;
import hyve.petshow.service.port.AvaliacaoService;
import hyve.petshow.service.port.ClienteService;
import hyve.petshow.service.port.ServicoDetalhadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
public class AvaliacaoFacade {
	@Autowired
	private ClienteService clienteService;
	@Autowired
	private ServicoDetalhadoService servicoDetalhadoService;
	@Autowired
	private AvaliacaoService avaliacaoService;
	@Autowired
	private AgendamentoService agendamentoService;
	@Autowired
	private AvaliacaoConverter converter;

	public Avaliacao adicionarAvaliacao(AvaliacaoRepresentation request, Long agendamentoId)
			throws Exception {
		var clienteId = request.getCliente().getId();
		var servicoAvaliadoId = request.getServicoAvaliadoId();

		var cliente = clienteService.buscarPorId(clienteId);
		var avaliacao = converter.toDomain(request);
		var agendamento = agendamentoService.buscarPorIdAtivo(agendamentoId, clienteId);

		avaliacao.setCliente(cliente);
		avaliacao.setServicoAvaliadoId(servicoAvaliadoId);
		avaliacao.setAgendamentoAvaliado(agendamento);

		var response = avaliacaoService.adicionarAvaliacao(avaliacao);

		this.atualizaMediaServico(servicoAvaliadoId);

		return response;
	}

	public Page<AvaliacaoRepresentation> buscarAvaliacaoPorServico(Long idServicoPrestado, Pageable pageable)
			throws Exception {
		var servico = servicoDetalhadoService.buscarPorId(idServicoPrestado);
		var avaliacoes = avaliacaoService.buscarAvaliacoesPorServicoId(servico.getId(), pageable);

		return converter.toRepresentationPage(avaliacoes);
	}

	private void atualizaMediaServico(Long servicoId) throws NotFoundException {
		var mediaAvaliacaoServico = avaliacaoService.buscarMediaAvaliacaoPorServicoDetalhadoId(servicoId);

		servicoDetalhadoService.atualizarMediaAvaliacaoServicoDetalhado(servicoId, mediaAvaliacaoServico);
	}
}
