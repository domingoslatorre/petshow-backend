package hyve.petshow.unit.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import hyve.petshow.mock.ServicoDetalhadoMock;

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
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;


import hyve.petshow.controller.representation.ServicoDetalhadoRepresentation;
import hyve.petshow.repository.ServicoDetalhadoRepository;

@TestMethodOrder(OrderAnnotation.class)
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ServicoDetalhadoControllerTest {
	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate template;

	@Autowired
	private ServicoDetalhadoRepository repository;

	private ServicoDetalhadoRepresentation servicoDetalhadoMock;
	private List<ServicoDetalhadoRepresentation> servicoDetalhadoMockList;
	
	private String url;

	@BeforeEach
	public void init() {
		url = "http://localhost:" + port + "/prestador/servicos";

	}

	@BeforeEach
	public void initMock() {
		servicoDetalhadoMock = ServicoDetalhadoMock.criarServicoDetalhadoRepresentation();
		servicoDetalhadoMockList = Arrays.asList(servicoDetalhadoMock);
	}

	@Test
	@Order(1)
	public void deve_salvar_servicos_detalhados() throws URISyntaxException {
		URI uri = new URI(this.url);
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<List<ServicoDetalhadoRepresentation>> request = new HttpEntity<>(servicoDetalhadoMockList, headers);

		ResponseEntity<ServicoDetalhadoRepresentation> response = template.postForEntity(uri, request, ServicoDetalhadoRepresentation.class);

		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertTrue(repository.existsById(response.getBody().getId()));
	}




//	@Test
//	@Order(4)
//	public void deve_retornar_por_prestador() throws URISyntaxException {
//		URI uri = new URI(this.url + "/login");
//		Login login = servicoDetalhadoMock.getLogin();
//
//		HttpEntity<Login> request = new HttpEntity<>(login, new HttpHeaders());
//
//		ResponseEntity<ServicoDetalhadoRepresentation> response = template.postForEntity(uri, request, ServicoDetalhadoRepresentation.class);
//		assertEquals(HttpStatus.OK, response.getStatusCode());
//		assertNotNull(response.getBody());
//	}

//	@Test
//	@Order(5)
//	public void deve_retornar_nao_encontrado() throws URISyntaxException {
//		URI uri = new URI(this.url + "/login");
//
//		Login login = new Login();
//		login.setEmail("aslkdjgs@aklsdjg.com");
//		login.setSenha("ASDOHIGJKLAjh0oiq");
//
//		HttpEntity<Login> request = new HttpEntity<>(login, new HttpHeaders());
//
//		ResponseEntity<String> response = template.postForEntity(uri, request, String.class);
//		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
//		assertNotNull(response.getBody());
//	}

	@Test
	@Order(6)
	public void deve_retornar_excecao() throws URISyntaxException {
		URI uri = new URI(this.url);
		ServicoDetalhadoRepresentation servicoDetalhadoMock = new ServicoDetalhadoRepresentation();

		HttpEntity<ServicoDetalhadoRepresentation> request = new HttpEntity<>(servicoDetalhadoMock, new HttpHeaders());

		ResponseEntity<String> response = template.postForEntity(uri, request, String.class);
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());

	}

	
}
