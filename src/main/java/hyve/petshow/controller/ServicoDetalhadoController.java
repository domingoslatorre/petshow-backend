package hyve.petshow.controller;

import hyve.petshow.controller.converter.AvaliacaoConverter;
import hyve.petshow.controller.converter.ServicoDetalhadoConverter;
import hyve.petshow.controller.representation.AvaliacaoRepresentation;
import hyve.petshow.controller.representation.MensagemRepresentation;
import hyve.petshow.controller.representation.ServicoDetalhadoRepresentation;
import hyve.petshow.exceptions.NotFoundException;
import hyve.petshow.facade.AvaliacaoFacade;
import hyve.petshow.service.port.ServicoDetalhadoService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static hyve.petshow.util.PagingAndSortingUtils.geraPageable;

@RestController
@RequestMapping
@OpenAPIDefinition(info = @Info(title = "API servico detalhado", description = "API para CRUD de servico detalhado"))
public class ServicoDetalhadoController {
	@Autowired
	private ServicoDetalhadoService service;
	@Autowired
	private ServicoDetalhadoConverter converter;
	@Autowired
	private AvaliacaoConverter avaliacaoConverter;
	@Autowired
	private AvaliacaoFacade avaliacaoFacade;

	@Operation(summary = "Busca todos os serviços detalhados por prestador.")
	@GetMapping("/prestador/{prestadorId}/servico-detalhado")
	public ResponseEntity<Page<ServicoDetalhadoRepresentation>> buscarServicosDetalhadosPorPrestador(
			@Parameter(description = "Id do prestador.")
			@PathVariable Long prestadorId,
			@Parameter(description = "Número da página")
			@RequestParam("pagina") Integer pagina,
			@Parameter(description = "Número de itens")
			@RequestParam("quantidadeItens") Integer quantidadeItens) throws Exception {
		var servico = service.buscarPorPrestadorId(prestadorId, geraPageable(pagina, quantidadeItens));
		var representation = converter.toRepresentationPage(servico);

		return ResponseEntity.ok(representation);
	}

	@Operation(summary = "Busca serviços detalhados por tipo de serviço.")
	@GetMapping("/servico-detalhado/tipo-servico/{id}")
	public ResponseEntity<Page<ServicoDetalhadoRepresentation>> buscarServicosDetalhadosPorTipoServico(
			@Parameter(description = "Id do tipo de serviço.")
			@PathVariable Integer id,
			@Parameter(description = "Número da página")
			@RequestParam("pagina") Integer pagina,
			@Parameter(description = "Número de itens")
			@RequestParam("quantidadeItens") Integer quantidadeItens) throws NotFoundException {
		var servicosDetalhados = service.buscarServicosDetalhadosPorTipoServico(id, geraPageable(pagina, quantidadeItens));
		var representation = converter.toRepresentationPage(servicosDetalhados);

		return ResponseEntity.ok(representation);
	}

	@Operation(summary = "Busca avaliações por serviço detalhado.")
	@GetMapping("/servico-detalhado/{id}/avaliacoes")
	public ResponseEntity<Page<AvaliacaoRepresentation>> buscarAvaliacoesPorServicoDetalhado(
			@Parameter(description = "Id do serviço detalhado.")
			@PathVariable Long id,
			@Parameter(description = "Número da página")
			@RequestParam("pagina") Integer pagina,
			@Parameter(description = "Número de itens")
			@RequestParam("quantidadeItens") Integer quantidadeItens) throws Exception {
		var avaliacoes = avaliacaoFacade.buscarAvaliacaoPorServico(id, geraPageable(pagina, quantidadeItens));

		return ResponseEntity.ok(avaliacoes);
	}

	@Operation(summary = "Adiciona serviço detalhado para prestador.")
	@PostMapping("/prestador/{idPrestador}/servico-detalhado")
	public ResponseEntity <ServicoDetalhadoRepresentation> adicionarServicoDetalhado(
			@Parameter(description = "Id do prestador.")
			@PathVariable Long idPrestador,
			@Parameter(description = "Serviço que será inserido.")
			@RequestBody ServicoDetalhadoRepresentation request) {
		var servico = converter.toDomain(request);
		servico.setPrestadorId(idPrestador);
		servico = service.adicionarServicoDetalhado(servico);
		var representation = converter.toRepresentation(servico);

		return ResponseEntity.status(HttpStatus.CREATED).body(representation);
	}

	@Operation(summary = "Deleta serviço detalhado por prestador e pelo próprio id.")
	@DeleteMapping("/prestador/{prestadorId}/servico-detalhado/{id}")
    public ResponseEntity<MensagemRepresentation> removerServicoDetalhado(
			@Parameter(description = "Id do prestador.")
			@PathVariable Long prestadorId,
			@Parameter(description = "Id do serviço detalhado.")
			@PathVariable Long id) throws Exception{
        var response = service.removerServicoDetalhado(id, prestadorId);

        return ResponseEntity.ok(response);
    }

	@Operation(summary = "Adiciona avaliação.")
	@PostMapping("/prestador/{prestadorId}/servico-detalhado/{id}/avaliacao")
	public ResponseEntity<ServicoDetalhadoRepresentation> adicionarAvaliacao(
			@Parameter(description = "Id do prestador.")
			@PathVariable Long prestadorId,
			@Parameter(description = "Id do serviço detalhado.")
			@PathVariable Long id,
			@Parameter(description = "Avaliação que será inserida")
			@RequestBody AvaliacaoRepresentation avaliacao) throws Exception {
		var clienteId = avaliacao.getClienteId();

		avaliacaoFacade.adicionarAvaliacao(avaliacao, clienteId, id);

		var servico = service.buscarPorPrestadorIdEServicoId(prestadorId, id);
		var representation = converter.toRepresentation(servico);

		return ResponseEntity.status(HttpStatus.CREATED).body(representation);
	}

	@Operation(summary = "Busca serviço detalhado.")
	@GetMapping("/prestador/{prestadorId}/servico-detalhado/{servicoId}")
	public ResponseEntity<ServicoDetalhadoRepresentation> buscarPorPrestadorIdEServicoId(
			@Parameter(description = "Id do prestador.")
			@PathVariable Long prestadorId,
			@Parameter(description = "Id do serviço detalhado.")
			@PathVariable Long servicoId) throws Exception {
		var servico = service.buscarPorPrestadorIdEServicoId(prestadorId, servicoId);
		var representation = converter.toRepresentation(servico);

		return ResponseEntity.ok(representation);
	}

	@Operation(summary = "Atualiza serviço detalhado.")
	@PutMapping("/prestador/{idPrestador}/servico-detalhado/{idServico}")
	public ResponseEntity<ServicoDetalhadoRepresentation> atualizarServicoDetalhado(
			@Parameter(description = "Id do prestador.")
			@PathVariable Long idPrestador,
			@Parameter(description = "Id do serviço detalhado.")
			@PathVariable Long idServico,
            @Parameter(description = "Serviço detalhado a ser atualizado")
            @RequestBody ServicoDetalhadoRepresentation request) throws Exception{
		var servico = converter.toDomain(request);
		servico = service.atualizarServicoDetalhado(idServico, idPrestador, servico);
		var representation = converter.toRepresentation(servico);

		return ResponseEntity.ok(representation);
	}
}
