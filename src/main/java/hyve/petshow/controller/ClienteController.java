package hyve.petshow.controller;

import hyve.petshow.controller.converter.AnimalEstimacaoConverter;
import hyve.petshow.controller.representation.AnimalEstimacaoRepresentation;
import hyve.petshow.controller.representation.MensagemRepresentation;
import hyve.petshow.exceptions.NotFoundException;
import hyve.petshow.service.port.AnimalEstimacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import hyve.petshow.controller.converter.ClienteConverter;
import hyve.petshow.controller.representation.ClienteRepresentation;
import hyve.petshow.domain.Login;
import hyve.petshow.service.port.ClienteService;

import java.util.List;

@RestController
@RequestMapping("/cliente")
@CrossOrigin(origins = {"http://localhost:4200", "https://petshow-frontend.herokuapp.com", "http:0.0.0.0:4200"})
public class ClienteController {
	@Autowired
	private ClienteService clienteService;

	@Autowired
	private AnimalEstimacaoService animalEstimacaoService;

	@Autowired
	private ClienteConverter clienteConverter;

	@Autowired
	private AnimalEstimacaoConverter animalEstimacaoConverter;

	@GetMapping("/{id}")
	public ResponseEntity<ClienteRepresentation> buscarCliente(@PathVariable Long id) throws Exception {
		var cliente = clienteService.buscarPorId(id);
		var representation = clienteConverter.toRepresentation(cliente);

		return ResponseEntity.status(HttpStatus.OK).body(representation);
	}

	@PostMapping
	public ResponseEntity<ClienteRepresentation> adicionarCliente(@RequestBody ClienteRepresentation request) throws Exception {
		var cliente = clienteService.adicionarConta(clienteConverter.toDomain(request));
		var representation = clienteConverter.toRepresentation(cliente);

		return ResponseEntity.status(HttpStatus.CREATED).body(representation);
	}

	@PostMapping("/login")
	public ResponseEntity<ClienteRepresentation> realizarLogin(@RequestBody Login requuest) throws Exception {
		var conta = clienteService.realizarLogin(requuest);
		var representation = clienteConverter.toRepresentation(conta);

		return ResponseEntity.status(HttpStatus.OK).body(representation);
	}

	@PutMapping("/{id}")
	public ResponseEntity<ClienteRepresentation> atualizarCliente(
			@PathVariable Long id,
			@RequestBody ClienteRepresentation request) throws Exception {
		var cliente = clienteService.atualizarConta(id, clienteConverter.toDomain(request));
		var representation = clienteConverter.toRepresentation(cliente);

		return ResponseEntity.status(HttpStatus.OK).body(representation);
	}

	@PostMapping("/animal-estimacao")
	public ResponseEntity<AnimalEstimacaoRepresentation> adicionarAnimalEstimacao(
			@RequestBody AnimalEstimacaoRepresentation request){
		var animalEstimacao = animalEstimacaoService.adicionarAnimalEstimacao(
				animalEstimacaoConverter.toDomain(request));
		var representation = animalEstimacaoConverter.toRepresentation(animalEstimacao);

		return ResponseEntity.status(HttpStatus.CREATED).body(representation);
	}

	@GetMapping("/animal-estimacao")
	public ResponseEntity<List<AnimalEstimacaoRepresentation>> buscarAnimaisEstimacao() throws NotFoundException {
		var animaisEstimacao = animalEstimacaoService.buscarAnimaisEstimacao();

		var representation = animalEstimacaoConverter.toRepresentationList(animaisEstimacao);

		return ResponseEntity.status(HttpStatus.OK).body(representation);
	}

	@PutMapping("/animal-estimacao/{id}")
	public ResponseEntity<AnimalEstimacaoRepresentation> atualizarAnimalEstimacao(
			@PathVariable Long id,
			@RequestBody AnimalEstimacaoRepresentation request) throws NotFoundException {
		var animalEstimacao = animalEstimacaoService
				.atualizarAnimalEstimacao(id, animalEstimacaoConverter.toDomain(request));

		var representation = animalEstimacaoConverter.toRepresentation(animalEstimacao);

		return ResponseEntity.status(HttpStatus.OK).body(representation);
	}

	@DeleteMapping("/animal-estimacao/{id}")
	public ResponseEntity<MensagemRepresentation> removerAnimalEstimacao(
			@PathVariable Long id){
		var response = animalEstimacaoService.removerAnimalEstimacao(id);

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

}
