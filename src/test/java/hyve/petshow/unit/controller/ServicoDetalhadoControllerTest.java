package hyve.petshow.unit.controller;

import static hyve.petshow.mock.MensagemMock.mensagemRepresentationSucesso;
import static hyve.petshow.mock.ServicoDetalhadoMock.servicoDetalhado;
import static hyve.petshow.mock.ServicoDetalhadoMock.servicoDetalhadoList;
import static hyve.petshow.mock.ServicoDetalhadoMock.servicoDetalhadoRepresentation;
import static hyve.petshow.mock.ServicoDetalhadoMock.servicoDetalhadoRepresentationList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.MockitoAnnotations.initMocks;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import hyve.petshow.controller.ServicoDetalhadoController;
import hyve.petshow.controller.converter.ServicoDetalhadoConverter;
import hyve.petshow.controller.representation.AdicionalRepresentation;
import hyve.petshow.controller.representation.ComparacaoWrapper;
import hyve.petshow.controller.representation.MensagemRepresentation;
import hyve.petshow.controller.representation.ServicoDetalhadoRepresentation;
import hyve.petshow.domain.ServicoDetalhado;
import hyve.petshow.exceptions.NotFoundException;
import hyve.petshow.facade.AvaliacaoFacade;
import hyve.petshow.facade.ServicoDetalhadoFacade;
import hyve.petshow.service.port.ServicoDetalhadoService;

public class ServicoDetalhadoControllerTest {
	@Mock
	private ServicoDetalhadoService service;
	@Mock
	private ServicoDetalhadoConverter converter;
	@Mock
	private AvaliacaoFacade avaliacaoFacade;
	@Mock
	private ServicoDetalhadoFacade servicoDetalhadoFacade;
	@InjectMocks
	private ServicoDetalhadoController controller;

	private ServicoDetalhado servicoDetalhado = servicoDetalhado();
	private ServicoDetalhadoRepresentation servicoDetalhadoRepresentation = servicoDetalhadoRepresentation();

	private List<ServicoDetalhado> servicoDetalhadoList = servicoDetalhadoList();
	private List<ServicoDetalhadoRepresentation> servicoDetalhadoRepresentationList = servicoDetalhadoRepresentationList();
	private Page<ServicoDetalhado> servicoDetalhadoPage = new PageImpl<>(servicoDetalhadoList);
	private Page<ServicoDetalhadoRepresentation> servicoDetalhadoRepresentationPage = new PageImpl<>(
			servicoDetalhadoRepresentationList);

	private MensagemRepresentation mensagemRepresentation = mensagemRepresentationSucesso();

	@BeforeEach
	public void init() throws Exception {
		initMocks(this);

		doReturn(servicoDetalhadoPage).when(service).buscarPorPrestadorId(anyLong(), any(Pageable.class));
		doReturn(servicoDetalhado).when(service).adicionarServicoDetalhado(any(ServicoDetalhado.class));
		doReturn(mensagemRepresentation).when(service).removerServicoDetalhado(anyLong(), anyLong());
		doReturn(servicoDetalhado).when(service).buscarPorPrestadorIdEServicoId(anyLong(), anyLong());
		doReturn(servicoDetalhado).when(service).atualizarServicoDetalhado(anyLong(), anyLong(),
				any(ServicoDetalhado.class));
		doReturn(servicoDetalhadoPage).when(service).buscarServicosDetalhadosPorTipoServico(anyInt(),
				any(Pageable.class));
		doReturn(servicoDetalhado).when(converter).toDomain(any(ServicoDetalhadoRepresentation.class));
		doReturn(servicoDetalhadoRepresentation).when(converter).toRepresentation(any(ServicoDetalhado.class));
		doReturn(servicoDetalhadoRepresentationList).when(converter).toRepresentationList(anyList());
		doReturn(servicoDetalhadoRepresentationPage).when(converter).toRepresentationPage(any(Page.class));
		doReturn(servicoDetalhadoRepresentation).when(servicoDetalhadoFacade).buscarPorPrestadorIdEServicoId(anyLong(),
				anyLong());
		doReturn(servicoDetalhadoRepresentationPage).when(servicoDetalhadoFacade)
				.buscarServicosDetalhadosPorTipoServico(anyInt(), any(Pageable.class));
	}

	@Test
	public void deve_retornar_todos_os_servicos_detalhados_de_um_prestador() throws Exception {
		var expected = ResponseEntity.ok(servicoDetalhadoRepresentationPage);

		var actual = controller.buscarServicosDetalhadosPorPrestador(1L, 0, 5);

		assertEquals(expected, actual);
	}

	@Test
	public void deve_adicionar_e_retornar_servico_detalhado() {
		var expected = ResponseEntity.status(HttpStatus.CREATED).body(servicoDetalhadoRepresentation);

		var actual = controller.adicionarServicoDetalhado(1L, servicoDetalhadoRepresentation);

		assertEquals(expected, actual);
	}

	@Test
	public void deve_remover_servico_detalhado() throws Exception {
		var expected = ResponseEntity.ok(mensagemRepresentation);

		var actual = controller.removerServicoDetalhado(1L, 1L);

		assertEquals(expected, actual);
	}

	@Test
	public void deve_retornar_servico_detalhado() throws Exception {
		var expected = ResponseEntity.ok(servicoDetalhadoRepresentation);

		var actual = controller.buscarPorPrestadorIdEServicoId(1L, 1L);

		assertEquals(expected, actual);
	}

	@Test
	public void deve_atualizar_e_retornar_servico_detalhado() throws Exception {
		var expected = ResponseEntity.ok(servicoDetalhadoRepresentation);

		var actual = controller.atualizarServicoDetalhado(1L, 1L, servicoDetalhadoRepresentation);

		assertEquals(expected, actual);
	}

	@Test
	public void deve_retornar_todos_os_servicos_detalhados_de_um_tipo() throws Exception {
		var expected = ResponseEntity.ok(servicoDetalhadoRepresentationPage);

		var actual = controller.buscarServicosDetalhadosPorTipoServico(1, 0, 5);

		assertEquals(expected, actual);
	}

	@Test
	public void deve_retornar_todos_os_adicionais_de_um_servico() throws Exception {
		var adicionais = new ArrayList<AdicionalRepresentation>() {
			private static final long serialVersionUID = 1L;

			{
				add(AdicionalRepresentation.builder().id(1l).nome("Teste").servicoDetalhadoId(1l)
						.preco(BigDecimal.valueOf(10)).build());
				add(AdicionalRepresentation.builder().id(2l).nome("Teste").servicoDetalhadoId(1l)
						.preco(BigDecimal.valueOf(10)).build());
			}
		};
		var expected = ResponseEntity.ok(adicionais);

		doReturn(adicionais).when(servicoDetalhadoFacade).buscarAdicionais(anyLong(), anyLong());

		var busca = controller.buscarAdicionais(1l, 1l);

		assertEquals(expected, busca);
	}

	@Test
	public void deve_retornar_excecao_ao_nao_encontrar_servicos() throws Exception {
		doThrow(NotFoundException.class).when(servicoDetalhadoFacade).buscarAdicionais(anyLong(), anyLong());
		assertThrows(NotFoundException.class, () -> controller.buscarAdicionais(1l, 1l));
	}

	@Test
	public void deve_criar_novo_adicional() throws Exception {
		var adicionalTeste = AdicionalRepresentation.builder().id(1l).nome("Teste").servicoDetalhadoId(1l)
				.preco(BigDecimal.valueOf(23)).build();

		var dbMock = new ArrayList<AdicionalRepresentation>();
		Mockito.doAnswer(mock -> {
			var adicional = (AdicionalRepresentation) mock.getArgument(2);
			dbMock.add(adicional);
			return adicional;
		}).when(servicoDetalhadoFacade).criaAdicional(anyLong(), anyLong(), any());

		controller.criarAdicional(1l, 1l, adicionalTeste);

		assertEquals(1, dbMock.size());
	}

	@Test
	public void deve_retornar_lista_de_servicos_detalhados() {
		var servicosRepresentation = new ArrayList<ServicoDetalhadoRepresentation>() {
			private static final long serialVersionUID = 1L;

			{
				add(new ServicoDetalhadoRepresentation());
				add(new ServicoDetalhadoRepresentation());
			}
		};

		doReturn(servicosRepresentation).when(servicoDetalhadoFacade).buscarServicosDetalhadosPorIds(anyList());

		var idsServico = new ArrayList<Long>() {
			private static final long serialVersionUID = 1L;

			{
				add(1l);
				add(2l);
			}
		};
		assertEquals(HttpStatus.OK, controller.buscarServicosParaComparacao(idsServico).getStatusCode());
		assertEquals(new ComparacaoWrapper(servicosRepresentation), controller.buscarServicosParaComparacao(idsServico).getBody());
	}
}
