package hyve.petshow.controller;


import hyve.petshow.controller.converter.AutonomoConverter;
import hyve.petshow.controller.representation.AutonomoRepresentation;
import hyve.petshow.controller.representation.MensagemRepresentation;
import hyve.petshow.domain.Autonomo;
import hyve.petshow.service.port.AutonomoService;
import hyve.petshow.util.UrlUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/autonomo")
public class AutonomoController {
    @Autowired
    private AutonomoService service;

    @Autowired
    private AutonomoConverter converter; //converte para uma entidade de dominio para não utilizar o mesmo objeto

    @GetMapping("{id}")
    public ResponseEntity<AutonomoRepresentation> buscarAutonomo(@PathVariable Long id) throws Exception {
        ResponseEntity<AutonomoRepresentation> response = ResponseEntity.status(HttpStatus.NO_CONTENT).build();

        Autonomo autonomo = service.buscarPorId(id);

        return  ResponseEntity.status(HttpStatus.OK).body(converter.toRepresentation(autonomo));
    }


    @PutMapping("{id}")
    public ResponseEntity<AutonomoRepresentation> atualizarAutonomo(@PathVariable Long id, @RequestBody AutonomoRepresentation autonomo) throws Exception {
        Autonomo domain = converter.toDomain(autonomo);
        Autonomo autonomoAtualizado = service.atualizarConta(id, domain);
        AutonomoRepresentation representation = converter.toRepresentation(autonomoAtualizado);
        return ResponseEntity.status(HttpStatus.OK).body(representation);
    }


//    @PostMapping
//    public ResponseEntity<AutonomoRepresentation> criarAutonomo(@RequestBody AutonomoRepresentation conta) throws Exception {
//        Autonomo domain = converter.toDomain(conta);
//        Autonomo contaSalva = service.salvaConta(domain);
//        AutonomoRepresentation representation = converter.toRepresentation(contaSalva);
//        return ResponseEntity.status(HttpStatus.CREATED).body(representation);
//    }


//    @GetMapping("{id}")
//    public ResponseEntity<List<AutonomoRepresentation>> buscarAutonomosParaComparacao(@PathVariable Long id) throws Exception {
//        ResponseEntity<List<AutonomoRepresentation>> response = ResponseEntity.status(HttpStatus.NO_CONTENT).build();
//
//        List<Autonomo> autonomos = service.obterContaPorId(id);
//
//        response = ResponseEntity.status(HttpStatus.OK).body(converter.toRepresentationList(autonomos));
//        return response;
//    }

    @DeleteMapping("{id}")
    public ResponseEntity<MensagemRepresentation> removerServicoDetalhado(@PathVariable Long id) throws Exception {
        ResponseEntity<MensagemRepresentation> response = ResponseEntity.status(HttpStatus.NO_CONTENT).build();

        MensagemRepresentation mensagem = service.removerConta(id);

        if(mensagem.getSucesso()){
            response = ResponseEntity.status(HttpStatus.OK).body(mensagem);
        }

        return response;
    }

}
