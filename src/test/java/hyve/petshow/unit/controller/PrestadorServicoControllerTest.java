package hyve.petshow.unit.controller;

import static hyve.petshow.mock.PrestadorMock.prestador;
import static hyve.petshow.mock.ServicoDetalhadoMock.servicoDetalhado;
import static hyve.petshow.mock.ServicoDetalhadoMock.servicoDetalhadoRepresentation;
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
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;

import hyve.petshow.controller.representation.AvaliacaoRepresentation;
import hyve.petshow.controller.representation.PrestadorRepresentation;
import hyve.petshow.controller.representation.ServicoDetalhadoRepresentation;
import hyve.petshow.domain.Prestador;
import hyve.petshow.domain.ServicoDetalhado;
import hyve.petshow.mock.PrestadorMock;
import hyve.petshow.mock.ServicoDetalhadoMock;
import hyve.petshow.repository.PrestadorRepository;
import hyve.petshow.repository.ServicoRepository;

@ActiveProfiles("test")
@DirtiesContext(classMode = ClassMode.BEFORE_CLASS)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class PrestadorServicoControllerTest {
	@LocalServerPort
	private int port;
	@Autowired
	private TestRestTemplate template;
	@Autowired
	private PrestadorRepository repository;
	@Autowired
	private ServicoRepository servicoRepository;

	private String url;
	private Prestador prestadorMock;
	
	@AfterEach
	public void limpar() {
		repository.deleteAll();
	}
	
	@BeforeEach
	public void init() {
		servicoRepository.save(servicoDetalhado().getTipo());
		prestadorMock = repository.save(prestador());
		this.url = "http://localhost:"+port+"/prestador/"+prestadorMock.getId()+"/servico-detalhado";
	}
	
	@Test
	public void deve_retornar_lista_de_servicos() throws Exception {
		var uri = new URI(this.url);
		
		var servico = servicoDetalhadoRepresentation();
		servico.setId(null);
		var requestBody = new HttpEntity<>(servico, new HttpHeaders());
		
		var response = template.postForEntity(uri, requestBody, ServicoDetalhadoRepresentation.class);
		
		var findById = repository.findById(prestadorMock.getId());
		assertFalse(findById.get().getServicosPrestados().isEmpty());
	}
	
	@Test
	public void deve_remover_item_da_lista() throws Exception {
		var uri = new URI(this.url);
		
		var servico = servicoDetalhadoRepresentation();
		servico.setId(null);
		var requestBody = new HttpEntity<>(servico, new HttpHeaders());
		
		var response = template.postForEntity(uri, requestBody, ServicoDetalhadoRepresentation.class);
				
		var uriDois = new URI(this.url + "/"+response.getBody().getId());
		
		template.delete(uriDois);
		
		var findById = repository.findById(prestadorMock.getId());
		
		assertTrue(findById.get().getServicosPrestados().isEmpty());
	}
}
