package hyve.petshow.unit.exceptions;

import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import hyve.petshow.exceptions.NotFoundException;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class NotFoundExceptionTest {
	 @Test
	    public void deve_ter_metodos_implementados(){
	        //dado
	        final Class<NotFoundException> exception =
	        		NotFoundException.class;

	        //entao
	        assertPojoMethodsFor(exception).areWellImplemented();
	    }
}
