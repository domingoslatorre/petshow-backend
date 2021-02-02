package hyve.petshow.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_servico_detalhado")
    private List<Adicional> adicionais = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "fk_servico")
    private Servico tipo;
    @Column(name = "fk_conta")
    private Long prestadorId;
    @OneToMany(mappedBy = "servicoDetalhado", cascade = CascadeType.ALL,  fetch = FetchType.LAZY)
    private List<ServicoDetalhadoTipoAnimalEstimacao> tiposAnimaisAceitos = new ArrayList<>();
}
