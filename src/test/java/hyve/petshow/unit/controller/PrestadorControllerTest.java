package hyve.petshow.unit.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.URI;
import java.net.URISyntaxException;

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
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;

import hyve.petshow.controller.representation.ContaRepresentation;
import hyve.petshow.domain.Login;
import hyve.petshow.repository.PrestadorRepository;

@TestMethodOrder(OrderAnnotation.class)
@ActiveProfiles("test")
@DirtiesContext(classMode = ClassMode.BEFORE_CLASS)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class PrestadorControllerTest {
	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate template;

	@Autowired
	private PrestadorRepository repository;

	private ContaRepresentation contaMock;

	private String url;

	@BeforeEach
	public void init() {
		url = "http://localhost:" + port + "/prestador";

	}

	@BeforeEach
	public void initMock() {
		contaMock = new ContaRepresentation();
		Login login = new Login();
		login.setEmail("teste@teste.com");
		login.setSenha("555555555555");
		contaMock.setLogin(login);
		contaMock.setCpf("44444444444");
	}

	@Test
	@Order(1)
	public void deve_salvar_conta() throws URISyntaxException {
		URI uri = new URI(this.url);
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<ContaRepresentation> request = new HttpEntity<>(contaMock, headers);

		ResponseEntity<ContaRepresentation> response = template.postForEntity(uri, request, ContaRepresentation.class);

		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertEquals(response.getBody().getLogin(), contaMock.getLogin());
		assertTrue(repository.existsById(response.getBody().getId()));
	}

	@Test
	@Order(2)
	public void deve_retornar_erro_por_email_repetido() throws URISyntaxException {
		URI uri = new URI(this.url);
		contaMock.setCpf("632478509");
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<ContaRepresentation> request = new HttpEntity<>(contaMock, headers);

		ResponseEntity<String> response = template.postForEntity(uri, request, String.class);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertNotNull(response.getBody());
	}

	@Test
	@Order(5)
	public void deve_retornar_nao_encontrado() throws URISyntaxException {
		URI uri = new URI(this.url + "/login");

		Login login = new Login();
		login.setEmail("aslkdjgs@aklsdjg.com");
		login.setSenha("ASDOHIGJKLAjh0oiq");

		HttpEntity<Login> request = new HttpEntity<>(login, new HttpHeaders());

		ResponseEntity<String> response = template.postForEntity(uri, request, String.class);
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		assertNotNull(response.getBody());
	}

	@Test
	@Order(6)
	public void deve_retornar_excecao() throws URISyntaxException {
		URI uri = new URI(this.url);
		ContaRepresentation contaMock = new ContaRepresentation();

		HttpEntity<ContaRepresentation> request = new HttpEntity<>(contaMock, new HttpHeaders());

		ResponseEntity<String> response = template.postForEntity(uri, request, String.class);
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());

	}
}