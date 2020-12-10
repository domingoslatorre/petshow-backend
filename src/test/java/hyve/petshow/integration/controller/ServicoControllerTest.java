package hyve.petshow.integration.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

import hyve.petshow.domain.Servico;
import hyve.petshow.repository.ServicoRepository;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class ServicoControllerTest {
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
