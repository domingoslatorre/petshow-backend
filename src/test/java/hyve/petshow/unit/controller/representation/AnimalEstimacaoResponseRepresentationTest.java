package hyve.petshow.unit.controller.representation;

import hyve.petshow.controller.representation.AnimalEstimacaoResponseRepresentation;
import org.junit.jupiter.api.Test;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

public class AnimalEstimacaoResponseRepresentationTest {
    @Test
    public void animalEstimacaoRepresentationTestCase01(){
        //dado
        final Class<AnimalEstimacaoResponseRepresentation> animalEstimacaoResponseRepresentation =
                AnimalEstimacaoResponseRepresentation.class;

        //entao
        assertPojoMethodsFor(animalEstimacaoResponseRepresentation).areWellImplemented();
    }
}
