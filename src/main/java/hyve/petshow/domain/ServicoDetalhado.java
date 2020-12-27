package hyve.petshow.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import hyve.petshow.domain.embeddables.Auditoria;
import lombok.Data;

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
<<<<<<< HEAD
    @OneToMany(mappedBy = "servicoDetalhado", cascade = CascadeType.ALL)
    private List<ServicoDetalhadoTipoAnimalEstimacao> tiposAnimaisAceitos = new ArrayList<>();
=======
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "fk_servico")
    private Set<Adicional> adicionais = Collections.emptySet();
>>>>>>> 205f6d1885fb0d324b30f2dcbf89d541b8164383
    
    public void addAvaliacao(Avaliacao avaliacao) {
    	avaliacao.setServicoAvaliadoId(this.getId());
        this.avaliacoes.add(avaliacao);
    }
}
