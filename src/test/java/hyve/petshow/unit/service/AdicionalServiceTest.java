package hyve.petshow.unit.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.any;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.MockitoAnnotations.initMocks;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

import hyve.petshow.domain.Adicional;
import hyve.petshow.exceptions.NotFoundException;
import hyve.petshow.repository.AdicionalRepository;
import hyve.petshow.service.implementation.AdicionalServiceImpl;
import hyve.petshow.util.AuditoriaUtils;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class AdicionalServiceTest {
	@Mock
	private AdicionalRepository repository;
	@InjectMocks
	private AdicionalServiceImpl service;

	@BeforeEach
	public void init() {
		initMocks(this);
	}

	@Test
	public void deve_lista_de_objetos() throws Exception {
		// Given
		var esperado = new ArrayList<Adicional>() {
			private static final long serialVersionUID = 1L;

			{
				add(new Adicional(null, "Teste", "Descricao", BigDecimal.valueOf(10), null, null));
				add(new Adicional(null, "Teste2", "Descricao2", BigDecimal.valueOf(15), null, null));
			}
		};

		doReturn(esperado).when(repository).findByServicoDetalhadoId(anyLong());

		// Then
		assertEquals(esperado, service.buscarPorServicoDetalhado(1l));
	}

	@Test
	public void deve_retornar_erro_por_lista_de_adicionais_vazia() {
		// Given
		doReturn(Collections.emptyList()).when(repository).findByServicoDetalhadoId(anyLong());

		// Then
		assertThrows(NotFoundException.class, () -> {
			service.buscarPorServicoDetalhado(1l);
		});
	}

	@Test
	public void deve_criar_adicional() {
		var mockDb = new ArrayList<Adicional>();

		doAnswer(mock -> {
			var adicional = (Adicional) mock.getArgument(0);
			adicional.setId((long) (mockDb.size() + 1));
			mockDb.add(adicional);
			return adicional;
		}).when(repository).save(any());

		var adicional = new Adicional(null, "Teste", "Descricao", BigDecimal.valueOf(10), null, null);
		service.criarAdicional(adicional);

		assertTrue(mockDb.contains(adicional));
	}

	@Test
	public void deve_adicionar_auditoria_para_adicional() {
		var adicional = new Adicional(null, "Teste", "Descricao", BigDecimal.valueOf(10), null, null);
		doReturn(adicional).when(repository).save(any());

		service.criarAdicional(adicional);

		assertNotNull(adicional.getAuditoria());
	}

	@Test
	public void deve_adicionar_auditoria_ativa() {
		var adicional = new Adicional(null, "Teste", "Descricao", BigDecimal.valueOf(10), null, null);
		doReturn(adicional).when(repository).save(any());

		service.criarAdicional(adicional);

		assertTrue(adicional.getAuditoria().isAtivo());
	}

	@Test
	public void deve_retornar_por_id() throws Exception {
		var adicional = new Adicional(null, "Teste", "Descricao", BigDecimal.valueOf(10), null, null);
		doReturn(Optional.ofNullable(adicional)).when(repository).findById(anyLong());

		assertEquals(adicional, service.buscarPorId(1l));
	}

	@Test
	public void deve_disparar_excecao_por_nao_encontrar_em_busca_por_id() {
		doReturn(Optional.empty()).when(repository).findById(anyLong());
		assertThrows(NotFoundException.class, () -> {
			service.buscarPorId(1l);
		});
	}
	
	@Test
	public void deve_atualizar_adicional() throws Exception {
		var adicional = new Adicional(1l, "Teste", "Descricao", BigDecimal.valueOf(10), 1l, AuditoriaUtils.geraAuditoriaInsercao(Optional.empty()));
		var esperado = new Adicional(1l, "Teste2", "Teste2", BigDecimal.valueOf(20), 1l, AuditoriaUtils.geraAuditoriaInsercao(Optional.empty()));
			
		
		doReturn(Optional.ofNullable(adicional)).when(repository).findById(anyLong());
		doReturn(adicional).when(repository).save(any());
		
		service.atualizarAdicional(adicional.getId(), esperado);
		assertEquals(esperado.getNome(), adicional.getNome());
		assertEquals(esperado.getDescricao(), adicional.getDescricao());
	}
	
	@Test
	public void deve_desativar_adicional() throws Exception {
		var auditoria = AuditoriaUtils.geraAuditoriaInsercao(Optional.empty());
		auditoria.setFlagAtivo("S");
		var adicional = new Adicional(1l, "Teste", "Teste", BigDecimal.valueOf(20), 1l,  auditoria);
		doReturn(Optional.ofNullable(adicional)).when(repository).findById(anyLong());
		doReturn(adicional).when(repository).save(any());
		
		service.desativarAdicional(adicional.getId());
		
		assertFalse(adicional.getAuditoria().isAtivo());
	}

}
