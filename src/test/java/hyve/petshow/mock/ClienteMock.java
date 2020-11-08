package hyve.petshow.mock;

import hyve.petshow.controller.converter.ClienteConverter;
import hyve.petshow.controller.representation.ClienteRepresentation;
import hyve.petshow.domain.Cliente;
import hyve.petshow.domain.Conta;
import hyve.petshow.domain.embeddables.Login;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static hyve.petshow.mock.AnimalEstimacaoMock.animalEstimacao;
import static hyve.petshow.mock.AuditoriaMock.auditoria;
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
