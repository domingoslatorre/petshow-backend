package hyve.petshow.controller;

import hyve.petshow.controller.converter.AnimalEstimacaoConverter;
import hyve.petshow.controller.converter.TipoAnimalEstimacaoConverter;
import hyve.petshow.controller.representation.AnimalEstimacaoRepresentation;
import hyve.petshow.controller.representation.MensagemRepresentation;
import hyve.petshow.controller.representation.TipoAnimalEstimacaoRepresentation;
import hyve.petshow.exceptions.BusinessException;
import hyve.petshow.exceptions.NotFoundException;
import hyve.petshow.service.port.AnimalEstimacaoService;
import hyve.petshow.service.port.TipoAnimalEstimacaoService;
import hyve.petshow.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AnimalEstimacaoController {
    @Autowired
    private AnimalEstimacaoService animalEstimacaoService;
    @Autowired
    private TipoAnimalEstimacaoService tipoAnimalEstimacaoService;
    @Autowired
    private AnimalEstimacaoConverter animalEstimacaoConverter;
    @Autowired
    private TipoAnimalEstimacaoConverter tipoAnimalEstimacaoConverter;
    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/cliente/animal-estimacao")
    public ResponseEntity<AnimalEstimacaoRepresentation> adicionarAnimalEstimacao(
            @RequestBody AnimalEstimacaoRepresentation request){
        var animalEstimacao = animalEstimacaoConverter.toDomain(request);

        animalEstimacao = animalEstimacaoService.adicionarAnimalEstimacao(animalEstimacao);

        var representation = animalEstimacaoConverter.toRepresentation(animalEstimacao);

        return ResponseEntity.status(HttpStatus.CREATED).body(representation);
    }

    @GetMapping("/cliente/{donoId}/animal-estimacao")
    public ResponseEntity<List<AnimalEstimacaoRepresentation>> buscarAnimaisEstimacao(
            @PathVariable Long donoId) throws NotFoundException {
        var animaisEstimacao = animalEstimacaoService.buscarAnimaisEstimacaoPorDono(donoId);

        var representation = animalEstimacaoConverter.toRepresentationList(animaisEstimacao);

        return ResponseEntity.status(HttpStatus.OK).body(representation);
    }

    @PutMapping("/cliente/animal-estimacao/{id}")
    public ResponseEntity<AnimalEstimacaoRepresentation> atualizarAnimalEstimacao(
            @PathVariable Long id,
            @RequestBody AnimalEstimacaoRepresentation request) throws NotFoundException, BusinessException {
        var animalEstimacao = animalEstimacaoConverter.toDomain(request);

        animalEstimacao = animalEstimacaoService.atualizarAnimalEstimacao(id, animalEstimacao);

        var representation = animalEstimacaoConverter.toRepresentation(animalEstimacao);

        return ResponseEntity.status(HttpStatus.OK).body(representation);
    }

    @DeleteMapping("/cliente/{donoId}/animal-estimacao/{id}")
    public ResponseEntity<MensagemRepresentation> removerAnimalEstimacao(
            @PathVariable Long donoId,
            @PathVariable Long id) throws BusinessException, NotFoundException {
        var response = animalEstimacaoService.removerAnimalEstimacao(id, donoId);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/cliente/animal-estimacao/tipos")
    public ResponseEntity<List<TipoAnimalEstimacaoRepresentation>> buscarTiposAnimalEstimacao()
            throws NotFoundException {
        var tiposAnimalEstimacao = tipoAnimalEstimacaoService.buscarTiposAnimalEstimacao();

        var representation = tipoAnimalEstimacaoConverter.toRepresentationList(tiposAnimalEstimacao);

        return ResponseEntity.status(HttpStatus.OK).body(representation);
    }
}
