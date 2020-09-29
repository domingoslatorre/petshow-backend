package hyve.petshow.unit.domain;


import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import hyve.petshow.domain.Servico;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class ServicoTest {
	@Test
	public void deve_ter_metodos_implementados() {
		final Class<Servico> servico =  Servico.class;
		assertPojoMethodsFor(servico).areWellImplemented();
	}
}

