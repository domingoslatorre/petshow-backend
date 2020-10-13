package hyve.petshow.unit.domain;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import hyve.petshow.domain.Cliente;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class ClienteTest {
	@Test
	public void deve_ter_metodos_implementados() {
		final Class<Cliente> cliente =  Cliente.class;
		assertPojoMethodsFor(cliente).areWellImplemented();
	}
}
