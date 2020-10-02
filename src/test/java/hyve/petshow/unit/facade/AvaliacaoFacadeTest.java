package hyve.petshow.unit.facade;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;

import hyve.petshow.controller.AvaliacaoFacade;
import hyve.petshow.controller.representation.AvaliacaoRepresentation;
import hyve.petshow.domain.Avaliacao;
import hyve.petshow.domain.Cliente;
import hyve.petshow.domain.Prestador;
import hyve.petshow.domain.ServicoDetalhado;
import hyve.petshow.mock.entidades.AvaliacaoMock;
import hyve.petshow.repository.AvaliacaoRepository;
import hyve.petshow.repository.ClienteRepository;
import hyve.petshow.repository.PrestadorRepository;
import hyve.petshow.repository.ServicoDetalhadoRepository;
import hyve.petshow.repository.ServicoRepository;

@SpringBootTest
@ActiveProfiles("test")
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class AvaliacaoFacadeTest {
	@Autowired
	private AvaliacaoFacade facade;
	@Autowired
	private ServicoRepository servicoRepository;
	@Autowired
	private ServicoDetalhadoRepository servicoDetalhadoRepository;
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private AvaliacaoRepository avaliacaoRepository;
	@Autowired
	private PrestadorRepository prestadorRepository;

	private Cliente clienteMock;
	private ServicoDetalhado servicoDetalhadoMock;
	private Prestador prestadorMock;
	@AfterEach
	public void limpaLista() {
		avaliacaoRepository.deleteAll();
	}

	@BeforeEach
	public void adicionaItens() {
		Avaliacao avaliacao = AvaliacaoMock.geraAvaliacao();
		prestadorMock = prestadorRepository.save(avaliacao.getServicoAvaliado().getPrestador());
		servicoRepository.save(avaliacao.getServicoAvaliado().getTipo());
		avaliacao.getServicoAvaliado().setPrestador(prestadorMock);
		this.servicoDetalhadoMock = servicoDetalhadoRepository.save(avaliacao.getServicoAvaliado());
		this.clienteMock = clienteRepository.save(avaliacao.getCliente());
	}

	@Test
	public void deve_criar_avaliacao() throws Exception {
		// dado
		var representation = AvaliacaoMock.geraAvaliacaoRepresentation();
		var idCliente = clienteMock.getId();
		var idServicoPrestado = servicoDetalhadoMock.getId();
		// quando
		var avaliacaoSalva = facade.adicionarAvaliacao(representation, idCliente, idServicoPrestado);

		assertNotNull(avaliacaoSalva);
	}
	
	@Test
	public void deve_retornar_avaliacao_em_lista() throws Exception {
		// dado
		var representation = AvaliacaoMock.geraAvaliacaoRepresentation();
		var idCliente = clienteMock.getId();
		var idServicoPrestado = servicoDetalhadoMock.getId();
		var avaliacaoSalva = facade.adicionarAvaliacao(representation, idCliente, idServicoPrestado);
		
		// quando
		List<AvaliacaoRepresentation> avaliacoes = facade.buscarAvaliacaoPorServico(idServicoPrestado);
		
		assertTrue(avaliacoes.contains(avaliacaoSalva));
	}
}
