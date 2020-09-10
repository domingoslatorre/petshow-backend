package hyve.petshow.unit.controller.representation;

import hyve.petshow.controller.representation.AnimalEstimacaoResponseRepresentation;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class AnimalEstimacaoResponseRepresentationTest {
    @Test
    public void deve_ter_metodos_implementados(){
        //dado
        final Class<AnimalEstimacaoResponseRepresentation> animalEstimacaoResponseRepresentation =
                AnimalEstimacaoResponseRepresentation.class;

        //entao
        assertPojoMethodsFor(animalEstimacaoResponseRepresentation).areWellImplemented();
    }
}
