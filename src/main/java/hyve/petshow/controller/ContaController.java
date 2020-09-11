package hyve.petshow.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hyve.petshow.controller.converter.ClienteConverter;
import hyve.petshow.controller.converter.ContaConverter;
import hyve.petshow.controller.representation.ClienteRepresentation;
import hyve.petshow.controller.representation.ContaRepresentation;
import hyve.petshow.domain.Cliente;
import hyve.petshow.domain.Conta;
import hyve.petshow.domain.Login;
import hyve.petshow.service.port.ContaService;

@RestController
@RequestMapping("/conta")
public class ContaController {
	@Autowired
	private ContaService service;

	@Autowired
	private ContaConverter converter;
	
	@Autowired
	private ClienteConverter clienteConverter;
	
	
	@PostMapping
	public ResponseEntity<ContaRepresentation> criaConta(@RequestBody ContaRepresentation conta) throws Exception {
		Conta domain = converter.toDomain(conta);
		Conta contaSalva = service.salvaConta(domain);
		ContaRepresentation representation = converter.toRepresentation(contaSalva);
		return ResponseEntity.status(HttpStatus.CREATED).body(representation);
	}

	@PostMapping("/login")
	public ResponseEntity<ContaRepresentation> buscaPorLogin(@RequestBody Login login) throws Exception {
		Conta conta = service.obterPorLogin(login);
		ContaRepresentation representation = converter.toRepresentation(conta);
		return ResponseEntity.status(HttpStatus.OK).body(representation);
	}
	
	@PutMapping("/cliente")
	public ResponseEntity<ClienteRepresentation> atualizaCliente(@RequestBody ClienteRepresentation cliente) {
		Cliente domain = clienteConverter.toDomain(cliente);
		Cliente atualizaCliente = service.atualizaCliente(domain);
		ClienteRepresentation representation = clienteConverter.toRepresentation(atualizaCliente);
		return ResponseEntity.status(HttpStatus.OK).body(representation);
	}

}
