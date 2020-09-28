package hyve.petshow.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import hyve.petshow.controller.converter.ServicoDetalhadoConverter;
import hyve.petshow.controller.representation.MensagemRepresentation;
import hyve.petshow.controller.representation.ServicoDetalhadoRepresentation;
import hyve.petshow.domain.ServicoDetalhado;
import hyve.petshow.service.port.ServicoDetalhadoService;

@RestController
@RequestMapping("/prestador/servicos")
@CrossOrigin(origins = {"http://localhost:4200", "https://petshow-frontend.herokuapp.com", "http:0.0.0.0:4200"})
public class ServicoDetalhadoController {
	@Autowired
	private ServicoDetalhadoService service;

	@Autowired
	private ServicoDetalhadoConverter converter;

	@PostMapping
	public ResponseEntity <ServicoDetalhadoRepresentation> adicionarServicoDetalhado(@RequestBody ServicoDetalhadoRepresentation servico) {
		ServicoDetalhado domain = converter.toDomain(servico);
		ServicoDetalhado servicoSalvo = service.adicionarServicoDetalhado(domain);
		ServicoDetalhadoRepresentation representation = converter.toRepresentation(servicoSalvo);
		return ResponseEntity.status(HttpStatus.CREATED).body(representation);
	}


	@PutMapping("{id}")
	public ResponseEntity<ServicoDetalhadoRepresentation> atualizarServicoDetalhado(@PathVariable Long id, @RequestBody ServicoDetalhadoRepresentation servico) throws Exception{
		ServicoDetalhado domain = converter.toDomain(servico);
		ServicoDetalhado atualizaServico = service.atualizarServicoDetalhado(id, domain);
	
		return ResponseEntity.status(HttpStatus.OK).body(converter.toRepresentation(atualizaServico));
	}
	
//    @GetMapping("/prestador/{id}")
//    public ResponseEntity<List<ServicoDetalhadoRepresentation>> buscarServicosPorPrestador(@PathVariable Long id){
//        ResponseEntity<List<ServicoDetalhadoRepresentation>> response = ResponseEntity.status(HttpStatus.NO_CONTENT).build();
//
//        List<ServicoDetalhado> servicos = service.buscarServicosDetalhadosPorPrestador(id);
//
//        if(!servicos.isEmpty()){
//            response = ResponseEntity.status(HttpStatus.OK).body(converter.toRepresentationList(servicos));
//        }
//
//        return response;
//    }
    
    @DeleteMapping("{id}")
    public ResponseEntity<MensagemRepresentation> removerServicoDetalhado(@PathVariable Long id) throws Exception{
        ResponseEntity<MensagemRepresentation> response = ResponseEntity.status(HttpStatus.NO_CONTENT).build();

        MensagemRepresentation mensagem = service.removerServicoDetalhado(id);

        if(mensagem.getSucesso()){
            response = ResponseEntity.status(HttpStatus.OK).body(mensagem);
        }

        return response;
    }
}
