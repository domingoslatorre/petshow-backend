package hyve.petshow.unit.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

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

import hyve.petshow.controller.representation.AnimalEstimacaoRepresentation;
import hyve.petshow.controller.representation.ClienteRepresentation;
import hyve.petshow.controller.representation.ContaRepresentation;
import hyve.petshow.domain.Conta;
import hyve.petshow.domain.Login;
import hyve.petshow.repository.ClienteRepository;

@TestMethodOrder(OrderAnnotation.class)
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ClienteControllerTest {
	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate template;

	@Autowired
	private ClienteRepository repository;

	private ContaRepresentation contaMock;

	private String url;

	@BeforeEach
	public void init() {
		url = "http://localhost:" + port + "/cliente";

	}

	@BeforeEach
	public void initMock() {
		contaMock = new ContaRepresentation();
		Login login = new Login();
		login.setEmail("teste@teste.com");
		login.setSenha("teste1234");
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
	@Order(3)
	public void deve_retornar_erro_por_cpf_repetido() throws URISyntaxException {
		URI uri = new URI(this.url);
		Login login = new Login();
		login.setEmail("oqiwuefnajs");
		login.setSenha("834hufakvdsn");
		contaMock.setLogin(login);
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<ContaRepresentation> request = new HttpEntity<>(contaMock, headers);

		ResponseEntity<String> response = template.postForEntity(uri, request, String.class);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertNotNull(response.getBody());

	}

	@Test
	@Order(4)
	public void deve_retornar_por_login() throws URISyntaxException {
		URI uri = new URI(this.url + "/login");
		Login login = contaMock.getLogin();

		HttpEntity<Login> request = new HttpEntity<>(login, new HttpHeaders());

		ResponseEntity<ClienteRepresentation> response = template.postForEntity(uri, request, ClienteRepresentation.class);
		assertEquals(HttpStatus.OK, response.getStatusCode());
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

	@Test
	@Order(7)
	public void deve_adicionar_animal() throws URISyntaxException {
		URI uri = new URI(this.url);

		Conta byLogin = repository.findByLogin(contaMock.getLogin()).get();
		ClienteRepresentation conta = new ClienteRepresentation();
		conta.setId(byLogin.getId());
		Login login = new Login();
		login.setEmail("teste@teste.com");
		login.setSenha("teste1234");
		conta.setLogin(login);
		conta.setCpf("44444444444");

		List<AnimalEstimacaoRepresentation> animaisEstimacao = new ArrayList<AnimalEstimacaoRepresentation>();
		AnimalEstimacaoRepresentation animal = new AnimalEstimacaoRepresentation();
		animal.setNome("Aslkajdgads");
		animaisEstimacao.add(animal);
		conta.setAnimaisEstimacao(animaisEstimacao);

		HttpEntity<ClienteRepresentation> request = new HttpEntity<>(conta, new HttpHeaders());

		ResponseEntity<ClienteRepresentation> response = template.exchange(uri, HttpMethod.PUT, request,
				ClienteRepresentation.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
		ClienteRepresentation body = response.getBody();
		assertTrue(body.getAnimaisEstimacao().stream().filter(el -> el.getNome().equals(animal.getNome())).findFirst()
				.isPresent());
	}
	
	
	@Test
	@Order(8)
	public void deve_adicionar_animal_a_lista() throws URISyntaxException {
		URI uri = new URI(this.url);
		
		ClienteRepresentation cliente = new ClienteRepresentation();
		Login login = new Login();
		login.setEmail("testeCalebe@teste.com");
		login.setSenha("testete1234");
		cliente.setLogin(login);
		
		cliente.setCpf("12345678909");
		
		cliente.setNome("Joao");
		List<AnimalEstimacaoRepresentation> animaisEstimacao = new ArrayList<AnimalEstimacaoRepresentation>();
		AnimalEstimacaoRepresentation animal = new AnimalEstimacaoRepresentation();
		animal.setNome("pedrinho");
		animal.setTipo(1l);
		animal.setFoto("");
		animaisEstimacao.add(animal);
		cliente.setAnimaisEstimacao(animaisEstimacao);
		
		HttpEntity<ClienteRepresentation> requestPost = new HttpEntity<ClienteRepresentation>(cliente, new HttpHeaders());
		ResponseEntity<ClienteRepresentation> responsePost = template.postForEntity(uri, requestPost, ClienteRepresentation.class);
		
		assertEquals(HttpStatus.CREATED, responsePost.getStatusCode());
		
		HttpEntity<Login> requestLogin = new HttpEntity<Login>(cliente.getLogin(), new HttpHeaders());
		ResponseEntity<ClienteRepresentation> responseLogin = template.postForEntity(new URI(this.url+"/login"), requestLogin, ClienteRepresentation.class);
		
		assertEquals(HttpStatus.OK, responseLogin.getStatusCode());
		
		cliente = responseLogin.getBody();
		animaisEstimacao = cliente.getAnimaisEstimacao();
		
		AnimalEstimacaoRepresentation animal2 = new AnimalEstimacaoRepresentation();
		animal2.setNome("felipinho");
		animal2.setTipo(1l);
		animal2.setFoto("");
		animaisEstimacao.add(animal2);
		
		cliente.setAnimaisEstimacao(animaisEstimacao);
		
		HttpEntity<ClienteRepresentation> requestPut = new HttpEntity<ClienteRepresentation>(cliente, new HttpHeaders());
		ResponseEntity<ClienteRepresentation> responsePut = template.exchange(uri, HttpMethod.PUT, requestPut,	ClienteRepresentation.class);
		responsePut.getBody();
		assertEquals(HttpStatus.OK, responsePut.getStatusCode());
		assertEquals(2, responsePut.getBody().getAnimaisEstimacao().size());
		
	}
}
