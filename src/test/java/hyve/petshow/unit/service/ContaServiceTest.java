package hyve.petshow.unit.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import hyve.petshow.domain.AnimalEstimacao;
import hyve.petshow.domain.Cliente;
import hyve.petshow.domain.Conta;
import hyve.petshow.domain.Login;
import hyve.petshow.mock.ContaMock;
import hyve.petshow.repository.ContaRepository;
import hyve.petshow.service.implementation.ContaServiceImpl;

@TestMethodOrder(OrderAnnotation.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class ContaServiceTest {
	@Mock
	private ContaRepository repository;
	@InjectMocks
	private ContaServiceImpl service;

	@BeforeEach
	public void init() {
		MockitoAnnotations.initMocks(this);
		Mockito.when(repository.findAll()).thenReturn(ContaMock.obterContas());
		Mockito.when(repository.save(Mockito.any(Conta.class))).then(mock -> {
			Conta conta = mock.getArgument(0);
			if (conta.getId() == null) {
				ContaMock.salvaConta(conta);
			} else {
				ContaMock.atualizaConta(conta);
			}
			return conta;
		});
		Mockito.doAnswer(mock -> {
			ContaMock.removeConta(mock.getArgument(0));
			return null;
		}).when(repository).delete(Mockito.any(Conta.class));
		Mockito.when(repository.findById(Mockito.anyLong())).then(mock -> ContaMock.buscaPorId(mock.getArgument(0)));
		Mockito.when(repository.findByLogin(Mockito.any(Login.class)))
				.then(mock -> ContaMock.buscaPorLogin(mock.getArgument(0)));
	}

	@Test
	@Order(1)
	public void deve_inserir_cliente_na_lista() {
		Cliente cliente = new Cliente();
		cliente.setNome("Danilo");
		service.salvaConta(cliente);
		assertTrue(repository.findAll().contains(cliente));
		assertNotNull(cliente.getId());
	}

	@Test
	@Order(2)
	public void deve_atualizar_conta() {
		Conta clienteAAlterar = service.obterContaPorId(1l).get();
		Login login = new Login();
		login.setEmail("teste@teste.com");
		login.setSenha("aslkjdgklsdjg");
		clienteAAlterar.setLogin(login);
		Conta contaDb = service.salvaConta(clienteAAlterar);
		assertEquals(login, clienteAAlterar.getLogin());
		assertEquals(contaDb.getId(), clienteAAlterar.getId());
	}

	@Test
	@Order(3)
	public void deve_atualizar_animal() {
		Cliente cliente = new Cliente();
		cliente.setId(2l);
		ArrayList<AnimalEstimacao> animaisEstimacao = new ArrayList<AnimalEstimacao>();
		AnimalEstimacao animalTeste = new AnimalEstimacao();
		animalTeste.setNome("Joao");
		animaisEstimacao.add(animalTeste);
		cliente.setAnimaisEstimacao(animaisEstimacao);

		Cliente salvaConta = (Cliente) service.salvaConta(cliente);
		assertTrue(salvaConta.getAnimaisEstimacao().contains(animalTeste));

	}

	@Test
	@Order(4)
	public void deve_encontrar_conta_correta() {
		Optional<Conta> obterContaPorId = service.obterContaPorId(1l);
		assertEquals("Teste", obterContaPorId.get().getNome());
	}

	@Test
	@Order(5)
	public void deve_retornar_objeto_vazio() {
		Optional<Conta> obterContaPorId = service.obterContaPorId(5l);
		assertTrue(obterContaPorId.isEmpty());
	}
	
	@Test
	@Order(6)
	public void deve_encontrar_elemento_por_login() {
		Login login = new Login();
		login.setEmail("teste@teste.com");
		login.setSenha("aslkjdgklsdjg");
		Optional<Conta> obterPorLogin = service.obterPorLogin(login);
		assertTrue(obterPorLogin.isPresent());
		assertTrue(obterPorLogin.get().getId() == 1);
	}
	
	@Test
	@Order(7)
	public void deve_encontrar_todos_os_elementos() {
		assertTrue(!service.obterContas().isEmpty());
	}

}
