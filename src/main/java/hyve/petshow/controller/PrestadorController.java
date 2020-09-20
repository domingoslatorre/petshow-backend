package hyve.petshow.controller;

import hyve.petshow.controller.converter.PrestadorConverter;
import hyve.petshow.controller.representation.*;
import hyve.petshow.domain.Autonomo;
import hyve.petshow.domain.Cliente;
import hyve.petshow.domain.Prestador;
import hyve.petshow.service.port.PrestadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController //controle de REST
@RequestMapping("/prestador")
@CrossOrigin(origins = {"http://localhost:4200", "https://petshow-frontend.herokuapp.com", "http:0.0.0.0:4200"}) //quem pode usar esses serviços nesse controller
public class PrestadorController {
    @Autowired // instancia automaticamente
    private PrestadorService service; //

    @Autowired
    private PrestadorConverter converter; //converte para uma entidade de dominio para não utilizar o mesmo objeto

    @GetMapping("{id}")
    public ResponseEntity<PrestadorRepresentation> buscarPrestador(@PathVariable Long id){
        ResponseEntity<PrestadorRepresentation> response = ResponseEntity.status(HttpStatus.NO_CONTENT).build();

        Prestador prestador = service.buscaPorEmail(id);

        if(!prestador.isEmpty()){
            response = ResponseEntity.status(HttpStatus.OK).body(converter.toRepresentationList(prestador));
        }

        return response;
    }


    @PutMapping("{id}")
    public ResponseEntity<PrestadorRepresentation> atualizarPrestador(@PathVariable Long id, @RequestBody PrestadorRepresentation prestador) {
        Prestador domain = converter.toDomain(prestador);
        Optional <Prestador> prestadorAtualizado = service.atualizaConta(id, domain);

        if(!prestadorAtualizado.isEmpty()) {
            PrestadorRepresentation representation = converter.toRepresentation(prestadorAtualizado.get());
            return ResponseEntity.status(HttpStatus.OK).body(representation);
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    @PostMapping
    public ResponseEntity<AutonomoRepresentation> criarAutonomo(@RequestBody AutonomoRepresentation conta) throws Exception {
        Autonomo domain = converter.toDomain(conta);
        Autonomo contaSalva = service.salvaConta(domain);
        AutonomoRepresentation representation = converter.toRepresentation(contaSalva);
        return ResponseEntity.status(HttpStatus.CREATED).body(representation);
    }


    @GetMapping("{id}")
    public ResponseEntity<List<PrestadorRepresentation>> buscarPrestadoresParaComparacao(@PathVariable Long id){
        ResponseEntity<List<PrestadorRepresentation>> response = ResponseEntity.status(HttpStatus.NO_CONTENT).build();

        List<Prestador> prestadores = service.buscaPorEmail(id);

        if(!prestadores.isEmpty()){
            response = ResponseEntity.status(HttpStatus.OK).body(converter.toRepresentationList(prestadores));
        }

        return response;
    }

    @DeleteMapping("{id}")
    public ResponseEntity<MensagemRepresentation> removerServicoDetalhado(@PathVariable Long id){
        ResponseEntity<MensagemRepresentation> response = ResponseEntity.status(HttpStatus.NO_CONTENT).build();

        MensagemRepresentation mensagem = service.removerConta(id);

        if(mensagem.getSucesso()){
            response = ResponseEntity.status(HttpStatus.OK).body(mensagem);
        }

        return response;
    }

}
