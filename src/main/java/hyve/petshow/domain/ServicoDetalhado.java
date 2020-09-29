package hyve.petshow.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import lombok.Data;
import hyve.petshow.domain.enums.TipoAnimalEstimacao;

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
