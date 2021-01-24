package hyve.petshow.integration.service;

import static hyve.petshow.mock.ContaMock.contaPrestador;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static hyve.petshow.mock.ContaMock.contaCliente;

import java.math.BigDecimal;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import hyve.petshow.domain.Avaliacao;
import hyve.petshow.domain.Cliente;
import hyve.petshow.domain.Prestador;
import hyve.petshow.domain.Servico;
import hyve.petshow.domain.ServicoDetalhado;
import hyve.petshow.exceptions.NotFoundException;
import hyve.petshow.repository.AvaliacaoRepository;
import hyve.petshow.repository.ClienteRepository;
import hyve.petshow.repository.PrestadorRepository;
import hyve.petshow.repository.ServicoDetalhadoRepository;
import hyve.petshow.repository.ServicoRepository;
import hyve.petshow.service.port.AvaliacaoService;
import static hyve.petshow.util.PagingAndSortingUtils.geraPageable;

@ActiveProfiles("test")
@SpringBootTest
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class AvaliacaoServiceTest {
	@Autowired
	private AvaliacaoRepository repository;
	@Autowired
	private AvaliacaoService service;
	
	@Autowired
	private ServicoDetalhadoRepository servicoDetalhadoRepository;
	
	@Autowired
	private ServicoRepository servicoRepository;
	@Autowired
	private PrestadorRepository prestadorRepository;
	@Autowired
	private ClienteRepository clienteRepository;
	
	
	private ServicoDetalhado servicoDetalhado;
	private Servico tipoServico;
	private Prestador prestador;
	private Avaliacao avaliacao;
	private Cliente cliente;
	
	@BeforeEach
	public void init() {
		cliente = new Cliente(contaCliente());
		cliente.setId(null);
		var clienteDb = clienteRepository.save(cliente);
		
		tipoServico = new Servico();
		tipoServico.setNome("Banho e tosa");
		var tipo = servicoRepository.save(tipoServico);
		
		prestador = new Prestador(contaPrestador());
		prestador.setId(null);
		var prestadorDb = prestadorRepository.save(prestador);
		
		servicoDetalhado = new ServicoDetalhado();
		servicoDetalhado.setPrestadorId(prestadorDb.getId());
		servicoDetalhado.setTipo(tipo);
		var servico = servicoDetalhadoRepository.save(servicoDetalhado);
		
		avaliacao = new Avaliacao();
		avaliacao.setServicoAvaliadoId(servico.getId());
		avaliacao.setCliente(clienteDb);
	}
	
	@AfterEach
	public void limparRepositorios() {
		repository.deleteAll();
		clienteRepository.deleteAll();
		servicoDetalhadoRepository.deleteAll();
		prestadorRepository.deleteAll();
		servicoRepository.deleteAll();
	}
	
	@Test
	public void deve_adicionar_avaliacao() {
		// Given
		var avaliacaoSalva = service.adicionarAvaliacao(avaliacao);
		
		// When
		var busca = repository.findById(avaliacaoSalva.getId());
		
		// Then
		assertTrue(busca.isPresent());
		
	}
	
	@Test
	public void deve_retornar_avaliacao_por_id() throws Exception {
		// Given
		var avaliacaoSalva = service.adicionarAvaliacao(avaliacao);
		
		// when
		var busca = service.buscarAvaliacaoPorId(avaliacaoSalva.getId());
		
		// Then
		assertEquals(avaliacaoSalva.getId(), busca.getId());
	}
	
	@Test
	public void deve_retornar_erro_de_avaliacao_nao_encontrada() {
		assertThrows(NotFoundException.class, () -> {
			service.buscarAvaliacaoPorId(10l);
		});
	}
	
	@Test
	public void deve_retornar_pagina_de_avaliacoes() throws NotFoundException {
		// Given
		var avaliacaoSalva = service.adicionarAvaliacao(avaliacao);
		
		// When
		var page = service.buscarAvaliacoesPorServicoId(avaliacaoSalva.getServicoAvaliadoId(), geraPageable(0, 5));
		// Then
		assertFalse(page.isEmpty());
	}
	
	@Test
	public void deve_retornar_erro_por_nao_encontrar_avaliacoes() {
		assertThrows(NotFoundException.class, () -> {
			service.buscarAvaliacoesPorServicoId(servicoDetalhado.getId(), geraPageable(0, 5));
		});
	}

}
