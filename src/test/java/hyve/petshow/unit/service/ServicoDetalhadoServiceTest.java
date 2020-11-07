package hyve.petshow.unit.service;

import hyve.petshow.controller.representation.MensagemRepresentation;
import hyve.petshow.domain.ServicoDetalhado;
import hyve.petshow.exceptions.BusinessException;
import hyve.petshow.exceptions.NotFoundException;
import hyve.petshow.repository.ServicoDetalhadoRepository;
import hyve.petshow.service.implementation.ServicoDetalhadoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static hyve.petshow.mock.ServicoDetalhadoMock.servicoDetalhado;
import static hyve.petshow.mock.ServicoDetalhadoMock.servicoDetalhadoList;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;


@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class ServicoDetalhadoServiceTest {
	@Mock
	private ServicoDetalhadoRepository repository;

	@InjectMocks
	private ServicoDetalhadoServiceImpl service;

	private ServicoDetalhado servicoDetalhado = servicoDetalhado();
	private List<ServicoDetalhado> servicoDetalhadoList = singletonList(servicoDetalhado);

    @BeforeEach
    public void init() {
		initMocks(this);

		doReturn(Optional.of(servicoDetalhado)).when(repository).findById(1L);
		doReturn(servicoDetalhadoList).when(repository).findAll();
		doReturn(servicoDetalhadoList).when(repository).findByPrestadorId(Mockito.anyLong());
		doReturn(servicoDetalhadoList).when(repository).findByTipo(anyInt());
		doReturn(Optional.of(servicoDetalhado)).when(repository).findByIdAndPrestadorId(Mockito.anyLong(), Mockito.anyLong());
		doNothing().when(repository).delete(any(ServicoDetalhado.class));
	}
    
    @Test
	public void deve_inserir_servico_detalhado() {
    	doReturn(servicoDetalhado).when(repository).save(servicoDetalhado);

		var actual = service.adicionarServicoDetalhado(servicoDetalhado);
		
		assertAll(() -> assertTrue(repository.findAll().contains(actual)),
				  () -> assertNotNull(actual));
	}
    
    @Test
	public void deve_atualizar_servico_detalhado() throws Exception {
		var servicoDetalhadoRequest = servicoDetalhado;
		servicoDetalhadoRequest.setPreco(BigDecimal.valueOf(100L));

		doReturn(servicoDetalhadoRequest).when(repository).save(servicoDetalhadoRequest);

		var actual = service.atualizarServicoDetalhado(1L, servicoDetalhadoRequest);

		assertEquals(actual.getPreco(), servicoDetalhado.getPreco());
	}

	@Test
	public void deve_retornar_mensagem_sucesso_ao_remover_elemento() throws Exception {
		var mensagem = service.removerServicoDetalhado(1L, 1L);

		doReturn(FALSE).when(repository).existsById(1L);

		assertAll(
				() -> assertEquals(MensagemRepresentation.MENSAGEM_SUCESSO, mensagem.getMensagem()),
				() -> assertTrue(mensagem.getSucesso()));
	}

	@Test
	public void deve_retornar_por_tipo() throws NotFoundException {
		var lista = service.buscarServicosDetalhadosPorTipoServico(1);

		assertFalse(lista.isEmpty());
	}
	
	@Test
	public void deve_retornar_excecao_em_busca_por_tipo_nao_encontrada() {
		doReturn(emptyList()).when(repository).findByTipo(anyInt());

		assertThrows(NotFoundException.class, () -> service.buscarServicosDetalhadosPorTipoServico(1));
	}
	
	@Test
	public void deve_retornar_excecao_nao_encontrado() {
		doReturn(Optional.empty()).when(repository).findById(anyLong());

		assertThrows(NotFoundException.class, () -> service.buscarPorId(1L));
	}
	
	@Test
	public void deve_retornar_por_id_prestador() throws NotFoundException {
		var lista = service.buscarPorPrestadorId(1L);

		assertFalse(lista.isEmpty());
	}
	
	@Test
	public void deve_retornar_erro_por_nao_encontrado() {
		doReturn(emptyList()).when(repository).findByPrestadorId(Mockito.anyLong());
		
		assertThrows(NotFoundException.class, () -> service.buscarPorPrestadorId(1L));
	}
	
	@Test
	public void deve_retornar_servico_por_prestador_e_id() throws NotFoundException {
		var servico = service.buscarPorPrestadorEId(1L, 1L);

		assertNotNull(servico);
	}
	
	@Test
	public void deve_retornar_excecao_por_nao_encontrar_por_id_e_prestador() {
		doReturn(Optional.empty()).when(repository).findByIdAndPrestadorId(Mockito.anyLong(), Mockito.anyLong());
		
		assertThrows(NotFoundException.class, () -> service.buscarPorPrestadorEId(1L, 1L));
	}
	
	@Test
	public void deve_retornar_excecao_por_donos_diferentes_em_atualizacao() {
		var servicoRequest = servicoDetalhado();
		servicoRequest.setPrestadorId(2L);

		assertThrows(BusinessException.class, () -> service.atualizarServicoDetalhado(1L, servicoRequest));
	}
	
	@Test
	public void deve_retornar_excecao_por_donos_diferentes_em_delecao() {
		assertThrows(BusinessException.class, () -> service.removerServicoDetalhado(1L, 2L));
	}
	
	@Test
	public void deve_retornar_mensagem_de_falha_em_delecao() throws BusinessException, NotFoundException {
		doReturn(TRUE).when(repository).existsById(Mockito.anyLong());

		var mensagem = service.removerServicoDetalhado(servicoDetalhado.getId(), servicoDetalhado.getPrestadorId());

		assertEquals(MensagemRepresentation.MENSAGEM_FALHA, mensagem.getMensagem());
	}
}
