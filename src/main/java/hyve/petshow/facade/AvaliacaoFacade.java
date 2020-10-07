package hyve.petshow.facade;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import hyve.petshow.controller.converter.AvaliacaoConverter;
import hyve.petshow.controller.representation.AvaliacaoRepresentation;
import hyve.petshow.service.port.AvaliacaoService;
import hyve.petshow.service.port.ClienteService;
import hyve.petshow.service.port.ServicoDetalhadoService;

@Component
public class AvaliacaoFacade {
	@Autowired
	private ClienteService clienteService;
	@Autowired
	private ServicoDetalhadoService servicoDetalhadoService;
	@Autowired
	private AvaliacaoService avaliacaoService;
	@Autowired
	private AvaliacaoConverter converter;

	public void adicionarAvaliacao(AvaliacaoRepresentation request, Long clienteId, Long servicoDetalhadoId)
			throws Exception {
		var cliente = clienteService.buscarPorId(clienteId);
		var servicoDetalhado = servicoDetalhadoService.buscarPorId(servicoDetalhadoId);
		var avaliacao = converter.toDomain(request);

		avaliacao.setServicoAvaliadoId(servicoDetalhado.getId());
		avaliacao.setClienteId(cliente.getId());

		avaliacaoService.adicionarAvaliacao(avaliacao);
	}

	public List<AvaliacaoRepresentation> buscarAvaliacaoPorServico(Long idServicoPrestado) throws Exception {
		var servico = servicoDetalhadoService.buscarPorId(idServicoPrestado);
		var avaliacoes = avaliacaoService.buscarAvaliacoesPorServicoId(servico.getId());
		return converter.toRepresentationList(avaliacoes);
	}

}
