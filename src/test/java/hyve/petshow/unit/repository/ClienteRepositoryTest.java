package hyve.petshow.unit.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import hyve.petshow.domain.AnimalEstimacao;
import hyve.petshow.domain.Cliente;
import hyve.petshow.domain.Conta;
import hyve.petshow.domain.Login;
import hyve.petshow.repository.ClienteRepository;

@SpringBootTest
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@ActiveProfiles("test")
@TestMethodOrder(OrderAnnotation.class)
public class ClienteRepositoryTest {
//	@Autowired
//	private ContaRepository<Cliente> repository;

	@Autowired
	private ClienteRepository repository;

	private Conta contaMock;

	@AfterEach
	public void removeItem() {
		repository.deleteAll();
		assertTrue(repository.findAll().isEmpty());
	}

	@Test
	@Order(1)
	public void deve_inserir_novo_item() {
		contaMock = new Cliente();
		repository.save((Cliente) contaMock);
		assertNotNull(contaMock.getId());
		assertTrue(repository.existsById(contaMock.getId()));

	}

	@Test
	@Order(2)
	public void deve_alterar_item() {
		contaMock = new Cliente();
		Cliente save = repository.save((Cliente) contaMock);
		String nomeEsperado = "Nome esperado";
		save.setNome(nomeEsperado);
		Conta contaDb = repository.save((Cliente) contaMock);
		assertEquals(repository.findAll().size(), 1);
		assertEquals(nomeEsperado, contaDb.getNome());
	}

	@Test
	@Order(3)
	public void deve_salvar_um_cliente() {
		contaMock = new Cliente();
		Conta save = repository.save((Cliente) contaMock);
		assertNotNull(save);
		Optional<Cliente> objetoDb = repository.findById(save.getId());
		assertTrue(objetoDb.isPresent());
	}

	@Test
	@Order(4)
	public void deve_atualizar_lista_de_animais() {
		contaMock = new Cliente();
		Cliente save = repository.save((Cliente) contaMock);
		ArrayList<AnimalEstimacao> animaisEstimacao = new ArrayList<AnimalEstimacao>();
		AnimalEstimacao animal = new AnimalEstimacao();
		animal.setNome("Jo�ozinho");
		animaisEstimacao.add(animal);
		save.setAnimaisEstimacao(animaisEstimacao);

		Cliente contaDb = repository.save(save);

		assertTrue(!contaDb.getAnimaisEstimacao().isEmpty());
	}

	@Test
	@Order(5)
	public void deve_remover_animal() {
		contaMock = new Cliente();
		Cliente save = repository.save((Cliente) contaMock);
		ArrayList<AnimalEstimacao> animaisEstimacao = new ArrayList<AnimalEstimacao>();
		AnimalEstimacao animal = new AnimalEstimacao();
		animal.setNome("Jo�ozinho");
		animaisEstimacao.add(animal);
		save.setAnimaisEstimacao(animaisEstimacao);

		Cliente contaDb = repository.save(save);

		contaDb.setAnimaisEstimacao(new ArrayList<AnimalEstimacao>());

		Cliente esperado = repository.save(contaDb);

		assertTrue(esperado.getAnimaisEstimacao().isEmpty());
	}

	@Test
	@Order(6)
	public void deve_atualizar_animal() {
		contaMock = new Cliente();
		Cliente save = repository.save((Cliente) contaMock);
		ArrayList<AnimalEstimacao> animaisEstimacao = new ArrayList<AnimalEstimacao>();
		AnimalEstimacao animal = new AnimalEstimacao();
		animal.setNome("Jo�ozinho");
		animaisEstimacao.add(animal);
		save.setAnimaisEstimacao(animaisEstimacao);

		Cliente contaDb = repository.save(save);
		AnimalEstimacao animalEstimacao = contaDb.getAnimaisEstimacao().get(0);
		animalEstimacao.setNome("Pedrinho");
		contaDb.setAnimaisEstimacao(Arrays.asList(animalEstimacao));
		Cliente esperado = repository.save(contaDb);

		assertTrue(esperado.getAnimaisEstimacao().contains(animalEstimacao));

	}

	@Test
	@Order(7)
	public void deve_retornar_usuario_via_login() {
		contaMock = new Cliente();
		Login login = new Login();
		login.setEmail("teste@teste.com");
		login.setSenha("teste1234");

		contaMock.setLogin(login);

		repository.save((Cliente) contaMock);

		Optional<Cliente> busca = repository.findByLogin(login);
		assertTrue(busca.isPresent());
	}

	@Test
	@Order(8)
	public void deve_retornar_usuario_via_cpf() {
		contaMock = new Cliente();
		String cpf = "444444444";
		contaMock.setCpf(cpf);
		repository.save((Cliente) contaMock);
		Optional<Cliente> busca = repository.findByCpf(cpf);
		assertTrue(busca.isPresent());
	}

	@Test
	@Order(9)
	public void deve_retornar_por_email() {
		contaMock = new Cliente();
		Login login = new Login();
		String email = "teste@teste.com";
		login.setEmail(email);
		contaMock.setLogin(login);
		repository.save((Cliente) contaMock);
		Optional<Cliente> busca = repository.findByEmail(email);
		assertTrue(busca.isPresent());
	}

}
