package hyve.petshow.service.implementation;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hyve.petshow.controller.representation.AnimalEstimacaoResponseRepresentation;
import hyve.petshow.domain.AnimalEstimacao;
import hyve.petshow.repository.AnimalEstimacaoRepository;
import hyve.petshow.service.port.AnimalEstimacaoService;

@Service
public class AnimalEstimacaoServiceImpl implements AnimalEstimacaoService {
    private static final String MENSAGEM_SUCESSO = "Operação executada com sucesso!";
    private static final String MENSAGEM_FALHA = "Falha durante a execução da operação.";

    @Autowired
    private AnimalEstimacaoRepository animalEstimacaoRepository;

    @Override
    public AnimalEstimacao criarAnimalEstimacao(AnimalEstimacao animalEstimacao) {
        return animalEstimacaoRepository.save(animalEstimacao);
    }

    @Override
    public Optional<AnimalEstimacao> obterAnimalEstimacaoPorId(Long id) {
        return animalEstimacaoRepository.findById(id);
    }

    @Override
    public List<AnimalEstimacao> obterAnimaisEstimacao() {
        return animalEstimacaoRepository.findAll();
    }

    @Override
    public Optional<AnimalEstimacao> atualizarAnimalEstimacao(Long id, AnimalEstimacao animalEstimacaoRequest) {
        Optional<AnimalEstimacao> animalEstimacaoOptional = animalEstimacaoRepository.findById(id);
        Optional<AnimalEstimacao> response = Optional.empty();

        if(animalEstimacaoOptional.isPresent()){
            AnimalEstimacao animalEstimacao = animalEstimacaoOptional.get();
           
            animalEstimacao.setNome(animalEstimacaoRequest.getNome());
            animalEstimacao.setFoto(animalEstimacaoRequest.getFoto());
            animalEstimacao.setTipo(animalEstimacaoRequest.getTipo());

            response = Optional.of(animalEstimacaoRepository.save(animalEstimacao));
        }

        return response;
    }

    @Override
    public AnimalEstimacaoResponseRepresentation removerAnimalEstimacao(Long id) {
        AnimalEstimacaoResponseRepresentation animalEstimacaoResponseRepresentation =
                new AnimalEstimacaoResponseRepresentation();

        animalEstimacaoRepository.deleteById(id);

        Boolean successo = !animalEstimacaoRepository.existsById(id);

        animalEstimacaoResponseRepresentation.setId(id);
        animalEstimacaoResponseRepresentation.setSucesso(successo);
        animalEstimacaoResponseRepresentation.setMensagem(successo ? MENSAGEM_SUCESSO : MENSAGEM_FALHA);

        return animalEstimacaoResponseRepresentation;
    }
}
