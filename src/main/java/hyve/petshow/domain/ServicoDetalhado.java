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
    
    @ManyToOne 
    @JoinColumn(name = "fk_conta", referencedColumnName = "id")
    private Prestador prestador;    

    @Column(name = "fk_tipo_animal_estimacao")
    private Integer animaisAceitos;
    
    //private List<Adicional> adicional = new ArrayList<Adicional>();
    
    //Criar a entidade empresa:
    //@ManyToOne
    //@JoinColumn(name = "fk_empresa", referencedColumnName = "id")
    //private Empresa empresa;    
}
