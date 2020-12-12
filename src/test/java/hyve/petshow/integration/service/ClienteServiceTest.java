package hyve.petshow.integration.service;

import static hyve.petshow.mock.ContaMock.contaCliente;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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
import org.springframework.test.context.ActiveProfiles;

import hyve.petshow.domain.Cliente;
import hyve.petshow.exceptions.NotFoundException;
import hyve.petshow.repository.ClienteRepository;
import hyve.petshow.service.port.ClienteService;

@ActiveProfiles("test")
@SpringBootTest
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class ClienteServiceTest {
	@Autowired
	private ClienteRepository repository;
	@Autowired
	private ClienteService service;
	
	private Cliente conta;
	
	@BeforeEach
	public void init() {
		conta = new Cliente(contaCliente());
		conta.setId(null);
	}
	

	@AfterEach
	public void deletar() {
		repository.deleteAll();
	}
	
	
	@Test
	public void deve_retornar_por_id() throws Exception {
		var cliente = repository.save(conta);
		var busca = service.buscarPorId(cliente.getId());
		assertNotNull(busca);
	}
	
	@Test
	public void deve_retornar_erro_por_id_nao_encontrado() {
		assertThrows(NotFoundException.class, () -> {
			service.buscarPorId(10l);
		});
	}
	
	@Test
	public void deve_atualizar_conta() throws Exception {
		// Given
		var cliente = repository.save(conta);
		var esperado = "1192034393";
		cliente.setTelefone(esperado);
		
		// When
		service.atualizarConta(cliente.getId(), cliente);
		
		// Then
		var busca = repository.findById(cliente.getId()).get();
		
		assertEquals(esperado, busca.getTelefone());
		
		
	}
	
	@Test
	public void deve_desativar_conta() throws Exception {
		// Given
		var cliente = repository.save(conta);
		service.atualizarConta(cliente.getId(), cliente);
		assertTrue(service.buscarPorId(cliente.getId()).isAtivo());
		
		// when
		service.desativarConta(cliente.getId());
		// Then
		assertFalse(service.buscarPorId(cliente.getId()).isAtivo());		
	}
	
	@Test
	public void deve_retornar_por_email() {
		// Given
		repository.save(conta);
		
		// Then
		assertTrue(service.buscarPorEmail(conta.getEmail()).isPresent());
	}
	
	@Test
	public void deve_retornar_vazio_em_busca_por_email() {
		assertTrue(service.buscarPorEmail(conta.getEmail()).isEmpty());
	}
	
	

}
