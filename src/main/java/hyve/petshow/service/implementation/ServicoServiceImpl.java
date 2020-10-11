package hyve.petshow.service.implementation;

import hyve.petshow.domain.Servico;
import hyve.petshow.exceptions.NotFoundException;
import hyve.petshow.repository.ServicoRepository;
import hyve.petshow.service.port.ServicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicoServiceImpl extends TipoService<Servico> implements ServicoService {
    public ServicoServiceImpl() {
		super("Nenhum serviço encontrado");
	}

    @Autowired
    private ServicoRepository repository;
    
    @Override
    public List<Servico> buscarServicos() throws NotFoundException {    	
    	return buscarTodos();
    }

	@Override
	public List<Servico> buscarLista() {
		return repository.findAll();
	}
}

