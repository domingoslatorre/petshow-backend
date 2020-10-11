package hyve.petshow.unit.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import hyve.petshow.domain.TipoAnimalEstimacao;
import hyve.petshow.exceptions.NotFoundException;
import hyve.petshow.repository.TipoAnimalEstimacaoRepository;
import hyve.petshow.service.implementation.TipoAnimalEstimacaoServiceImpl;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class TipoAnimalEstimacaoServiceTest {
	@Mock
	private TipoAnimalEstimacaoRepository repository;
	@InjectMocks
	private TipoAnimalEstimacaoServiceImpl service;
	
	@BeforeEach
	public void init() {
		MockitoAnnotations.initMocks(this);
	}
	
	
	@Test
	public void deve_retornar_lista() throws NotFoundException {
		Mockito.when(repository.findAll()).thenReturn(Arrays.asList(new TipoAnimalEstimacao()));
		assertFalse(service.buscarTiposAnimalEstimacao().isEmpty());
	}
	
	@Test
	public void deve_disparar_excecao() {
		Mockito.when(repository.findAll()).thenReturn(new ArrayList<>());
		assertThrows(NotFoundException.class, () -> {
			service.buscarTiposAnimalEstimacao();
		});
	}
	
	

}
