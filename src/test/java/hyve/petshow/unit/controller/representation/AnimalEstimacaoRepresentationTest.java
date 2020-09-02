package hyve.petshow.unit.controller.representation;

import hyve.petshow.controller.representation.AnimalEstimacaoRepresentation;
import org.junit.jupiter.api.Test;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

public class AnimalEstimacaoRepresentationTest {

    @Test
    public void animalEstimacaoRepresentationTestCase01(){
        //dado
        final Class<AnimalEstimacaoRepresentation> animalEstimacaoRepresentation = AnimalEstimacaoRepresentation.class;

        //entao
        assertPojoMethodsFor(animalEstimacaoRepresentation).areWellImplemented();
    }
}
