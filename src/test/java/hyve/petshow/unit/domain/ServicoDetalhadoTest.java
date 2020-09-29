package hyve.petshow.unit.domain;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import hyve.petshow.domain.ServicoDetalhado;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class ServicoDetalhadoTest {
	@Test
	public void deve_ter_metodos_implementados() {
		final Class<ServicoDetalhado> servicoDetalhado =  ServicoDetalhado.class;
		assertPojoMethodsFor(servicoDetalhado).areWellImplemented();
	}
}
