package hyve.petshow.service.implementation;

import hyve.petshow.domain.Validacao;
import hyve.petshow.repository.ValidacaoRepository;
import hyve.petshow.service.port.ValidacaoService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Log4j2
@Service
public class ValidacaoServiceImpl implements ValidacaoService {
    @Autowired
    private ValidacaoRepository validacaoRepository;

    @Override
    public Validacao criar(Validacao validacao) {
        return validacaoRepository.save(validacao);
    }

    @Override
    public Validacao atualizar(Validacao validacao) {
        return validacaoRepository.save(validacao);
    }

    @Override
    public Boolean remover(Long id) {
        Boolean response = Boolean.TRUE;
        try {
            validacaoRepository.deleteById(id);
        }catch (Exception e){
            log.error(e.getMessage());
            response = false;
        }
        return response;
    }

    @Override
    public Optional<Validacao> obterUmPorId(Long id) {
        return validacaoRepository.findById(id);
    }

    @Override
    public Iterable<Validacao> obterTodos() {
        return validacaoRepository.findAll();
    }
}
