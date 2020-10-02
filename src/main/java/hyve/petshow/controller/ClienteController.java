package hyve.petshow.controller;

import hyve.petshow.controller.converter.ClienteConverter;
import hyve.petshow.controller.representation.ClienteRepresentation;
import hyve.petshow.domain.Login;
import hyve.petshow.service.port.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cliente")
@CrossOrigin(origins = {"http://localhost:4200", "https://petshow-frontend.herokuapp.com", "http:0.0.0.0:4200"})
public class ClienteController {
	@Autowired
	private ClienteService clienteService;

	@Autowired
	private ClienteConverter clienteConverter;

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
}
