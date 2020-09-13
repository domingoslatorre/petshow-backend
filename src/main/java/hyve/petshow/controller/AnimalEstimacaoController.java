package hyve.petshow.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hyve.petshow.controller.converter.AnimalEstimacaoConverter;
import hyve.petshow.controller.representation.AnimalEstimacaoRepresentation;
import hyve.petshow.controller.representation.AnimalEstimacaoResponseRepresentation;
import hyve.petshow.domain.AnimalEstimacao;
import hyve.petshow.service.port.AnimalEstimacaoService;

@RestController
@RequestMapping("/animalEstimacao")
public class AnimalEstimacaoController {
    @Autowired
    private AnimalEstimacaoService animalEstimacaoService;

    @Autowired
    private AnimalEstimacaoConverter animalEstimacaoConverter;

    @PostMapping
    public ResponseEntity<AnimalEstimacaoRepresentation> criarAnimalEstimacao(
            @RequestBody AnimalEstimacaoRepresentation animalEstimacaoRepresentation){
        AnimalEstimacaoRepresentation response =
                animalEstimacaoConverter
                        .toRepresentation(animalEstimacaoService
                                .criarAnimalEstimacao(animalEstimacaoConverter
                                        .toDomain(animalEstimacaoRepresentation)));

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("{id}")
    public ResponseEntity<AnimalEstimacaoRepresentation> obterAnimalEstimacao(
            @PathVariable Long id){
        ResponseEntity<AnimalEstimacaoRepresentation> response = new ResponseEntity(HttpStatus.NO_CONTENT);

        Optional<AnimalEstimacao> animalEstimacao = animalEstimacaoService.obterAnimalEstimacaoPorId(id);

        if(animalEstimacao.isPresent()){
            response = new ResponseEntity<AnimalEstimacaoRepresentation>(
                    animalEstimacaoConverter.toRepresentation(animalEstimacao.get()), HttpStatus.OK);
        }

        return response;
    }

    @GetMapping
    public ResponseEntity<List<AnimalEstimacaoRepresentation>> obterAnimaisEstimacao(){
        ResponseEntity<List<AnimalEstimacaoRepresentation>> response = new ResponseEntity(HttpStatus.NO_CONTENT);

        List<AnimalEstimacao> animaisEstimacao = animalEstimacaoService.obterAnimaisEstimacao();

        if(animaisEstimacao.isEmpty() == false){
            response = new ResponseEntity<List<AnimalEstimacaoRepresentation>>(
                    animalEstimacaoConverter.toRepresentationList(animaisEstimacao), HttpStatus.OK);
        }

        return response;
    }

    @PutMapping("{id}")
    public ResponseEntity<AnimalEstimacaoRepresentation> atualizarAnimalEstimacao(
            @PathVariable Long id,
            @RequestBody AnimalEstimacaoRepresentation animalEstimacaoRepresentation){
        ResponseEntity<AnimalEstimacaoRepresentation> response = new ResponseEntity(HttpStatus.NO_CONTENT);

        Optional<AnimalEstimacao> animalEstimacao = animalEstimacaoService
                .atualizarAnimalEstimacao(id, animalEstimacaoConverter.toDomain(animalEstimacaoRepresentation));

        if(animalEstimacao.isPresent()){
            response = new ResponseEntity<AnimalEstimacaoRepresentation>(
                    animalEstimacaoConverter.toRepresentation(animalEstimacao.get()), HttpStatus.OK);
        }

        return response;
    }

    @DeleteMapping("{id}")
    public ResponseEntity<AnimalEstimacaoResponseRepresentation> removerAnimalEstimacao(
            @PathVariable Long id){
        ResponseEntity<AnimalEstimacaoResponseRepresentation> response = new ResponseEntity(HttpStatus.NO_CONTENT);

        AnimalEstimacaoResponseRepresentation animalEstimacaoResponseRepresentation =
                animalEstimacaoService.removerAnimalEstimacao(id);

        if(animalEstimacaoResponseRepresentation.getSucesso()){
            response = new ResponseEntity<AnimalEstimacaoResponseRepresentation>(
                    animalEstimacaoResponseRepresentation, HttpStatus.OK);
        }

        return response;
    }
}