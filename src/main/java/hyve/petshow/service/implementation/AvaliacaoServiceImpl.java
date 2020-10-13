package hyve.petshow.service.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hyve.petshow.domain.Avaliacao;
import hyve.petshow.domain.ServicoDetalhado;
import hyve.petshow.exceptions.NotFoundException;
import hyve.petshow.repository.AvaliacaoRepository;
import hyve.petshow.service.port.AvaliacaoService;

@Service
public class AvaliacaoServiceImpl implements AvaliacaoService {
	private final String AVALIACAO_NAO_ENCONTRADA = "Avaliação não encontrada";
	private final String NENHUMA_AVALIACAO_ENCONTRADA = "Nenhuma avaliação encontrada";

	@Autowired
	private AvaliacaoRepository repository;
	
	@Override
	public List<Avaliacao> buscarAvaliacoesPorServicoId(Long id) throws NotFoundException {
		var avaliacoes = repository.findByServicoAvaliadoId(id);

		if(avaliacoes.isEmpty()){
			throw new NotFoundException(NENHUMA_AVALIACAO_ENCONTRADA);
		}

		return avaliacoes;
	}

	@Override
	public Avaliacao adicionarAvaliacao(Avaliacao avaliacao) {
		return repository.save(avaliacao);
	}

	@Override
	public Avaliacao buscarAvaliacaoPorId(Long id) throws NotFoundException {
		return repository.findById(id)
				.orElseThrow(()-> new NotFoundException(AVALIACAO_NAO_ENCONTRADA));
	}

}