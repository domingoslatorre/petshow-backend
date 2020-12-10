package hyve.petshow.integration.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ActiveProfiles;

import hyve.petshow.domain.Cliente;
import hyve.petshow.domain.Conta;
import hyve.petshow.domain.Prestador;
import hyve.petshow.exceptions.BusinessException;
import hyve.petshow.exceptions.NotFoundException;

import static hyve.petshow.mock.ContaMock.contaCliente;
import static hyve.petshow.mock.ContaMock.contaPrestador;
import hyve.petshow.repository.AcessoRepository;
import hyve.petshow.repository.ClienteRepository;
import hyve.petshow.repository.PrestadorRepository;
import hyve.petshow.repository.VerificationTokenRepository;
import hyve.petshow.service.port.AcessoService;

@ActiveProfiles("test")
@SpringBootTest
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class AcessoServiceTest {
	@Autowired
	private AcessoRepository repository;
	@Autowired
	private AcessoService service;
	@Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private PrestadorRepository prestadorRepository;
    @Autowired
    private VerificationTokenRepository tokenRepository;
	private Conta conta;
	
	@BeforeEach
	public void init() {
		conta = new Cliente(contaCliente());
		conta.setId(null);
	}
	
	@AfterEach
	public void limpaRepositorios() {
		tokenRepository.deleteAll();
		clienteRepository.deleteAll();
		prestadorRepository.deleteAll();
		repository.deleteAll();
	}
	
	@Test
	public void deve_adicionar_cliente() throws BusinessException {
		// Given
		var contaDb = service.adicionarConta(conta);
		
		// Then
		assertTrue(clienteRepository.existsById(contaDb.getId()));
	}
	
	@Test
	public void deve_adicionar_prestador() throws BusinessException {
		// Given
		conta = new Prestador(contaPrestador());
		conta.setId(null);
		var contaDb = service.adicionarConta(conta);
		
		// Then
		assertTrue(prestadorRepository.existsById(contaDb.getId()));
	}
	
	@Test
	public void deve_carregar_por_email() throws BusinessException {
		// Given
		var contaDb = service.adicionarConta(conta);
		
		// When
		var user = service.loadUserByUsername(contaDb.getEmail());
		
		// Then
		assertNotNull(user);
	}
	
	@Test
	public void deve_retornar_erro_por_nao_encontrar_usuario() {
		assertThrows(UsernameNotFoundException.class, () -> {
			service.loadUserByUsername(conta.getEmail());
		});
	}
	
	@Test
	public void deve_encontrar_por_Email() throws BusinessException {
		// Given
		var contaDb = service.adicionarConta(conta);
		
		// When
		var busca = service.buscarPorEmail(contaDb.getEmail());
		
		// Then
		assertTrue(busca.isPresent());
	}
	
	@Test
	public void deve_retornar_vazio_ao_buscar_por_email_nao_existente() {
		// Given
		var busca = service.buscarPorEmail(conta.getEmail());
		
		// Then
		assertTrue(busca.isEmpty());
	}
	
	@Test
	public void deve_retornar_conta() throws Exception {
		// Given
		service.adicionarConta(conta);
		
		// When
		var busca = service.buscarContaPorEmail(conta.getEmail());
		
		// Then
		assertNotNull(busca);
	}
	
	@Test
	public void deve_Retornar_erro_por_conta_nao_encontrada() {
		assertThrows(NotFoundException.class, () -> {
			service.buscarContaPorEmail(conta.getEmail());
		});
	}
	
	@Test
	public void deve_criar_token_verificacao() throws BusinessException {
		// Given
		var cliente = clienteRepository.save((Cliente) conta);
		var token = "ALSKJGA";
		// When
		service.criaTokenVerificacao(cliente, token);
		
		// Then
		assertNotNull(tokenRepository.findByToken(token));
	}
	
	@Test
	public void deve_ativar_conta() throws Exception {
		// Given
		var cliente = clienteRepository.save((Cliente) conta);
		var token = "ALSKJGA";
		service.criaTokenVerificacao(cliente, token);
		// When
		service.ativaConta(token);
		
		// Then
		var busca = clienteRepository.findById(cliente.getId());
		assertTrue(busca.get().isAtivo());
	}
	
	@Test
	public void deve_dar_erro_ao_tentar_ativar_conta_ja_ativa() throws Exception {
		// Given
		var cliente = clienteRepository.save((Cliente) conta);
		var token = "ALSKJGA";
		service.criaTokenVerificacao(cliente, token);
		
		// Then
		
		service.ativaConta(token);
		assertThrows(BusinessException.class, () -> {
			service.ativaConta(token);
		});
	}
	
	@Test
	public void deve_retornar_erro_ao_nao_encontrar_token() {
		assertThrows(NotFoundException.class, () -> {
			service.buscarTokenVerificacao("AAA");
		});
	}
}
