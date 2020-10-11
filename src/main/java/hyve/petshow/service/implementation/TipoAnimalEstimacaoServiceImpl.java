package hyve.petshow.service.implementation;

import hyve.petshow.domain.TipoAnimalEstimacao;
import hyve.petshow.exceptions.NotFoundException;
import hyve.petshow.repository.TipoAnimalEstimacaoRepository;
import hyve.petshow.service.port.TipoAnimalEstimacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TipoAnimalEstimacaoServiceImpl extends TipoService<TipoAnimalEstimacao> implements TipoAnimalEstimacaoService {
    public TipoAnimalEstimacaoServiceImpl() {
    	super("Nenhum tipo animal de estimação encontrado");
    }
    
    @Autowired
    private TipoAnimalEstimacaoRepository repository;

    @Override
    public List<TipoAnimalEstimacao> buscarTiposAnimalEstimacao() throws NotFoundException {
    	return buscarTodos();
    }

	@Override
	public List<TipoAnimalEstimacao> buscarLista() {
		return repository.findAll();
	}
}
