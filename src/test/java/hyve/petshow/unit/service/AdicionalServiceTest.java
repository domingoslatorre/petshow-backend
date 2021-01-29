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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

import hyve.petshow.domain.Adicional;
import hyve.petshow.exceptions.NotFoundException;
import static hyve.petshow.mock.AdicionalMock.criaAdicional;
import hyve.petshow.repository.AdicionalRepository;
import hyve.petshow.service.implementation.AdicionalServiceImpl;
import static hyve.petshow.util.AuditoriaUtils.geraAuditoriaInsercao;

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
				add(criaAdicional(1l));
				add(criaAdicional(2l));
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

		var adicional = criaAdicional(1l);
		service.criarAdicional(adicional,1l);

		assertTrue(mockDb.contains(adicional));
	}

	@Test
	public void deve_adicionar_auditoria_para_adicional() {
		var adicional = criaAdicional(1l);
		doReturn(adicional).when(repository).save(any());

		service.criarAdicional(adicional, 1l);

		assertNotNull(adicional.getAuditoria());
	}

	@Test
	public void deve_adicionar_auditoria_ativa() {
		var adicional = criaAdicional(1l);
		doReturn(adicional).when(repository).save(any());

		service.criarAdicional(adicional, 1l);

		assertTrue(adicional.getAuditoria().isAtivo());
	}

	@Test
	public void deve_retornar_por_id() throws Exception {
		var adicional = criaAdicional(1l);
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
		var adicional = criaAdicional(1l);
		adicional.setAuditoria(geraAuditoriaInsercao(Optional.empty()));
		var esperado = criaAdicional(1l);
		esperado.setNome("Teste2");
		esperado.setDescricao("Teste2");
		
		doReturn(Optional.ofNullable(adicional)).when(repository).findById(anyLong());
		doReturn(adicional).when(repository).save(any());
		
		service.atualizarAdicional(adicional.getId(), esperado);
		assertEquals(esperado.getNome(), adicional.getNome());
		assertEquals(esperado.getDescricao(), adicional.getDescricao());
	}
	
	@Test
	public void deve_desativar_adicional() throws Exception {
		var auditoria = geraAuditoriaInsercao(Optional.empty());
		auditoria.setFlagAtivo("S");
		var adicional = criaAdicional(1l);
		adicional.setAuditoria(auditoria);
		doReturn(Optional.ofNullable(adicional)).when(repository).findById(anyLong());
		doReturn(adicional).when(repository).save(any());
		
		service.desativarAdicional(adicional.getId());
		
		assertFalse(adicional.getAuditoria().isAtivo());
	}

}
