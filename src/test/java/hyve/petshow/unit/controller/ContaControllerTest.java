package hyve.petshow.unit.controller;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URI;
import java.net.URISyntaxException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;

import hyve.petshow.controller.representation.ContaRepresentation;
import hyve.petshow.domain.Cliente;
import hyve.petshow.domain.Prestador;
import hyve.petshow.domain.enums.TipoConta;
import hyve.petshow.mock.ClienteMock;
import hyve.petshow.mock.PrestadorMock;
import hyve.petshow.repository.ContaGenericaRepository;

@TestMethodOrder(OrderAnnotation.class)
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = ClassMode.BEFORE_CLASS)
public class ContaControllerTest {
	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate template;
	
	private String url;

	@Autowired
	private ContaGenericaRepository contaRepository;
	
	private Cliente cliente;
	private Prestador prestador;
	
	@BeforeEach
	public void init() {
		url = "http://localhost:" + port + "/conta";
		cliente = contaRepository.save(ClienteMock.criaCliente());
		prestador = contaRepository.save(PrestadorMock.criaPrestador());
	}
	
	@AfterEach
	public void limpar() {
		contaRepository.deleteAll();
	}
	
	@Test
	public void deve_retornar_cliente() throws URISyntaxException {
		var uri = new URI(url+"/"+cliente.getId());
		var response = template.getForEntity(uri, ContaRepresentation.class);
		
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(TipoConta.CLIENTE.getTipo(), response.getBody().getTipo());
	}
	
	@Test
	public void deve_retornar_prestador() throws URISyntaxException {
		var uri = new URI(url+"/"+prestador.getId());
		var response = template.getForEntity(uri, ContaRepresentation.class);
		
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(TipoConta.PRESTADOR_AUTONOMO.getTipo(), response.getBody().getTipo());
	}
	
}
