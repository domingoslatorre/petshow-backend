package hyve.petshow.unit.repository;

import hyve.petshow.repository.ContaGenericaRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@ActiveProfiles("test")
@DirtiesContext(classMode = ClassMode.BEFORE_CLASS)
public class ContaRepositoryTest {
	
	@Autowired
	private ContaGenericaRepository contaRepository;
	

	@BeforeEach
	public void init() {
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
