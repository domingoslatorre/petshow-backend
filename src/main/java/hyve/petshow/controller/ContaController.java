package hyve.petshow.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hyve.petshow.controller.converter.ContaConverter;
import hyve.petshow.controller.representation.ContaRepresentation;
import hyve.petshow.service.port.ContaGenericaService;
import hyve.petshow.util.UrlUtils;

@RestController
@RequestMapping("/conta")
public class ContaController {
	@Autowired
	private ContaGenericaService service;
	@Autowired
	private ContaConverter converter;
	
	@GetMapping("{id}")
	public ResponseEntity<ContaRepresentation> buscarPorId(@PathVariable Long id) throws Exception {
		var conta = service.buscarPorId(id);
		return ResponseEntity.status(HttpStatus.OK).body(converter.toRepresentation(conta));
	}
}
