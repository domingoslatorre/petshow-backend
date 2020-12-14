package hyve.petshow.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import hyve.petshow.domain.embeddables.Auditoria;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@Entity(name = "servico_detalhado")
public class ServicoDetalhado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal preco;
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
    
    public void addAvaliacao(Avaliacao avaliacao) {
    	avaliacao.setServicoAvaliadoId(this.getId());
        this.avaliacoes.add(avaliacao);
    }
}
