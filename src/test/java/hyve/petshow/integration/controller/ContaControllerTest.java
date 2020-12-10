package hyve.petshow.integration.controller;

import static hyve.petshow.mock.ContaMock.contaPrestador;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URI;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

import hyve.petshow.domain.Prestador;
import hyve.petshow.repository.GenericContaRepository;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class ContaControllerTest {
	@LocalServerPort
	private int port;
	@Autowired
	private TestRestTemplate template;
	@Autowired
	private GenericContaRepository repository;

	private Prestador prestador;
	private String url;

	@BeforeEach
	public void init() {
		prestador = new Prestador(contaPrestador());
		prestador.setId(null);

		url = "http://localhost:" + port + "/conta/";
	}

	@AfterEach
	public void limpaRepositorio() {
		repository.deleteAll();
	}

	@Test
	public void deve_retornar_por_id() throws Exception {
		repository.save(prestador);
		var uri = new URI(url + prestador.getId());
		var response = template.getForEntity(uri, String.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	public void deve_retornar_erro_em_busca_por_id() throws Exception {
		var uri = new URI(url + "1000");
		var response = template.getForEntity(uri, String.class);
		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
	}

}
