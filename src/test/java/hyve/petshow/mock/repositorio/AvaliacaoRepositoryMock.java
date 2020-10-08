package hyve.petshow.mock.repositorio;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import hyve.petshow.domain.Avaliacao;
import hyve.petshow.domain.ServicoDetalhado;

public class AvaliacaoRepositoryMock {
	private static List<Avaliacao> dbMock = new ArrayList<Avaliacao>();

	public static void limpaLista() {
		dbMock.clear();
	}

	public static List<Avaliacao> findAll() {
		return dbMock;
	}

	public static List<Avaliacao> findByServicoAvaliado(ServicoDetalhado servico) {
		return dbMock.stream().filter(el -> el.getServicoAvaliadoId().equals(servico.getId()))
				.collect(Collectors.toList());
	}

	public static Avaliacao save(Avaliacao avaliacao) {
		if (avaliacao.getId() != null) {
			return editaAvaliacao(avaliacao);
		}
		avaliacao.setId((long) dbMock.size() + 1);
		dbMock.add(avaliacao);
		return avaliacao;
	}

	private static Avaliacao editaAvaliacao(Avaliacao avaliacao) {
		dbMock = dbMock.stream().map(el -> {
			return el.getId().equals(avaliacao.getId()) ? avaliacao : el;
		}).collect(Collectors.toList());
		return avaliacao;
	}
}
