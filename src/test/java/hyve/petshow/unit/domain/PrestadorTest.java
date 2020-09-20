package hyve.petshow.unit.domain;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import hyve.petshow.domain.Conta;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class PrestadorTest {
    @Test
    public void deve_ter_metodos_implementados() {
        final Class<Conta> conta =  Conta.class;
        assertPojoMethodsFor(conta).areWellImplemented();
    }
}