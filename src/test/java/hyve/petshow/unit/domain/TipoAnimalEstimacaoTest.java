package hyve.petshow.unit.domain;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import hyve.petshow.domain.TipoAnimalEstimacao;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class TipoAnimalEstimacaoTest {
	@Test
	public void deve_ter_metodos_implementados() {
		// dado
		final Class<TipoAnimalEstimacao> tipo = TipoAnimalEstimacao.class;

		// entao
		assertPojoMethodsFor(tipo).areWellImplemented();
	}
}
