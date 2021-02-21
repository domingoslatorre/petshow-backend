package hyve.petshow.controller.converter;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import hyve.petshow.controller.representation.VinculoEmpregaticioRepresentation;
import hyve.petshow.domain.VinculoEmpregaticio;

@Component
public class VinculoEmpregaticioConverter implements Converter<VinculoEmpregaticio, VinculoEmpregaticioRepresentation> {
	@Autowired
	private EmpresaConverter empresaConverter = new EmpresaConverter();

	@Override
	public VinculoEmpregaticioRepresentation toRepresentation(VinculoEmpregaticio domain) {
		return Optional.ofNullable(domain).map(vinculo -> {
			var representation = new VinculoEmpregaticioRepresentation();
			representation.setCargo(vinculo.getCargo());
			representation.setEmpresa(empresaConverter.toRepresentation(vinculo.getEmpresa()));
			return representation;
		}).orElse(new VinculoEmpregaticioRepresentation());
	}

	@Override
	public VinculoEmpregaticio toDomain(VinculoEmpregaticioRepresentation representation) {
		return Optional.ofNullable(representation).map(vinculo -> {
			var domain = new VinculoEmpregaticio();
			domain.setCargo(vinculo.getCargo());
			domain.setEmpresa(empresaConverter.toDomain(vinculo.getEmpresa()));
			return domain;
		}).orElse(new VinculoEmpregaticio());
	}

}
