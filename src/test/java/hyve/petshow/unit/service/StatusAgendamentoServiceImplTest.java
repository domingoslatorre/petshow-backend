package hyve.petshow.unit.service;

import hyve.petshow.domain.ServicoDetalhado;
import hyve.petshow.repository.StatusAgendamentoRepository;
import hyve.petshow.service.port.StatusAgendamentoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.MockitoAnnotations.initMocks;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class StatusAgendamentoServiceImplTest {
    @Mock
    private StatusAgendamentoRepository repository;
    @InjectMocks
    private StatusAgendamentoService service;

    @BeforeEach
    public void init() {
        initMocks(this);

        doReturn(Optional.of(servicoDetalhado)).when(repository).findById(1L);
        doReturn(servicoDetalhadoList).when(repository).findAll();
        doReturn(servicoDetalhadoPage).when(repository).findByPrestadorId(anyLong(), any(Pageable.class));
        doReturn(servicoDetalhadoPage).when(repository).findByTipo(anyInt(), any(Pageable.class));
        doReturn(Optional.of(servicoDetalhado)).when(repository).findByIdAndPrestadorId(anyLong(), anyLong());
        doNothing().when(repository).delete(any(ServicoDetalhado.class));
    }

    @Test
    public void deve_retornar_status_agendamento(){

    }
}