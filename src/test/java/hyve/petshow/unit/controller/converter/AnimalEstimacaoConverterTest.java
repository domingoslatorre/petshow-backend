package hyve.petshow.unit.controller.converter;

import hyve.petshow.controller.converter.AnimalEstimacaoConverter;
import hyve.petshow.mock.AnimalEstimacaoMock;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class AnimalEstimacaoConverterTest {
    @Autowired
    private AnimalEstimacaoConverter animalEstimacaoConverter;

    @Test
    public void toRepresentationTestCase01(){
        //dado
        var expected = AnimalEstimacaoMock.animalEstimacaoRepresentation();
        var animalEstimacao = AnimalEstimacaoMock.animalEstimacao();

        //quando
        var actual = animalEstimacaoConverter.toRepresentation(animalEstimacao);

        //entao
        assertEquals(expected, actual);
    }

    @Test
    public void toDomainTestCase01(){
        //dado
        var expected = AnimalEstimacaoMock.animalEstimacao();
        var animalEstimacaoRepresentation = AnimalEstimacaoMock.animalEstimacaoRepresentation();

        //quando
        var actual = animalEstimacaoConverter.toDomain(animalEstimacaoRepresentation);

        //entao
        assertEquals(expected, actual);
    }

    @Test
    public void toRepresentationListTestCase01(){
        //dado
        var expected = Arrays.asList(AnimalEstimacaoMock.animalEstimacaoRepresentation());
        var animaisEstimacao = Arrays.asList(AnimalEstimacaoMock.animalEstimacao());

        //quando
        var actual = animalEstimacaoConverter.toRepresentationList(animaisEstimacao);

        //entao
        assertEquals(expected, actual);
    }

}
