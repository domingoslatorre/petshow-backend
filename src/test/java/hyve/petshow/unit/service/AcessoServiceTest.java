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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static hyve.petshow.mock.ClienteMock.cliente;
import static hyve.petshow.mock.PrestadorMock.prestador;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
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
	
	@BeforeEach
	public void init() {
		initMocks(this);
	}
	
	@Test
	public void deve_retornar_erro_por_nao_encontrado() {
		when(acessoRepository.findByEmail(anyString())).thenReturn(Optional.empty());
		
		assertThrows(UsernameNotFoundException.class, () -> service.loadUserByUsername("teste@teste.com"));
	}
	
	@Test
	public void deve_retornar_usuario() {
		when(acessoRepository.findByEmail(anyString())).thenReturn(Optional.of(cliente()));

		var conta = service.loadUserByUsername("teste@teste.com");

		assertNotNull(conta);
	}
	
	@Test
	public void deve_retornar_por_email() {
		when(acessoRepository.findByEmail(anyString())).thenReturn(Optional.of(cliente()));
		
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
		when(passwordEncoder.encode(anyString())).thenReturn("token");
		when(clienteRepository.save(Mockito.any(Cliente.class))).then(mock -> {
			var cliente = mock.getArgument(0);
			dbMock.add((Cliente) cliente);
			return cliente;
		});
		
		var cliente = cliente();
		service.adicionarConta(cliente);

		assertTrue(dbMock.stream().anyMatch(el -> el.getNome().equals(cliente.getNome())));
	}
	
	
	@Test
	public void deve_adicionar_prestador() throws BusinessException {
		List<Prestador> dbMock = new ArrayList<>();
		when(passwordEncoder.encode(anyString())).thenReturn("token");
		when(prestadorRepository.save(Mockito.any(Prestador.class))).then(mock -> {
			var prestador = mock.getArgument(0);
			dbMock.add((Prestador) prestador);
			return prestador;
		});
		
		var prestador = prestador();
		service.adicionarConta(prestador);

		assertTrue(dbMock.stream().anyMatch(el -> el.getNome().equals(prestador.getNome())));
	}
}
