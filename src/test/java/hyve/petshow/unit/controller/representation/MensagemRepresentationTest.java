package hyve.petshow.unit.controller.representation;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import hyve.petshow.controller.representation.ContaRepresentation;
import hyve.petshow.controller.representation.MensagemRepresentation;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class MensagemRepresentationTest {
	@Test
	public void deve_ter_metodos_implementados() {
		// dado
		final Class<MensagemRepresentation> mensagem = MensagemRepresentation.class;

		// entao
		assertPojoMethodsFor(mensagem).areWellImplemented();
	}
}
