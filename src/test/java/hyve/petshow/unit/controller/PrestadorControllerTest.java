package hyve.petshow.unit.controller;

import hyve.petshow.controller.PrestadorController;
import hyve.petshow.controller.converter.PrestadorConverter;
import hyve.petshow.controller.representation.PrestadorRepresentation;
import hyve.petshow.domain.Prestador;
import hyve.petshow.service.port.PrestadorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;

import static hyve.petshow.mock.PrestadorMock.prestador;
import static hyve.petshow.mock.PrestadorMock.prestadorRepresentation;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doReturn;
import static org.mockito.MockitoAnnotations.initMocks;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class PrestadorControllerTest {
    @Mock
    private PrestadorService service;
    @Mock
    private PrestadorConverter converter;
    @InjectMocks
    private PrestadorController controller;

    private Prestador prestador = prestador();
    private PrestadorRepresentation prestadorRepresentation = prestadorRepresentation();

    @BeforeEach
    public void init() throws Exception {
        initMocks(this);

        doReturn(prestador).when(service).buscarPorId(anyLong());
        doReturn(prestador).when(service).atualizarConta(anyLong(), any(Prestador.class));
        doReturn(prestadorRepresentation).when(converter).toRepresentation(any(Prestador.class));
        doReturn(prestador).when(converter).toDomain(any(PrestadorRepresentation.class));
    }

    @Test
    public void deve_retornar_cliente_buscado() throws Exception {
        var expected = ResponseEntity.ok(prestadorRepresentation);

        var actual = controller.buscarPrestadorPorId(1L);

        assertEquals(expected, actual);
    }

    @Test
    public void deve_retornar_cliente_atualizado() throws Exception {
        var expected = ResponseEntity.ok(prestadorRepresentation);

        var actual = controller.atualizarPrestador(1L, prestadorRepresentation);

        assertEquals(expected, actual);
    }

}
