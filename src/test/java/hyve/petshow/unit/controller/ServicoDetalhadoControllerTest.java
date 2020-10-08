package hyve.petshow.unit.controller;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.net.URI;
import java.net.URISyntaxException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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

import hyve.petshow.controller.ServicoDetalhadoController;
import hyve.petshow.controller.converter.ServicoDetalhadoConverter;
import hyve.petshow.controller.representation.ServicoDetalhadoRepresentation;
import hyve.petshow.mock.ServicoDetalhadoMock;
import hyve.petshow.service.port.ServicoDetalhadoService;

@TestMethodOrder(OrderAnnotation.class)
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ServicoDetalhadoControllerTest {
	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate template;

	@Mock
	private ServicoDetalhadoService service;

	@Mock
	private ServicoDetalhadoConverter converter;
	@InjectMocks
	private ServicoDetalhadoController controller;

	private String url;

	@BeforeEach
	public void init() {
		url = "http://localhost:" + port + "/servico-detalhado";

	}

	private ServicoDetalhadoRepresentation servicoDetalhadoMock = ServicoDetalhadoMock
			.criarServicoDetalhadoRepresentation();

	@Test
	@Order(1)
	public void deve_salvar_servico_detalhado() throws URISyntaxException {
		// dado
		var expectedBody = servicoDetalhadoMock;
		var expectedStatus = HttpStatus.CREATED;
		var servicoDetalhadoRepresentation = servicoDetalhadoMock;
		var servicoDetalhado = ServicoDetalhadoMock.criarServicoDetalhado();

		when(converter.toDomain(servicoDetalhadoRepresentation)).thenReturn(servicoDetalhado);
		when(service.adicionarServicoDetalhado(servicoDetalhado)).thenReturn(servicoDetalhado);
		when(converter.toRepresentation(servicoDetalhado)).thenReturn(expectedBody);

		// quando
		var actual = controller.adicionarServicoDetalhado(servicoDetalhadoRepresentation);

		// entao
		assertAll(() -> assertEquals(expectedBody, actual.getBody()),
				() -> assertEquals(expectedStatus, actual.getStatusCode()));
	}

// Esse teste provavelmente est� errado, rs
//	@Test
//	@Order(4)
//	public void deve_retornar_por_prestador() throws URISyntaxException {
//		URI uri = new URI(this.url + "/login");
//		Prestador prestador = servicoDetalhado.getPrestador();
//
//		HttpEntity<Prestador> request = new HttpEntity<>(prestador, new HttpHeaders());
//
//		ResponseEntity<ServicoDetalhado> response = template.postForEntity(uri, request, new ParametrizedTypeReference();
//		assertEquals(HttpStatus.OK, response.getStatusCode());
//		assertNotNull(response.getBody());
//	}

	@Test
	@Order(2)
	public void deve_retornar_excecao() throws URISyntaxException {
		URI uri = new URI(this.url);
		ServicoDetalhadoRepresentation servicoDetalhadoMock = new ServicoDetalhadoRepresentation();

		HttpEntity<ServicoDetalhadoRepresentation> request = new HttpEntity<>(servicoDetalhadoMock, new HttpHeaders());

		ResponseEntity<String> response = template.postForEntity(uri, request, String.class);
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());

	}

	@Test
	@Order(3)
	public void deve_remover_servico_detalhado() throws Exception {
		// dado
		var id = 1L;
		var expectedStatus = HttpStatus.OK;
		var expectedBody = ServicoDetalhadoMock.criarMensagemRepresentation();

		when(service.removerServicoDetalhado(id, id)).thenReturn(expectedBody);

		// quando
		var actual = controller.removerServicoDetalhado(id, id);

		// entao
		assertAll(() -> assertEquals(expectedBody, actual.getBody()),
				() -> assertEquals(expectedStatus, actual.getStatusCode()));
	}

	@Test
	@Order(4)
	public void deve_retornar_servico_detalhado_atualizado() throws Exception {
		// dado
		var expectedBody = servicoDetalhadoMock;
		var expectedStatus = HttpStatus.OK;
		var representation = servicoDetalhadoMock;
		var domain = ServicoDetalhadoMock.criarServicoDetalhado();
		var id = 1L;

		when(converter.toDomain(representation)).thenReturn(domain);
		when(service.atualizarServicoDetalhado(id, domain)).thenReturn(domain);
		when(converter.toRepresentation(domain)).thenReturn(expectedBody);

		// quando
		var actual = controller.atualizarServicoDetalhado(id, representation);

		// entao
		assertAll(() -> assertEquals(expectedBody, actual.getBody()),
				() -> assertEquals(expectedStatus, actual.getStatusCode()));
	}

}