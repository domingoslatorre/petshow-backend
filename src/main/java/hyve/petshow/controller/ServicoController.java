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

import hyve.petshow.controller.converter.PrestadorConverter;
import hyve.petshow.controller.converter.ServicoConverter;
import hyve.petshow.controller.converter.ServicoDetalhadoConverter;
import hyve.petshow.controller.representation.ServicoRepresentation;
import hyve.petshow.controller.representation.MensagemRepresentation;
import hyve.petshow.controller.representation.PrestadorRepresentation;
import hyve.petshow.domain.Prestador;
import hyve.petshow.domain.Servico;
import hyve.petshow.domain.ServicoDetalhado;
import hyve.petshow.service.port.ServicoDetalhadoService;
import hyve.petshow.service.port.ServicoService;
import hyve.petshow.util.UrlUtils;

@RestController
@RequestMapping("/servicos")
@CrossOrigin(origins = {UrlUtils.URL_API_LOCAL, UrlUtils.URL_API_LOCAL_DOCKER, UrlUtils.URL_API_PROD})
public class ServicoController {
	@Autowired
	private ServicoService service;

	@Autowired
	private  ServicoConverter converter;


	@PutMapping("/{id}")
	public ResponseEntity<ServicoRepresentation> atualizarServico(@PathVariable Long id, @RequestBody ServicoRepresentation servico) throws Exception{
		Servico domain = converter.toDomain(servico);
		Servico atualizaServico = service.atualizarServico(id, domain);
		return ResponseEntity.status(HttpStatus.OK).body(converter.toRepresentation(atualizaServico));
	}
	
    @GetMapping
    public ResponseEntity<List<ServicoRepresentation>> buscarServicos(){
        ResponseEntity<List<ServicoRepresentation>> response = ResponseEntity.status(HttpStatus.NO_CONTENT).build();

        List<Servico> servicos = service.buscarServicos();

        if(!servicos.isEmpty()){
            response = ResponseEntity.status(HttpStatus.OK).body(converter.toRepresentationList(servicos));
        }

        return response;
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<MensagemRepresentation> removerServico(@PathVariable Long id) throws Exception{
        ResponseEntity<MensagemRepresentation> response = ResponseEntity.status(HttpStatus.NO_CONTENT).build();

        MensagemRepresentation mensagem = service.removerServico(id);
        return response;
    }
	
	
	
//	@PostMapping
//	public ResponseEntity <ServicoRepresentation> adicionarServico(@RequestBody ServicoRepresentation servico) throws Exception{
//		Servico domain = converter.toDomain(servico);
//		Servico servicoSalvo = service.adicionarServico(domain);
//		ServicoRepresentation representation = converter.toRepresentation(servicoSalvo);
//		return ResponseEntity.status(HttpStatus.CREATED).body(representation);
//	}
//	
//
//	@PutMapping("{id}")
//	public ResponseEntity<ServicoRepresentation> atualizarServico(@PathVariable Long id, @RequestBody ServicoRepresentation servico) throws Exception{
//		Servico domain = converter.toDomain(servico);
//		Servico servicoAtualizado = service.atualizarServico(id, domain);
//		
//		return ResponseEntity.status(HttpStatus.OK).body(converter.toRepresentation(servicoAtualizado));
//	}
//	
//    @GetMapping
//    public ResponseEntity<List<ServicoRepresentation>> buscarServicos(){
//        ResponseEntity<List<ServicoRepresentation>> response = ResponseEntity.status(HttpStatus.NO_CONTENT).build();
//
//        List<Servico> servicos = service.buscarServicos();
//
//        if(!servicos.isEmpty()){
//            response = ResponseEntity.status(HttpStatus.OK).body(converter.toRepresentationList(servicos));
//        }
//
//        return response;
//    }
//    
//    @DeleteMapping("{id}")
//    public ResponseEntity<MensagemRepresentation> removerServico(@PathVariable Long id) throws Exception{
//        ResponseEntity<MensagemRepresentation> response = ResponseEntity.status(HttpStatus.NO_CONTENT).build();
//
//        MensagemRepresentation mensagem = service.removerServico(id);
//
//        if(mensagem.getSucesso()){
//            response = ResponseEntity.status(HttpStatus.OK).body(mensagem);
//         }
//
//        return response;
//    }
}
