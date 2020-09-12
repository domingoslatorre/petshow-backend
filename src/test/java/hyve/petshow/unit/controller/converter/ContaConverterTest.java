package hyve.petshow.unit.controller.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

import hyve.petshow.controller.converter.ContaConverter;
import hyve.petshow.controller.representation.ContaRepresentation;
import hyve.petshow.domain.Conta;
import hyve.petshow.domain.Endereco;
import hyve.petshow.domain.Login;
import hyve.petshow.domain.enums.TipoConta;
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
public class ContaConverterTest {
	private ContaConverter converter = new ContaConverter();
	
	@Test
	public void deve_converter_para_conta_representation() {
		Conta conta = new Conta(1l, "Teste", "Teste", "44444444444", "1129292828", TipoConta.CLIENTE, "", new Endereco(), new Login());
		ContaRepresentation representation = converter.toRepresentation(conta);
		assertEquals(conta.getId(), representation.getId());
	}
	
	@Test
	public void deve_converter_para_domain() {
		ContaRepresentation contaRepresentation = new ContaRepresentation(1l, "Teste", "Teste", "44444444444", "1129292828", TipoConta.CLIENTE.getTipo(), "", new Endereco(), new Login());
		Conta domain = converter.toDomain(contaRepresentation);
		assertEquals(contaRepresentation.getId(), domain.getId());
	
	}
}
