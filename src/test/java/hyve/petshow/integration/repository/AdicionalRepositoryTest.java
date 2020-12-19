package hyve.petshow.integration.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import hyve.petshow.domain.Adicional;
import hyve.petshow.repository.AdicionalRepository;

@ActiveProfiles("test")
@SpringBootTest
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class AdicionalRepositoryTest {
	@Autowired
	private AdicionalRepository repository;
	
	@Test
	public void deve_retornar_lista_de_animais() {
		// Given
		var adicionais = new ArrayList<Adicional>() {{
			var adicional1 = new Adicional();
			var adicional2 = new Adicional();
			var adicional3 = new Adicional();
			adicional1.setNome("Teste1");
			adicional2.setNome("Teste2");
			adicional3.setNome("Teste3");
			
			adicional1.setDescricao("Teste1");
			adicional2.setNome("Testeeee");
			adicional3.setNome("Testeeeeee");
			
			adicional1.setPreco(BigDecimal.valueOf(20));
			adicional2.setPreco(BigDecimal.valueOf(25));
			adicional3.setPreco(BigDecimal.valueOf(22));
			
			adicional1.setIdServicoDetalhado(1l);
			adicional2.setIdServicoDetalhado(1l);
			adicional3.setIdServicoDetalhado(1l);
			add(adicional1);
			add(adicional2);
			add(adicional3);
		}};
		repository.saveAll(adicionais);
		
		// When
		var busca = repository.findByServicoDetalhado(1l);
		
		// then
		assertEquals(3, busca.size());
	}
}
