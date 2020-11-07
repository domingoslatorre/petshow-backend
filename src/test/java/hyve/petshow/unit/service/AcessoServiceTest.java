package hyve.petshow.unit.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static hyve.petshow.mock.ClienteMock.criaCliente;
import static hyve.petshow.mock.PrestadorMock.criaPrestador;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import hyve.petshow.domain.Cliente;
import hyve.petshow.domain.Prestador;
import hyve.petshow.domain.VerificationToken;
import hyve.petshow.domain.enums.TipoConta;
import hyve.petshow.exceptions.BusinessException;
import hyve.petshow.exceptions.NotFoundException;
import hyve.petshow.repository.AcessoRepository;
import hyve.petshow.repository.ClienteRepository;
import hyve.petshow.repository.PrestadorRepository;
import hyve.petshow.repository.VerificationTokenRepository;
import hyve.petshow.service.implementation.AcessoServiceImpl;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class AcessoServiceTest {

	@Mock
	private AcessoRepository acessoRepository;
	@Mock
	private ClienteRepository clienteRepository;
	@Mock
	private PrestadorRepository prestadorRepository;
	@Mock
	private PasswordEncoder passwordEncoder;
	@Mock
	private VerificationTokenRepository tokenRepository;
	@InjectMocks
	private AcessoServiceImpl service;
	
	@BeforeEach
	public void init() {
		initMocks(this);
	}
	
	@Test
	public void deve_retornar_erro_por_nao_encontrado() {
		when(acessoRepository.findByEmail(anyString())).thenReturn(Optional.empty());
		
		assertThrows(UsernameNotFoundException.class, () -> {
			service.loadUserByUsername("teste@teste.com");
		});
	}
	
	@Test
	public void deve_retornar_usuario() {
		when(acessoRepository.findByEmail(anyString())).thenReturn(Optional.of(criaCliente()));
		var conta = service.loadUserByUsername("teste@teste.com");
		assertNotNull(conta);
	}
	
	@Test
	public void deve_retornar_por_email() {
		when(acessoRepository.findByEmail(anyString())).thenReturn(Optional.of(criaCliente()));
		
		var conta = service.buscarPorEmail("teste@teste.com");
		assertTrue(conta.isPresent());
	}
	
	@Test
	public void deve_retornar_vazio() {
		when(acessoRepository.findByEmail(anyString())).thenReturn(Optional.empty());
		
		var conta = service.buscarPorEmail("teste@teste.com");
		assertTrue(conta.isEmpty());
	}
	
	@Test
	public void deve_adicionar_cliente() throws BusinessException {
		List<Cliente> dbMock = new ArrayList<>();
		when(passwordEncoder.encode(anyString())).thenReturn("BATATA");
		when(clienteRepository.save(any(Cliente.class))).then(mock -> {
			var cliente = mock.getArgument(0);
			dbMock.add((Cliente) cliente);
			return cliente;
		});
		
		var cliente = criaCliente();
		service.adicionarConta(cliente);
		assertTrue(dbMock.stream().anyMatch(el -> el.getNome().equals(cliente.getNome())));
		
	}
	
	
	@Test
	public void deve_adicionar_prestador() throws BusinessException {
		List<Prestador> dbMock = new ArrayList<>();
		when(passwordEncoder.encode(anyString())).thenReturn("BATATA");
		when(prestadorRepository.save(any(Prestador.class))).then(mock -> {
			var prestador = mock.getArgument(0);
			dbMock.add((Prestador) prestador);
			return prestador;
		});
		
		var prestador = criaPrestador();
		service.adicionarConta(prestador);
		assertTrue(dbMock.stream().anyMatch(el -> el.getNome().equals(prestador.getNome())));
		
	}
	
	@Test
	public void deve_buscar_por_email() throws Exception {
		var mock = criaPrestador();
		when(acessoRepository.findByEmail(any())).thenReturn(Optional.of(mock));
		var conta = service.buscarConta(mock.getEmail());
		assertNotNull(conta);
	}
	
	@Test
	public void deve_retornar_erro_por_nao_encontrar_conta() throws Exception {
		when(acessoRepository.findByEmail(any())).thenReturn(Optional.empty());
		assertThrows(NotFoundException.class, () -> {
			service.buscarConta("aaa");
		});
	}
	
	@Test
	public void deve_salvar_token() {
		List<VerificationToken> dbMock = new ArrayList<>();
		when(tokenRepository.save(any(VerificationToken.class))).then(mock -> {
			var argument = (VerificationToken) mock.getArgument(0);
			dbMock.add(argument);
			return argument;
		});
		
		var token = "kasljgaskld";
		var conta = criaCliente();
		
		service.criaTokenVerificacao(conta, token);
		assertTrue(dbMock.size() > 0);
		assertTrue(dbMock.contains(new VerificationToken(conta, token)));
		
	}
	
	@Test
	public void deve_buscar_token() throws Exception {
		var tokenStr = "asldkjgaskdjlg";
		var token = new VerificationToken(criaCliente(), tokenStr);
		when(tokenRepository.findByToken(anyString())).thenReturn(Optional.of(token));
		var tokenDb = service.buscarTokenVerificacao(tokenStr);
		assertEquals(token, tokenDb);
		
	}
	
	@Test
	public void deve_retornar_erro_em_busca_por_token() {
		when(tokenRepository.findByToken(anyString())).thenReturn(Optional.empty());
		assertThrows(NotFoundException.class, () -> {
			service.buscarTokenVerificacao("asdglkjsdg");
		});
	}
	
	@Test
	public void deve_ativar_conta() throws Exception {
		var token = "laskjasldkjgg";
		var conta = criaCliente();
		conta.setEnabled(false);
		var verificationToken = new VerificationToken(conta, token);
		when(acessoRepository.findByEmail(anyString())).thenReturn(Optional.of(conta));
		when(tokenRepository.findByToken(anyString())).thenReturn(Optional.of(verificationToken));
		when(acessoRepository.save(any())).thenReturn(conta);
		service.ativaConta(token);
		assertTrue(conta.getEnabled());
	}
	
	@Test
	public void deve_retornar_erro_por_conta_ja_ativa() {
		var token = "laskjasldkjgg";
		var conta = criaCliente();
		conta.setEnabled(true);
		var verificationToken = new VerificationToken(conta, token);
		when(acessoRepository.findByEmail(anyString())).thenReturn(Optional.of(conta));
		when(tokenRepository.findByToken(anyString())).thenReturn(Optional.of(verificationToken));
		assertThrows(BusinessException.class, () -> {
			service.ativaConta(token);
		});
	}
	
	@Test
	public void deve_retornar_erro_ao_adicionar_conta() {
		var cliente = criaCliente();
		cliente.setTipo(TipoConta.CLIENTE_PRESTADOR_AUTONOMO);
		assertThrows(BusinessException.class, () -> {
			service.adicionarConta(cliente);
		});
	}
}
