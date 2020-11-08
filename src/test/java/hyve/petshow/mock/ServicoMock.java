package hyve.petshow.mock;

import hyve.petshow.controller.converter.ServicoConverter;
import hyve.petshow.controller.representation.ServicoRepresentation;
import hyve.petshow.domain.Servico;

public class ServicoMock {
	public static Servico servico() {
		var servico = new Servico();

		servico.setId(1);
		servico.setNome("Banho e Tosa");

		return servico;
	}

	public static ServicoRepresentation servicoRepresentation(){
		var converter = new ServicoConverter();

		return converter.toRepresentation(servico());
	}
}
