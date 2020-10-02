package hyve.petshow.domain;

import javax.persistence.*;
import java.math.BigDecimal;

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
//    @Column
//    @ElementCollection(targetClass=TipoAnimalEstimacao.class)
//    private List<TipoAnimalEstimacao> animaisAceitos;
    
//    @ManyToOne 
//    @JoinColumn(name = "fk_conta", referencedColumnName = "id")
//    private Prestador prestador;    

    
}
