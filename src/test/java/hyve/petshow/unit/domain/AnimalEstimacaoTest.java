package hyve.petshow.unit.domain;

import hyve.petshow.domain.AnimalEstimacao;
import org.junit.jupiter.api.Test;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

public class AnimalEstimacaoTest {
    @Test
    public void animalEstimacaoTestCase01(){
        //dado
        final Class<AnimalEstimacao> animalEstimacao =
                AnimalEstimacao.class;

        //entao
        assertPojoMethodsFor(animalEstimacao).areWellImplemented();
    }
}
