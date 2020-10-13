package hyve.petshow.service.implementation;

import java.util.List;

import hyve.petshow.controller.representation.MensagemRepresentation;
import hyve.petshow.exceptions.BusinessException;
import hyve.petshow.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hyve.petshow.domain.AnimalEstimacao;
import hyve.petshow.repository.AnimalEstimacaoRepository;
import hyve.petshow.service.port.AnimalEstimacaoService;

import static hyve.petshow.util.ProxyUtils.verificarIdentidade;

@Service
public class AnimalEstimacaoServiceImpl implements AnimalEstimacaoService {
    private final String ANIMAL_ESTIMACAO_NAO_ENCONTRADO = "Animal de estimação não encontrado";
    private final String NENHUM_ANIMAL_ESTIMACAO_ENCONTRADO = "Nenhum animal de estimação encontrado";
    private final String USUARIO_NAO_PROPRIETARIO = "Este animal não pertence a este usuário";

    @Autowired
    private AnimalEstimacaoRepository animalEstimacaoRepository;

    @Override
    public AnimalEstimacao adicionarAnimalEstimacao(AnimalEstimacao animalEstimacao) {
        return animalEstimacaoRepository.save(animalEstimacao);
    }

    @Override
    public AnimalEstimacao buscarAnimalEstimacaoPorId(Long id) throws NotFoundException {
        return animalEstimacaoRepository.findById(id).orElseThrow(
                () -> new NotFoundException(ANIMAL_ESTIMACAO_NAO_ENCONTRADO));
    }

    @Override
    public List<AnimalEstimacao> buscarAnimaisEstimacaoPorDono(Long id) throws NotFoundException {
        var animaisEstimacao = animalEstimacaoRepository.findByDonoId(id);

        if(animaisEstimacao.isEmpty()) {
            throw new NotFoundException(NENHUM_ANIMAL_ESTIMACAO_ENCONTRADO);
        }

        return animaisEstimacao;
    }

    @Override
    public AnimalEstimacao atualizarAnimalEstimacao(Long id, AnimalEstimacao request)
            throws NotFoundException, BusinessException {
        var animalEstimacao = buscarAnimalEstimacaoPorId(id);

        if(verificarIdentidade(animalEstimacao.getDonoId(), request.getDonoId())){
            animalEstimacao.setNome(request.getNome());
            animalEstimacao.setTipo(request.getTipo());
            animalEstimacao.setFoto(request.getFoto());
            var response = animalEstimacaoRepository.save(animalEstimacao);
            return response;
        } else {
            throw new BusinessException(USUARIO_NAO_PROPRIETARIO);
        }
    }

    @Override
    public MensagemRepresentation removerAnimalEstimacao(Long id, Long donoId)
            throws BusinessException, NotFoundException {
        var animalEstimacao =  buscarAnimalEstimacaoPorId(id);

        if(verificarIdentidade(animalEstimacao.getDonoId(), donoId)) {
            animalEstimacaoRepository.deleteById(id);

            var sucesso = !animalEstimacaoRepository.existsById(id);
            var response = new MensagemRepresentation(id);

            response.setSucesso(sucesso);

            return response;
        } else {
            throw new BusinessException(USUARIO_NAO_PROPRIETARIO);
        }
    }
}
