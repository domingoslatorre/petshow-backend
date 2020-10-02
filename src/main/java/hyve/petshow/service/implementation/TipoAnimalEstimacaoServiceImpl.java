package hyve.petshow.service.implementation;

import hyve.petshow.domain.TipoAnimalEstimacao;
import hyve.petshow.repository.TipoAnimalEstimacaoRepository;
import hyve.petshow.service.port.TipoAnimalEstimacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TipoAnimalEstimacaoServiceImpl implements TipoAnimalEstimacaoService {
    @Autowired
    private TipoAnimalEstimacaoRepository repository;

    @Override
    public List<TipoAnimalEstimacao> buscarTiposAnimalEstimacao() {
        return repository.findAll();
    }
}
