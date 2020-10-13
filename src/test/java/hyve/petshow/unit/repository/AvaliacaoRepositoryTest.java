package hyve.petshow.unit.repository;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.stream.Collectors;

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

import hyve.petshow.domain.Avaliacao;
import hyve.petshow.domain.Cliente;
import hyve.petshow.domain.ServicoDetalhado;
import hyve.petshow.mock.ClienteMock;
import hyve.petshow.mock.PrestadorMock;
import hyve.petshow.mock.ServicoDetalhadoMock;
import hyve.petshow.mock.entidades.AvaliacaoMock;
import hyve.petshow.repository.AvaliacaoRepository;
import hyve.petshow.repository.ClienteRepository;
import hyve.petshow.repository.PrestadorRepository;
import hyve.petshow.repository.ServicoDetalhadoRepository;
import hyve.petshow.repository.ServicoRepository;

@SpringBootTest
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@ActiveProfiles("test")
@DirtiesContext(classMode = ClassMode.BEFORE_CLASS)
public class AvaliacaoRepositoryTest {

	@Autowired
	private AvaliacaoRepository repository;
	@Autowired
	private ServicoRepository servicoRepository;
	@Autowired
	private ServicoDetalhadoRepository servicoDetalhadoRepository;
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private PrestadorRepository prestadorRepository;
	
	private ServicoDetalhado servico;
	private Cliente cliente;

	@BeforeEach
	public void adicionaServico() {
//		Avaliacao avaliacao = AvaliacaoMock.geraAvaliacao();
//		var servico = servicoDetalhadoRepository.findById(avaliacao.getId());
//		prestador = prestadorRepository.save(avaliacao.getServicoAvaliadoId().getPrestadorId());
//		servicoRepository.save(avaliacao.getServicoAvaliadoId().getTipo());
//		servico = servicoDetalhadoRepository.save(avaliacao.getServicoAvaliadoId());
//		cliente = clienteRepository.save(avaliacao.getClienteId());
		
		var servicoDetalhado = ServicoDetalhadoMock.criarServicoDetalhado();
		var prestadorMock = PrestadorMock.criaPrestador();
		var clienteMock = ClienteMock.criaCliente();
		
		servicoRepository.save(servicoDetalhado.getTipo());
		cliente = clienteRepository.save(clienteMock);
		var prestador = prestadorRepository.save(prestadorMock);
		servicoDetalhado.setPrestadorId(prestador.getId());
		servico = servicoDetalhadoRepository.save(servicoDetalhado);
	}

	@AfterEach
	public void limpaListas() {
		repository.deleteAll();
	}

	@Test
	public void deve_retornar_avaliacao_de_servico() {
		Avaliacao avaliacao = AvaliacaoMock.geraAvaliacao();
//		servico.addAvaliacao(avaliacao);
		avaliacao.setServicoAvaliadoId(servico.getId());
		avaliacao.setClienteId(cliente.getId());
		Avaliacao avaliacaoSalva = repository.save(avaliacao);
		List<Avaliacao> avaliacoes = repository.findByServicoAvaliadoId(servico.getId());

		assertTrue(avaliacoes.stream().filter(el -> avaliacaoSalva.getId().equals(el.getId())).findFirst().isPresent());
	}

	@Test
	public void deve_retornar_lista_de_avaliacoes() {
		List<Avaliacao> avaliacoes = AvaliacaoMock.geraListaAvaliacao();
		List<Avaliacao> avaliacoesSalvas = repository.saveAll(avaliacoes.stream().map(avaliacao -> {
			avaliacao.setClienteId(cliente.getId());
			avaliacao.setServicoAvaliadoId(servico.getId());
//			servico.addAvaliacao(avaliacao);
			return avaliacao;
		}).collect(Collectors.toList()));

		Long servicoAvaliado = avaliacoesSalvas.get(0).getServicoAvaliadoId();
		List<Avaliacao> busca = repository.findByServicoAvaliadoId(servicoAvaliado);

		assertEquals(busca.size(), avaliacoesSalvas.size());
	}
	
	@Test
	public void deve_retornar_servico_com_avaliacoes() {
		Avaliacao avaliacao = AvaliacaoMock.geraAvaliacao();
		avaliacao.setServicoAvaliadoId(servico.getId());
		avaliacao.setClienteId(cliente.getId());
		Avaliacao avaliacaoSalva = repository.save(avaliacao);
		var servicoEncontrado = servicoDetalhadoRepository.findById(avaliacaoSalva.getServicoAvaliadoId());
		assertFalse(servicoEncontrado.get().getAvaliacoes().isEmpty());
	}

}
