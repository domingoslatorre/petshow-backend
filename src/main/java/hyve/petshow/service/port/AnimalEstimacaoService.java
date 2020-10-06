package hyve.petshow.service.port;

import hyve.petshow.controller.representation.MensagemRepresentation;
import hyve.petshow.domain.AnimalEstimacao;
import hyve.petshow.exceptions.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AnimalEstimacaoService {
    AnimalEstimacao adicionarAnimalEstimacao(AnimalEstimacao animalEstimacao);

    AnimalEstimacao buscarAnimalEstimacaoPorId(Long id) throws NotFoundException;

    List<AnimalEstimacao> buscarAnimaisEstimacao() throws NotFoundException;

    AnimalEstimacao atualizarAnimalEstimacao(Long id, AnimalEstimacao animalEstimacao) throws NotFoundException;

    MensagemRepresentation removerAnimalEstimacao(Long id);
}
