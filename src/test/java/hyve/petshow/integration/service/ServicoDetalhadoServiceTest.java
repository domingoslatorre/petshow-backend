package hyve.petshow.integration.service;

import static hyve.petshow.mock.ContaMock.contaPrestador;
import static hyve.petshow.util.PagingAndSortingUtils.geraPageable;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import hyve.petshow.domain.Adicional;
import hyve.petshow.domain.Prestador;
import hyve.petshow.domain.Servico;
import hyve.petshow.domain.ServicoDetalhado;
import hyve.petshow.exceptions.BusinessException;
import hyve.petshow.exceptions.NotFoundException;
import hyve.petshow.repository.PrestadorRepository;
import hyve.petshow.repository.ServicoDetalhadoRepository;
import hyve.petshow.repository.ServicoRepository;
import hyve.petshow.service.port.ServicoDetalhadoService;

@ActiveProfiles("test")
@SpringBootTest
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class ServicoDetalhadoServiceTest {
	@Autowired
	private ServicoDetalhadoService service;
	@Autowired
	private ServicoDetalhadoRepository repository;

	@Autowired
	private ServicoRepository servicoRepository;
	@Autowired
	private PrestadorRepository prestadorRepository;

	private ServicoDetalhado servicoDetalhado;
	private Servico tipoServico;
	private Prestador prestador;

	@BeforeEach
	public void init() {
		tipoServico = new Servico();
		tipoServico.setNome("Banho e tosa");
		var tipo = servicoRepository.save(tipoServico);

		prestador = new Prestador(contaPrestador());
		prestador.setId(null);
		var prestadorDb = prestadorRepository.save(prestador);

		servicoDetalhado = new ServicoDetalhado();
		servicoDetalhado.setPrestadorId(prestadorDb.getId());
		servicoDetalhado.setTipo(tipo);
	}

	@AfterEach
	public void removeTipos() {
		repository.deleteAll();
		servicoRepository.deleteAll();
		prestadorRepository.deleteAll();
	}

	@Test
	public void deve_adicionar_servico() {
		// Given
		var servico = service.adicionarServicoDetalhado(servicoDetalhado);

		// Then
		assertTrue(repository.findById(servico.getId()).isPresent());
	}

  @Test
	public void deve_atualizar_servico() throws BusinessException, NotFoundException {
		// Given
		var servico = service.adicionarServicoDetalhado(servicoDetalhado);

		// When
		var mediaAvaliacao = Float.valueOf(5);
		servico.setMediaAvaliacao(mediaAvaliacao);
		var servicoAtualizado = service.atualizarServicoDetalhado(servico.getId(), servico.getPrestadorId(), servico);

		// Then
		var servicoDb = repository.findById(servicoAtualizado.getId());
		assertEquals(mediaAvaliacao.doubleValue(), servicoDb.get().getMediaAvaliacao().doubleValue(), 0.001);
	}

	@Test
	public void deve_retornar_erro_ao_atualizar_por_nao_pertencer_ao_prestador() {
		// Given
		var servico = service.adicionarServicoDetalhado(servicoDetalhado);

		// When
		var mediaAvaliacao = Float.valueOf(5);
		servico.setMediaAvaliacao(mediaAvaliacao);
		assertThrows(BusinessException.class, () -> {
			service.atualizarServicoDetalhado(servico.getId(), servico.getPrestadorId() + 1, servico);
		});
	}

	@Test
	public void deve_remover_servico() throws BusinessException, NotFoundException {
		// Given
		var servico = service.adicionarServicoDetalhado(servicoDetalhado);

		// When
		service.removerServicoDetalhado(servico.getId(), servico.getPrestadorId());

		// Then
		assertFalse(repository.existsById(servico.getId()));
	}

	@Test
	public void deve_retornar_erro_ao_remover_servico_por_nao_pertencer_ao_prestador() {
		// Given
		var servico = service.adicionarServicoDetalhado(servicoDetalhado);

		// Then
		assertThrows(BusinessException.class, () -> {
			service.removerServicoDetalhado(servico.getId(), servico.getPrestadorId() + 1);
		});

	}

	@Test
	public void deve_retornar_pagina_de_busca_por_tipo() throws NotFoundException {
		// Given
		var servico = service.adicionarServicoDetalhado(servicoDetalhado);

		// When
		var page = service.buscarServicosDetalhadosPorTipoServico(servico.getTipo().getId(), geraPageable(0, 5));

		// Then
		assertFalse(page.isEmpty());
	}

	@Test
	public void deve_retornar_erro_por_pagina_vazia() {
		assertThrows(NotFoundException.class, () -> {
			service.buscarServicosDetalhadosPorTipoServico(tipoServico.getId(), geraPageable(0, 5));
		});
	}

	@Test
	public void deve_buscar_por_id() throws NotFoundException {
		var servico = service.adicionarServicoDetalhado(servicoDetalhado);
		var servicoDb = service.buscarPorId(servico.getId());
		assertEquals(servico.getId(), servicoDb.getId());
	}

	@Test
	public void deve_retornar_erro_por_id_nao_encontrado() {
		assertThrows(NotFoundException.class, () -> {
			service.buscarPorId(10l);
		});

	}

	@Test
	public void deve_retornar_por_prestador() throws NotFoundException {
		// Given
		var servico = service.adicionarServicoDetalhado(servicoDetalhado);
		// When
		var page = service.buscarPorPrestadorId(servico.getPrestadorId(), geraPageable(0, 5));
		// Then
		assertFalse(page.isEmpty());
	}

	@Test
	public void deve_retornar_erro_por_nao_encontrar_pro_prestador() {
		assertThrows(NotFoundException.class, () -> {
			service.buscarPorPrestadorId(prestador.getId(), geraPageable(0, 5));
		});
	}

	@Test
	public void deve_retornar_por_prestador_e_id_servico() throws NotFoundException {
		// Given
		var servico = service.adicionarServicoDetalhado(servicoDetalhado);

		// When
		var servicoDb = service.buscarPorPrestadorIdEServicoId(servico.getPrestadorId(), servico.getId());
		// Then
		assertEquals(servico.getId(), servicoDb.getId());
	}

	@Test
	public void deve_retornar_erro_por_nao_encontrar_por_prestador_e_servico() {
		assertThrows(NotFoundException.class, () -> {
			service.buscarPorPrestadorIdEServicoId(prestador.getId(), 10l);
		});
	}

	@Test
	public void deve_inserir_com_um_adicional() throws Exception {
		// Given
		var adicional = new Adicional(null, "Teste", "Descricao", BigDecimal.valueOf(10), null, null);
		var adicionais = new ArrayList<>(Arrays.asList(adicional));

		servicoDetalhado.setAdicionais(adicionais);

		// When
		service.adicionarServicoDetalhado(servicoDetalhado);
		var servico = service.buscarPorId(servicoDetalhado.getId());
		// Then
		assertEquals(1, servico.getAdicionais().size());
	}

	@Test
	public void deve_inserir_com_dois_adicionais() throws Exception {
		// Given
		var adicional = new Adicional(null, "Teste", "Descricao", BigDecimal.valueOf(10), null, null);
		var adicional2 = new Adicional(null, "Teste2", "Descricao2", BigDecimal.valueOf(15), null, null);
		var adicionais = new ArrayList<>(Arrays.asList(adicional, adicional2));

		servicoDetalhado.setAdicionais(adicionais);

		// When
		service.adicionarServicoDetalhado(servicoDetalhado);
		var servico = service.buscarPorId(servicoDetalhado.getId());
		// Then
		assertEquals(2, servico.getAdicionais().size());
	}

	@Test
	public void adicional_deve_ter_auditoria() throws NotFoundException {
		// Given
		var adicional = new Adicional(null, "Teste", "Descricao", BigDecimal.valueOf(10), null, null);
		var adicionais = new ArrayList<>(Arrays.asList(adicional));

		servicoDetalhado.setAdicionais(adicionais);

		// When
		service.adicionarServicoDetalhado(servicoDetalhado);
		var servico = service.buscarPorId(servicoDetalhado.getId());
		// Then
		assertNotNull(servico.getAdicionais().stream().findFirst().get().getAuditoria());
	}
}
