package hyve.petshow.integration;

import hyve.petshow.controller.representation.AgendamentoRepresentation;
import hyve.petshow.domain.Agendamento;
import hyve.petshow.facade.AgendamentoFacade;
import hyve.petshow.repository.AgendamentoRepository;
import hyve.petshow.repository.AvaliacaoRepository;
import hyve.petshow.repository.StatusAgendamentoRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import static hyve.petshow.mock.AgendamentoMock.criaAgendamento;
import static hyve.petshow.mock.AgendamentoMock.criaAgendamentoRepresentation;

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
