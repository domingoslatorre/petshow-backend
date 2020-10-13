package hyve.petshow.unit.controller.converter;

import hyve.petshow.controller.converter.AnimalEstimacaoConverter;
import hyve.petshow.controller.representation.AnimalEstimacaoRepresentation;
import hyve.petshow.controller.representation.ClienteRepresentation;
import hyve.petshow.mock.AnimalEstimacaoMock;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@ActiveProfiles("test")
// TODO: Pensar em forma melhor de arrumar esses testes. Não setei direto no mock pois achei que talvez quebrasse coisas
public class AnimalEstimacaoConverterTest {
    @Autowired
    private AnimalEstimacaoConverter animalEstimacaoConverter;

    @Test
    public void deve_converter_para_representation(){
        //dado
        var expected = AnimalEstimacaoMock.animalEstimacaoRepresentation();
        expected.setId(1L);
        var animalEstimacao = AnimalEstimacaoMock.animalEstimacao();

        //quando
        var actual = animalEstimacaoConverter.toRepresentation(animalEstimacao);

        //entao
        assertEquals(expected, actual);
    }

    @Test
    public void deve_converter_para_domain(){
        //dado
        var expected = AnimalEstimacaoMock.animalEstimacao();
        var animalEstimacaoRepresentation = AnimalEstimacaoMock.animalEstimacaoRepresentation();
        animalEstimacaoRepresentation.setId(1L);
        //quando
        var actual = animalEstimacaoConverter.toDomain(animalEstimacaoRepresentation);

        //entao
        assertEquals(expected, actual);
    }

    @Test
    public void deve_converter_para_lista_de_representation(){
        //dado
        AnimalEstimacaoRepresentation animalEstimacaoRepresentation = AnimalEstimacaoMock.animalEstimacaoRepresentation();
		animalEstimacaoRepresentation.setId(1L);
        var expected = Arrays.asList(animalEstimacaoRepresentation);
        var animaisEstimacao = Arrays.asList(AnimalEstimacaoMock.animalEstimacao());

        //quando
        var actual = animalEstimacaoConverter.toRepresentationList(animaisEstimacao);

        //entao
        assertEquals(expected, actual);
    }

}
