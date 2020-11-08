package hyve.petshow.unit.facade;

import hyve.petshow.controller.converter.AvaliacaoConverter;
import hyve.petshow.controller.representation.AvaliacaoRepresentation;
import hyve.petshow.domain.Avaliacao;
import hyve.petshow.domain.Cliente;
import hyve.petshow.domain.ServicoDetalhado;
import hyve.petshow.facade.AvaliacaoFacade;
import hyve.petshow.service.port.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static hyve.petshow.mock.AvaliacaoMock.avaliacao;
import static hyve.petshow.mock.AvaliacaoMock.avaliacaoRepresentation;
import static hyve.petshow.mock.ClienteMock.cliente;
import static hyve.petshow.mock.ServicoDetalhadoMock.servicoDetalhado;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.mockito.MockitoAnnotations.initMocks;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class AvaliacaoFacadeTest {
	@Mock
	private ServicoDetalhadoService servicoDetalhadoService;
	@Mock
	private AvaliacaoService avaliacaoService;
	@Mock
	private ClienteService clienteService;
	@Mock
	private AvaliacaoConverter converter;
	@InjectMocks
	private AvaliacaoFacade facade;

	private Cliente cliente = cliente();
	private ServicoDetalhado servicoDetalhado = servicoDetalhado();
	private AvaliacaoRepresentation avaliacaoRepresentation = avaliacaoRepresentation();
	private List<Avaliacao> avaliacoes = new ArrayList<>();
	private Avaliacao avaliacao = avaliacao();

	@BeforeEach
	public void init() throws Exception {
		initMocks(this);

		doReturn(cliente).when(clienteService).buscarPorId(anyLong());
		doReturn(servicoDetalhado).when(servicoDetalhadoService).buscarPorId(anyLong());
		doReturn(avaliacao).when(converter).toDomain(any(AvaliacaoRepresentation.class));
		doReturn(singletonList(avaliacaoRepresentation)).when(converter).toRepresentationList(any(List.class));
		doReturn(singletonList(avaliacao())).when(avaliacaoService).buscarAvaliacoesPorServicoId(anyLong());
		doAnswer(a -> {
			avaliacoes.add(avaliacao);
			return null;
		}).when(avaliacaoService).adicionarAvaliacao(any(Avaliacao.class));
	}

	@Test
	public void deve_criar_avaliacao() throws Exception {
		facade.adicionarAvaliacao(avaliacaoRepresentation, 1L, 1L);

		assertFalse(avaliacoes.isEmpty());
	}
	
	@Test
	public void deve_retornar_avaliacao_em_lista() throws Exception {
		facade.adicionarAvaliacao(avaliacaoRepresentation, 1L, 1L);
		
		// quando
		var avaliacoes = facade.buscarAvaliacaoPorServico(1L);
		
		assertFalse(avaliacoes.isEmpty());
	}
}
