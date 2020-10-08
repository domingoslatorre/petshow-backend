package hyve.petshow.unit.controller;
// Eu sei q não tem controller de Avaliacao. Eu coloquei mais pra não ficar bagunçado no de prestador

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;

import hyve.petshow.controller.representation.AvaliacaoRepresentation;
import hyve.petshow.controller.representation.ServicoDetalhadoRepresentation;
import hyve.petshow.domain.Cliente;
import hyve.petshow.domain.Prestador;
import hyve.petshow.domain.ServicoDetalhado;
import hyve.petshow.mock.ClienteMock;
import hyve.petshow.mock.PrestadorMock;
import hyve.petshow.mock.ServicoDetalhadoMock;
import hyve.petshow.mock.entidades.AvaliacaoMock;
import hyve.petshow.repository.AvaliacaoRepository;
import hyve.petshow.repository.ClienteRepository;
import hyve.petshow.repository.PrestadorRepository;
import hyve.petshow.repository.ServicoDetalhadoRepository;
import hyve.petshow.repository.ServicoRepository;

@ActiveProfiles("test")
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)

public class AvaliacaoControllerTest {
	@LocalServerPort
	private int port;
	@Autowired
	private TestRestTemplate template;

	@Autowired
	private ServicoRepository servicoRepository;
	@Autowired
	private ServicoDetalhadoRepository servicoDetalhadoRepository;
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private AvaliacaoRepository avaliacaoRepository;
	@Autowired
	private PrestadorRepository prestadorRepository;

	private String url;

	private Cliente clienteMock;
	private ServicoDetalhado servicoDetalhadoMock;
	private Prestador prestadorMock;

	@AfterEach
	public void limpaLista() {
		avaliacaoRepository.deleteAll();
	}

	@BeforeEach
	public void adicionaItens() {
		this.clienteMock = clienteRepository.save(ClienteMock.criaCliente());
		this.prestadorMock = prestadorRepository.save(PrestadorMock.criaPrestador());
		var servicoAvaliado = ServicoDetalhadoMock.criarServicoDetalhado();
		servicoAvaliado.setPrestadorId(prestadorMock.getId());
		servicoRepository.save(servicoAvaliado.getTipo());
		this.servicoDetalhadoMock = servicoDetalhadoRepository.save(servicoAvaliado);
	}

	@BeforeEach
	public void init() {
		this.url = "http://localhost:" + this.port + "/prestador";
	}

	@Test
	public void deve_adicionar_avaliacao_a_lista() throws Exception {
		// dado
		var representation = AvaliacaoMock.geraAvaliacaoRepresentation();
		representation.setClienteId(clienteMock.getId());
		
		var urlAvaliacao = this.url + "/" + this.prestadorMock.getId() + "/servicoDetalhado/"
				+ this.servicoDetalhadoMock.getId() + "/avaliacoes";
		var uri = new URI(urlAvaliacao);
		// quando
		var requestBody = new HttpEntity<AvaliacaoRepresentation>(representation, new HttpHeaders());
		// var response = template.exchange(uri, HttpMethod.POST, requestBody,
		// ServicoDetalhadoRepresentation.class);
		var response = template.postForEntity(uri, requestBody, ServicoDetalhadoRepresentation.class);
		// então
		assertFalse(response.getBody().getAvaliacoes().isEmpty());
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		servicoDetalhadoRepository.findById(this.servicoDetalhadoMock.getId()).ifPresent(el -> {
			assertFalse(el.getAvaliacoes().isEmpty());
		});
	}

	@Test
	public void deve_retornar_lista_vazia() throws Exception {
		// dado
		String urlAvaliacao = this.url + "/" + this.prestadorMock.getId() + "/servicoDetalhado/"
				+ this.servicoDetalhadoMock.getId();
		var uri = new URI(urlAvaliacao);

		// quando
		var response = template.exchange(uri, HttpMethod.GET, null, ServicoDetalhadoRepresentation.class);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertTrue(response.getBody().getAvaliacoes().isEmpty());
	}

	@Test
	public void deve_retornar_lista_preenchida() throws Exception {
		// dado
		var representation = AvaliacaoMock.geraAvaliacaoRepresentation();
		representation.setClienteId(clienteMock.getId());
		var urlAvaliacao = this.url + "/" + this.prestadorMock.getId() + "/servicoDetalhado/"
				+ this.servicoDetalhadoMock.getId() + "/avaliacoes";
		var uri = new URI(urlAvaliacao);

		var requestBody = new HttpEntity<AvaliacaoRepresentation>(representation, new HttpHeaders());
		var response = template.exchange(uri, HttpMethod.POST, requestBody, ServicoDetalhadoRepresentation.class);
		// quando

		uri = new URI(
				this.url + "/" + this.prestadorMock.getId() + "/servicoDetalhado/" + this.servicoDetalhadoMock.getId());
		var responseGet = template.exchange(uri, HttpMethod.GET, null, ServicoDetalhadoRepresentation.class);
		// entao
		assertEquals(HttpStatus.OK, responseGet.getStatusCode());
		assertFalse(response.getBody().getAvaliacoes().isEmpty());
	}

	@Test
	public void deve_retornar_Prestador() throws Exception {
		// dado
		var representation = AvaliacaoMock.geraAvaliacaoRepresentation();
		representation.setClienteId(clienteMock.getId());
		var urlAvaliacao = this.url + "/" + this.prestadorMock.getId() + "/servicoDetalhado/"
				+ this.servicoDetalhadoMock.getId() + "/avaliacoes";
		var uri = new URI(urlAvaliacao);

		var requestBody = new HttpEntity<AvaliacaoRepresentation>(representation, new HttpHeaders());
		var response = template.exchange(uri, HttpMethod.POST, requestBody, ServicoDetalhadoRepresentation.class);
		// quando

		uri = new URI(
				this.url + "/" + this.prestadorMock.getId() + "/servicoDetalhado/" + this.servicoDetalhadoMock.getId());
		var responseGet = template.exchange(uri, HttpMethod.GET, null, ServicoDetalhadoRepresentation.class);
		// entao
		assertEquals(HttpStatus.OK, responseGet.getStatusCode());
		assertFalse(response.getBody().getPrestadorId() == null);
	}

}
