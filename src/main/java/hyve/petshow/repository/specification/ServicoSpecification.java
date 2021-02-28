package hyve.petshow.repository.specification;

import javax.persistence.criteria.JoinType;

import org.springframework.data.jpa.domain.Specification;

import hyve.petshow.domain.Prestador;
import hyve.petshow.domain.Servico;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ServicoSpecification {

	public static Specification<Servico> geraSpecification(String cidade, String estado) {
		return (Specification<Servico>) (root, query, builder) -> {
			var predicate = builder.and();
			var joinServicos = root.join("servicosDetalhados", JoinType.LEFT);
			var queryPrestador = query.subquery(Prestador.class);
			var prestador = queryPrestador.from(Prestador.class);
			query.distinct(true);
			return builder.and(predicate,
					builder.equal(queryPrestador.select(prestador.get("id")).where(builder.and(builder.or(
							builder.and(builder.isNull(prestador.get("empresa")),
									builder.equal(prestador.get("endereco").get("cidade"), cidade)),
							builder.and(builder.isNotNull(prestador.get("empresa")),
									builder.equal(prestador.get("empresa").get("endereco").get("cidade"), cidade))),
//											builder.equal(prestador.get("endereco").get("estado"), estado),
							builder.or(
									builder.and(builder.isNull(prestador.get("empresa")),
											builder.equal(prestador.get("endereco").get("estado"), estado)),
									builder.and(builder.isNotNull(prestador.get("empresa")),
											builder.equal(prestador.get("empresa").get("endereco").get("estado"),
													estado))),
							builder.equal(prestador.get("id"), joinServicos.get("prestadorId")))),
							joinServicos.get("prestadorId")));
		};
	}

}
