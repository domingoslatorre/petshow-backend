package hyve.petshow.unit.controller;

import hyve.petshow.controller.ServicoDetalhadoController;
import hyve.petshow.controller.converter.ServicoDetalhadoConverter;
import hyve.petshow.controller.representation.AvaliacaoRepresentation;
import hyve.petshow.controller.representation.MensagemRepresentation;
import hyve.petshow.controller.representation.ServicoDetalhadoRepresentation;
import hyve.petshow.domain.AnimalEstimacao;
import hyve.petshow.domain.ServicoDetalhado;
import hyve.petshow.exceptions.NotFoundException;
import hyve.petshow.facade.AvaliacaoFacade;
import hyve.petshow.service.port.ServicoDetalhadoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static hyve.petshow.mock.AvaliacaoMock.avaliacaoRepresentation;
import static hyve.petshow.mock.MensagemMock.mensagemRepresentationSucesso;
import static hyve.petshow.mock.ServicoDetalhadoMock.*;
import static hyve.petshow.util.PagingAndSortingUtils.geraPageable;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.MockitoAnnotations.initMocks;

public class ServicoDetalhadoControllerTest {
    @Mock
    private ServicoDetalhadoService service;
    @Mock
    private ServicoDetalhadoConverter converter;
    @Mock
    private AvaliacaoFacade avaliacaoFacade;
    @InjectMocks
    private ServicoDetalhadoController controller;

    private ServicoDetalhado servicoDetalhado = servicoDetalhado();
    private ServicoDetalhadoRepresentation servicoDetalhadoRepresentation = servicoDetalhadoRepresentation();

    private List<ServicoDetalhado> servicoDetalhadoList = servicoDetalhadoList();
    private List<ServicoDetalhadoRepresentation> servicoDetalhadoRepresentationList = servicoDetalhadoRepresentationList();
    private Page<ServicoDetalhado> servicoDetalhadoPage = new PageImpl<>(servicoDetalhadoList);
    private Page<ServicoDetalhadoRepresentation> servicoDetalhadoRepresentationPage = new PageImpl<>(servicoDetalhadoRepresentationList);

    private MensagemRepresentation mensagemRepresentation = mensagemRepresentationSucesso();

    private AvaliacaoRepresentation avaliacaoRepresentation = avaliacaoRepresentation();

    @BeforeEach
    public void init() throws Exception {
        initMocks(this);

        doReturn(servicoDetalhadoPage).when(service).buscarPorPrestadorId(anyLong(), any(Pageable.class));
        doReturn(servicoDetalhado).when(service).adicionarServicoDetalhado(any(ServicoDetalhado.class));
        doReturn(mensagemRepresentation).when(service).removerServicoDetalhado(anyLong(), anyLong());
        doReturn(servicoDetalhado).when(service).buscarPorPrestadorIdEServicoId(anyLong(), anyLong());
        doReturn(servicoDetalhado).when(service).atualizarServicoDetalhado(anyLong(), anyLong(), any(ServicoDetalhado.class));
        doReturn(servicoDetalhadoPage).when(service).buscarServicosDetalhadosPorTipoServico(anyInt(), any(Pageable.class));
        doReturn(servicoDetalhado).when(converter).toDomain(any(ServicoDetalhadoRepresentation.class));
        doReturn(servicoDetalhadoRepresentation).when(converter).toRepresentation(any(ServicoDetalhado.class));
        doReturn(servicoDetalhadoRepresentationList).when(converter).toRepresentationList(anyList());
        doReturn(servicoDetalhadoRepresentationPage).when(converter).toRepresentationPage(any(Page.class));
        doNothing().when(avaliacaoFacade).adicionarAvaliacao(any(AvaliacaoRepresentation.class), anyLong(), anyLong());
    }

    @Test
    public void deve_retornar_todos_os_servicos_detalhados_de_um_prestador() throws Exception {
        var expected = ResponseEntity.ok(servicoDetalhadoRepresentationPage);

        var actual = controller.buscarServicosDetalhadosPorPrestador(1L, 0, 5);

        assertEquals(expected, actual);
    }

    @Test
    public void deve_adicionar_e_retornar_servico_detalhado(){
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
    public void deve_adicionar_avaliacao_e_retornar_servico_avaliado() throws Exception {
        var expected = ResponseEntity.status(HttpStatus.CREATED).body(servicoDetalhadoRepresentation);

        var actual = controller.adicionarAvaliacao(1L, 1L, avaliacaoRepresentation);

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
    public void deve_retornar_todos_os_servicos_detalhados_de_um_tipo() throws NotFoundException {
        var expected = ResponseEntity.ok(servicoDetalhadoRepresentationPage);

        var actual = controller.buscarServicosDetalhadosPorTipoServico(1, 0, 5);

        assertEquals(expected, actual);
    }
}
