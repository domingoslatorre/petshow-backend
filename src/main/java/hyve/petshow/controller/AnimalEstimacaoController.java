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
@RequestMapping("/cliente/animal-estimacao")
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

    @PostMapping
    public ResponseEntity<AnimalEstimacaoRepresentation> adicionarAnimalEstimacao(
            @RequestHeader(name = "Authorization") String token,
            @RequestBody AnimalEstimacaoRepresentation request){
        var animalEstimacao = animalEstimacaoConverter.toDomain(request);
        var donoId = jwtUtil.extractId(token);

        animalEstimacao.setDonoId(donoId);
        animalEstimacao = animalEstimacaoService.adicionarAnimalEstimacao(animalEstimacao);

        var representation = animalEstimacaoConverter.toRepresentation(animalEstimacao);

        return ResponseEntity.status(HttpStatus.CREATED).body(representation);
    }

    @GetMapping
    public ResponseEntity<List<AnimalEstimacaoRepresentation>> buscarAnimaisEstimacao(
            @RequestHeader(name = "Authorization") String token) throws NotFoundException {
        var donoId = jwtUtil.extractId(token);
        var animaisEstimacao = animalEstimacaoService.buscarAnimaisEstimacao(donoId);

        var representation = animalEstimacaoConverter.toRepresentationList(animaisEstimacao);

        return ResponseEntity.status(HttpStatus.OK).body(representation);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AnimalEstimacaoRepresentation> atualizarAnimalEstimacao(
            @PathVariable Long id,
            @RequestBody AnimalEstimacaoRepresentation request,
            @RequestHeader(name = "Authorization") String token) throws NotFoundException, BusinessException {
        var animalEstimacao = animalEstimacaoConverter.toDomain(request);
        var donoId = jwtUtil.extractId(token);

        animalEstimacao = animalEstimacaoService
                .atualizarAnimalEstimacao(id, animalEstimacao, donoId);

        var representation = animalEstimacaoConverter.toRepresentation(animalEstimacao);

        return ResponseEntity.status(HttpStatus.OK).body(representation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MensagemRepresentation> removerAnimalEstimacao(
            @PathVariable Long id,
            @RequestHeader(name = "Authorization") String token) throws BusinessException, NotFoundException {
        var donoId = jwtUtil.extractId(token);
        var response = animalEstimacaoService.removerAnimalEstimacao(id, donoId);

        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/tipos")
    public ResponseEntity<List<TipoAnimalEstimacaoRepresentation>> buscarTiposAnimalEstimacao(){
        var tiposAnimalEstimacao = tipoAnimalEstimacaoService.buscarTiposAnimalEstimacao();

        var representation = tipoAnimalEstimacaoConverter.toRepresentationList(tiposAnimalEstimacao);

        return ResponseEntity.status(HttpStatus.OK).body(representation);
    }
}
