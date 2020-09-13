package hyve.petshow.service.port;

import hyve.petshow.controller.representation.AnimalEstimacaoResponseRepresentation;
import hyve.petshow.domain.AnimalEstimacao;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface AnimalEstimacaoService {
    AnimalEstimacao criarAnimalEstimacao(AnimalEstimacao animalEstimacao);

    Optional<AnimalEstimacao> obterAnimalEstimacaoPorId(Long id);

    List<AnimalEstimacao> obterAnimaisEstimacao();

    Optional<AnimalEstimacao> atualizarAnimalEstimacao(Long id, AnimalEstimacao animalEstimacao);

    AnimalEstimacaoResponseRepresentation removerAnimalEstimacao(Long id);
}
