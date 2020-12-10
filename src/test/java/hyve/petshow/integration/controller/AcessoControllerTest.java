package hyve.petshow.integration.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static hyve.petshow.mock.ContaMock.contaCliente;

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
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import hyve.petshow.controller.converter.ContaConverter;
import hyve.petshow.controller.representation.ContaRepresentation;
import hyve.petshow.domain.Cliente;
import hyve.petshow.repository.AcessoRepository;
import hyve.petshow.repository.ClienteRepository;
import hyve.petshow.repository.PrestadorRepository;
import hyve.petshow.repository.VerificationTokenRepository;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class AcessoControllerTest {
	@LocalServerPort
	private Integer port;
	@Autowired
	private TestRestTemplate template;
	@Autowired
	private AcessoRepository acessoRepository;
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private PrestadorRepository prestadorRepository;
	@Autowired
	private VerificationTokenRepository tokenRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private ContaConverter converter;

	private String url;
	private ContaRepresentation conta;

	@BeforeEach
	public void init() {
		conta = converter.toRepresentation(new Cliente(contaCliente()));
		conta.setId(null);
		url = "http://localhost:" + port + "/acesso";
	}

	@Test
	public void deve_realizar_cadastro() {
		var url = this.url + "/cadastro";
		var headers = new HttpHeaders();
		var request = new HttpEntity<>(conta, headers);
		var response = template.postForEntity(url, request, String.class);

		assertEquals(HttpStatus.CREATED, response.getStatusCode());
	}

}
