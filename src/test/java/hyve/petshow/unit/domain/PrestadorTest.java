package hyve.petshow.unit.domain;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

import hyve.petshow.domain.Prestador;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;


@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class PrestadorTest {
    @Test
    public void deve_ter_metodos_implementados() {
        final Class<Prestador> contaPrestador =  Prestador.class;
        assertPojoMethodsFor(contaPrestador).areWellImplemented();
    }
}