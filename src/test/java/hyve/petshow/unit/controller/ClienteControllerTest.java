package hyve.petshow.unit.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import hyve.petshow.domain.enums.TipoAnimalEstimacao;
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

	/*TODO: TESTES ANIMAL ESTIMACAO
	@Autowired
    private AnimalEstimacaoController animalEstimacaoController;

    @MockBean
    private AnimalEstimacaoConverter animalEstimacaoConverter;

    @MockBean(name = "animalEstimacaoService")
    private AnimalEstimacaoService animalEstimacaoService;

    @Test
    public void deve_retornar_animal_salvo(){
        //dado
        var expectedBody = AnimalEstimacaoMock.animalEstimacaoRepresentation();
        var expectedStatus = HttpStatus.CREATED;
        var animalEstimacaoRepresentation = AnimalEstimacaoMock.animalEstimacaoRepresentation();
        var animalEstimacao = AnimalEstimacaoMock.animalEstimacao();

        when(animalEstimacaoConverter.toDomain(animalEstimacaoRepresentation)).thenReturn(animalEstimacao);
        when(animalEstimacaoService.adicionarAnimalEstimacao(animalEstimacao)).thenReturn(animalEstimacao);
        when(animalEstimacaoConverter.toRepresentation(animalEstimacao)).thenReturn(expectedBody);

        //quando
        var actual = animalEstimacaoController.criarAnimalEstimacao(animalEstimacaoRepresentation);

        //entao
        assertAll(
                () -> assertEquals(expectedBody, actual.getBody()),
                () -> assertEquals(expectedStatus, actual.getStatusCode())
        );
    }

    @Test
    public void deve_buscar_animal_correto(){
        //dado
        var expectedBody = AnimalEstimacaoMock.animalEstimacaoRepresentation();
        var expectedStatus = HttpStatus.OK;
        var animalEstimacao = Optional.of(AnimalEstimacaoMock.animalEstimacao());
        var id = 1L;

        when(animalEstimacaoService.obterAnimalEstimacaoPorId(id)).thenReturn(animalEstimacao);
        when(animalEstimacaoConverter.toRepresentation(animalEstimacao.get())).thenReturn(expectedBody);

        //quando
        var actual = animalEstimacaoController.obterAnimalEstimacao(id);

        //entao

        assertAll(
                () -> assertEquals(expectedBody, actual.getBody()),
                () -> assertEquals(expectedStatus, actual.getStatusCode())
        );
    }

    @Test
    public void obterAnimalEstimacaoTestCase02(){
        var expectedStatus = HttpStatus.NO_CONTENT;
        Optional<AnimalEstimacao> animalEstimacao = Optional.empty();
        var id = 1L;

        when(animalEstimacaoService.obterAnimalEstimacaoPorId(id)).thenReturn(animalEstimacao);

        //quando
        var actual = animalEstimacaoController.obterAnimalEstimacao(id);

        //entao
        assertEquals(expectedStatus, actual.getStatusCode());
    }

    @Test
    public void deve_obter_lista_de_animais(){
        //dado
        var expectedBody = Arrays.asList(AnimalEstimacaoMock.animalEstimacaoRepresentation());
        var expectedStatus = HttpStatus.OK;
        var animaisEstimacao = Arrays.asList(AnimalEstimacaoMock.animalEstimacao());

        when(animalEstimacaoService.buscarAnimaisEstimacao()).thenReturn(animaisEstimacao);
        when(animalEstimacaoConverter.toRepresentationList(animaisEstimacao)).thenReturn(expectedBody);

        //quando
        var actual = animalEstimacaoController.obterAnimaisEstimacao();

        //entao
        assertAll(
                () -> assertEquals(expectedBody, actual.getBody()),
                () -> assertEquals(expectedStatus, actual.getStatusCode())
        );
    }

    @Test
    public void deve_retornar_lista_vazia_de_animais(){
        //dado
        var expectedStatus = HttpStatus.NO_CONTENT;
        List<AnimalEstimacao> animaisEstimacao = Collections.emptyList();

        when(animalEstimacaoService.buscarAnimaisEstimacao()).thenReturn(animaisEstimacao);

        //quando
        var actual = animalEstimacaoController.obterAnimaisEstimacao();

        //entao
        assertEquals(expectedStatus, actual.getStatusCode());
    }

    @Test
    public void deve_retornar_animal_atualizado(){
        //dado
        var expectedBody = AnimalEstimacaoMock.animalEstimacaoRepresentation();
        var expectedStatus = HttpStatus.OK;
        var animalEstimacaoRepresentation = AnimalEstimacaoMock.animalEstimacaoRepresentation();
        var animalEstimacao = AnimalEstimacaoMock.animalEstimacao();
        var id = 1L;

        when(animalEstimacaoConverter.toDomain(animalEstimacaoRepresentation)).thenReturn(animalEstimacao);
        when(animalEstimacaoService.atualizarAnimalEstimacao(id, animalEstimacao)).thenReturn(Optional.of(animalEstimacao));
        when(animalEstimacaoConverter.toRepresentation(animalEstimacao)).thenReturn(expectedBody);

        //quando
        var actual = animalEstimacaoController.atualizarAnimalEstimacao(id, animalEstimacaoRepresentation);

        //entao
        assertAll(
                () -> assertEquals(expectedBody, actual.getBody()),
                () -> assertEquals(expectedStatus, actual.getStatusCode())
        );
    }

    @Test
    public void deve_retornar_vazio_quando_animal_nao_existir(){
        //dado
        var expectedStatus = HttpStatus.NO_CONTENT;
        var animalEstimacaoRepresentation = AnimalEstimacaoMock.animalEstimacaoRepresentation();
        var animalEstimacao = AnimalEstimacaoMock.animalEstimacao();
        var id = 1L;

        when(animalEstimacaoConverter.toDomain(animalEstimacaoRepresentation)).thenReturn(animalEstimacao);
        when(animalEstimacaoService.atualizarAnimalEstimacao(id, animalEstimacao)).thenReturn(Optional.empty());

        //quando
        var actual = animalEstimacaoController.atualizarAnimalEstimacao(id, animalEstimacaoRepresentation);

        //entao
        assertEquals(expectedStatus, actual.getStatusCode());
    }

    @Test
    public void removerAnimalEstimacaoTestCase01(){
        //dado
        var id = 1L;
        var expectedStatus = HttpStatus.OK;
        var expectedBody = AnimalEstimacaoMock.animalEstimacaoResponseRepresentation();

        when(animalEstimacaoService.removerAnimalEstimacao(id)).thenReturn(expectedBody);

        //quando
        var actual = animalEstimacaoController.removerAnimalEstimacao(id);

        //entao
        assertAll(
                () -> assertEquals(expectedBody, actual.getBody()),
                () -> assertEquals(expectedStatus, actual.getStatusCode())
        );
    }

    @Test
    public void deve_retornar_status_204_quando_nao_tiver_o_que_remover(){
        //dado
        var id = 1L;
        var expectedStatus = HttpStatus.NO_CONTENT;
        var animalEstimacaoResponseRepresentation = AnimalEstimacaoMock.animalEstimacaoResponseRepresentationAlt();

        when(animalEstimacaoService.removerAnimalEstimacao(id)).thenReturn(animalEstimacaoResponseRepresentation);

        //quando
        var actual = animalEstimacaoController.removerAnimalEstimacao(id);

        //entao
        assertEquals(expectedStatus, actual.getStatusCode());
    }*/
}
