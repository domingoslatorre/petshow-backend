package hyve.petshow.mock;

import java.math.BigDecimal;

import hyve.petshow.controller.representation.AdicionalRepresentation;
import hyve.petshow.domain.Adicional;

public class AdicionalMock {
	public static Adicional criaAdicional(Long idAdicional) {
		var adicional = new Adicional();
		adicional.setId(idAdicional);
		adicional.setNome(String.valueOf(idAdicional));
		adicional.setDescricao(String.valueOf(idAdicional));
		adicional.setPreco(BigDecimal.valueOf(idAdicional));
		adicional.setServicoDetalhadoId(1l);
		return adicional;
	}
	public static AdicionalRepresentation criaRepresentation(Long idAdicional) {
		var adicional = new AdicionalRepresentation();
		adicional.setId(idAdicional);
		adicional.setNome(String.valueOf(idAdicional));
		adicional.setDescricao(String.valueOf(idAdicional));
		adicional.setPreco(BigDecimal.valueOf(idAdicional));
		adicional.setServicoDetalhadoId(1l);
		return adicional;
	}
}

