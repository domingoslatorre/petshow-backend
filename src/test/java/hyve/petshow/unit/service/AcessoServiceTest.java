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
}
