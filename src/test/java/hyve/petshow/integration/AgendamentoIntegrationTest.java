package hyve.petshow.integration;

import hyve.petshow.controller.converter.AgendamentoConverter;
import hyve.petshow.controller.converter.AvaliacaoConverter;
import hyve.petshow.controller.converter.StatusAgendamentoConverter;
import hyve.petshow.controller.representation.AgendamentoRepresentation;
import hyve.petshow.domain.Agendamento;
import hyve.petshow.domain.Cliente;
import hyve.petshow.facade.AgendamentoFacade;
import hyve.petshow.facade.AvaliacaoFacade;
import hyve.petshow.repository.AgendamentoRepository;
import hyve.petshow.repository.AvaliacaoRepository;
import hyve.petshow.repository.StatusAgendamentoRepository;
import hyve.petshow.service.port.AgendamentoService;
import hyve.petshow.service.port.AvaliacaoService;
import hyve.petshow.service.port.StatusAgendamentoService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

import java.net.URI;

import static hyve.petshow.mock.AgendamentoMock.criaAgendamento;
import static hyve.petshow.mock.AgendamentoMock.criaAgendamentoRepresentation;
import static hyve.petshow.mock.ContaMock.contaCliente;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class AgendamentoIntegrationTest {
    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate template;
    @Autowired
    private AgendamentoRepository agendamentoRepository;
    @Autowired
    private StatusAgendamentoRepository statusAgendamentoRepository;
    @Autowired
    private AvaliacaoRepository avaliacaoRepository;
    @Autowired
    private AgendamentoFacade agendamentoFacade;

    private String url;
    private Agendamento agendamento;
    private AgendamentoRepresentation agendamentoRepresentation;

    @BeforeEach
    public void init() {
        agendamento = criaAgendamento();
        agendamento.setId(null);
        agendamentoRepresentation = criaAgendamentoRepresentation();

        url = "http://localhost:" + port + "/agendamento";
    }

    @AfterEach
    public void limpaRepositorios() {
        agendamentoRepository.deleteAll();
        statusAgendamentoRepository.deleteAll();
        avaliacaoRepository.deleteAll();
    }

    /*@Test
    public void deve_adicionar_agendamento()  {
        var headers = new HttpHeaders();

        var requestBody = new HttpEntity<>(agendamentoRepresentation, headers);
        var response = template.postForEntity(this.url, requestBody, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }*/
}
