package hyve.petshow.controller;

import hyve.petshow.controller.converter.PrestadorConverter;
import hyve.petshow.controller.representation.AnimalEstimacaoRepresentation;
import hyve.petshow.controller.representation.ClienteRepresentation;
import hyve.petshow.controller.representation.PrestadorRepresentation;
import hyve.petshow.domain.Cliente;
import hyve.petshow.domain.Prestador;
import hyve.petshow.service.port.PrestadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController //controle de REST
@RequestMapping("/Prestador")
@CrossOrigin(origins = {"http://localhost:4200", "https://petshow-frontend.herokuapp.com", "http:0.0.0.0:4200"}) //quem pode usar esses serviços nesse controller
public class PrestadorController {
    @Autowired // instancia automaticamente
    private PrestadorService service; //

    @Autowired
    private PrestadorConverter converter; //converte para uma entidade de dominio para não utilizar o mesmo objeto

    @PostMapping
    public ResponseEntity<PrestadorRepresentation> buscaPrestador(@RequestBody PrestadorRepresentation prestador) throws Exception {

        return ResponseEntity.status(HttpStatus.OK).body(representation);
    }

    @PutMapping("{id}")
    public ResponseEntity<PrestadorRepresentation> editarPrestador(@RequestBody PrestadorRepresentation prestador) throws Exception {

        return ResponseEntity.status(HttpStatus.OK).body(representation);
    }

    @PostMapping
    public ResponseEntity<PrestadorRepresentation> criaAutonomo(@RequestBody PrestadorRepresentation prestador) throws Exception {
        Prestador domain = converter.toDomain(conta);
        Prestador contaSalva = service.salvaConta(domain);
        PrestadorRepresentation representation = converter.toRepresentation(contaSalva);
        return ResponseEntity.status(HttpStatus.OK).body(representation);
    }




    @PostMapping
    @Putmapping
    @PutMapping("{id}")
    @Getmaping
    @Getmapping("{id}")
    @DeleteMapping
    @DeleteMapping("{id}")


    public ResponseEntity<PrestadorRepresentation> CriaAutonomo


}
