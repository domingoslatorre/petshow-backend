package hyve.petshow.integration.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import hyve.petshow.domain.Servico;
import hyve.petshow.exceptions.NotFoundException;
import hyve.petshow.repository.ServicoRepository;
import hyve.petshow.service.port.ServicoService;

@ActiveProfiles("test")
@SpringBootTest
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class ServicoServiceTest {
	@Autowired
	private ServicoService service;
	@Autowired
	private ServicoRepository repository;

	@AfterEach
	public void apagaDatabase() {
		repository.deleteAll();
	}
	
	@Test
	public void deve_retornar_lista_de_tipos() throws NotFoundException {
		// Given
		var tipos = new ArrayList<Servico>();
		var tipo = new Servico();
		tipo.setNome("BANHO E TOSA");
		tipos.add(tipo);
		repository.saveAll(tipos);
		
		// When
		var tiposDb = service.buscarServicos();
		
		// Then
		assertEquals(tipos, tiposDb);
		
		
	}
	
	@Test
	public void deve_retornar_excecao_de_lista_vazia() {
		assertThrows(NotFoundException.class, () -> {
			service.buscarServicos();
		});
	}
}
