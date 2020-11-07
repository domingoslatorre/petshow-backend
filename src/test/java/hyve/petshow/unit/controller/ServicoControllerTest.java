package hyve.petshow.unit.controller;

import static hyve.petshow.mock.ServicoMock.servico;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;

import hyve.petshow.controller.representation.ServicoRepresentation;
import hyve.petshow.mock.ServicoMock;
import hyve.petshow.repository.ServicoRepository;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = ClassMode.BEFORE_CLASS)
public class ServicoControllerTest {
	@LocalServerPort
	private Integer port;
	
	@Autowired
	private TestRestTemplate template;
	
	@Autowired
	private ServicoRepository repository;
	
	private String url;
	
	@AfterEach
	public void limpa() {
		repository.deleteAll();
	}
	
	@BeforeEach
	public void initUrl() {
		this.url = "http://localhost:"+port+"/servico";
	}
	
	@Test
	public void deve_trazer_lista() {
		repository.saveAll(Arrays.asList(servico()));
		
		var response = template
	       .exchange(this.url, HttpMethod.GET, null,  new ParameterizedTypeReference<List<ServicoRepresentation>>() {
		});
		
		assertFalse(response.getBody().isEmpty());
	}
	
	@Test
	public void deve_trazer_mensagem_de_nao_encontrado() {
		var response = template.exchange(this.url, HttpMethod.GET, null,  String.class);
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		assertNotNull(response.getBody());
	}
	
}
