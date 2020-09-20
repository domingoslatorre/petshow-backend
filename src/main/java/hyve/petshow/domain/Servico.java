package hyve.petshow.domain;

import javax.persistence.*;
import lombok.Data;
@Data
@Entity(name = "servico")

//fazer como enum? se simm,esse file tem que sair daqui e ir para "enums";
public class Servico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String nome;
    private String descricao;
}
