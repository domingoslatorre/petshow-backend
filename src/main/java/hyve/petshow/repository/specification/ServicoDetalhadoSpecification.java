package hyve.petshow.repository.specification;

import hyve.petshow.controller.filter.ServicoDetalhadoFilter;
import hyve.petshow.domain.ServicoDetalhado;
import hyve.petshow.domain.ServicoDetalhadoTipoAnimalEstimacao;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.JoinType;
import java.math.BigDecimal;

import static hyve.petshow.util.NullUtils.isNotNull;

@Slf4j
public class ServicoDetalhadoSpecification {
    public static Specification<ServicoDetalhado> geraSpecification(ServicoDetalhadoFilter filtragem) {
        return (Specification<ServicoDetalhado>) (root, query, builder) -> {
            var predicate = builder.and();
            var servico = root.join("tipo");
            var adicionais = root.join("adicionais", JoinType.LEFT);
            var tiposAnimaisAceitos = root.join("tiposAnimaisAceitos", JoinType.LEFT);

            var subQueryFiltrarPreco = query.subquery(BigDecimal.class);
            var subQueryTiposAnimaisAceitos = subQueryFiltrarPreco.from(ServicoDetalhadoTipoAnimalEstimacao.class);

            predicate = builder.and(predicate, builder.equal(servico.get("id"), filtragem.getTipoServicoId()));

            if(isNotNull(filtragem.getPossuiAdicionais()) && filtragem.getPossuiAdicionais()){
                predicate = builder.and(predicate, builder.isNotNull(adicionais.get("id")));
            }

            if(isNotNull(filtragem.getMediaAvaliacao())){
                predicate = builder.and(predicate,
                        builder.greaterThanOrEqualTo(root.get("mediaAvaliacao"), filtragem.getMediaAvaliacao()));
            }

            if(isNotNull(filtragem.getMenorPreco())){

                predicate = builder.and(predicate, builder.greaterThanOrEqualTo(
                        subQueryFiltrarPreco
                                .select((builder.min(subQueryTiposAnimaisAceitos.get("preco"))))
                                .where(builder.equal(subQueryTiposAnimaisAceitos.get("servicoDetalhado"), root.get("id"))),
                        filtragem.getMenorPreco()));
            }

            if(isNotNull(filtragem.getMaiorPreco())){
                predicate = builder.and(predicate, builder.lessThanOrEqualTo(
                        subQueryFiltrarPreco
                                .select((builder.min(subQueryTiposAnimaisAceitos.get("preco"))))
                                .where(builder.equal(subQueryTiposAnimaisAceitos.get("servicoDetalhado"), root.get("id"))),
                        filtragem.getMaiorPreco()));
            }

            if(isNotNull(filtragem.getOrdenacao())){
                var ordenacao = filtragem.getOrdenacao();

                if(ordenacao.equals("mediaAvaliacao")){
                    query.orderBy(builder.desc(root.get("mediaAvaliacao")));
                }
                if(ordenacao.equals("menorPreco")) {
                    query.orderBy(builder.asc(builder.min(tiposAnimaisAceitos.get("preco"))));
                }
                if(ordenacao.equals("maiorPreco")) {
                    query.orderBy(builder.desc(builder.min(tiposAnimaisAceitos.get("preco"))));
                }
            }

            query.groupBy(root.get("id"));

            return predicate;
        };
    }
}
