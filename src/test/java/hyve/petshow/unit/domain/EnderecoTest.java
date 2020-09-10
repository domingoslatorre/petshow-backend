package hyve.petshow.unit.domain;
import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import hyve.petshow.domain.Endereco;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class EnderecoTest {
	@Test
	public void deve_ter_metodos_implementados() {
		final Class<Endereco> endereco =  Endereco.class;
		assertPojoMethodsFor(endereco).areWellImplemented();
	}
}
