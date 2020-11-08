package hyve.petshow.unit.service;

import hyve.petshow.domain.Cliente;
import hyve.petshow.domain.Prestador;
import hyve.petshow.exceptions.BusinessException;
import hyve.petshow.repository.AcessoRepository;
import hyve.petshow.repository.ClienteRepository;
import hyve.petshow.repository.PrestadorRepository;
import hyve.petshow.service.implementation.AcessoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static hyve.petshow.mock.ClienteMock.cliente;
import static hyve.petshow.mock.PrestadorMock.prestador;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.MockitoAnnotations.initMocks;

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
	@InjectMocks
	private AcessoServiceImpl service;

	private Cliente cliente = cliente();
	private Prestador prestador = prestador();

	@BeforeEach
	public void init() {
		initMocks(this);

		doReturn(Optional.of(cliente)).when(acessoRepository).findByEmail(anyString());
		doReturn("token").when(passwordEncoder).encode(anyString());
		doReturn(cliente).when(clienteRepository).save(Mockito.any(Cliente.class));
		doReturn(prestador).when(prestadorRepository).save(Mockito.any(Prestador.class));
	}
	
	@Test
	public void deve_retornar_erro_por_nao_encontrado() {
		doReturn(Optional.empty()).when(acessoRepository).findByEmail(anyString());
		
		assertThrows(UsernameNotFoundException.class, () -> service.loadUserByUsername("teste@teste.com"));
	}
	
	@Test
	public void deve_retornar_usuario() {
		var conta = service.loadUserByUsername("teste@teste.com");

		assertNotNull(conta);
	}
	
	@Test
	public void deve_retornar_por_email() {
		var conta = service.buscarPorEmail("teste@teste.com");

		assertTrue(conta.isPresent());
	}
	
	@Test
	public void deve_retornar_vazio() {
		doReturn(Optional.empty()).when(acessoRepository).findByEmail(anyString());
		
		var conta = service.buscarPorEmail("teste@teste.com");

		assertTrue(conta.isEmpty());
	}
	
	@Test
	public void deve_adicionar_cliente() throws BusinessException {
		var actual = service.adicionarConta(cliente);

		assertNotNull(actual);
	}
	
	
	@Test
	public void deve_adicionar_prestador() throws BusinessException {
		var actual = service.adicionarConta(prestador);

		assertNotNull(actual);
	}

	/*
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
	* */
}
