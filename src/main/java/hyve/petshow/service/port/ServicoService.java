package hyve.petshow.service.port;

import hyve.petshow.domain.Servico;
import hyve.petshow.exceptions.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ServicoService {
	List<Servico> buscarServicos() throws NotFoundException;
	
	List<Servico> buscarServicosPresentesEmEstado(String cidade, String estado) throws NotFoundException;
}
