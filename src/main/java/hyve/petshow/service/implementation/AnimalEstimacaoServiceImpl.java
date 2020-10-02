package hyve.petshow.service.implementation;

import java.util.List;

import hyve.petshow.controller.representation.MensagemRepresentation;
import hyve.petshow.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hyve.petshow.domain.AnimalEstimacao;
import hyve.petshow.repository.AnimalEstimacaoRepository;
import hyve.petshow.service.port.AnimalEstimacaoService;

@Service
public class AnimalEstimacaoServiceImpl implements AnimalEstimacaoService {
    @Autowired
    private AnimalEstimacaoRepository animalEstimacaoRepository;

    @Override
    public AnimalEstimacao adicionarAnimalEstimacao(AnimalEstimacao animalEstimacao) {
        return animalEstimacaoRepository.save(animalEstimacao);
    }

    @Override
    public AnimalEstimacao buscarAnimalEstimacaoPorId(Long id) throws NotFoundException {
        return animalEstimacaoRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Animal de estimação não encontrado"));
    }

    @Override
    public List<AnimalEstimacao> buscarAnimaisEstimacao() throws NotFoundException {
        var animaisEstimacao = animalEstimacaoRepository.findAll();

        if(animaisEstimacao.isEmpty()) {
            throw new NotFoundException("Nenhum animal de estimação foi encontrado.");
        }

        return animaisEstimacao;
    }

    @Override
    public AnimalEstimacao atualizarAnimalEstimacao(Long id, AnimalEstimacao request) throws NotFoundException {
        var animalEstimacao = animalEstimacaoRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Animal de estimação não encontrado"));

        var response = animalEstimacaoRepository.save(request);

        return response;
    }

    @Override
    public MensagemRepresentation removerAnimalEstimacao(Long id) {
        animalEstimacaoRepository.deleteById(id);

        var sucesso = ! animalEstimacaoRepository.existsById(id);
        var response = new MensagemRepresentation(id);

        response.setSucesso(sucesso);

        return response;
    }
}
