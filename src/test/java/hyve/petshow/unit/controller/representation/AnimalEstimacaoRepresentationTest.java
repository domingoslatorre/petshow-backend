package hyve.petshow.unit.controller.representation;

import hyve.petshow.controller.representation.AnimalEstimacaoRepresentation;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class AnimalEstimacaoRepresentationTest {

    @Test
    public void deve_ter_os_metodos_implementados(){
        //dado
        final Class<AnimalEstimacaoRepresentation> animalEstimacaoRepresentation = AnimalEstimacaoRepresentation.class;

        //entao
        assertPojoMethodsFor(animalEstimacaoRepresentation).areWellImplemented();
    }
}
