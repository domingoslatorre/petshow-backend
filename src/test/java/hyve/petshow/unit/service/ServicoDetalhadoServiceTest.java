package hyve.petshow.unit.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import hyve.petshow.controller.representation.MensagemRepresentation;
import hyve.petshow.domain.ServicoDetalhado;
import hyve.petshow.exceptions.BusinessException;
import hyve.petshow.exceptions.NotFoundException;
import hyve.petshow.mock.ServicoDetalhadoMock;
import hyve.petshow.repository.ServicoDetalhadoRepository;
import hyve.petshow.service.implementation.ServicoDetalhadoServiceImpl;


@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@TestMethodOrder(OrderAnnotation.class)

public class ServicoDetalhadoServiceTest {

	@Mock
	private ServicoDetalhadoRepository repository;
	@InjectMocks
	private ServicoDetalhadoServiceImpl service;
    
    @BeforeEach
    public void init() {
		MockitoAnnotations.initMocks(this);
		
		Mockito.when(repository.findAll()).thenReturn(ServicoDetalhadoMock.findAll());
		
		Mockito.when(repository.save(Mockito.any(ServicoDetalhado.class))).then(mock -> {
			ServicoDetalhado servicoDetalhado = mock.getArgument(0);
			if (servicoDetalhado.getId() == null) {
				ServicoDetalhadoMock.save(servicoDetalhado);
			} else {
				ServicoDetalhadoMock.atualizarServicoDetalhado(servicoDetalhado);
			}
			return servicoDetalhado;
		});

		
		Mockito.when(repository.findById(Mockito.anyLong())).then(mock -> ServicoDetalhadoMock.findById(mock.getArgument(0)));
		
		Mockito.doAnswer(mock -> {
			ServicoDetalhadoMock.removerPorId(mock.getArgument(0));
			return null;
		}).when(repository).deleteById(Mockito.anyLong());
	}
    
    @Test
	@Order(1)
	public void deve_inserir_servico_detalhado_na_lista() throws Exception {
		var servicoDetalhado = ServicoDetalhadoMock.criarServicoDetalhado();
    	servicoDetalhado.setId(null);
		service.adicionarServicoDetalhado(servicoDetalhado);
		
		assertTrue(repository.findAll().contains(servicoDetalhado));
		
		assertNotNull(servicoDetalhado.getId());
	}
    
    @Test
	@Order(2)
	public void deve_atualizar_servico_detalhado() throws Exception {
		ServicoDetalhado servicoDetalhadoAAlterar = new ServicoDetalhado();
		BigDecimal p = new BigDecimal(800);
		servicoDetalhadoAAlterar.setId(1L);
		servicoDetalhadoAAlterar.setPreco(p);
		servicoDetalhadoAAlterar.setPrestadorId(1l);
		ServicoDetalhado servicoDetalhadoDb = (ServicoDetalhado) service.atualizarServicoDetalhado(servicoDetalhadoAAlterar.getId(), servicoDetalhadoAAlterar);
		assertEquals(p,servicoDetalhadoDb.getPreco());
		assertEquals(servicoDetalhadoDb.getId(), servicoDetalhadoAAlterar.getId());
	}

	@Test
	@Order(3)
	public void deve_remover_elemento() throws Exception {
		service.removerServicoDetalhado(1L, 1L);
		assertFalse(repository.findById(1L).isPresent());
	}
	
	@Test
	@Order(4)
	public void deve_retornar_mensagem_sucesso() throws Exception {
		
		MensagemRepresentation removerServicoDetalhado = service.removerServicoDetalhado(2L, 1L);
		assertEquals(MensagemRepresentation.MENSAGEM_SUCESSO, removerServicoDetalhado.getMensagem());
		assertTrue(removerServicoDetalhado.getSucesso());
	}
	
	
	@Test
	public void deve_retornar_por_tipo() throws NotFoundException {
		Mockito.when(repository.findByTipo(Mockito.anyInt())).thenReturn(Arrays.asList(ServicoDetalhadoMock.criarServicoDetalhado()));
		var lista = service.buscarServicosDetalhadosPorTipoServico(1);
		assertFalse(lista.isEmpty());
	}
	
	@Test
	public void deve_retornar_excecao_em_busca_por_tipo_nao_encontrada() {
		Mockito.when(repository.findByTipo(Mockito.anyInt())).thenReturn(new ArrayList<>());
		assertThrows(NotFoundException.class, () -> {
			service.buscarServicosDetalhadosPorTipoServico(1);
		});
	}
	
	@Test
	public void deve_retornar_excecao_nao_encontrado() {
		Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
		
		assertThrows(NotFoundException.class, () -> {
			service.buscarPorId(1l);
		});
	}
	
	@Test
	public void deve_retornar_por_id_prestador() throws NotFoundException {
		Mockito.when(repository.findByPrestadorId(Mockito.anyLong())).thenReturn(Arrays.asList(ServicoDetalhadoMock.criarServicoDetalhado()));
		var lista = service.buscarPorPrestadorId(1l);
		assertFalse(lista.isEmpty());
	}
	
	@Test
	public void deve_retornar_erro_por_nao_encontrado() {
		Mockito.when(repository.findByPrestadorId(Mockito.anyLong())).thenReturn(new ArrayList<>());
		
		assertThrows(NotFoundException.class, () -> {
			service.buscarPorPrestadorId(1l);
		});
	}
	
	@Test
	public void deve_retornar_servico_por_prestador_e_id() throws NotFoundException {
		Mockito.when(repository.findByIdAndPrestadorId(Mockito.anyLong(), Mockito.anyLong())).thenReturn(Optional.of(new ServicoDetalhado()));
		
		var servico = service.buscarPorPrestadorEId(1l, 1l);
		assertNotNull(servico);
	}
	
	@Test
	public void deve_retornar_excecao_por_nao_encontrar_por_id_e_prestador() {
		Mockito.when(repository.findByIdAndPrestadorId(Mockito.anyLong(), Mockito.anyLong())).thenReturn(Optional.empty());
		
		assertThrows(NotFoundException.class, () -> {
			service.buscarPorPrestadorEId(1l, 1l);
		});
	}
	
	@Test
	public void deve_retornar_excecao_por_donos_diferentes_em_atualizacao() {
		var servicoRequest = new ServicoDetalhado();
		var servicoDb = new ServicoDetalhado();
		servicoRequest.setPrestadorId(1l);
		servicoDb.setPrestadorId(2l);
		
		Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(Optional.of(servicoDb));
		
		assertThrows(BusinessException.class, () -> {
			service.atualizarServicoDetalhado(1l, servicoRequest);
		});
	}
	
	@Test
	public void deve_retornar_excecao_por_donos_diferentes_em_delecao() {
		var servicoRequest = new ServicoDetalhado();
		var servicoDb = new ServicoDetalhado();
		servicoRequest.setPrestadorId(1l);
		servicoDb.setPrestadorId(2l);
		
		Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(Optional.of(servicoDb));
		assertThrows(BusinessException.class, () -> {
			service.removerServicoDetalhado(1l, servicoRequest.getPrestadorId());
		});
	}
	
	@Test
	public void deve_retornar_mensagem_de_falha_em_delecao() throws BusinessException, NotFoundException {
		var servicoRequest = new ServicoDetalhado();
		servicoRequest.setPrestadorId(1l);
		Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(Optional.of(servicoRequest));
		Mockito.when(repository.existsById(Mockito.anyLong())).thenReturn(true);
		
		assertEquals(MensagemRepresentation.MENSAGEM_FALHA, service.removerServicoDetalhado(1l, servicoRequest.getPrestadorId()).getMensagem());
		
	}
	
	
	
}
