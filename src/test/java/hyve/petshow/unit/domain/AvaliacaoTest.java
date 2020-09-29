package hyve.petshow.unit.domain;
import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import hyve.petshow.domain.Avaliacao;
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class AvaliacaoTest {
	 @Test
	    public void deve_ter_metodos_implementados(){
	        //dado
		final Class<Avaliacao> avaliacao =
	        		Avaliacao.class;

	        //entao
	        assertPojoMethodsFor(avaliacao).areWellImplemented();
	    }
}
