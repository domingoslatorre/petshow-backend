package hyve.petshow.integration;

import hyve.petshow.controller.converter.AnimalEstimacaoConverter;
import hyve.petshow.controller.representation.AnimalEstimacaoRepresentation;
import hyve.petshow.domain.AnimalEstimacao;
import hyve.petshow.domain.Cliente;
import hyve.petshow.domain.TipoAnimalEstimacao;
import hyve.petshow.repository.AnimalEstimacaoRepository;
import hyve.petshow.repository.ClienteRepository;
import hyve.petshow.repository.TipoAnimalEstimacaoRepository;
import org.junit.jupiter.api.*;
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
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

import static hyve.petshow.mock.AuditoriaMock.auditoria;
import static hyve.petshow.mock.ContaMock.contaCliente;
import static hyve.petshow.util.AuditoriaUtils.ATIVO;
import static org.junit.jupiter.api.Assertions.*;

@Disabled("Destativado enquanto não funcionar em PRD")
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class AnimalEstimacaoIntegrationTest {
	@LocalServerPort
	private int port;
	@Autowired
	private TestRestTemplate template;
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private TipoAnimalEstimacaoRepository tipoRepository;
	@Autowired
	private AnimalEstimacaoRepository repository;
    	@Autowired
    	private AnimalEstimacaoConverter converter;
	private Cliente cliente;
	private AnimalEstimacao animal;
	private String url;
	
	@AfterEach
	public void limpaRepositorios() {
		repository.deleteAll();
		clienteRepository.deleteAll();
		tipoRepository.deleteAll();
	}

	@BeforeEach
	public void init() {
		url = "http://localhost:"+port+"/cliente/animal-estimacao";

		var tipo = new TipoAnimalEstimacao();
		tipo.setNome("Cachorro");
		tipo.setPelagem("Pelagem");
		tipo.setPorte("Porte");
		tipoRepository.save(tipo);
		
		cliente = new Cliente(contaCliente());
		cliente.setId(null);
		clienteRepository.save(cliente);
		
		
		animal = new AnimalEstimacao();
		animal.setDonoId(cliente.getId());
		animal.setTipo(tipo);
		animal.setNome("Bidu");
		animal.setAuditoria(auditoria(ATIVO));
	}
	
	
	@Test
	public void deve_retornar_erro_em_lista_de_tipos() throws Exception {
		tipoRepository.deleteAll();
		var uri = new URI(url + "/tipos");
		var response = template.getForEntity(uri, String.class);
		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
	}
	
	@Test
	public void deve_retornar_lista_de_tipos() throws Exception {
		var uri = new URI(url + "/tipos");
		var response = template.getForEntity(uri, String.class);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	
	@Test
	public void deve_remover_animal() throws Exception {
		repository.save(animal);
		var httpUrl = "http://localhost:" + port + "/cliente/"+cliente.getId()+
				"/animal-estimacao/"+animal.getId();
		
		template.delete(httpUrl);
		
		assertFalse(repository.existsById(animal.getId()));
	}
	
	@Test
	public void deve_atualizar_animal() throws Exception {
		repository.save(animal);
		var uri = new URI(url + "/" + animal.getId());
		var expected = "P";
		animal.setNome(expected);
		var body = new HttpEntity<>(converter.toRepresentation(animal), new HttpHeaders());
		template.exchange(uri, HttpMethod.PUT, body, String.class);
		
		var busca = repository.findById(animal.getId());
		assertEquals(expected, busca.get().getNome());
	}
	
	@Test
	public void deve_retornar_lista_vazia_de_animais() throws Exception {
		var httpUrl = "http://localhost:"+port+"/cliente/"+cliente.getId()+"/animal-estimacao";
		var uri = UriComponentsBuilder.fromHttpUrl(httpUrl)
				.queryParam("pagina", 0)
				.queryParam("quantidadeItens", 5)
				.toUriString();
		
		var response = template.getForEntity(uri, String.class);
		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
	}
	
	@Test
	public void deve_retornar_lista_de_animais() throws Exception {
		repository.save(animal);
		var httpUrl = "http://localhost:"+port+"/cliente/"+cliente.getId()+"/animal-estimacao";
		var uri = UriComponentsBuilder.fromHttpUrl(httpUrl)
				.queryParam("pagina", 0)
				.queryParam("quantidadeItens", 5)
				.toUriString();
		
		var response = template.getForEntity(uri, String.class);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	
	@Test
	public void deve_adicionar_animal() throws Exception {
		var uri = new URI(url);
		var body = new HttpEntity<>(converter.toRepresentation(animal), new HttpHeaders());
		var response = template.postForEntity(uri, body, AnimalEstimacaoRepresentation.class);
		
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		
		var anyMatch = repository.findAll().stream().anyMatch(el -> el.getNome().equals(animal.getNome()));
		assertTrue(anyMatch);
		
	}
}
