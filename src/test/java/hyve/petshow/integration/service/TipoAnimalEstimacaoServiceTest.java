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

import hyve.petshow.domain.TipoAnimalEstimacao;
import hyve.petshow.exceptions.NotFoundException;
import hyve.petshow.repository.TipoAnimalEstimacaoRepository;
import hyve.petshow.service.port.TipoAnimalEstimacaoService;

@ActiveProfiles("test")
@SpringBootTest
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class TipoAnimalEstimacaoServiceTest {
	
	@Autowired
	private TipoAnimalEstimacaoService service;
	@Autowired
	private TipoAnimalEstimacaoRepository repository;
	
	@AfterEach
	public void apagaDatabase() {
		repository.deleteAll();
	}
	
	@Test
	public void deve_retornar_lista_de_tipos() throws NotFoundException {
		// Given
		var tipos = new ArrayList<TipoAnimalEstimacao>();
		var tipo = new TipoAnimalEstimacao();
		tipo.setNome("CACHORRO");
		tipos.add(tipo);
		repository.saveAll(tipos);
		
		// When
		var tiposDb = service.buscarTiposAnimalEstimacao();
		
		// Then
		assertEquals(tipos, tiposDb);
		
		
	}
	
	@Test
	public void deve_retornar_excecao_de_lista_vazia() {
		assertThrows(NotFoundException.class, () -> {
			service.buscarTiposAnimalEstimacao();
		});
	}

}
