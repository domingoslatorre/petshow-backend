package hyve.petshow.unit.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.URI;
import java.net.URISyntaxException;

import hyve.petshow.mock.ServicoMock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import hyve.petshow.controller.representation.ServicoRepresentation;
import hyve.petshow.repository.ServicoRepository;

@TestMethodOrder(OrderAnnotation.class)
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ServicoControllerTest {
	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate template;

	@Autowired
	private ServicoRepository repository;

	private String url;

	@BeforeEach
	public void init() {
		url = "http://localhost:" + port + "/servicos";
	}

	public ServicoRepresentation initMock() {
		 var servicoMock = ServicoMock.criarServicoRepresentation();
		 return servicoMock;
	}

	@Test
	@Order(1)
	public void deve_salvar_servico() throws URISyntaxException {
		URI uri = new URI(this.url);
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<ServicoRepresentation> request = new HttpEntity<>(this.initMock(), headers);

		ResponseEntity<ServicoRepresentation> response = template.postForEntity(uri, request, ServicoRepresentation.class);

		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertTrue(repository.existsById(response.getBody().getId()));
	}

	@Test
	@Order(2)
	public void deve_retornar_erro_por_nome_repetido() throws URISyntaxException {
		URI uri = new URI(this.url);
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<ServicoRepresentation> request = new HttpEntity<>(this.initMock(), headers);

		ResponseEntity<String> response = template.postForEntity(uri, request, String.class);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertNotNull(response.getBody());
	}




	@Test
	@Order(3)
	public void deve_retornar_excecao() throws URISyntaxException {
		URI uri = new URI(this.url);
		ServicoRepresentation servicoMock = new ServicoRepresentation();

		HttpEntity<ServicoRepresentation> request = new HttpEntity<>(this.initMock(), new HttpHeaders());

		ResponseEntity<String> response = template.postForEntity(uri, request, String.class);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

	}

}
