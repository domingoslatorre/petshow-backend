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
				add(Adicional.builder().id(1l).servicoDetalhadoId(1l).nome("Teste").descricao("Teste").build());
				add(Adicional.builder().id(2l).servicoDetalhadoId(1l).nome("Teste2").descricao("Teste2").build());
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

		var adicional = Adicional.builder().nome("Teste").descricao("Teste").servicoDetalhadoId(1l).build();
		service.criarAdicional(adicional);

		assertTrue(mockDb.contains(adicional));
	}

	@Test
	public void deve_adicionar_auditoria_para_adicional() {
		var adicional = Adicional.builder().nome("Teste").descricao("Teste").servicoDetalhadoId(1l).build();
		doReturn(adicional).when(repository).save(any());

		service.criarAdicional(adicional);

		assertNotNull(adicional.getAuditoria());
	}

	@Test
	public void deve_adicionar_auditoria_ativa() {
		var adicional = Adicional.builder().nome("Teste").descricao("Teste").servicoDetalhadoId(1l).build();
		doReturn(adicional).when(repository).save(any());

		service.criarAdicional(adicional);

		assertTrue(adicional.getAuditoria().isAtivo());
	}

	@Test
	public void deve_retornar_por_id() throws Exception {
		var adicional = Adicional.builder().id(1l).nome("Teste").descricao("Teste").servicoDetalhadoId(1l).build();
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
		var adicional = Adicional.builder().id(1l).nome("Teste").descricao("Teste")
						.servicoDetalhadoId(1l).preco(BigDecimal.valueOf(20))
						.auditoria(AuditoriaUtils.geraAuditoriaInsercao(Optional.empty())).build();
		var esperado = Adicional.builder().id(1l).nome("Teste2").descricao("Teste2")
						.servicoDetalhadoId(1l).preco(BigDecimal.valueOf(20)).build();
		
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
		var adicional = Adicional.builder().id(1l).nome("Teste").descricao("Teste")
				.servicoDetalhadoId(1l).preco(BigDecimal.valueOf(20))
				.auditoria(auditoria).build();
		doReturn(Optional.ofNullable(adicional)).when(repository).findById(anyLong());
		doReturn(adicional).when(repository).save(any());
		
		service.desativarAdicional(adicional.getId());
		
		assertFalse(adicional.getAuditoria().isAtivo());
	}

}
