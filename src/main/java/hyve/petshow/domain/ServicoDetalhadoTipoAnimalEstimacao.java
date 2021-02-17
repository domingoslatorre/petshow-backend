package hyve.petshow.domain;

import hyve.petshow.domain.embeddables.Auditoria;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@Entity(name = "servico_detalhado_tipo_animal_estimacao")
public class ServicoDetalhadoTipoAnimalEstimacao implements Serializable {
    private static final long serialVersionUID = 5866811628218235928L;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_servico_detalhado", referencedColumnName = "id", nullable = false)
    @ToString.Exclude
    private ServicoDetalhado servicoDetalhado;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fkTipoAnimalEstimacao", referencedColumnName = "id", nullable = false)
    private TipoAnimalEstimacao tipoAnimalEstimacao;

    private BigDecimal preco;
    @Embedded
    private Auditoria auditoria;

    public ServicoDetalhadoTipoAnimalEstimacao(ServicoDetalhado servicoDetalhado,
                                               TipoAnimalEstimacao tipoAnimalEstimacao,
                                               BigDecimal preco){
        this.servicoDetalhado = servicoDetalhado;
        this.tipoAnimalEstimacao = tipoAnimalEstimacao;
        this.preco = preco;
    }

    public ServicoDetalhadoTipoAnimalEstimacao(ServicoDetalhado servicoDetalhado,
                                               TipoAnimalEstimacao tipoAnimalEstimacao,
                                               BigDecimal preco,
                                               Auditoria auditoria){
        this.servicoDetalhado = servicoDetalhado;
        this.tipoAnimalEstimacao = tipoAnimalEstimacao;
        this.preco = preco;
        this.auditoria = auditoria;
    }
}