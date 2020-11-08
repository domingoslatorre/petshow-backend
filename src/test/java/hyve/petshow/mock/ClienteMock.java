package hyve.petshow.mock;

import hyve.petshow.controller.converter.ClienteConverter;
import hyve.petshow.controller.representation.ClienteRepresentation;
import hyve.petshow.domain.Cliente;

import java.util.Arrays;

import static hyve.petshow.mock.AnimalEstimacaoMock.animalEstimacao;
import static hyve.petshow.mock.ContaMock.contaCliente;

public class ClienteMock {
	public static Cliente cliente() {
		return new Cliente(contaCliente(), Arrays.asList(animalEstimacao()));
	}

	public static ClienteRepresentation clienteRepresentation() {
		var converter = new ClienteConverter();

		return converter.toRepresentation(cliente());
	}
}
