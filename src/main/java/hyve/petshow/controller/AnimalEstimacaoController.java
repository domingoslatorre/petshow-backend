package hyve.petshow.controller;

import hyve.petshow.controller.representation.AnimalEstimacaoRepresentation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/animalEstimacao")
public class AnimalEstimacaoController {
    @PostMapping
    public ResponseEntity<AnimalEstimacaoRepresentation> criarAnimal(
            @RequestBody AnimalEstimacaoRepresentation animalEstimacaoRepresentation){
        return new ResponseEntity(new AnimalEstimacaoRepresentation(), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<AnimalEstimacaoRepresentation>> obterAnimais(){
        return new ResponseEntity(Arrays.asList(new AnimalEstimacaoRepresentation()), HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<AnimalEstimacaoRepresentation> obterAnimal(
            @PathVariable Long id){
        return new ResponseEntity(new AnimalEstimacaoRepresentation(), HttpStatus.OK);
    }

    @PutMapping("{id}")
    public ResponseEntity<AnimalEstimacaoRepresentation> atualizarAnimal(
            @PathVariable Long id,
            @RequestBody AnimalEstimacaoRepresentation animalEstimacaoRepresentation){
        return new ResponseEntity(new AnimalEstimacaoRepresentation(), HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Boolean> removerAnimal(
            @PathVariable Long id){
        return new ResponseEntity(Boolean.TRUE, HttpStatus.OK);
    }
}