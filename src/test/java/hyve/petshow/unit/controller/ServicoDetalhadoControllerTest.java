package hyve.petshow.unit.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;

import hyve.petshow.controller.representation.ServicoDetalhadoRepresentation;
import hyve.petshow.domain.Prestador;
import hyve.petshow.mock.PrestadorMock;
import hyve.petshow.mock.ServicoDetalhadoMock;
import hyve.petshow.repository.PrestadorRepository;
import hyve.petshow.repository.ServicoDetalhadoRepository;
import hyve.petshow.repository.ServicoRepository;

@TestMethodOrder(OrderAnnotation.class)
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = ClassMode.BEFORE_CLASS)
public class ServicoDetalhadoControllerTest {
	@LocalServerPort
	private Integer port;

	@Autowired
	private TestRestTemplate template;

	@Autowired
	private ServicoRepository servicoRepository;

	@Autowired
	private PrestadorRepository prestadorRepository;
	
	private String url = "http://localhost:{port}/prestador/{idPrestador}/servico-detalhado/{idServico}";
	
	private Prestador prestador;
	
	private ServicoDetalhadoRepresentation servicoDetalhado;
	
	@Autowired
	private ServicoDetalhadoRepository repository;
	
	@BeforeEach
	public void initRepository() {
		var prestador = PrestadorMock.criaPrestador();
		prestador.setServicosPrestados(null);
		this.prestador = prestadorRepository.save(prestador);
		servicoDetalhado = ServicoDetalhadoMock.criarServicoDetalhadoRepresentation();
		servicoDetalhado.setId(null);
		
		servicoRepository.save(ServicoDetalhadoMock.criarServicoDetalhado().getTipo());
	}

	@BeforeEach
	public void init() {
		this.url = this.url.replace("{port}",port.toString());
	}
	
	@AfterEach
	public void limpaLista() {
		repository.deleteAll();
		prestadorRepository.deleteAll();
	}

	@Test
	public void deve_salvar_servico_detalhado() throws URISyntaxException {
		var urlPost = this.url.replace("{idPrestador}", prestador.getId().toString()).replace("{idServico}", "");
		var uri = new URI(urlPost);
		
		var request = new HttpEntity<ServicoDetalhadoRepresentation>(this.servicoDetalhado, new HttpHeaders());
	
		var response = template.postForEntity(uri, request, ServicoDetalhadoRepresentation.class);
		
		assertEquals(prestador.getId(), response.getBody().getPrestadorId());
		
		var prestadorDb = prestadorRepository.findById(prestador.getId());
	
		assertFalse(prestadorDb.get().getServicosPrestados().isEmpty());
	}

	@Test
	public void deve_remover_servico_detalhado() throws Exception {
		var url = this.url.replace("{idPrestador}", prestador.getId().toString()).replace("{idServico}", "");
		var uri = new URI(url);
		
		var request = new HttpEntity<ServicoDetalhadoRepresentation>(this.servicoDetalhado, new HttpHeaders());
	
		var response = template.postForEntity(uri, request, ServicoDetalhadoRepresentation.class);
		
		url = this.url.replace("{idPrestador}", prestador.getId().toString()).replace("{idServico}", response.getBody().getId().toString());
		
		
		template.delete(new URI(url));
		var prestadorDb = prestadorRepository.findById(prestador.getId());
		assertTrue(prestadorDb.get().getServicosPrestados().isEmpty());
		
		
	}

	@Test
	public void deve_retornar_servico_detalhado_atualizado() throws Exception {
		var url = this.url.replace("{idPrestador}", prestador.getId().toString()).replace("{idServico}", "");
		var uri = new URI(url);
		
		var request = new HttpEntity<ServicoDetalhadoRepresentation>(this.servicoDetalhado, new HttpHeaders());
	
		var response = template.postForEntity(uri, request, ServicoDetalhadoRepresentation.class);
		
		var servicoDetalhado = response.getBody();
		servicoDetalhado.setPreco(new BigDecimal(23));
		
		url = this.url.replace("{idPrestador}", prestador.getId().toString()).replace("{idServico}", servicoDetalhado.getId().toString());
		
		template.put(url, servicoDetalhado);
	}

}