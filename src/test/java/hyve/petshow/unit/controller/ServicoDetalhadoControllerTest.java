package hyve.petshow.unit.controller;

import hyve.petshow.controller.ServicoDetalhadoController;
import hyve.petshow.controller.converter.ServicoDetalhadoConverter;
import hyve.petshow.controller.representation.AvaliacaoRepresentation;
import hyve.petshow.controller.representation.MensagemRepresentation;
import hyve.petshow.controller.representation.ServicoDetalhadoRepresentation;
import hyve.petshow.domain.Avaliacao;
import hyve.petshow.domain.Prestador;
import hyve.petshow.domain.ServicoDetalhado;
import hyve.petshow.exceptions.NotFoundException;
import hyve.petshow.facade.AvaliacaoFacade;
import hyve.petshow.service.port.ServicoDetalhadoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static hyve.petshow.mock.AvaliacaoMock.avaliacaoRepresentation;
import static hyve.petshow.mock.MensagemMock.mensagemRepresentationSucesso;
import static hyve.petshow.mock.PrestadorMock.prestador;
import static hyve.petshow.mock.ServicoDetalhadoMock.*;
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

    private MensagemRepresentation mensagemRepresentation = mensagemRepresentationSucesso();

    private AvaliacaoRepresentation avaliacaoRepresentation = avaliacaoRepresentation();

    @BeforeEach
    public void init() throws Exception {
        initMocks(this);

        doReturn(servicoDetalhadoList).when(service).buscarPorPrestadorId(anyLong());
        doReturn(servicoDetalhado).when(service).adicionarServicoDetalhado(any(ServicoDetalhado.class));
        doReturn(mensagemRepresentation).when(service).removerServicoDetalhado(anyLong(), anyLong());
        doReturn(servicoDetalhado).when(service).buscarPorPrestadorIdEServicoId(anyLong(), anyLong());
        doReturn(servicoDetalhado).when(service).atualizarServicoDetalhado(anyLong(), anyLong(), any(ServicoDetalhado.class));
        doReturn(servicoDetalhadoList).when(service).buscarServicosDetalhadosPorTipoServico(anyInt());
        doReturn(servicoDetalhado).when(converter).toDomain(any(ServicoDetalhadoRepresentation.class));
        doReturn(servicoDetalhadoRepresentation).when(converter).toRepresentation(any(ServicoDetalhado.class));
        doReturn(servicoDetalhadoRepresentationList).when(converter).toRepresentationList(anyList());
        doNothing().when(avaliacaoFacade).adicionarAvaliacao(any(AvaliacaoRepresentation.class), anyLong(), anyLong());
    }

    @Test
    public void deve_retornar_todos_os_servicos_detalhados_de_um_prestador() throws Exception {
        var expected = ResponseEntity.ok(servicoDetalhadoRepresentationList);

        var actual = controller.buscarServicosDetalhadosPorPrestador(1L);

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

        var actual = controller.buscarServicoDetalhadoPorPrestador(1L, 1L);

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
        var expected = ResponseEntity.ok(servicoDetalhadoRepresentationList);

        var actual = controller.buscarServicosDetalhadosPorTipoServico(1);

        assertEquals(expected, actual);
    }
}
