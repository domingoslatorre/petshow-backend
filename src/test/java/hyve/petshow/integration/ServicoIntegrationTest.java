package hyve.petshow.integration;

import hyve.petshow.domain.Servico;
import hyve.petshow.repository.ServicoRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Disabled("Destativado enquanto não funcionar em PRD")
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class ServicoIntegrationTest {
	@LocalServerPort
	private int port;
	@Autowired
	private TestRestTemplate template;
	@Autowired
	private ServicoRepository repository;
	private String url;
	private Servico servico;

	@BeforeEach
	public void init() {
		url = "http://localhost:" + port + "/servico";
		servico = new Servico();
		servico.setNome("Banho");
	}

	@AfterEach
	public void limpaRepository() {
		repository.deleteAll();
	}

	@Test
	public void deve_retornar_lista_de_servicos() {
		repository.save(servico);

		var response = template.getForEntity(url, String.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	public void deve_retornar_erro_de_lista_vazia() {
		var response = template.getForEntity(url, String.class);

		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
	}

}
