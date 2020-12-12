package hyve.petshow.integration.service;

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

import hyve.petshow.domain.AnimalEstimacao;
import hyve.petshow.domain.Cliente;
import hyve.petshow.domain.TipoAnimalEstimacao;
import hyve.petshow.exceptions.BusinessException;
import hyve.petshow.exceptions.NotFoundException;

import static hyve.petshow.mock.ContaMock.contaCliente;
import hyve.petshow.repository.AnimalEstimacaoRepository;
import hyve.petshow.repository.ClienteRepository;
import hyve.petshow.repository.TipoAnimalEstimacaoRepository;
import hyve.petshow.service.port.AnimalEstimacaoService;
import static hyve.petshow.util.PagingAndSortingUtils.geraPageable;

@ActiveProfiles("test")
@SpringBootTest
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class AnimalEstimacaoServiceTest {
	@Autowired
	private AnimalEstimacaoService service;
	@Autowired
	private AnimalEstimacaoRepository repository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private TipoAnimalEstimacaoRepository tipoRepository;
	
	private Cliente cliente;
	private AnimalEstimacao animalEstimacao;
	private TipoAnimalEstimacao tipoAnimal;
	
	@BeforeEach
	public void init() {
		cliente = new Cliente(contaCliente());
		cliente.setId(null);
		clienteRepository.save(cliente);
		
		tipoAnimal = new TipoAnimalEstimacao();
		tipoAnimal.setNome("Cachorro");
		tipoRepository.save(tipoAnimal);
		
		animalEstimacao = new AnimalEstimacao();
		animalEstimacao.setDonoId(cliente.getId());
		animalEstimacao.setTipo(tipoAnimal);
		animalEstimacao.setNome("Bidu");
		animalEstimacao.setPelagem("Peludo");
		animalEstimacao.setPorte("Pequeno");	
	}
	
	@AfterEach
	public void limpaRepositorios() {
		repository.deleteAll();
		clienteRepository.deleteAll();
		tipoRepository.deleteAll();
	}
	
	@Test
	public void deve_adicionar_animal() {
		// Given
		var animal = service.adicionarAnimalEstimacao(animalEstimacao);
		
		// When
		var busca = repository.findById(animal.getId());
		
		// Then
		assertTrue(busca.isPresent());
	}
	
	@Test
	public void deve_buscar_por_id() throws NotFoundException {
		// Given
		var animal = service.adicionarAnimalEstimacao(animalEstimacao);
		
		// When
		var busca = service.buscarAnimalEstimacaoPorId(animal.getId());
		
		// Then
		assertNotNull(busca);
	}
	
	@Test
	public void deve_retornar_erro_em_busca_por_id() {
		assertThrows(NotFoundException.class, () -> {
			service.buscarAnimalEstimacaoPorId(10l);
		});
	}
	
	@Test
	public void deve_retornar_pagina_pelo_id_do_dono() throws NotFoundException {
		// Given
		var animal = service.adicionarAnimalEstimacao(animalEstimacao);
		
		// When
		var busca = service.buscarAnimaisEstimacaoPorDono(animal.getDonoId(), geraPageable(0, 5));
		
		// Then
		assertFalse(busca.isEmpty());
	}
	
	@Test
	public void deve_retornar_erro_por_pagina_vazia() {
		assertThrows(NotFoundException.class, () -> {
			service.buscarAnimaisEstimacaoPorDono(cliente.getId(), geraPageable(0, 5));
		});
	}
	
	@Test
	public void deve_atualizar_animal() throws NotFoundException, BusinessException {
		// Given
		var animal = service.adicionarAnimalEstimacao(animalEstimacao);
		
		// When
		var esperado = "Kiko";
		animal.setNome(esperado);
		service.atualizarAnimalEstimacao(animal.getId(),animal);
		
		// Then
		var busca = service.buscarAnimalEstimacaoPorId(animal.getId());
		
		assertEquals(esperado, busca.getNome());
	}
	
	@Test
	public void deve_retornar_erro_ao_atualizar_por_donos_diferentes() {
		// Given
		var animal = service.adicionarAnimalEstimacao(animalEstimacao);
		
		// Then
		animal.setDonoId(cliente.getId() + 1);
		assertThrows(BusinessException.class, () -> {
			service.atualizarAnimalEstimacao(animal.getId(), animal);
		});
	}
	
	@Test
	public void deve_remover_animal() throws BusinessException, NotFoundException {
		// Given
		var animal = service.adicionarAnimalEstimacao(animalEstimacao);
		
		// When
		service.removerAnimalEstimacao(animal.getId(), animal.getDonoId());
		
		// Then
		assertFalse(repository.existsById(animal.getId()));
	}
	
	@Test
	public void deve_dar_erro_ao_remover_animal_por_dono_incorreto() {
		// Given
		var animal = service.adicionarAnimalEstimacao(animalEstimacao);
		
		// Then
		assertThrows(BusinessException.class, () -> {
			service.removerAnimalEstimacao(animal.getId(), animal.getDonoId() + 1);
		});
	}
	

}
