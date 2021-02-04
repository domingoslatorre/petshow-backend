package hyve.petshow.mock;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import hyve.petshow.controller.representation.AdicionalRepresentation;
import hyve.petshow.domain.Adicional;

import static hyve.petshow.util.AuditoriaUtils.geraAuditoriaInsercao;

public class AdicionalMock {
	public static Adicional criaAdicional(Long idAdicional) {
		var adicional = new Adicional();
		adicional.setId(idAdicional);
		adicional.setNome(String.valueOf(idAdicional));
		adicional.setDescricao(String.valueOf(idAdicional));
		adicional.setPreco(BigDecimal.valueOf(idAdicional));
		adicional.setServicoDetalhadoId(1l);
		adicional.setAuditoria(geraAuditoriaInsercao(Optional.of(1L)));
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

