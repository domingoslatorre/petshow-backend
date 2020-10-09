package hyve.petshow.controller;

import hyve.petshow.controller.converter.ServicoDetalhadoConverter;
import hyve.petshow.controller.representation.AvaliacaoRepresentation;
import hyve.petshow.controller.representation.MensagemRepresentation;
import hyve.petshow.controller.representation.ServicoDetalhadoRepresentation;
import hyve.petshow.exceptions.NotFoundException;
import hyve.petshow.facade.AvaliacaoFacade;
import hyve.petshow.service.port.ServicoDetalhadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class ServicoDetalhadoController {
	@Autowired
	private ServicoDetalhadoService service;
	@Autowired
	private ServicoDetalhadoConverter converter;
	@Autowired
	private AvaliacaoFacade avaliacaoFacade;
	@Autowired
	private ServicoDetalhadoConverter servicoDetalhadoConverter;
	@Autowired
	private ServicoDetalhadoService servicoDetalhadoService;

	@PostMapping("/prestador/{idPrestador}/servico-detalhado")
	public ResponseEntity <ServicoDetalhadoRepresentation> adicionarServicoDetalhado(
	        @PathVariable Long idPrestador, @RequestBody ServicoDetalhadoRepresentation request) {
		var servico = converter.toDomain(request);
		servico.setPrestadorId(idPrestador);
		servico = service.adicionarServicoDetalhado(servico);
		var representation = converter.toRepresentation(servico);
		return ResponseEntity.status(HttpStatus.CREATED).body(representation);
	}

	@GetMapping("/servico-detalhado/{id}")
	public ResponseEntity<ServicoDetalhadoRepresentation> buscarServicoDetalhado(
			@PathVariable Long id) throws NotFoundException {
		var servicoDetalhado = service.buscarPorId(id);
		var representation = converter.toRepresentation(servicoDetalhado);
		return ResponseEntity.ok(representation);
	}

	@GetMapping("/servico-detalhado/tipo-servico/{id}")
	public ResponseEntity<List<ServicoDetalhadoRepresentation>> buscarServicosDetalhadosPorTipoServico(
			@PathVariable Integer id) throws NotFoundException {
		var servicosDetalhados = service.buscarServicosDetalhadosPorTipoServico(id);
		var representation = converter.toRepresentationList(servicosDetalhados);
		return ResponseEntity.ok(representation);
	}

	@GetMapping("/prestador/{prestadorId}/servico-detalhado")
	public ResponseEntity<List<ServicoDetalhadoRepresentation>> buscarServicoDetalhadoPorPrestador(
			@PathVariable Long prestadorId) throws Exception {
		var servico = servicoDetalhadoService.buscarPorPrestadorId(prestadorId);
		var representation = servicoDetalhadoConverter.toRepresentationList(servico);
		return ResponseEntity.ok(representation);
	}

	
	@GetMapping("/prestador/{prestadorId}/servico-detalhado/{servicoId}")
	public ResponseEntity<ServicoDetalhadoRepresentation> buscarServicoDetalhadoPorPrestador(
			@PathVariable Long prestadorId, @PathVariable Long servicoId) throws Exception {
		var servico = servicoDetalhadoService.buscarPorPrestadorEId(prestadorId, servicoId);
		var representation = servicoDetalhadoConverter.toRepresentation(servico);
		return ResponseEntity.ok(representation);
	}
	
	
	@PutMapping("/prestador/{idPrestador}/servico-detalhado/{idServico}")
	public ResponseEntity<ServicoDetalhadoRepresentation> atualizarServicoDetalhado(
	        @PathVariable Long idPrestador,
			@PathVariable Long idServico,
            @RequestBody ServicoDetalhadoRepresentation request) throws Exception{
		var servico = converter.toDomain(request);
		servico = service.atualizarServicoDetalhado(idServico, servico);
		var representation = converter.toRepresentation(servico);
		return ResponseEntity.ok(representation);
	}

    @DeleteMapping("/prestador/{prestadorId}/servico-detalhado/{id}")
    public ResponseEntity<MensagemRepresentation> removerServicoDetalhado(
            @PathVariable Long prestadorId,
            @PathVariable Long id) throws Exception{
        var response = service.removerServicoDetalhado(id, prestadorId);
        return ResponseEntity.ok(response);
    }

	@PostMapping("/servico-detalhado/{id}/avaliacao")
	public ResponseEntity<ServicoDetalhadoRepresentation> adicionarAvaliacao(
			@PathVariable Long id,
			@RequestBody AvaliacaoRepresentation avaliacao) throws Exception {
		var clienteId = avaliacao.getClienteId();

		avaliacaoFacade.adicionarAvaliacao(avaliacao, clienteId, id);

		var servico = servicoDetalhadoService.buscarPorId(id);
		var representation = servicoDetalhadoConverter.toRepresentation(servico);
		return ResponseEntity.status(HttpStatus.CREATED).body(representation);
	}
}
