package hyve.petshow.mock;

import hyve.petshow.controller.representation.ServicoRepresentation;
import hyve.petshow.domain.Servico;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ServicoMock {
	public static Servico servico() {
		var servico = new Servico();

		servico.setId(1);
		servico.setNome("Banho e Tosa");

		return servico;
	}
}
