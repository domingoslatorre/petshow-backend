package hyve.petshow.controller;

import hyve.petshow.controller.converter.AnimalEstimacaoConverter;
import hyve.petshow.controller.converter.TipoAnimalEstimacaoConverter;
import hyve.petshow.controller.representation.AnimalEstimacaoRepresentation;
import hyve.petshow.controller.representation.MensagemRepresentation;
import hyve.petshow.controller.representation.TipoAnimalEstimacaoRepresentation;
import hyve.petshow.exceptions.NotFoundException;
import hyve.petshow.service.port.AnimalEstimacaoService;
import hyve.petshow.service.port.TipoAnimalEstimacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cliente/animal-estimacao")
@CrossOrigin(origins = {"http://localhost:4200", "https://petshow-frontend.herokuapp.com", "http:0.0.0.0:4200"})
public class AnimalEstimacaoController {
    @Autowired
    private AnimalEstimacaoService animalEstimacaoService;

    @Autowired
    private TipoAnimalEstimacaoService tipoAnimalEstimacaoService;

    @Autowired
    private AnimalEstimacaoConverter animalEstimacaoConverter;

    @Autowired
    private TipoAnimalEstimacaoConverter tipoAnimalEstimacaoConverter;

    @PostMapping
    public ResponseEntity<AnimalEstimacaoRepresentation> adicionarAnimalEstimacao(
            @RequestBody AnimalEstimacaoRepresentation request){
        var animalEstimacao = animalEstimacaoService.adicionarAnimalEstimacao(
                animalEstimacaoConverter.toDomain(request));
        var representation = animalEstimacaoConverter.toRepresentation(animalEstimacao);

        return ResponseEntity.status(HttpStatus.CREATED).body(representation);
    }

    @GetMapping
    public ResponseEntity<List<AnimalEstimacaoRepresentation>> buscarAnimaisEstimacao() throws NotFoundException {
        var animaisEstimacao = animalEstimacaoService.buscarAnimaisEstimacao();

        var representation = animalEstimacaoConverter.toRepresentationList(animaisEstimacao);

        return ResponseEntity.status(HttpStatus.OK).body(representation);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AnimalEstimacaoRepresentation> atualizarAnimalEstimacao(
            @PathVariable Long id,
            @RequestBody AnimalEstimacaoRepresentation request) throws NotFoundException {
        var animalEstimacao = animalEstimacaoService
                .atualizarAnimalEstimacao(id, animalEstimacaoConverter.toDomain(request));

        var representation = animalEstimacaoConverter.toRepresentation(animalEstimacao);

        return ResponseEntity.status(HttpStatus.OK).body(representation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MensagemRepresentation> removerAnimalEstimacao(
            @PathVariable Long id){
        var response = animalEstimacaoService.removerAnimalEstimacao(id);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/tipos")
    public ResponseEntity<List<TipoAnimalEstimacaoRepresentation>> buscarTiposAnimalEstimacao(){
        var tiposAnimalEstimacao = tipoAnimalEstimacaoService.buscarTiposAnimalEstimacao();

        var representation = tipoAnimalEstimacaoConverter.toRepresentationList(tiposAnimalEstimacao);

        return ResponseEntity.status(HttpStatus.OK).body(representation);
    }
}
