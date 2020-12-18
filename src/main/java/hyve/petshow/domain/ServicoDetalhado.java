package hyve.petshow.domain;

import hyve.petshow.domain.embeddables.Auditoria;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity(name = "servico_detalhado")
public class ServicoDetalhado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Float mediaAvaliacao;
    @Embedded
    private Auditoria auditoria;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "fk_servico_detalhado")
    private List<Avaliacao> avaliacoes = new ArrayList<Avaliacao>();
    @ManyToOne
    @JoinColumn(name = "fk_servico")
    private Servico tipo;
    @Column(name = "fk_conta")
    private Long prestadorId;
    @OneToMany(mappedBy = "servicoDetalhado", cascade = CascadeType.ALL)
    private List<ServicoDetalhadoTipoAnimalEstimacao> tiposAnimaisAceitos = new ArrayList<>();
    
    public void addAvaliacao(Avaliacao avaliacao) {
    	avaliacao.setServicoAvaliadoId(this.getId());
        this.avaliacoes.add(avaliacao);
    }
}
