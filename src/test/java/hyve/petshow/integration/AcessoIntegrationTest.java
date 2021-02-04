package hyve.petshow.integration;

import hyve.petshow.controller.converter.ContaConverter;
import hyve.petshow.domain.Cliente;
import hyve.petshow.domain.Conta;
import hyve.petshow.domain.embeddables.Login;
import hyve.petshow.repository.AcessoRepository;
import hyve.petshow.repository.ClienteRepository;
import hyve.petshow.repository.VerificationTokenRepository;
import hyve.petshow.service.port.AcessoService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;

import static hyve.petshow.mock.ContaMock.contaCliente;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Disabled("Destativado enquanto não funcionar em PRD")
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class AcessoIntegrationTest {
	@LocalServerPort
	private int port;
	@Autowired
	private TestRestTemplate template;
	@Autowired
	private ContaConverter converter;
	@Autowired
    private AcessoService service;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private VerificationTokenRepository tokenRepository;
    @Autowired
    private AcessoRepository acessoRepository;
    
	private String url;
	private Conta conta;

	@BeforeEach
	public void init() {
		conta = new Cliente(contaCliente());
		conta.setId(null);
		url = "http://localhost:" + port + "/acesso";
	}
	
	@AfterEach
	public void limpaRepositorios() {
		tokenRepository.deleteAll();
		clienteRepository.deleteAll();
		acessoRepository.deleteAll();
	}

	@Test
	public void deve_realizar_login() throws Exception {
		service.adicionarConta(conta);
		var uri = new URI(this.url + "/login");
		var headers = new HttpHeaders();
		
		var requestBody = new HttpEntity<>(new Cliente(contaCliente()).getLogin(), headers);
		var response = template.postForEntity(uri, requestBody, String.class);
		
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	
	@Test
	public void deve_retornar_erro_por_login_incorreto() throws Exception {
		service.adicionarConta(conta);
		var uri = new URI(this.url + "/login");
		var headers = new HttpHeaders();
		
		var login = new Login();
		login.setEmail(conta.getEmail());
		login.setSenha("ASLDGJjskldgja");
		var requestBody = new HttpEntity<>(login, headers);
		var response = template.postForEntity(uri, requestBody, String.class);
		
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}

	@Test
	public void deve_cadastrar_nova_conta() throws Exception {
		var uri = new URI(this.url + "/cadastro");
		var headers = new HttpHeaders();
		
		var representation = converter.toRepresentation(conta);
		representation.setLogin(conta.getLogin());
		var requestBody = new HttpEntity<>(representation, headers);
		var response = template.postForEntity(uri, requestBody, String.class);
		
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	
	@Test
	public void deve_dar_erro_por_email_ja_cadastrado() throws Exception {
		service.adicionarConta(conta);
		var uri = new URI(this.url + "/cadastro");
		
		var headers = new HttpHeaders();
		var representation = converter.toRepresentation(conta);
		representation.setLogin(conta.getLogin());
		
		var requestBody = new HttpEntity<>(representation, headers);
		var response = template.postForEntity(uri, requestBody, String.class);
		
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}
	
	@Test
	public void deve_ativar_a_conta() throws Exception {
		// Given
		var token = "LSKDJGLASDLKJ";
		clienteRepository.save((Cliente) conta);
		service.criaTokenVerificacao(conta, token);
		var uri = UriComponentsBuilder.fromHttpUrl(this.url + "/ativar")
				.queryParam("token", token)
				.toUriString();
		
		// When		
		var params = new HashMap<String, String>();
		params.put("token", token);
		template.getForEntity(uri, String.class);
		
		// Then
		var busca = clienteRepository.findById(conta.getId());
		assertTrue(busca.get().isAtivo());
	}
	
	@Test
	public void deve_reenviar_token_de_ativacao() throws Exception {
		// Given
		var token = "LSKDJGLASDLKJ";
		clienteRepository.save((Cliente) conta);
		service.criaTokenVerificacao(conta, token);
		
		var uri = new URI(this.url + "/reenvia-ativacao");
		
		var headers = new HttpHeaders();
		var body = new HttpEntity<>(conta.getEmail(), headers);
		
		var response = template.postForEntity(uri, body, String.class);
		
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

}
