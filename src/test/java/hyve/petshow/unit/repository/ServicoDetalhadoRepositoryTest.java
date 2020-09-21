package hyve.petshow.unit.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
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

import hyve.petshow.domain.ServicoDetalhado;
import hyve.petshow.domain.Servico;
import hyve.petshow.repository.ServicoDetalhadoRepository;

@SpringBootTest
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@ActiveProfiles("test")
@TestMethodOrder(OrderAnnotation.class)
public class ServicoDetalhadoRepositoryTest {
//	@Autowired
//	private ContaRepository<Cliente> repository;

	@Autowired
	private ServicoDetalhadoRepository repository;

	private ServicoDetalhado mock;

	@AfterEach
	public void deve_remover_item() {
		repository.deleteAll();
		assertTrue(repository.findAll().isEmpty());
	}

	@Test
	@Order(1)
	public void deve_inserir_novo_item() {
		mock = new ServicoDetalhado();
		List<ServicoDetalhado> lista = new ArrayList<>();
		lista.add(mock);
		List<ServicoDetalhado> save = repository.saveAll(lista);
		assertNotNull(save);
		
		assertTrue(!repository.findAll().isEmpty());
	}

	@Test
	@Order(2)
	public void deve_alterar_item() {
		mock = new ServicoDetalhado();
		ServicoDetalhado save = repository.save((ServicoDetalhado) mock);
    	
		BigDecimal p = new BigDecimal(0);
		BigDecimal precoEsperado = p;
		save.setPreco(precoEsperado);
		ServicoDetalhado objetoDB = repository.save((ServicoDetalhado) mock);
		assertEquals(repository.findAll().size(), 1);
		assertEquals(precoEsperado, objetoDB.getPreco());
	}


//	@Test
//	@Order(7)
//	public void deve_retornar_servicos_via_prestador() {
//		mock = new ServicoDetalhado();
//		Prestador prestador = new Prestador();
//		prestador.setID(2L); 
//		
//		List<ServicoDetalhado> lista = new ArrayList();
//		lista.add(mock);
//		prestador.setServicosDetalhados(lista);
//
//		repository.save((ServicoDetalhado) mock);
//
//		List<ServicoDetalhado> busca = repository.findByPrestador(2L);
//		assertTrue(!busca.isEmpty());
//	}

}
