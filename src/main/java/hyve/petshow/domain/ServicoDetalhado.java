package hyve.petshow.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
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
    private Servico tipo;
    @Column(name = "fk_tipo_animal_estimacao")
    private List<Integer> animaisAceitos;
    
//    @ManyToOne 
//    @JoinColumn(name = "fk_conta", referencedColumnName = "id")
//    private Prestador prestador;    

    
}
