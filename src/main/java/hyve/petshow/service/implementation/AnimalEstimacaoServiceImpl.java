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
    public List<AnimalEstimacao> buscarAnimaisEstimacao(Long id) throws NotFoundException {
        var animaisEstimacao = animalEstimacaoRepository.findByDonoId(id);

        if(animaisEstimacao.isEmpty()) {
            throw new NotFoundException("Nenhum animal de estimação foi encontrado.");
        }

        return animaisEstimacao;
    }

    @Override
    public AnimalEstimacao atualizarAnimalEstimacao(Long id, AnimalEstimacao request, Long donoId)
            throws NotFoundException, BusinessException {
        var animalEstimacao = animalEstimacaoRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Animal de estimação não encontrado"));

        if(verificarDono(animalEstimacao.getDonoId(), donoId)){

            animalEstimacao.setNome(request.getNome());
            animalEstimacao.setTipo(request.getTipo());
            animalEstimacao.setFoto(request.getFoto());

            var response = animalEstimacaoRepository.save(animalEstimacao);

            return response;
        } else {
            throw new BusinessException("Este animal não pertence a este usuário");
        }
    }

    @Override
    public MensagemRepresentation removerAnimalEstimacao(Long id, Long donoId)
            throws BusinessException, NotFoundException {
        var animalEstimacao = animalEstimacaoRepository.findById(id).orElseThrow(
                () -> new NotFoundException("Animal de estimação não encontrado"));

        if(verificarDono(animalEstimacao.getDonoId(), donoId)) {
            animalEstimacaoRepository.deleteById(id);

            var sucesso = !animalEstimacaoRepository.existsById(id);
            var response = new MensagemRepresentation(id);

            response.setSucesso(sucesso);

            return response;
        } else {
            throw new BusinessException("Este animal não pertence a este usuário");
        }
    }

    private Boolean verificarDono(Long domainId, Long requestId){
        return domainId == requestId;
    }
}
