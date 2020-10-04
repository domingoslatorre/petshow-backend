package hyve.petshow.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hyve.petshow.controller.converter.PrestadorConverter;
import hyve.petshow.controller.converter.ServicoDetalhadoConverter;
import hyve.petshow.controller.representation.AvaliacaoRepresentation;
import hyve.petshow.controller.representation.ClienteRepresentation;
import hyve.petshow.controller.representation.MensagemRepresentation;
import hyve.petshow.controller.representation.PrestadorRepresentation;
import hyve.petshow.controller.representation.ServicoDetalhadoRepresentation;
import hyve.petshow.domain.Login;
import hyve.petshow.domain.Prestador;
import hyve.petshow.service.port.PrestadorService;
import hyve.petshow.service.port.ServicoDetalhadoService;

@RestController // controle de REST
@RequestMapping("/prestador")
@CrossOrigin(origins = { "http://localhost:4200", "https://petshow-frontend.herokuapp.com", "http:0.0.0.0:4200" }) // quem
																													// pode
																													// usar
																													// esses
																													// serviços
																													// nesse
																													// controller
public class PrestadorController {
	@Autowired // instancia automaticamente
	private PrestadorService service; //

	@Autowired
	private PrestadorConverter converter; // converte para uma entidade de dominio para não utilizar o mesmo objeto

	@Autowired
	private AvaliacaoFacade avaliacaoFacade;

	@Autowired
	private ServicoDetalhadoConverter servicoDetalhadoConverter;

	@Autowired
	private ServicoDetalhadoService servicoDetalhadoService;

	@GetMapping("{id}")
	public ResponseEntity<PrestadorRepresentation> buscarPrestador(@PathVariable Long id) throws Exception {
		Prestador prestador = service.buscarPorId(id);

		return ResponseEntity.status(HttpStatus.OK).body(converter.toRepresentation(prestador));
	}

	@PutMapping("{id}")
	public ResponseEntity<PrestadorRepresentation> atualizarPrestador(@PathVariable Long id,
			@RequestBody PrestadorRepresentation prestador) throws Exception {
		Prestador domain = converter.toDomain(prestador);
		Prestador prestadorAtualizado = service.atualizarConta(id, domain);
		PrestadorRepresentation representation = converter.toRepresentation(prestadorAtualizado);
		return ResponseEntity.status(HttpStatus.OK).body(representation);
	}

	@PostMapping("/login")
	public ResponseEntity<PrestadorRepresentation> buscaPorLogin(@RequestBody Login login) throws Exception {
		Prestador prestador = service.realizarLogin(login);
		return ResponseEntity.status(HttpStatus.OK).body(converter.toRepresentation(prestador));
	}

	@PostMapping
	public ResponseEntity<PrestadorRepresentation> criaPrestador(@RequestBody PrestadorRepresentation prestador)
			throws Exception {
		Prestador domain = converter.toDomain(prestador);
		Prestador salvaConta = service.adicionarConta(domain);

		return ResponseEntity.status(HttpStatus.CREATED).body(converter.toRepresentation(salvaConta));
	}

//    @PostMapping
//    public ResponseEntity<AutonomoRepresentation> criarAutonomo(@RequestBody AutonomoRepresentation conta) throws Exception {
//        Autonomo domain = converter.toDomain(conta);
//        Autonomo contaSalva = service.salvaConta(domain);
//        AutonomoRepresentation representation = converter.toRepresentation(contaSalva);
//        return ResponseEntity.status(HttpStatus.CREATED).body(representation);
//    }

//    @GetMapping("{id}")
//    public ResponseEntity<List<PrestadorRepresentation>> buscarPrestadoresParaComparacao(@PathVariable Long id) throws Exception {
//        ResponseEntity<List<PrestadorRepresentation>> response = ResponseEntity.status(HttpStatus.NO_CONTENT).build();
//
//        List<Prestador> prestadores = service.obterContaPorId(id);
//
//        response = ResponseEntity.status(HttpStatus.OK).body(converter.toRepresentationList(prestadores));
//        return response;
//    }

	@DeleteMapping("{id}")
	public ResponseEntity<MensagemRepresentation> removerServicoDetalhado(@PathVariable Long id) throws Exception {
		ResponseEntity<MensagemRepresentation> response = ResponseEntity.status(HttpStatus.NO_CONTENT).build();

		MensagemRepresentation mensagem = service.removerConta(id);

		if (mensagem.getSucesso()) {
			response = ResponseEntity.status(HttpStatus.OK).body(mensagem);
		}

		return response;
	}

	@GetMapping("{idPrestador}/servicoDetalhado/{idServico}")
	public ResponseEntity<ServicoDetalhadoRepresentation> buscarServicoDetalhado(@PathVariable Long idPrestador,
			@PathVariable Long idServico) throws Exception {
		var servico = servicoDetalhadoService.buscarPorId(idServico);
		return ResponseEntity.status(HttpStatus.OK).body(servicoDetalhadoConverter.toRepresentation(servico));
	}

	@PostMapping("{idPrestador}/servicoDetalhado/{idServico}/avaliacoes")
	public ResponseEntity<ServicoDetalhadoRepresentation> adicionarAvaliacao(@PathVariable Long idPrestador,
			@PathVariable Long idServico, @RequestBody AvaliacaoRepresentation avaliacao) throws Exception {
		var idCliente = Optional.ofNullable(avaliacao.getCliente()).orElse(new ClienteRepresentation()).getId();
		avaliacaoFacade.adicionarAvaliacao(avaliacao, idCliente, idServico);

		var servico = servicoDetalhadoService.buscarPorId(idServico);
		return ResponseEntity.status(HttpStatus.CREATED).body(servicoDetalhadoConverter.toRepresentation(servico));
	}

}
