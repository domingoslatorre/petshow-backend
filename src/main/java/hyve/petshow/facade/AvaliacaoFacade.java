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
	private AvaliacaoService service;
	@Autowired
	private AvaliacaoConverter converter;

	public AvaliacaoRepresentation adicionarAvaliacao(AvaliacaoRepresentation representation, Long idCliente,
			Long idServicoPrestado) throws Exception {
		var cliente = clienteService.buscarPorId(idCliente);
		var servicoAvaliado = servicoDetalhadoService.buscarPorId(idServicoPrestado);
		var avaliacao = converter.toDomain(representation);
		servicoAvaliado.addAvaliacao(avaliacao);
		avaliacao.setServicoAvaliado(servicoAvaliado);
		avaliacao.setCliente(cliente);
		var avaliacaoSalva = service.adicionarAvaliacao(avaliacao);
		return converter.toRepresentation(avaliacaoSalva);
	}

	public List<AvaliacaoRepresentation> buscarAvaliacaoPorServico(Long idServicoPrestado) throws Exception {
		var servico = servicoDetalhadoService.buscarPorId(idServicoPrestado);
		var avaliacoes = service.buscarAvaliacoesPorServico(servico);
		return converter.toRepresentationList(avaliacoes);
	}

}
