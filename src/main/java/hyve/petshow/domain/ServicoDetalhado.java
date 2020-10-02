package hyve.petshow.domain;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Data;

@Data
@Entity(name = "servico_detalhado")

public class ServicoDetalhado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal preco;
    @ManyToOne
    private Servico tipo;
    @OneToMany
    @JoinColumn(name = "fk_servico")
    private List<Avaliacao> avaliacoes;
    @ManyToOne
    @JoinColumn(name = "fk_prestador", referencedColumnName = "id")
    private Prestador prestador;
    
//    @Column
//    @ElementCollection(targetClass=TipoAnimalEstimacao.class)
//    private List<TipoAnimalEstimacao> animaisAceitos;
    
//    @ManyToOne 
//    @JoinColumn(name = "fk_conta", referencedColumnName = "id")
//    private Prestador prestador;    

    
}
