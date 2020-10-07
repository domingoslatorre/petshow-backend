package hyve.petshow.controller;

import hyve.petshow.controller.converter.ClienteConverter;
import hyve.petshow.controller.representation.ClienteRepresentation;
import hyve.petshow.domain.Login;
import hyve.petshow.service.port.ClienteService;
import hyve.petshow.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cliente")
public class ClienteController {
	@Autowired
	private ClienteService clienteService;
	@Autowired
	private ClienteConverter clienteConverter;
	@Autowired
	private JwtUtil jwtUtil;

	@GetMapping
	public ResponseEntity<ClienteRepresentation> buscarCliente(
			@RequestHeader(name = "Authorization") String token) throws Exception {
		var id = jwtUtil.extractId(token);
		var cliente = clienteService.buscarPorId(id);
		var representation = clienteConverter.toRepresentation(cliente);

		return ResponseEntity.status(HttpStatus.OK).body(representation);
	}

	@PutMapping
	public ResponseEntity<ClienteRepresentation> atualizarCliente(
			@RequestHeader(name = "Authorization") String token,
			@RequestBody ClienteRepresentation request) throws Exception {
		var id = jwtUtil.extractId(token);
		var cliente = clienteService.atualizarConta(id, clienteConverter.toDomain(request));
		var representation = clienteConverter.toRepresentation(cliente);

		return ResponseEntity.status(HttpStatus.OK).body(representation);
	}
}
