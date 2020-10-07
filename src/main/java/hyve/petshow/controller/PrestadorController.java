package hyve.petshow.controller;

import java.util.Optional;

import hyve.petshow.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import hyve.petshow.controller.converter.PrestadorConverter;
import hyve.petshow.controller.converter.ServicoDetalhadoConverter;
import hyve.petshow.controller.representation.AvaliacaoRepresentation;
import hyve.petshow.controller.representation.ClienteRepresentation;
import hyve.petshow.controller.representation.MensagemRepresentation;
import hyve.petshow.controller.representation.PrestadorRepresentation;
import hyve.petshow.controller.representation.ServicoDetalhadoRepresentation;
import hyve.petshow.domain.Login;
import hyve.petshow.domain.Prestador;
import hyve.petshow.facade.AvaliacaoFacade;
import hyve.petshow.service.port.PrestadorService;
import hyve.petshow.service.port.ServicoDetalhadoService;
import hyve.petshow.util.UrlUtils;

@RestController
@RequestMapping("/prestador")
public class PrestadorController {
	@Autowired
	private PrestadorService service;
	@Autowired
	private PrestadorConverter converter;
	@Autowired
	private JwtUtil jwtUtil;

	@GetMapping("/{id}")
	public ResponseEntity<PrestadorRepresentation> buscarPrestadorPorId(
			@PathVariable Long id) throws Exception {
		var prestador = service.buscarPorId(id);
		var representation = converter.toRepresentation(prestador);

		return ResponseEntity.status(HttpStatus.OK).body(representation);
	}

	@PutMapping("/{id}")
	public ResponseEntity<PrestadorRepresentation> atualizarPrestador(
			@PathVariable Long id,
			@RequestBody PrestadorRepresentation request) throws Exception {
		var prestador = converter.toDomain(request);
		prestador = service.atualizarConta(id, prestador);
		var representation = converter.toRepresentation(prestador);

		return ResponseEntity.status(HttpStatus.OK).body(representation);
	}
}
