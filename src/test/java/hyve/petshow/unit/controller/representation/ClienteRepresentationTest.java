package hyve.petshow.unit.controller.representation;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import hyve.petshow.controller.representation.ClienteRepresentation;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class ClienteRepresentationTest {

	@Test
	public void deve_ter_metodos_implementados() {
		// dado
		final Class<ClienteRepresentation> clienteRepresentation = ClienteRepresentation.class;

		// entao
		assertPojoMethodsFor(clienteRepresentation).areWellImplemented();
	}
}
