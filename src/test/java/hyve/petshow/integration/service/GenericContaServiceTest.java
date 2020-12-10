package hyve.petshow.integration.service;

import static hyve.petshow.mock.ContaMock.contaPrestador;
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

import hyve.petshow.domain.Conta;
import hyve.petshow.domain.Prestador;
import hyve.petshow.exceptions.NotFoundException;
import hyve.petshow.repository.GenericContaRepository;
import hyve.petshow.service.port.GenericContaService;

@ActiveProfiles("test")
@SpringBootTest
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class GenericContaServiceTest {
	@Autowired
	private GenericContaRepository repository;
	@Autowired
	private GenericContaService service;
	
	private Conta conta;
	
	@BeforeEach
	public void init() {
		conta = new Prestador(contaPrestador());
		conta.setId(null);
	}
	

	@AfterEach
	public void deletar() {
		repository.deleteAll();
	}
	
	
	@Test
	public void deve_retornar_por_id() throws Exception {
		var prestadorDb = repository.save(conta);
		var busca = service.buscarPorId(prestadorDb.getId());
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
		var prestadorDb = repository.save(conta);
		var esperado = "1192034393";
		prestadorDb.setTelefone(esperado);
		
		// When
		service.atualizarConta(prestadorDb.getId(), prestadorDb);
		
		// Then
		var busca = repository.findById(prestadorDb.getId()).get();
		
		assertEquals(esperado, busca.getTelefone());
		
		
	}
	
	@Test
	public void deve_desativar_conta() throws Exception {
		// Given
		var prestadorDb = repository.save(conta);
		service.atualizarConta(prestadorDb.getId(), prestadorDb);
		assertTrue(service.buscarPorId(prestadorDb.getId()).isAtivo());
		
		// when
		service.desativarConta(prestadorDb.getId());
		// Then
		assertFalse(service.buscarPorId(prestadorDb.getId()).isAtivo());		
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
