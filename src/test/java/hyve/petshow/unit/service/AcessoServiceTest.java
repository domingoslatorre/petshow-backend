package hyve.petshow.unit.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import hyve.petshow.domain.Cliente;
import hyve.petshow.domain.Prestador;
import hyve.petshow.exceptions.BusinessException;
import hyve.petshow.mock.ClienteMock;
import hyve.petshow.mock.PrestadorMock;
import hyve.petshow.repository.AcessoRepository;
import hyve.petshow.repository.ClienteRepository;
import hyve.petshow.repository.PrestadorRepository;
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
	@InjectMocks
	private AcessoServiceImpl service;
	
	@BeforeEach
	public void init() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void deve_retornar_erro_por_nao_encontrado() {
		Mockito.when(acessoRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());
		
		assertThrows(UsernameNotFoundException.class, () -> {
			service.loadUserByUsername("teste@teste.com");
		});
	}
	
	@Test
	public void deve_retornar_usuario() {
		Mockito.when(acessoRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(ClienteMock.criaCliente()));
		var conta = service.loadUserByUsername("teste@teste.com");
		assertNotNull(conta);
	}
	
	@Test
	public void deve_retornar_por_email() {
		Mockito.when(acessoRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(ClienteMock.criaCliente()));
		
		var conta = service.buscarPorEmail("teste@teste.com");
		assertTrue(conta.isPresent());
	}
	
	@Test
	public void deve_retornar_vazio() {
		Mockito.when(acessoRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.empty());
		
		var conta = service.buscarPorEmail("teste@teste.com");
		assertTrue(conta.isEmpty());
	}
	
	@Test
	public void deve_adicionar_cliente() throws BusinessException {
		List<Cliente> dbMock = new ArrayList<>();
		Mockito.when(passwordEncoder.encode(Mockito.anyString())).thenReturn("BATATA");
		Mockito.when(clienteRepository.save(Mockito.any(Cliente.class))).then(mock -> {
			var cliente = mock.getArgument(0);
			dbMock.add((Cliente) cliente);
			return cliente;
		});
		
		var cliente = ClienteMock.criaCliente();
		service.adicionarConta(cliente);
		assertTrue(dbMock.stream().anyMatch(el -> el.getNome().equals(cliente.getNome())));
		
	}
	
	
	@Test
	public void deve_adicionar_prestador() throws BusinessException {
		List<Prestador> dbMock = new ArrayList<>();
		Mockito.when(passwordEncoder.encode(Mockito.anyString())).thenReturn("BATATA");
		Mockito.when(prestadorRepository.save(Mockito.any(Prestador.class))).then(mock -> {
			var prestador = mock.getArgument(0);
			dbMock.add((Prestador) prestador);
			return prestador;
		});
		
		var prestador = PrestadorMock.criaPrestador();
		service.adicionarConta(prestador);

		assertTrue(dbMock.stream().anyMatch(el -> el.getNome().equals(prestador.getNome())));
	}
}
