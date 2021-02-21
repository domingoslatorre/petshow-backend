package hyve.petshow.controller.representation;

import hyve.petshow.domain.Prestador;
import hyve.petshow.domain.enums.Cargo;
import lombok.Data;

@Data
public class VinculoEmpregaticioRepresentation {
	private Prestador prestador;
	private EmpresaRepresentation empresa;
	private Cargo cargo;
}
