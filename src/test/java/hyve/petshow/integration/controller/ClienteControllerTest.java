package hyve.petshow.integration.controller;

import static hyve.petshow.mock.ContaMock.contaCliente;
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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

import hyve.petshow.controller.converter.ClienteConverter;
import hyve.petshow.domain.Cliente;
import hyve.petshow.repository.ClienteRepository;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class ClienteControllerTest {
	@LocalServerPort
	private int port;
	@Autowired
	private TestRestTemplate template;
	@Autowired
	private ClienteConverter converter;
	@Autowired
	private ClienteRepository repository;

	private Cliente cliente;
	private String url;

	@BeforeEach
	public void init() {
		cliente = new Cliente(contaCliente());
		cliente.setId(null);

		url = "http://localhost:" + port + "/cliente/";
	}

	@AfterEach
	public void limpaRepositorio() {
		repository.deleteAll();
	}

	@Test
	public void deve_retornar_por_id() throws Exception {
		repository.save(cliente);
		var uri = new URI(url + cliente.getId());
		var response = template.getForEntity(uri, String.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	public void deve_retornar_erro_em_busca_por_id() throws Exception {
		var uri = new URI(url + "1000");
		var response = template.getForEntity(uri, String.class);
		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
	}

	@Test
	public void deve_atualizar_o_restador() throws Exception {
		var clienteDb = repository.save(cliente);
		var uri = new URI(url + clienteDb.getId());
		var telefoneEsperado = "2835093";
		clienteDb.setTelefone(telefoneEsperado);

		var body = new HttpEntity<>(converter.toRepresentation(clienteDb), new HttpHeaders());
		template.exchange(uri, HttpMethod.PUT, body, Void.class);

		var busca = repository.findById(clienteDb.getId());
		assertEquals(telefoneEsperado, busca.get().getTelefone());
	}
}
