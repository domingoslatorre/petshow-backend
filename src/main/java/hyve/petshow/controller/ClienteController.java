package hyve.petshow.controller;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hyve.petshow.controller.converter.ClienteConverter;
import hyve.petshow.controller.representation.ClienteRepresentation;
import hyve.petshow.domain.Cliente;
import hyve.petshow.domain.Login;
import hyve.petshow.service.port.ClienteService;

@RestController
@RequestMapping("/cliente")
@CrossOrigin(origins = {"http://localhost:4200", "https://petshow-frontend.herokuapp.com", "http:0.0.0.0:4200"})
public class ClienteController {
	@Autowired
	private ClienteService service;

	@Autowired
	private ClienteConverter converter;

	@PostMapping
	public ResponseEntity<ClienteRepresentation> criaConta(@RequestBody ClienteRepresentation conta) throws Exception {
		Cliente domain = converter.toDomain(conta);
		Cliente contaSalva = service.salvaConta(domain);
		ClienteRepresentation representation = converter.toRepresentation(contaSalva);
		return ResponseEntity.status(HttpStatus.CREATED).body(representation);
	}

	@PostMapping("/login")
	public ResponseEntity<ClienteRepresentation> buscaPorLogin(@RequestBody Login login) throws Exception {
		Cliente conta = service.obterPorLogin(login);
		ClienteRepresentation representation = converter.toRepresentation(conta);
		return ResponseEntity.status(HttpStatus.OK).body(representation);
	}

	@PutMapping
	public ResponseEntity<ClienteRepresentation> atualizaCliente(@RequestBody ClienteRepresentation cliente) {
		Cliente domain = converter.toDomain(cliente);
		Cliente atualizaCliente = service.atualizaConta(domain);

		ClienteRepresentation representation = converter.toRepresentation(atualizaCliente);
		return ResponseEntity.status(HttpStatus.OK).body(representation);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<ClienteRepresentation> buscarClientePorId(@PathVariable Long id) throws Exception {
		var cliente = service.obterContaPorId(id);
		var representation = converter.toRepresentation(cliente);
		return ResponseEntity.status(HttpStatus.OK).body(representation);
	}

}
