package hyve.petshow.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import hyve.petshow.domain.embeddables.Auditoria;
import lombok.Data;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Data
@Entity(name = "animal_estimacao")
public class AnimalEstimacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String foto;
    private String porte;
    private String pelagem;
    @Embedded
    private Auditoria auditoria;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_tipo_animal_estimacao")
    private TipoAnimalEstimacao tipo;
    @Column(name = "fk_conta")
    private Long donoId;
}
