package hyve.petshow.unit.repository;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
import hyve.petshow.domain.Prestador;
import hyve.petshow.domain.ServicoDetalhado;
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
	
	private Prestador prestador;
	private ServicoDetalhado servico;
	private Cliente cliente;

	@BeforeEach
	public void adicionaServico() {
		Avaliacao avaliacao = AvaliacaoMock.geraAvaliacao();
		prestador = prestadorRepository.save(avaliacao.getServicoAvaliado().getPrestador());
		avaliacao.getServicoAvaliado().setPrestador(prestador);
		servicoRepository.save(avaliacao.getServicoAvaliado().getTipo());
		servico = servicoDetalhadoRepository.save(avaliacao.getServicoAvaliado());
		cliente = clienteRepository.save(avaliacao.getCliente());
	}

	@AfterEach
	public void limpaListas() {
		repository.deleteAll();
	}

	@Test
	public void deve_retornar_avaliacao_de_servico() {
		Avaliacao avaliacao = AvaliacaoMock.geraAvaliacao();
		avaliacao.setServicoAvaliado(servico);
		avaliacao.setCliente(cliente);
		Avaliacao avaliacaoSalva = repository.save(avaliacao);
		servico.getPrestador();
		List<Avaliacao> avaliacoes = repository.findByServicoAvaliado(servico);

		assertTrue(avaliacoes.stream().filter(el -> avaliacaoSalva.getId().equals(el.getId())).findFirst().isPresent());
	}

	@Test
	public void deve_retornar_lista_de_avaliacoes() {
		List<Avaliacao> avaliacoes = AvaliacaoMock.geraListaAvaliacao();
		List<Avaliacao> avaliacoesSalvas = repository.saveAll(avaliacoes.stream().map(avaliacao -> {
			avaliacao.setCliente(cliente);
			avaliacao.setServicoAvaliado(servico);
			return avaliacao;
		}).collect(Collectors.toList()));

		ServicoDetalhado servicoAvaliado = avaliacoesSalvas.get(0).getServicoAvaliado();
		List<Avaliacao> busca = repository.findByServicoAvaliado(servico);

		assertEquals(busca.size(), avaliacoesSalvas.size());
	}

}
