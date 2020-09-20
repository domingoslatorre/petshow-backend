package hyve.petshow.controller;

import java.util.List;

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

import hyve.petshow.controller.converter.ServicoDetalhadoConverter;
import hyve.petshow.controller.representation.AnimalEstimacaoRepresentation;
import hyve.petshow.controller.representation.AnimalEstimacaoResponseRepresentation;
import hyve.petshow.controller.representation.MensagemRepresentation;
import hyve.petshow.controller.representation.ServicoDetalhadoRepresentation;
import hyve.petshow.domain.ServicoDetalhado;
import hyve.petshow.domain.AnimalEstimacao;
import hyve.petshow.domain.Servico;
import hyve.petshow.service.port.ServicoDetalhadoService;

@RestController
@RequestMapping("/servicos")
@CrossOrigin(origins = {"http://localhost:4200", "https://petshow-frontend.herokuapp.com", "http:0.0.0.0:4200"})
public class ServicoController {
	@Autowired
	private ServicoDetalhadoService service;

	@Autowired
	private ServicoDetalhadoConverter converter;

	@PostMapping
	public ResponseEntity<ServicoDetalhadoRepresentation> adicionaServicos(@RequestBody List<ServicoDetalhadoRepresentation> servicos) throws Exception {
		List<ServicoDetalhado> domainList = converter.toDomainList(servicos);
		List<ServicoDetalhado> servicosSalvos = service.criarServico(domainList);
		List<ServicoDetalhadoRepresentation> representation = converter.toRepresentationList(servicosSalvos);
		return ResponseEntity.status(HttpStatus.CREATED).body(representation);
	}


	@PutMapping
	public ResponseEntity<ServicoDetalhadoRepresentation> atualizaServicoDetalhado(@RequestBody ServicoDetalhadoRepresentation servico) {
		ServicoDetalhado domain = converter.toDomain(servico);
		ServicoDetalhado atualizaServico = service.atualizarServicoDetalhado(domain);
		ServicoDetalhadoRepresentation representation = converter.toRepresentation(atualizaServico);
		return ResponseEntity.status(HttpStatus.OK).body(representation);
	}
	
    @GetMapping
    public ResponseEntity<List<ServicoDetalhadoRepresentation>> obterAnimaisEstimacao(Long Id, Prestador prestador){
        ResponseEntity<List<ServicoDetalhadoRepresentation>> response = new ResponseEntity(HttpStatus.NO_CONTENT);

        List<ServicoDetalhado> servicos = service.buscaServicosPorPrestador(id, prestador);

        if(servicos.isEmpty() == false){
            response = new ResponseEntity<List<ServicoDetalhadoRepresentation>>(
                    converter.toRepresentationList(servicos), HttpStatus.OK);
        }

        return response;
    }
    
    @DeleteMapping("{id}")
    public ResponseEntity<MensagemRepresentation> removerAnimalEstimacao(
            @PathVariable Long id){
        ResponseEntity<MensagemRepresentation> response = new ResponseEntity(HttpStatus.NO_CONTENT);

        MensagemRepresentation mensagem = service.removerServicoDetalhado(id);

        if(mensagem.getSucesso()){
            response = new ResponseEntity<MensagemRepresentation>(
            		MensagemRepresentation, HttpStatus.OK);
        }

        return response;
    }
}
