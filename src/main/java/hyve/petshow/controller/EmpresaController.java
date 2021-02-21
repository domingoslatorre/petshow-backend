package hyve.petshow.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import hyve.petshow.controller.converter.EmpresaConverter;
import hyve.petshow.controller.representation.EmpresaRepresentation;
import hyve.petshow.service.port.EmpresaService;

@RestController
@RequestMapping("/empresa")
public class EmpresaController {
	@Autowired
	private EmpresaService service;
	@Autowired
	private EmpresaConverter converter;
	
	@PutMapping("/{id}")
	public ResponseEntity<EmpresaRepresentation> atualizaEmpresa(@PathVariable Long id,
			@RequestBody EmpresaRepresentation representation) throws Exception {
		var domain = converter.toDomain(representation);
		var empresa = service.atualizaEmpresa(id, domain);
		var response = converter.toRepresentation(empresa);
		return ResponseEntity.ok(response);
	}
	
	@PatchMapping("/{id}")
	public ResponseEntity<EmpresaRepresentation> atualizarEmpresa(@PathVariable Long id, 
			@RequestBody EmpresaRepresentation representation,
			@RequestParam Boolean ativo) throws Exception {
		var domain = converter.toDomain(representation);
		var empresa = service.desativaEmpresa(domain.getId(), ativo);
		var response = converter.toRepresentation(empresa);
		return ResponseEntity.ok(response);
	}

}
