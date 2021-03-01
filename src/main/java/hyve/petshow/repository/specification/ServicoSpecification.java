package hyve.petshow.repository.specification;

import javax.persistence.criteria.*;

import hyve.petshow.domain.ServicoDetalhado;
import org.springframework.data.jpa.domain.Specification;

import hyve.petshow.domain.Prestador;
import hyve.petshow.domain.Servico;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ServicoSpecification {

	public static Specification<Servico> geraSpecification(String cidade, String estado) {
		return (Specification<Servico>) (root, query, builder) -> {
			var predicate = builder.and();

			var subQueryServicoDetalhado = query.subquery(ServicoDetalhado.class);
			var subQueryPrestador = query.subquery(Prestador.class);

			var servicoDetalhado = subQueryServicoDetalhado.from(ServicoDetalhado.class);
			var prestador = subQueryPrestador.from(Prestador.class);
			var empresa = prestador.join("empresa", JoinType.LEFT);

			predicate = adicionaFiltroCidadeEstadoPorPrestador(builder, predicate, prestador,subQueryPrestador,
					servicoDetalhado, subQueryServicoDetalhado, root, empresa, cidade, estado);

			return predicate;
		};
	}

	private static Predicate adicionaFiltroCidadeEstadoPorPrestador(CriteriaBuilder builder, Predicate predicate,
																	Root<Prestador> prestador, Subquery<Prestador> queryPrestador,
																	Root<ServicoDetalhado> servicoDetalhado,
																	Subquery<ServicoDetalhado> queryServicoDetalhado,
																	Root<Servico> servico, Join<Object, Object> empresa,
																	String cidade, String estado){
		var predicateEmpresaPrestador = prestador.get("empresa").isNull();
		var predicateCidadePrestador = builder.equal(prestador.get("endereco").get("cidade"), cidade);
		var predicateEstadoPrestador = builder.equal(prestador.get("endereco").get("estado"), estado);
		var predicateFinalPrestador = builder.and(predicateEmpresaPrestador,
				predicateCidadePrestador, predicateEstadoPrestador);

		var predicateCidadeEmpresa= builder.equal(empresa.get("endereco").get("cidade"), cidade);
		var predicateEstadoEmpresa = builder.equal(empresa.get("endereco").get("estado"), estado);
		var predicateFinalEmpresa = builder.and(predicateCidadeEmpresa, predicateEstadoEmpresa);

		var prestadorEmpresaSubquery =
				queryPrestador
						.select(prestador)
						.where(builder.or(predicateFinalPrestador, predicateFinalEmpresa));

		var servicoDetalhadoSubquery =
				queryServicoDetalhado
						.select(servicoDetalhado.get("tipo").get("id"))
						.where(builder.in(servicoDetalhado.get("prestadorId")).value(prestadorEmpresaSubquery));

		predicate = builder.and(predicate, builder.in(servico.get("id")).value(servicoDetalhadoSubquery));

		return predicate;
	}
}
