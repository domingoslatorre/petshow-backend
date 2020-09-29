package hyve.petshow.service.port;

import java.util.List;

import org.springframework.stereotype.Service;

import hyve.petshow.domain.Avaliacao;
import hyve.petshow.domain.ServicoDetalhado;

@Service
public interface AvaliacaoService {
	List<Avaliacao> buscarAvaliacoesPorServico(ServicoDetalhado servico);
	Avaliacao adicionarAvaliacao(Avaliacao avaliacao);
}
