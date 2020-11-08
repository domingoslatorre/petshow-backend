package hyve.petshow.unit.service;

import hyve.petshow.domain.Avaliacao;
import hyve.petshow.exceptions.NotFoundException;
import hyve.petshow.repository.AvaliacaoRepository;
import hyve.petshow.service.implementation.AvaliacaoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static hyve.petshow.mock.AvaliacaoMock.avaliacao;
import static hyve.petshow.mock.AvaliacaoMock.avaliacaoList;
import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;
import static org.mockito.MockitoAnnotations.initMocks;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class AvaliacaoServiceTest {
	@Mock
	private AvaliacaoRepository repository;

	@InjectMocks
	private AvaliacaoServiceImpl service;

	private Avaliacao avaliacao = avaliacao();
	private List<Avaliacao> avaliacoes = avaliacaoList();

	@BeforeEach
	public void init() {
		initMocks(this);

		doReturn(avaliacoes).when(repository).findByServicoAvaliadoId(anyLong());
		doReturn(Optional.of(avaliacao())).when(repository).findById(Mockito.anyLong());
	}

	@Test
	public void deve_inserir_avaliacao() {
		doReturn(avaliacao).when(repository).save(any(Avaliacao.class));

		avaliacao = service.adicionarAvaliacao(avaliacao);

		assertNotNull(avaliacao);
	}

	@Test
	public void deve_retornar_avaliacoes() throws NotFoundException {
		var actual = service.buscarAvaliacoesPorServicoId(1L);

		assertEquals(avaliacoes, actual);
	}

	@Test
	public void deve_retornar_excecao_de_nenhuma_encontrada_por_servico() {
		doReturn(emptyList()).when(repository).findByServicoAvaliadoId(Mockito.anyLong());

		assertThrows(NotFoundException.class, () -> service.buscarAvaliacoesPorServicoId(10L));
	}
	
	@Test
	public void deve_retornar_algo_por_id() throws NotFoundException {
		var actual = service.buscarAvaliacaoPorId(1L);

		assertNotNull(actual);
	}
	
	@Test
	public void deve_retornar_excecao_por_nao_encontrar_por_id() {
		doReturn(Optional.empty()).when(repository).findById(Mockito.anyLong());
		
		assertThrows(NotFoundException.class, () -> service.buscarAvaliacaoPorId(1L));
	}
}
