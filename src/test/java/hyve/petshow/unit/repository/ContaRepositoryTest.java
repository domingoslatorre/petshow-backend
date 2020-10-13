package hyve.petshow.unit.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;

import hyve.petshow.domain.Cliente;
import hyve.petshow.domain.Prestador;
import hyve.petshow.domain.enums.TipoConta;
import hyve.petshow.mock.ClienteMock;
import hyve.petshow.mock.PrestadorMock;
import hyve.petshow.repository.ContaGenericaRepository;

@SpringBootTest
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@ActiveProfiles("test")
@DirtiesContext(classMode = ClassMode.BEFORE_CLASS)
public class ContaRepositoryTest {
	
	@Autowired
	private ContaGenericaRepository contaRepository;
	

	@BeforeEach
	public void init() {
		var cliente = contaRepository.save(ClienteMock.criaCliente());
		var prestador = contaRepository.save(PrestadorMock.criaPrestador());
	}
	
	@AfterEach
	public void limpar() {
		contaRepository.deleteAll();
	}
	
//	@Test
//	public void deve_retornar_cliente() {
//		var findByLogin = contaRepository.findByLogin(cliente.getLogin());
//		assertEquals(TipoConta.CLIENTE, findByLogin.get().getTipo());
//	}
//	
//	@Test
//	public void deve_retornar_prestador() {
//		var findByLogin = contaRepository.findByLogin(prestador.getLogin());
//		assertEquals(TipoConta.PRESTADOR_AUTONOMO, findByLogin.get().getTipo());
//	}
	

}
