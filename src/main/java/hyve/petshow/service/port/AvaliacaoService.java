package hyve.petshow.service.port;

import java.util.List;

import hyve.petshow.exceptions.NotFoundException;
import org.springframework.stereotype.Service;

import hyve.petshow.domain.Avaliacao;
import hyve.petshow.domain.ServicoDetalhado;

@Service
public interface AvaliacaoService {
	List<Avaliacao> buscarAvaliacoesPorServicoId(Long id) throws NotFoundException;
	Avaliacao adicionarAvaliacao(Avaliacao avaliacao);
	Avaliacao buscarAvaliacaoPorId(Long id) throws Exception;
}
