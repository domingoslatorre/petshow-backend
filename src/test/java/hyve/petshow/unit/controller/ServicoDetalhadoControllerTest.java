package hyve.petshow.unit.controller;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

import hyve.petshow.mock.AnimalEstimacaoMock;
import hyve.petshow.mock.ServicoDetalhadoMock;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import hyve.petshow.controller.ServicoDetalhadoController;
import hyve.petshow.controller.converter.ServicoDetalhadoConverter;
import hyve.petshow.controller.representation.ContaRepresentation;
import hyve.petshow.controller.representation.ServicoDetalhadoRepresentation;
import hyve.petshow.domain.ServicoDetalhado;
import hyve.petshow.repository.ClienteRepository;
import hyve.petshow.repository.ServicoDetalhadoRepository;
import hyve.petshow.service.port.ServicoDetalhadoService;

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
	@Mock
	
	private ServicoDetalhadoService service;
	
	 
	@Autowired
	@Mock
	private ServicoDetalhadoConverter converter;
	

	@Mock
	private ServicoDetalhadoConverter converter;
	@InjectMocks
	private ServicoDetalhadoController controller;
	
	private String url;

	@BeforeEach
	public void init() {
		url = "http://localhost:" + port + "/prestador/servicos";

	}


	private ServicoDetalhadoRepresentation servicoDetalhadoMock = ServicoDetalhadoMock.criarServicoDetalhadoRepresentation();
		
	

	@Test
	@Order(1)
	public void deve_salvar_servico_detalhado() throws URISyntaxException {
		   //dado
        var expectedBody = servicoDetalhadoMock;
        var expectedStatus = HttpStatus.CREATED;
        var servicoDetalhadoRepresentation = servicoDetalhadoMock;
        var servicoDetalhado = ServicoDetalhadoMock.criarServicoDetalhado();

        when(converter.toDomain(servicoDetalhadoRepresentation)).thenReturn(servicoDetalhado);
        when(service.adicionarServicoDetalhado(servicoDetalhado)).thenReturn(servicoDetalhado);
        when(converter.toRepresentation(servicoDetalhado)).thenReturn(expectedBody);

        //quando
        var actual = controller.adicionarServicoDetalhado(servicoDetalhadoRepresentation);

        //entao
        assertAll(
                () -> assertEquals(expectedBody, actual.getBody()),
                () -> assertEquals(expectedStatus, actual.getStatusCode())
        );
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
	@Order(2)
	public void deve_retornar_excecao() throws URISyntaxException {
		URI uri = new URI(this.url);
		ServicoDetalhadoRepresentation servicoDetalhadoMock = new ServicoDetalhadoRepresentation();

		HttpEntity<ServicoDetalhadoRepresentation> request = new HttpEntity<>(servicoDetalhadoMock, new HttpHeaders());

		ResponseEntity<String> response = template.postForEntity(uri, request, String.class);
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());

	}
	
//	  @Test
//	  @Order(3)
//	    public void deve_remover_servico_detalhado() throws Exception {
//	        //dado
//	        var id = 1L;
//	        var expectedStatus = HttpStatus.OK;
//	        var expectedBody = ServicoDetalhadoMock.criarMensagemRepresentation();
//
//	        when(service.removerServicoDetalhado(id)).thenReturn(expectedBody);
//
//	        //quando
//	        var actual = controller.removerServicoDetalhado(id);
//
//	        //entao
//	        assertAll(
//	                () -> assertEquals(expectedBody, actual.getBody()),
//	                () -> assertEquals(expectedStatus, actual.getStatusCode())
//	        );
//	    }

	
}
