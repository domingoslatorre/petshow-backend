package hyve.petshow.repository.nativeQueryRepository;

import hyve.petshow.domain.ServicoDetalhadoTipoAnimalEstimacao;
import hyve.petshow.exceptions.BusinessException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;
import java.sql.SQLIntegrityConstraintViolationException;

@Repository
public class ServicoDetalhadoTipoAnimalEstimacaoNativeQueryRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void adicionar(ServicoDetalhadoTipoAnimalEstimacao servicoDetalhadoTipoAnimalEstimacao) throws PersistenceException {
        try {
            entityManager.createNativeQuery(
                    "INSERT INTO servico_detalhado_tipo_animal_estimacao (" +
                                "fk_servico_detalhado," +
                                "fk_tipo_animal_estimacao," +
                                "preco," +
                                "data_criacao," +
                                "data_atualizacao," +
                                "usuario_criacao," +
                                "flag_ativo) " +
                             "VALUES (?,?,?,?,?,?,?)")
                    .setParameter(1, servicoDetalhadoTipoAnimalEstimacao.getServicoDetalhado().getId())
                    .setParameter(2, servicoDetalhadoTipoAnimalEstimacao.getTipoAnimalEstimacao().getId())
                    .setParameter(3, servicoDetalhadoTipoAnimalEstimacao.getPreco())
                    .setParameter(4, servicoDetalhadoTipoAnimalEstimacao.getAuditoria().getDataCriacao())
                    .setParameter(5, servicoDetalhadoTipoAnimalEstimacao.getAuditoria().getDataAtualizacao())
                    .setParameter(6, servicoDetalhadoTipoAnimalEstimacao.getAuditoria().getUsuarioCriacao())
                    .setParameter(7, servicoDetalhadoTipoAnimalEstimacao.getAuditoria().getFlagAtivo())
                    .executeUpdate();
        } catch (PersistenceException e) {
            throw e;
        }
    }
}
