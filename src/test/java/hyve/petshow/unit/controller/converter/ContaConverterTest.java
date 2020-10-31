package hyve.petshow.unit.controller.converter;

import hyve.petshow.controller.converter.ContaConverter;
import hyve.petshow.controller.representation.ContaRepresentation;
import hyve.petshow.domain.Conta;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import static hyve.petshow.mock.ContaMock.conta;
import static hyve.petshow.mock.ContaMock.contaRepresentation;
import static org.junit.jupiter.api.Assertions.assertEquals;
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class ContaConverterTest {
	private ContaConverter converter = new ContaConverter();
	
	@Test
	public void deve_converter_para_conta_representation() {
		Conta conta = conta();
		ContaRepresentation representation = converter.toRepresentation(conta);
		assertEquals(conta.getId(), representation.getId());
	}
	
	@Test
	public void deve_converter_para_domain() {
		ContaRepresentation contaRepresentation = contaRepresentation();
		Conta domain = converter.toDomain(contaRepresentation);
		assertEquals(contaRepresentation.getId(), domain.getId());
	
	}
}
